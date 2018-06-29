package com.ywq.ti;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.ywq.ti.common.EthUtils;

public class TestIt {
	private static final Logger log = LoggerFactory.getLogger(TestIt.class);

	public static void main(String[] args) throws Exception {
		
		Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/gKGM2GtuAn4ubcw1pRBp"));
		
		String tokenAddress = "0xf230b790e05390fc8295f4d3f60332c93bed42e2";
		String wallet = "0x9ea145606c4f694c28cf2a5a021f526a6764e1ba";
		BigInteger b = EthUtils.balanceOf(web3j, tokenAddress, wallet);
		BigDecimal basicValue = EthUtils.fixBigIntegerValue(b, BigInteger.valueOf(6L));
		
		System.out.println(basicValue);
//		
//		String txHash = "0x2d4af80549da948029712f59518635318df187d195e573835f55f92f8eeddcfe";		
//		Transaction tx = web3j.ethGetTransactionByHash(txHash).send().getResult();
//		DefaultBlockParameterNumber blockParam = new DefaultBlockParameterNumber(tx.getBlockNumber());
//		String code = web3j.ethGetCode(tx.getTo(), blockParam).send().getResult();
//		log.info("code = " + code);

		
//		String token_address = "0xd654bdd32fc99471455e86c2e7f7d7b6437e9179";
//		ERC20Token token = EthUtils.getTokenInfo(web3j, token_address);
//		log.info(token.getTokenName()+"("+token.getSymbol()+"):" + token.getTotalSupply() +","+ token.getDecimals());
		
	}
}
