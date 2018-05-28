package com.ywq.ti.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ywq.ti.dao.BcBlockMapper;

@RestController
public class BlockAction {
	
	@Autowired
	private BcBlockMapper dao;
	
	@RequestMapping(value = "/addFoo", method = RequestMethod.GET)
	public void addFoo() {
		dao.toString();
	}

}
