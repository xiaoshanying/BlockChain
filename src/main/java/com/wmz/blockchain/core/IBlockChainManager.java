package com.wmz.blockchain.core;

import com.wmz.blockchain.model.BlockChainDO;
import com.wmz.blockchain.model.BlockDO;
import com.wmz.blockchain.model.TransactionDO;

import java.io.IOException;
import java.net.MalformedURLException;

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
     * @param blockChainDO 区块链
     * @param proof        工作量证明
     * @param previousHash 上一个区块的hash
     * @return
     */
    BlockDO newBlock(BlockChainDO blockChainDO, long proof, String previousHash);

    /**
     * 注册节点
     *
     * @param blockChainDO 区块链
     * @param address      节点地址
     * @throws MalformedURLException
     */
    void registerNode(BlockChainDO blockChainDO, String address) throws MalformedURLException;

    /**
     * 验证区块链的有效性
     * 验证根据:遍历每一个区块,如果该区块的previous_hash和hash(上一个区块)相等,则认为该区块有效.依次遍历到最后
     *
     * @param blockChainDO
     * @return
     */
    boolean validChain(BlockChainDO blockChainDO);

    /**
     * 解决冲突
     * 使用网络中最长的链,遍历所有的邻居节点,并检查链的有效性
     * 如果发现有效更长的链,就替换掉自己的链
     * <p>
     * 链被取代返回true
     * 否则返回false
     *
     * @param blockChainDO
     * @return 最新的链
     */
    BlockChainDO resolveConflicts(BlockChainDO blockChainDO) throws IOException;

}
