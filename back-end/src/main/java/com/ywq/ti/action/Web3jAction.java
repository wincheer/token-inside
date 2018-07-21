package com.ywq.ti.action;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;

import com.ywq.ti.common.EthUtils;
import com.ywq.ti.common.Web3jConfig;

@RestController
public class Web3jAction {
	
	private static final Logger log = LoggerFactory.getLogger(Web3jAction.class);
	
	@Value("${web3j.client-address}")
	private String WEB3_CLIENT_URL;
	
	@RequestMapping(value = "/queryBalanceOf", method = RequestMethod.POST)
	public Map<String,Object> queryBalanceOf(@RequestBody Map<String,String> wallet) throws Exception {
		
		String walletAddress = wallet.get("wallet_address");
		String tokenAddress = wallet.get("token_address");
		String decimals = wallet.get("decimals");

		Map<String,Object> result = new HashMap<String, Object>();

		Web3jConfig web3jConfig = new Web3jConfig();
		Web3j web3j = Web3j.build(web3jConfig.buildService(WEB3_CLIENT_URL));
		
		BigInteger balance = EthUtils.balanceOf(web3j, tokenAddress,walletAddress);
		BigDecimal _balance = EthUtils.fixBigIntegerValue(balance, new BigInteger(decimals));
		
		result.put("balanceOfEth", _balance.toString());
	
		return result;
	}
}
