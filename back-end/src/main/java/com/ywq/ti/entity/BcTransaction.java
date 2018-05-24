package com.ywq.ti.entity;

import java.math.BigInteger;
import java.util.Date;

public class BcTransaction {

	private String hash; //交易地址 长度66
    private String blockHash; //块地址, 长度66
    private Long blockNumber; //块编号
    private BigInteger gas;
    private BigInteger gasPrice;
    private String sendAddress; //发送方，合约地址长度42
    private String receiveAddress;//接收方
    private BigInteger value; //交易金额
    private String data; //附加数据
    private Date timestamp;
    private String txType; //交易类型
    
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getBlockHash() {
		return blockHash;
	}
	
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	
	public Long getBlockNumber() {
		return blockNumber;
	}
	
	public void setBlockNumber(Long blockNumber) {
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
	
	public BigInteger getValue() {
		return value;
	}
	
	public void setValue(BigInteger value) {
		this.value = value;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getTxType() {
		return txType;
	}
	
	public void setTxType(String txType) {
		this.txType = txType;
	}

 
}