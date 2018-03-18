package com.wmz.blockchain.core;

import com.alibaba.fastjson.JSONObject;
import com.wmz.blockchain.model.BlockChainDO;
import com.wmz.blockchain.model.BlockDO;
import com.wmz.blockchain.model.TransactionDO;
import com.wmz.blockchain.utils.HashUtil;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by wmz on 2018/3/17.
 *
 * @author wmz
 */
@Component
public class BlockChainImp implements IBlockChainManager {

    private static final Logger logger = LoggerFactory.getLogger(BlockChainImp.class);

    @Override
    public BlockDO lastBlock(BlockChainDO blockChain) {
        List<BlockDO> chain = blockChain.getChain();
        return chain.get(chain.size() - 1);
    }

    @Override
    public long generateNewTransaction(BlockChainDO blockChainDO, TransactionDO transactionDO) {
        List<TransactionDO> currentTransactions = blockChainDO.getCurrentTransactions();
        currentTransactions.add(transactionDO);
        return lastBlock(blockChainDO).getIndex() + 1;
    }

    @Override
    public BlockDO newBlock(BlockChainDO blockChainDO, long proof, String previousHash) {
        BlockDO blockDO = new BlockDO();
        blockDO.setIndex(blockChainDO.getChain().size() + 1);
        blockDO.setTimestamp(System.currentTimeMillis());
        blockDO.setTransactions(blockChainDO.getCurrentTransactions());
        blockDO.setProof(proof);
        //如果没有传递上一个区块的hash就计算出区块链的最后一个区块的hash
        blockDO.setPreviousHash(previousHash != null ? previousHash : HashUtil.hashBlock(blockChainDO.getChain().get(blockChainDO.getChain().size() - 1)));
        //重置当前交易信息列表
        blockChainDO.setCurrentTransactions(new ArrayList<>());
        blockChainDO.getChain().add(blockDO);
        return blockDO;
    }

    @Override
    public void registerNode(BlockChainDO blockChainDO, String address) throws MalformedURLException {
        URL url = new URL(address);
        String node = url.getHost() + ":" + (url.getPort() == -1 ? url.getDefaultPort() : url.getPort());
        blockChainDO.getNodes().add(node);
    }

    @Override
    public boolean validChain(BlockChainDO blockChainDO) {
        List<BlockDO> chains = blockChainDO.getChain();
        BlockDO lastBlock = chains.get(0);
        //从第一个区块开始
        int currentIndex = 1;
        while (currentIndex < chains.size()) {
            BlockDO currentBlock = chains.get(currentIndex);
            //检查当前区块previous_hash值,是否是前一个区块的hash
            if (!currentBlock.getPreviousHash().equals(HashUtil.hashBlock(lastBlock))) {
                return false;
            }
            lastBlock = currentBlock;
            currentIndex++;
        }
        return true;
    }

    @Override
    public BlockChainDO resolveConflicts(BlockChainDO blockChainDO) throws IOException {
        Set<String> neighbours = blockChainDO.getNodes();
        BlockChainDO newChain = null;

        //寻找最长的区块链
        long maxLength = blockChainDO.getChain().size();
        if (neighbours != null && neighbours.size() > 0) {
            //获取并验证网络中的所有节点的区块链
            for (String node : neighbours) {
                String url = "http://" + node + "/block-chain/get-chain.json";
                String result = Request.Get(url).execute().returnContent().asString();
                JSONObject jsonObject = JSONObject.parseObject(result);
                String chainData = jsonObject.getString("data");
                BlockChainDO currentBlockChain = JSONObject.parseObject(chainData, BlockChainDO.class);
                long currentChainLength = currentBlockChain.getChain().size();
                if (currentChainLength > maxLength && validChain(currentBlockChain)) {
                    maxLength = currentChainLength;
                    newChain = currentBlockChain;
                    logger.info("达到共识,当前最长有效链是:" + JSONObject.toJSON(newChain));
                }
            }
        }
        if (newChain != null) {
            blockChainDO = newChain;
            logger.info("解决冲突......");
        }
        return blockChainDO;
    }
}
