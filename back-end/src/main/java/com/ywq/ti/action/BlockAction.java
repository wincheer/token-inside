package com.ywq.ti.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import com.ywq.ti.dao.BcBlockMapper;
import com.ywq.ti.socket.SpringWebSocketHandler;

@RestController
public class BlockAction {
	
	@Autowired
	private BcBlockMapper dao;
	@Autowired
	private SpringWebSocketHandler myHandler;
	
	@RequestMapping(value = "/addFoo", method = RequestMethod.GET)
	public void addFoo() {
		dao.toString();
	}

	@RequestMapping(value = "/addFoo1", method = RequestMethod.GET)
	public void addFoo1() {
		myHandler.sendMessageToUser("18602010502",new TextMessage("这是来自遥远的第三方发送的消息"));
	}

}
