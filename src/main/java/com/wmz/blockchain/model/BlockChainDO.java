package com.wmz.blockchain.model;

import com.wmz.blockchain.utils.HashUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wmz on 2018/3/17.
 * 区块链
 * 当区块链实例化后,我们需要构造一个创世区块(即当前链中的第一个区块),并加上工作量证明
 * 每个区块都需要工作量证明,俗称挖矿
 *
 * @author wmz
 */
public class BlockChainDO implements Serializable {

    private static final long serialVersionUID = 5838094876025430121L;

    /**
     * 区块链
     */
    private List<BlockDO> chain;

    /**
     * 存储当前交易信息列表
     */
    private List<TransactionDO> currentTransactions;

    /**
     * 存储网络中其它节点的集合
     */
    private Set<String> nodes;


    private volatile static BlockChainDO blockChainDO = null;


    private BlockChainDO() {
        chain = new ArrayList<>();
        currentTransactions = new ArrayList<>();
        nodes = new HashSet<>();
        //创建创世区块
        initBlockChain(100, "0");
    }

    public static BlockChainDO getInstance() {
        if (blockChainDO == null) {
            synchronized (BlockChainDO.class) {
                if (blockChainDO == null) {
                    blockChainDO = new BlockChainDO();
                }
            }
        }
        return blockChainDO;
    }

    private void initBlockChain(long proof, String previousHash) {
        BlockDO blockDO = new BlockDO();
        blockDO.setIndex(getChain().size() + 1);
        blockDO.setTimestamp(System.currentTimeMillis());
        blockDO.setTransactions(getCurrentTransactions());
        blockDO.setProof(proof);
        blockDO.setPreviousHash(previousHash != null ? previousHash : HashUtil.hashBlock(getChain().get(getChain().size() - 1)));
        //重置当前交易信息列表
        setCurrentTransactions(new ArrayList<>());
        getChain().add(blockDO);
    }

    public List<BlockDO> getChain() {
        return chain;
    }

    public void setChain(List<BlockDO> chain) {
        this.chain = chain;
    }

    public List<TransactionDO> getCurrentTransactions() {
        return currentTransactions;
    }

    public void setCurrentTransactions(List<TransactionDO> currentTransactions) {
        this.currentTransactions = currentTransactions;
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public void setNodes(Set<String> nodes) {
        this.nodes = nodes;
    }

    public static BlockChainDO getBlockChainDO() {
        return blockChainDO;
    }

    public static void setBlockChainDO(BlockChainDO blockChainDO) {
        BlockChainDO.blockChainDO = blockChainDO;
    }

    @Override
    public String toString() {
        return "BlockChainDO{" +
                "chain=" + chain +
                ", currentTransactions=" + currentTransactions +
                ", nodes=" + nodes +
                '}';
    }
}
