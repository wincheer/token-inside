package com.ywq.ti.common;

import java.math.BigInteger;

public class ERC20TokenData {
	
	private String toAddress; // 接收地址
	private BigInteger value; // 交易金额
	
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public BigInteger getValue() {
		return value;
	}
	public void setValue(BigInteger value) {
		this.value = value;
	}
}
