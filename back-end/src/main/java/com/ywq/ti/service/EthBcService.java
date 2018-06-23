package com.ywq.ti.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.ywq.ti.common.ERC20Token;
import com.ywq.ti.common.ERC20TokenData;
import com.ywq.ti.common.EthUtils;
import com.ywq.ti.common.TxType;
import com.ywq.ti.dao.BcBlockMapper;
import com.ywq.ti.dao.BcContractMapper;
import com.ywq.ti.dao.BcCurrentBlockMapper;
import com.ywq.ti.dao.BcErc20TokenMapper;
import com.ywq.ti.dao.BcErc20TransactionMapper;
import com.ywq.ti.dao.BcTransactionMapper;
import com.ywq.ti.entity.BcBlock;
import com.ywq.ti.entity.BcContract;
import com.ywq.ti.entity.BcCurrentBlock;
import com.ywq.ti.entity.BcErc20Token;
import com.ywq.ti.entity.BcErc20Transaction;
import com.ywq.ti.entity.BcTransaction;

@Service
@Transactional
public class EthBcService {

	private static final Logger log = LoggerFactory.getLogger(EthBcService.class);

	@Autowired
	private BcCurrentBlockMapper currentBlockDao;
	@Autowired
	private BcBlockMapper blockDao;
	@Autowired
	private BcTransactionMapper txDao;
	@Autowired
	private BcErc20TokenMapper tokenDao;
	@Autowired
	private BcContractMapper contractDao;
	@Autowired
	private BcErc20TransactionMapper tokenTxDao;

	public BcCurrentBlock currentBlockNumber(String BcType) {
		BcCurrentBlock currentBlock = currentBlockDao.selectCurrentBlock(TxType.ETH);
		return currentBlock;
	}

	public Set<String> allTokens() {
		Set<String> tokenSet = new TreeSet<String>();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", 0);
		param.put("pageSize", Integer.MAX_VALUE);
		List<BcErc20Token> tokenList = tokenDao.selectTokenListPage(param);
		for (BcErc20Token token : tokenList) {
			tokenSet.add(token.getTokenAddress());
		}

		return tokenSet;
	}

	public Set<String> allContracts() {
		Set<String> contractSet = new TreeSet<String>();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", 0);
		param.put("pageSize", Integer.MAX_VALUE);
		List<BcContract> contractList = contractDao.selectContractListPage(param);
		for (BcContract contract : contractList) {
			contractSet.add(contract.getContractAddress());
		}

		return contractSet;
	}

	/**
	 * 处理当前区块
	 * @param currentBlock
	 * @throws IOException 
	 */
	public void handleBlock(Web3j web3j, Block currentBlock, Set<String> tokenSet, Set<String> contractSet)
			throws IOException {
		BcBlock block = buildBlock(currentBlock);
		List<BcTransaction> txList = buildTxList(currentBlock.getTransactions());
		List<BcErc20Token> tokenList = new ArrayList<BcErc20Token>();
		List<BcContract> contractList = new ArrayList<BcContract>();
		List<BcErc20Transaction> tokenTxList = new ArrayList<BcErc20Transaction>();

		for (BcTransaction tx : txList) {
			tx.setTimestamp(block.getTimestamp());
			String to = tx.getReceiveAddress();
			if (to == null) {
				// 交易：创建智能合约
				tx.setTxType(TxType.CREATE_CONTRACT);
				tx.setReceiveAddress("");
				TransactionReceipt txr = web3j.ethGetTransactionReceipt(tx.getHash()).send().getResult();
				ERC20Token token = EthUtils.getTokenInfo(web3j, txr.getContractAddress());
				if (token.isValid()) {
					// 添加Token记录
					BcErc20Token bcToken = buildToken(token, tx);
					tokenList.add(bcToken);
					log.info("交易类型：创建 Token，Token地址  - " + txr.getContractAddress());
					tokenSet.add(txr.getContractAddress());
				} else {
					// 添加智能合约记录
					BcContract contract = buildContract(txr.getContractAddress(), tx);
					contractList.add(contract);
					log.info("交易类型：创建合约，合约地址  - " + txr.getContractAddress());
					contractSet.add(txr.getContractAddress());
				}
			} else {
				if (tokenSet.contains(to) && tx.getData().startsWith("0xa9059cbb")
						&& tx.getData().trim().length() == 138) {
					tx.setTxType(TxType.TOKEN_TRANSFER);
					log.info("交易类型：Token交易 - " + to); // TODO 处理钱包交易订阅和Token交易订阅
					BcErc20Transaction tokenTx = buildTokenTx(tx);
					tokenTxList.add(tokenTx);
				} // token交易
				else if (contractSet.contains(to)) {
					tx.setTxType(TxType.EXE_CONTRACT);
					log.info("交易类型：智能合约调用 - " + to);
				} // 合约调用
				else {
					tx.setTxType(TxType.ETHER_TRANSFER);
					log.info("交易类型：以太币交易 "); // TODO 处理钱包交易订阅
				} // ETH交易
			}
		}
		// 持久化block、txList、token、tokenTxList
		blockDao.insertBlock(block);
		if (!txList.isEmpty())
			txDao.insertTransactionBatch(txList);
		if (!tokenList.isEmpty())
			tokenDao.insertTokenBatch(tokenList);
		if (!contractList.isEmpty())
			contractDao.insertContractBatch(contractList);
		if (!tokenTxList.isEmpty())
			tokenTxDao.insertErc20TransactionBatch(tokenTxList);
		// 更新待处理block_number
		BcCurrentBlock _currentBlock = new BcCurrentBlock();
		_currentBlock.setBcType(TxType.ETH);
		_currentBlock.setBlockNumber(currentBlock.getNumber().longValue() + 1);
		currentBlockDao.updateCurrentBlock(_currentBlock);
	}

