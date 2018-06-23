package com.ywq.ti.entity;

public class BcContract {

    private String contractAddress; //合约地址，长度 42
    private String txHash; //所属交易
    private String blockHash; //所属区块
    private long blockNumber;
    
    
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public String getTxHash() {
		return txHash;
	}
	public void setTxHash(String txHash) {
		this.txHash = txHash;
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
    


}