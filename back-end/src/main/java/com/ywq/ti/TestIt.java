package com.ywq.ti;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.ywq.ti.common.EthUtils;

public class TestIt {
	private static final Logger log = LoggerFactory.getLogger(TestIt.class);

	public static void main(String[] args) throws Exception {
		
		Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/gKGM2GtuAn4ubcw1pRBp"));
		
		String walletAddress = "0x0975ca9f986eee35f5cbba2d672ad9bc8d2a0844";
		
		Long maxBlockBumber = (web3j.ethBlockNumber().send().getBlockNumber()).longValue();
		DefaultBlockParameter blockParam = new DefaultBlockParameterNumber(maxBlockBumber);
		BigInteger balanceOfEth = web3j.ethGetBalance(walletAddress, blockParam).send().getBalance();
		
		System.out.println(EthUtils.fixBigIntegerValue(balanceOfEth, BigInteger.valueOf(18L)));
		
	}
}
