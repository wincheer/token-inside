package com.ywq.ti.dao;

import java.util.List;
import java.util.Map;

import com.ywq.ti.entity.BcTransaction;

public interface BcTransactionMapper {
	
	BcTransaction selectTransaction(String hash);

	List<BcTransaction> selectTransactionList(BcTransaction transaction);

	Long totalTransactions(Map<String,Object> param);
	
	List<BcTransaction> selectTransactionListPage(Map<String, Object> map);

	void insertTransaction(BcTransaction transaction);

	void insertTransactionBatch(List<BcTransaction> transactionList);
	
}