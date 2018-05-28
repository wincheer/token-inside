package com.ywq.ti.entity;

public class BcErc20Token {

    private String tokenAddress; //Token地址，长度 42
    private String tokenName; //名称
    private String symbol; // 标志
    private String totalSupply; //发行总量 - 偶尔有变态的代币，发行量大到离谱。所以改用字符串来保存
    private long decimals; //小数点
    private long holders; //持有人总数量
    private long transfers; //交易总数量
    
	public String getTokenAddress() {
		return tokenAddress;
	}
	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	}
	public String getTokenName() {
		return tokenName;
	}
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getTotalSupply() {
		return totalSupply;
	}
	public void setTotalSupply(String totalSupply) {
		this.totalSupply = totalSupply;
	}
	public long getDecimals() {
		return decimals;
	}
	public void setDecimals(long decimals) {
		this.decimals = decimals;
	}
	public long getHolders() {
		return holders;
	}
	public void setHolders(long holders) {
		this.holders = holders;
	}
	public long getTransfers() {
		return transfers;
	}
	public void setTransfers(long transfers) {
		this.transfers = transfers;
	}


}