package com.ywq.ti.servie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.ywq.ti.common.ERC20Token;
import com.ywq.ti.common.TxType;
import com.ywq.ti.common.Utils;
import com.ywq.ti.dao.BcBlockMapper;
import com.ywq.ti.dao.BcCurrentBlockMapper;
import com.ywq.ti.entity.BcBlock;
import com.ywq.ti.entity.BcErc20Token;
import com.ywq.ti.entity.BcTransaction;

@Service
@Transactional
public class EthBcSrvice {

	@Value("${web3j.client-address}")
	private String WEB3_CLIENT_URL;

	@Autowired
	private BcCurrentBlockMapper currentBlockDao;
	@Autowired
	private BcBlockMapper blockDao;

	public long currentBlockNumber(String BcType) {
		return currentBlockDao.selectCurrentBlock("ETH").getBlockNumber();
	}

	/**
	 * 处理当前区块
	 * @param currentBlock
	 * @throws IOException 
	 */
	public void handleBlock(Block currentBlock) throws IOException {
		// TODO 处理区块：插入block，批量解析tx,更新已处理完block_number值
		// TODO 发送监听事件
		Web3j web3j = Web3j.build(new HttpService(WEB3_CLIENT_URL));

		BcBlock block = buildBlock(currentBlock);
		List<BcTransaction> txList = buildTxList(currentBlock.getTransactions());
		for (BcTransaction tx : txList) {
			tx.setTimestamp(block.getTimestamp());
			if (tx.getReceiveAddress() == null) {
				// 这是在创建智能合约
				tx.setTxType(TxType.CREATE_CONTRACT);
				TransactionReceipt txr = web3j.ethGetTransactionReceipt(tx.getHash()).send().getResult();
				ERC20Token token = Utils.getTokenInfo(web3j, txr.getContractAddress());
				if (token.isValid()) {
					// 添加Token记录
					BcErc20Token bcToken = new BcErc20Token(); // 填充字段，然后保存
				}
			} else {
				DefaultBlockParameterNumber blockParam = new DefaultBlockParameterNumber(tx.getBlockNumber());
				String code = web3j.ethGetCode(tx.getReceiveAddress(), blockParam).send().getResult();
				if(code==null || code.trim().equals("")){
					//以太币转账
				} else{
					//智能合约交易->检索token表->存在的话解析交易数据
				}
			}
		}

		web3j.shutdown();
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

}
