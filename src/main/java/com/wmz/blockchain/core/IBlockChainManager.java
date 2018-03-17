package com.wmz.blockchain.core;

import com.wmz.blockchain.model.BlockChainDO;
import com.wmz.blockchain.model.BlockDO;
import com.wmz.blockchain.model.TransactionDO;

/**
 * Created by wmz on 2018/3/17.
 * 区块链功能
 *
 * @author wmz
 */
public interface IBlockChainManager {

    /**
     * 获取最后一个区块
     *
     * @param blockChain
     * @return
     */
    BlockDO lastBlock(BlockChainDO blockChain);

    /**
     * 生成一个新的交易信息,信息将加入到下一个待挖的区块中
     * 其实就是最后一个区块的索引 + 1 是下一个区块的索引
     *
     * @param transactionDO
     * @param blockChainDO
     * @return 存储该交易事务的块的索引
     */
    long generateNewTransaction(BlockChainDO blockChainDO, TransactionDO transactionDO);


    /**
     * 生成一个新的区块
     *
     * @param proof 工作量证明
     * @return
     */
    BlockDO newBlock(BlockChainDO blockChainDO, long proof, String previousHash);
    
}
