package com.ywq.ti.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;

import com.ywq.ti.common.EthUtils;
import com.ywq.ti.common.Web3jConfig;
import com.ywq.ti.entity.BcErc20Token;
import com.ywq.ti.service.BlockService;

@RestController
public class BlockAction {
	
	private static final Logger log = LoggerFactory.getLogger(BlockAction.class);
	@Value("${web3j.client-address}")
	private String WEB3_CLIENT_URL;
	
	@Autowired
	private BlockService service;
	
	@RequestMapping(value = "/selectTokenInfo", method = RequestMethod.POST)
	public Map<String,Object> selectTokenInfo(@RequestBody BcErc20Token token) {
		
		log.info("查询Token信息，tokenAddress = " + token.getTokenAddress());
		Map<String,Object> result = new HashMap<String, Object>();

		BcErc20Token _token = service.selectToken(token);
		BigDecimal _supply = EthUtils.fixBigIntegerValue(new BigInteger(_token.getTotalSupply()), BigInteger.valueOf(_token.getDecimals()));
		_token.setTotalSupply(_supply.toString());
		
		List<Map<String, Object>> _tokenTxList = service.selectTokenTxListRaw(token);
		for(Map<String, Object> tx:_tokenTxList){
			BigDecimal _value = EthUtils.fixBigIntegerValue(new BigInteger(tx.get("value").toString()), BigInteger.valueOf(_token.getDecimals()));
			tx.put("value", _value.toString());
		}
		
		result.put("token", _token);
		result.put("txList", _tokenTxList);
		
		return result;
	}
	
	@RequestMapping(value = "/selectWalletInfo", method = RequestMethod.POST)
	public Map<String,Object> selectWalletInfo(@RequestBody Map<String,String> wallet) throws IOException {
		
		String walletAddress = wallet.get("wallet_address");
		log.info("查询Wallet信息，Address = " + walletAddress); 
		Map<String,Object> result = new HashMap<String, Object>();

		Web3jConfig web3jConfig = new Web3jConfig();
		Web3j web3j = Web3j.build(web3jConfig.buildService(WEB3_CLIENT_URL));
		Long maxBlockBumber = (web3j.ethBlockNumber().send().getBlockNumber()).longValue();
		DefaultBlockParameter blockParam = new DefaultBlockParameterNumber(maxBlockBumber);
		BigInteger balanceOfEth = web3j.ethGetBalance(walletAddress, blockParam).send().getBalance();
		BigDecimal _balanceOfEth = EthUtils.fixBigIntegerValue(balanceOfEth, BigInteger.valueOf(18L));
		
		List<Map<String, Object>> _txList = service.selectWalletTxListRaw(walletAddress);
		for(Map<String, Object> tx:_txList){
			BigDecimal _value = EthUtils.fixBigIntegerValue(new BigInteger(tx.get("value").toString()), BigInteger.valueOf(18L));
			tx.put("value", _value.toString());
		}
		
		List<Map<String, Object>> _tokenTxList = service.selectWalletTokenTxListRaw(walletAddress);
		for(Map<String, Object> tx:_tokenTxList){
			BigDecimal _value = EthUtils.fixBigIntegerValue(new BigInteger(tx.get("value").toString()), BigInteger.valueOf(Long.valueOf(tx.get("decimals").toString())));
			tx.put("value", _value.toString());
		}
		
		result.put("balanceOfEth", _balanceOfEth.toString());
		result.put("txList", _txList);
		result.put("tokenTxList", _tokenTxList);
		
		return result;
	}
	
	@RequestMapping(value = "/selectGraphInfo", method = RequestMethod.POST)
	public Map<String,Object> selectGraphInfo(@RequestBody Map<String,String> wallet) throws IOException {
		
		String walletAddress = wallet.get("wallet_address");
		Map<String,Object> result = new HashMap<String, Object>();
		
		Set<String> nodeSet = new TreeSet<String>(); //节点
		List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();//边

		List<Map<String, Object>> _txList = service.selectWalletTxListRaw(walletAddress);
		for(Map<String, Object> tx:_txList){
			BigDecimal _value = EthUtils.fixBigIntegerValue(new BigInteger(tx.get("value").toString()), BigInteger.valueOf(18L));
			tx.put("value", _value.toString());
			
			nodeSet.add(tx.get("from").toString());
			nodeSet.add(tx.get("to").toString());
			
			Map<String,Object> edge = new HashMap<String,Object>();
			edge.put("source", tx.get("from").toString());
			edge.put("target", tx.get("to").toString());
			edge.put("value", _value.toString());
			edge.put("token", tx.get("symbol").toString());
			edgeList.add(edge);
		}
		
		List<Map<String, Object>> _tokenTxList = service.selectWalletTokenTxListRaw(walletAddress);
		for(Map<String, Object> tx:_tokenTxList){
			BigDecimal _value = EthUtils.fixBigIntegerValue(new BigInteger(tx.get("value").toString()), BigInteger.valueOf(Long.valueOf(tx.get("decimals").toString())));
			tx.put("value", _value.toString());
			
			nodeSet.add(tx.get("from").toString());
			nodeSet.add(tx.get("to").toString());
			
			Map<String,Object> edge = new HashMap<String,Object>();
			edge.put("source", tx.get("from").toString());
			edge.put("target", tx.get("to").toString());
			edge.put("value", _value.toString());
			edge.put("token", tx.get("symbol").toString());
			edgeList.add(edge);
		}
		
		List<Map<String,String>> nodeList = new ArrayList<>();
		for(String wallet_address:nodeSet){
			Map<String,String> node = new HashMap<>();
			node.put("name", wallet_address);
			nodeList.add(node);
		}
		
		result.put("nodes", nodeList);
		result.put("edges", edgeList);
		
		return result;
	}

}
