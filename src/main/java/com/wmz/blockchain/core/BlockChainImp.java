package com.wmz.blockchain.core;

import com.wmz.blockchain.model.BlockChainDO;
import com.wmz.blockchain.model.BlockDO;
import com.wmz.blockchain.model.TransactionDO;
import com.wmz.blockchain.utils.HashUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wmz on 2018/3/17.
 *
 * @author wmz
 */
@Component
public class BlockChainImp implements IBlockChainManager {

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


}
