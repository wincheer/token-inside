package com.ywq.ti.common;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.web3j.protocol.Web3j;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.exceptions.ContractCallException;

/**
 * <b>版权信息 :</b> 2018，杨文清<br/>
 * <b>功能描述 :</b> 区块链交易解析工具类<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年5月22日 下午6:23:34 | 创建
 */
public class EthUtils {

	private static Logger log = Logger.getLogger(EthUtils.class);

	/**
	 * 解析区块链交易（ERC20交易）的交易信息
	 * @param data
	 * @return
	 */
	public static ERC20TokenData DecodeErc20Data(String data) {

		ERC20TokenData erc20Data = new ERC20TokenData();
		if (data.substring(0, 10).equals("0xa9059cbb")) {
			// transfer|to|value
			erc20Data.setToAddress("0x" + data.substring(10, 10 + 64).replaceAll("^0+(?!$)", ""));
			erc20Data.setValue(new BigInteger(data.substring(10 + 64, 10 + 64 + 64).replaceAll("^0+(?!$)", ""), 16));
		} else if (data.substring(0, 10).equals("0x23b872dd")) {
			// transferFrom|from|to|value
			erc20Data.setFromAddress("0x" + data.substring(10, 10 + 64).replaceAll("^0+(?!$)", ""));
			erc20Data.setToAddress("0x" + data.substring(10 + 64, 10 + 64 + 64).replaceAll("^0+(?!$)", ""));
			erc20Data.setValue(new BigInteger(data.substring(10 + 64 + 64, 10 + 64 + 64 + 64).replaceAll("^0+(?!$)", ""), 16));
		}

		return erc20Data;
	}

	/**
	 * 获取ERC20代币信息
	 * @param web3j
	 * @param tokenAddress
	 * @return
	 */
	public static ERC20Token getTokenInfo(Web3j web3j, String tokenAddress) {

		ERC20Token erc20Token = new ERC20Token();

		HumanStandardToken token = load(web3j, tokenAddress);
		// Token信息
		String tokenName = TxType.INVALID_ERC20TOKEN; // 无效的token标志
		String symbol = null;
		BigInteger totalSupply = BigInteger.valueOf(0);
		BigInteger decimals = BigInteger.valueOf(18);
		try {
			totalSupply = token.totalSupply().send(); // 一定会有值，否则无效代币
			decimals = token.decimals().send();
			symbol = token.symbol().send();
			tokenName = token.name().send();
		} catch (ContractCallException cce) {
			log.warn("当前地址不是有效的 ERC20 Token 合约：" + cce.getMessage() + " - " + tokenAddress);
		} catch (Exception e) {
			log.error("载入合约异常：" + e.getMessage() + " - " + tokenAddress);
		}

		erc20Token.setTokenAddress(tokenAddress);
		erc20Token.setTokenName(tokenName);
		erc20Token.setSymbol(symbol);
		erc20Token.setTotalSupply(totalSupply);
		erc20Token.setDecimals(decimals);

		return erc20Token;
	}

	/**
	 * 查询指定代币的钱包余额
	 * @param web3j
	 * @param tokenAddress 代币合约地址
	 * @param owner 钱包地址
	 * @return
	 * @throws Exception
	 */
	public static BigInteger balanceOf(Web3j web3j, String tokenAddress, String owner) throws Exception {

		HumanStandardToken token = load(web3j, tokenAddress);
		BigInteger balance = token.balanceOf(owner).send();

		return balance;
	}

	/**
	 * 载入通用的ERC20合约
	 * @param web3j
	 * @param contractAddress 合约地址
	 * @return
	 */
	public static HumanStandardToken load(Web3j web3j, String contractAddress) {

		// String fromAddress = "0xa70fda378fa295ab08722a534f894b8794445d10"; //
		// 任意有效钱包地址
		String fromAddress = "0x0000000000000000000000000000000000000000";

		BigInteger GAS_PRICE = BigInteger.valueOf(250000L);
		BigInteger GAS_LIMIT = BigInteger.valueOf(12000000000L);

		TransactionManager transactionManager = new ClientTransactionManager(web3j, fromAddress);

		return HumanStandardToken.load(contractAddress, web3j, transactionManager, GAS_PRICE, GAS_LIMIT);

	}

	/**
	 * 将基本单位金额转换为标准单位金额。
	 * 比如小数点3位，交易金额1000基本单位，实际是1.000个标准单位
	 * @param unitValue
	 * @param decimals
	 * @return
	 */
	public static BigDecimal fixBigIntegerValue(BigInteger unitValue, BigInteger decimals) {

		BigInteger bigInstance = BigInteger.valueOf(10);
		BigInteger powValue = bigInstance.pow(decimals.intValue());

		BigInteger result[] = unitValue.divideAndRemainder(powValue);
		BigDecimal _result = new BigDecimal(result[0] + "." + result[1]);

		return _result;
	}
}
