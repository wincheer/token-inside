package com.ywq.ti.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ywq.ti.dao.AppUserMapper;
import com.ywq.ti.entity.AppUser;

@Service
@Transactional
public class AppService {
	
	private static final Logger log = LoggerFactory.getLogger(AppService.class);
	
	@Autowired
	private AppUserMapper userDao;

	public AppUser selectAppUser(AppUser user) {
		
		AppUser _user = userDao.selectUser(user.getId());
		log.debug("AppUser = " + _user);
		if(_user == null){
			_user = new AppUser();
			long timestamp = System.currentTimeMillis();
			_user.setUnionId("temp_"+timestamp);
			_user.setNickName("temp_"+timestamp);
			_user.setBonus(0);
			userDao.insertUser(_user);
		}
		return _user;
	}
}
