package com.ywq.ti;

import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.ywq.ti.common.ERC20Token;
import com.ywq.ti.common.Utils;

public class Temp {

	public static void main(String[] args) throws Exception {

		Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/gKGM2GtuAn4ubcw1pRBp"));
		
//		String tokenAddress = "0x05f4a42e251f2d52b8ed15e9fedaacfcef1fad27"; // 代币地址
//		//String tokenAddress = "0xa70fda378fa295ab08722a534f894b8794445d10"; //非代币地址
//		ERC20Token token = Utils.getTokenInfo(web3j, tokenAddress);
//		
//		// 指定账户的该Token余额
//		if(token.isValid()){
//			System.out.println(token);
//			String owner = "0xe09a628aa519dea86c18f93b47430ee94fc9ac80";
//			BigInteger balance = Utils.balanceOf(web3j, tokenAddress, owner);
//			System.out.println(owner + " balance Of "+ token.getSymbol() + " = " + balance);
//		}
		
		// 交易地址
		String txHash = "0xb649f4a1585900faa4d44178e0c6994cce83e628a0ed31d9f699e2801d283916" ; // ICON (ICX)识别错误
		Transaction tx = web3j.ethGetTransactionByHash(txHash).send().getResult();
		System.out.printf("From: %s ,To: %s ,Value: \n",tx.getFrom(),tx.getTo(),tx.getValue());
		TransactionReceipt txr = web3j.ethGetTransactionReceipt(txHash).send().getResult();
		ERC20Token token = Utils.getTokenInfo(web3j, txr.getContractAddress());
		System.out.println(txr.getBlockHash().length());
		
		web3j.shutdown();
	}

}
