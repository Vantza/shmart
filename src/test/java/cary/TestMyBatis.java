package cary;

import javax.annotation.Resource;  

import org.apache.log4j.Logger;  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.springframework.test.context.ContextConfiguration;  
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
  
import com.alibaba.fastjson.JSON;
import com.cary.cwish.pojo.User;
import com.cary.cwish.service.UserService;  
  
@RunWith(SpringJUnit4ClassRunner.class)    //表示继承了SpringJUnit4ClassRunner类 
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
  
public class TestMyBatis {  
    private static Logger logger = Logger.getLogger(TestMyBatis.class);  
    @Resource  
    private UserService userService = null;
    
    
    @Test  
    public void testGetUserById() throws Exception {
    	logger.info("get in testGetUserById");
        User user = userService.getUserById(1);  
        // System.out.println(user.getUserName());  
        // logger.info("用户名"+user.getUserName());  
        logger.info(JSON.toJSONString(user));  
    }
    
}  