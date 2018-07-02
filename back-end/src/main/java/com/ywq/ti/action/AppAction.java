package com.ywq.ti.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ywq.ti.entity.AppUser;
import com.ywq.ti.service.AppService;

@RestController
public class AppAction {
	
	private static final Logger log = LoggerFactory.getLogger(AppAction.class);
	
	@Autowired
	private AppService service;
	
	@RequestMapping(value = "/selectAppUser", method = RequestMethod.POST)
	public AppUser selectAppUser(@RequestBody AppUser user) {
		log.debug("user_id:" + user.getId());
		AppUser _user = service.selectAppUser(user);
		return _user;
	}
	

}
