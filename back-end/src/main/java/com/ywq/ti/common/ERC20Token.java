package com.ywq.ti.common;

import java.math.BigInteger;

public class ERC20Token {

	private String tokenName; //代币名称
	private String symbol; //标志
	private BigInteger totalSupply; //总发行量
	private BigInteger decimals; //小数点

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

	public BigInteger getDecimals() {
		return decimals;
	}

	public void setDecimals(BigInteger decimals) {
		this.decimals = decimals;
	}

	@Override
	public String toString() {

		String info = tokenName + "(" + symbol + "),totalSupply = " + totalSupply + " ,decimals = " + decimals;
		return info;
	}

	public boolean isValid() {
		//if (tokenName.trim().equals(TxType.INVALID_ERC20TOKEN))
		if(totalSupply.equals(BigInteger.valueOf(0)))
			return false;
		else
			return true;
	}

}
