package com.wmz.blockchain.model;

import java.io.Serializable;

/**
 * Created by wmz on 2018/3/17.
 * 交易
 *
 * @author wmz
 */
public class TransactionDO implements Serializable {

    private static final long serialVersionUID = 1528073053546384098L;

    /**
     * 发送方地址
     */
    private String sender;

    /**
     * 接受方的地址
     */
    private String receiver;

    /**
     * 交易量
     */
    private Double amount;

    public TransactionDO() {
        super();
    }

    public TransactionDO(String sender, String receiver, Double amount) {
        super();
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionDO{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", amount=" + amount +
                '}';
    }
}
