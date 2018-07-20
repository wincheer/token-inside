package com.ywq.ti.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ywq.ti.common.TxType;
import com.ywq.ti.dao.BcErc20TokenMapper;
import com.ywq.ti.dao.BcErc20TransactionMapper;
import com.ywq.ti.dao.BcTransactionMapper;
import com.ywq.ti.entity.BcErc20Token;
import com.ywq.ti.entity.BcErc20Transaction;
import com.ywq.ti.entity.BcTransaction;

@Service
@Transactional
public class BlockService {
	
	//private static final Logger log = LoggerFactory.getLogger(BlockService.class);
	
	@Autowired
	private BcTransactionMapper txDao;
	@Autowired
	private BcErc20TokenMapper tokenDao;
	@Autowired
	private BcErc20TransactionMapper tokenTxDao;


	public BcErc20Token selectToken(BcErc20Token token) {
		return tokenDao.selectToken(token.getTokenAddress());
	}

	public List<BcErc20Transaction> selectTokenTxList(BcErc20Token token) {
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("tokenAddress", token.getTokenAddress());
		params.put("start", 0);
		params.put("pageSize", 100);
		
		return tokenTxDao.selectErc20TransactionListPage(params);
	}
	
	//
	public List<Map<String,Object>> selectTokenTxListRaw(BcErc20Token token) {
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("tokenAddress", token.getTokenAddress());
		params.put("start", 0);
		params.put("pageSize", 100);
		
		return tokenTxDao.selectErc20TransactionListPageRaw(params);
	}

	public List<BcTransaction> selectWalletTxList(String walletAddress) {
		
		Map<String,Object> params = new HashMap<String, Object>();		
		params.put("start", 0);
		params.put("pageSize", 100);
		
		params.put("sendAddress", walletAddress);
		List<BcTransaction> fromTxList = txDao.selectTransactionListPage(params);
		
		params.remove("sendAddress");
		params.put("receiveAddress", walletAddress);
		List<BcTransaction> toTxList = txDao.selectTransactionListPage(params);
		
		fromTxList.addAll(toTxList);
		
		return fromTxList;
	}
	
	//
	public List<Map<String,Object>> selectWalletTxListRaw(String walletAddress) {
		
		Map<String,Object> params = new HashMap<String, Object>();		
		params.put("start", 0);
		params.put("pageSize", 100);
		
		params.put("txType", TxType.ETHER_TRANSFER); //Ether交易
		
		params.put("sendAddress", walletAddress);
		List<Map<String,Object>> fromTxList = txDao.selectTransactionListPageRaw(params);
		
		params.remove("sendAddress");
		params.put("receiveAddress", walletAddress);
		List<Map<String,Object>> toTxList = txDao.selectTransactionListPageRaw(params);
		
		fromTxList.addAll(toTxList);
		
		return fromTxList;
	}

	public List<BcErc20Transaction> selectWalletTokenTxList(String walletAddress) {
		
		Map<String,Object> params = new HashMap<String, Object>();		
		params.put("start", 0);
		params.put("pageSize", 100);
		
		params.put("sendAddress", walletAddress);
		List<BcErc20Transaction> fromTxList = tokenTxDao.selectErc20TransactionListPage(params);
		
		params.remove("sendAddress");
		params.put("receiveAddress", walletAddress);
		List<BcErc20Transaction> toTxList = tokenTxDao.selectErc20TransactionListPage(params);
		
		fromTxList.addAll(toTxList);
		
		return fromTxList;
	}
	
	//
	public List<Map<String,Object>> selectWalletTokenTxListRaw(String walletAddress) {
		
		Map<String,Object> params = new HashMap<String, Object>();		
		params.put("start", 0);
		params.put("pageSize", 100);
		
		params.put("sendAddress", walletAddress);
		List<Map<String,Object>> fromTxList = tokenTxDao.selectErc20TransactionListPageRaw(params);
		
		params.remove("sendAddress");
		params.put("receiveAddress", walletAddress);
		List<Map<String,Object>> toTxList = tokenTxDao.selectErc20TransactionListPageRaw(params);
		
		fromTxList.addAll(toTxList);
		
		return fromTxList;
	}


}
