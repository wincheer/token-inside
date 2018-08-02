package com.ywq.ti.common;

import java.math.BigInteger;

public class ERC20TokenData {
	
	private String fromAddress; // 发送地址 - transfer为空，transferFrom为发送地址
	private String toAddress; // 接收地址
	private BigInteger value; // 交易金额
		
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
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