	/**
	 * 将原生Block转换为本地持久化对象Block
	 * @param _block
	 * @return
	 */
	private BcBlock buildBlock(Block _block) {

		BcBlock block = new BcBlock();

		block.setHash(_block.getHash());
		block.setNumber(_block.getNumber().longValue());
		block.setParentHash(_block.getParentHash());
		block.setCoinbase(_block.getMiner());
		block.setDifficulty(_block.getDifficulty().longValue());
		block.setGasLimit(_block.getGasLimit());
		block.setGasUsed(_block.getGasUsed());
		block.setTimestamp(new Date(_block.getTimestamp().longValue() * 1000));

		return block;
	}

	/**
	 * 将原生Transaction转换为本地持久化对象交易Transaction
	 * @param block
	 * @param transactions
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<BcTransaction> buildTxList(List<TransactionResult> transactions) {

		List<BcTransaction> txList = new ArrayList<BcTransaction>();

		for (TransactionResult tx : transactions) {
			Transaction _tx = (Transaction) tx.get();

			BcTransaction bcTx = new BcTransaction();
			bcTx.setHash(_tx.getHash());
			bcTx.setBlockHash(_tx.getBlockHash());
			bcTx.setBlockNumber(_tx.getBlockNumber().longValue());
			bcTx.setGas(_tx.getGas());
			bcTx.setGasPrice(_tx.getGasPrice());
			bcTx.setSendAddress(_tx.getFrom());
			bcTx.setReceiveAddress(_tx.getTo());
			bcTx.setValue(_tx.getValue());
			bcTx.setData(_tx.getInput());

			txList.add(bcTx);
		}

		return txList;
	}

	/**
	 * 将区块链交易转换为代币交易
	 * @param tx
	 * @return
	 */
	private BcErc20Transaction buildTokenTx(BcTransaction tx) {

		ERC20TokenData txData = EthUtils.DecodeErc20Data(tx.getData());

		BcErc20Transaction erc20Tx = new BcErc20Transaction();

		erc20Tx.setTxHash(tx.getHash());
		erc20Tx.setTokenAddress(tx.getReceiveAddress());
		erc20Tx.setBlockHash(tx.getBlockHash());
		erc20Tx.setBlockNumber(tx.getBlockNumber());
		erc20Tx.setGas(tx.getGas());
		erc20Tx.setGasPrice(tx.getGasPrice());
		erc20Tx.setSendAddress(tx.getSendAddress());
		erc20Tx.setReceiveAddress(txData.getToAddress());
		erc20Tx.setValue(txData.getValue());
		erc20Tx.setTimestamp(tx.getTimestamp());

		return erc20Tx;
	}

	/**
	 * 生成Token持久化对象
	 * @param token
	 * @return
	 */
	private BcErc20Token buildToken(ERC20Token token, BcTransaction tx) {

		BcErc20Token erc20Token = new BcErc20Token();

		erc20Token.setTokenAddress(token.getTokenAddress());
		erc20Token.setTokenName(token.getTokenName());
		erc20Token.setSymbol(token.getSymbol());
		erc20Token.setTotalSupply(token.getTotalSupply().toString());
		erc20Token.setDecimals(token.getDecimals().longValue());
		erc20Token.setHolders(0L);
		erc20Token.setTransfers(0L);

		erc20Token.setTxHash(tx.getHash());
		erc20Token.setBlockHash(tx.getBlockHash());
		erc20Token.setBlockNumber(tx.getBlockNumber());

		return erc20Token;
	}

	/**
	 * 生成Token持久化对象
	 * @param token
	 * @return
	 */
	private BcContract buildContract(String contractAddress, BcTransaction tx) {

		BcContract contract = new BcContract();

		contract.setContractAddress(contractAddress);

		contract.setTxHash(tx.getHash());
		contract.setBlockHash(tx.getBlockHash());
		contract.setBlockNumber(tx.getBlockNumber());

		return contract;
	}

}
