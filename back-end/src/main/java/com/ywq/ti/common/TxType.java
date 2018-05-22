package com.ywq.ti.common;

/**
 * <b>版权信息 :</b> 2018，杨文清<br/>
 * <b>功能描述 :</b> 常量表<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年5月22日 下午6:27:32 | 创建
 */
public class TxType {
	
	// 无效的 Token名
	public final static String INVALID_ERC20TOKEN = "invalid_erc20_token";
	// 区块链交易类型
	public final static String ETHER_TRANSFER = "1"; // 以太币交易
	public final static String EXE_CONTRACT = "2"; // 合约调用
	public final static String CREATE_CONTRACT = "3"; // 创建合约
}
