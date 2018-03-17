package com.wmz.blockchain.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wmz on 2018/3/17.
 *
 * @author wmz
 */
public class BlockDO implements Serializable {

    private static final long serialVersionUID = -2158963369268193427L;

    /**
     * 区块索引
     */
    private Integer index;

    /**
     * 区块时间戳
     */
    private Long timestamp;

    /**
     * 区块包含的交易信息
     */
    private List<TransactionDO> transactions;

    /**
     * 工作量证明
     */
    private Long proof;

    /**
     * 上一个区块的hash值
     */
    private String previousHash;


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<TransactionDO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDO> transactions) {
        this.transactions = transactions;
    }

    public Long getProof() {
        return proof;
    }

    public void setProof(Long proof) {
        this.proof = proof;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    @Override
    public String toString() {
        return "BlockDO{" +
                "index=" + index +
                ", timestamp=" + timestamp +
                ", transactions=" + transactions +
                ", proof='" + proof + '\'' +
                ", previousHash='" + previousHash + '\'' +
                '}';
    }
}
