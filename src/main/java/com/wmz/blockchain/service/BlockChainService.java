package com.wmz.blockchain.service;

import com.alibaba.fastjson.JSONObject;
import com.wmz.blockchain.core.BlockChainImp;
import com.wmz.blockchain.model.BlockChainDO;
import com.wmz.blockchain.model.BlockDO;
import com.wmz.blockchain.model.BlockResult;
import com.wmz.blockchain.model.TransactionDO;
import com.wmz.blockchain.utils.ProofOfWorkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by wmz on 2018/3/17.
 * 区块链业务
 *
 * @author wmz
 */
@Service
public class BlockChainService {

    @Autowired
    private BlockChainImp blockChainImp;

    private static final Logger logger = LoggerFactory.getLogger(BlockChainService.class);

    /**
     * 挖矿
     *
     * @return
     */
    public BlockResult<BlockDO> mine(String uuid) throws IOException {
        long startTime = System.currentTimeMillis();
        BlockChainDO blockChainDO = BlockChainDO.getInstance();
        blockChainDO = blockChainImp.resolveConflicts(blockChainDO);
        BlockDO lastBlock = blockChainImp.lastBlock(blockChainDO);
        long lastProof = lastBlock.getProof();
        long newProof = ProofOfWorkUtil.proofOfWork(lastProof);

        //给挖矿的节点提供奖励,发送者为0,表明是新挖出的币
        TransactionDO transactionDO = new TransactionDO("0", uuid, 1d);
        blockChainImp.generateNewTransaction(blockChainDO, transactionDO);

        //构建新的区块
        BlockDO newBlock = blockChainImp.newBlock(blockChainDO, newProof, null);
        long endTime = System.currentTimeMillis();
        return new BlockResult<>(newBlock, "new block forged,挖矿用时:" + (endTime - startTime) + "ms");
    }

    /**
     * 处理交易
     *
     * @param req
     * @return
     */
    public BlockResult newTransaction(TransactionDO req) throws IOException {
        if (StringUtils.isEmpty(req.getSender())
                || StringUtils.isEmpty(req.getReceiver()) ||
                req.getAmount() == null) {
            return new BlockResult<>("9999", "交易出错,参数有误");
        }
        long startTime = System.currentTimeMillis();
        BlockChainDO blockChainDO = BlockChainDO.getInstance();
        //处理之前需要找到最长且有效的链
        blockChainDO = blockChainImp.resolveConflicts(blockChainDO);
        long nextIndex = blockChainImp.generateNewTransaction(blockChainDO, req);
        logger.info("nextIndex is:" + nextIndex);
        long endTime = System.currentTimeMillis();
        return new BlockResult<>(nextIndex, "Transaction will be added to Block:" + nextIndex + ",交易用时:" + (endTime - startTime) + "ms");
    }

    /**
     * 整个区块链的信息
     *
     * @return
     */
    public BlockResult<BlockChainDO> getChain() throws IOException {
        BlockChainDO blockChainDO = BlockChainDO.getInstance();
        logger.info("chain info is:" + JSONObject.toJSONString(blockChainDO));
        return new BlockResult<>(blockChainDO, "get chain success");
    }

    /**
     * 注册节点
     *
     * @param nodes
     * @return
     * @throws MalformedURLException
     */
    public BlockResult register(String nodes) throws MalformedURLException {
        BlockChainDO blockChainDO = BlockChainDO.getInstance();
        blockChainImp.registerNode(blockChainDO, nodes);
        return new BlockResult<>(blockChainDO.getNodes(), "New nodes have been added");
    }

}
