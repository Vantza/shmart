package com.cary.cwish.service;

import com.cary.cwish.pojo.User;

public interface UserService {  
    public User getUserById(int userId) throws Exception;
    
    public User getUserByName(String userName) throws Exception;
}  
