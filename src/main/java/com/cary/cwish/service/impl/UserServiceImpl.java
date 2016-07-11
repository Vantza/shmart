package com.cary.cwish.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cary.cwish.dao.UserDao;
import com.cary.cwish.pojo.User;
import com.cary.cwish.service.UserService;

@Service("userService")  
public class UserServiceImpl implements UserService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
    @Resource  
    private UserDao userDao;
    
    public User getUserById(int userId) throws Exception {
        return userDao.selectByPrimaryKey(userId);  
    }
    
	public User getUserByName(String userName) throws Exception {
		logger.info("get in UserService");
		return userDao.selectByName(userName);
	}  
  
} 
