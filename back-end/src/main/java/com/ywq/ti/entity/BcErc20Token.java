package com.ywq.ti.entity;

import java.math.BigInteger;

public class BcErc20Token {

    private String tokenAddress; //Token地址，长度 42
    private String tokenName; //名称
    private String symbol; // 标志
    private BigInteger totalSupply; //发行总量
    private Long decimals; //小数点
    private Long holders; //持有人总数量
    private Long transfers; //交易总数量
    
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
	public BigInteger getTotalSupply() {
		return totalSupply;
	}
	public void setTotalSupply(BigInteger totalSupply) {
		this.totalSupply = totalSupply;
	}
	public Long getDecimals() {
		return decimals;
	}
	public void setDecimals(Long decimals) {
		this.decimals = decimals;
	}
	public Long getHolders() {
		return holders;
	}
	public void setHolders(Long holders) {
		this.holders = holders;
	}
	public Long getTransfers() {
		return transfers;
	}
	public void setTransfers(Long transfers) {
		this.transfers = transfers;
	}


}