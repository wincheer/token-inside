package com.ywq.ti.entity;

import java.math.BigInteger;
import java.util.Date;

public class BcErc20Transaction {

    private String txHash; //交易hash
    private String tokenAddress;  //代币地址
    private String blockHash; //块hash
    private long blockNumber; //块编号
    private BigInteger gas; 
    private BigInteger gasPrice;
    private String sendAddress; //发送方
    private String receiveAddress; //接收方
    private String value; //Token 交易金额 - 有变态大的数据超出数据库存储范围
    private Date timestamp; //实际完成时间
    
	public String getTxHash() {
		return txHash;
	}
	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}
	public String getTokenAddress() {
		return tokenAddress;
	}
	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	}
	public String getBlockHash() {
		return blockHash;
	}
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	public long getBlockNumber() {
		return blockNumber;
	}
	public void setBlockNumber(long blockNumber) {
		this.blockNumber = blockNumber;
	}
	public BigInteger getGas() {
		return gas;
	}
	public void setGas(BigInteger gas) {
		this.gas = gas;
	}
	public BigInteger getGasPrice() {
		return gasPrice;
	}
	public void setGasPrice(BigInteger gasPrice) {
		this.gasPrice = gasPrice;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


}