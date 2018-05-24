package com.ywq.ti.dao;

import java.util.List;
import java.util.Map;

import com.ywq.ti.entity.BcErc20Transaction;

public interface BcErc20TransactionMapper {
	
	BcErc20Transaction selectErc20Transaction(String hash);

	List<BcErc20Transaction> selectErc20TransactionList(BcErc20Transaction transaction);

	Long totalErc20Transactions(Map<String,Object> param);
	
	List<BcErc20Transaction> selectErc20TransactionListPage(Map<String, Object> map);

	void insertErc20Transaction(BcErc20Transaction transaction);

	void insertErc20TransactionBatch(List<BcErc20Transaction> transactionList);
	
}