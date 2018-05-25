package com.ywq.ti.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ywq.ti.dao.BcCurrentBlockMapper;
import com.ywq.ti.entity.BcCurrentBlock;

@RestController
public class BlockAction {
	
	@Autowired
	private BcCurrentBlockMapper dao;
	
	@RequestMapping(value = "/currentBlock", method = RequestMethod.GET)
	public BcCurrentBlock currentBlock() {

		BcCurrentBlock b = dao.selectCurrentBlock("ETH");
		return b;
	}

}
