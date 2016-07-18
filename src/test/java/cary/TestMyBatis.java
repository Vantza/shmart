package cary;

import java.util.List;

import javax.annotation.Resource;  

import org.apache.log4j.Logger;  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.springframework.test.context.ContextConfiguration;  
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
  
import com.alibaba.fastjson.JSON;
import com.cary.cwish.pojo.Invoice;
import com.cary.cwish.service.InvoiceService;
import com.cary.cwish.service.UserService;  
  
@RunWith(SpringJUnit4ClassRunner.class)    //表示继承了SpringJUnit4ClassRunner类 
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
  
public class TestMyBatis {  
    private static Logger logger = Logger.getLogger(TestMyBatis.class);  
    @Resource  
    private UserService userService = null;
    
    @Resource
    private InvoiceService invoiceService = null;
    
    
//    @Test  
//    public void testGetUserById() throws Exception {
//    	logger.info("get in testGetUserById");
//        User user = userService.getUserById(1);  
//        // System.out.println(user.getUserName());  
//        // logger.info("用户名"+user.getUserName());  
//        logger.info(JSON.toJSONString(user));  
//    }
    
    @Test
    public void testGetInvoiceById() throws Exception {
    	logger.info("get in testGetInvoiceById");
    	Invoice i = invoiceService.getInvoiceByID(2);
    	logger.info(JSON.toJSONString(i));
    }
    
    @Test
    public void testGetHundredRecords() throws Exception {
    	logger.info("get in testGetHundredRecords");
    	List<Invoice> is = invoiceService.getHundredRecords(0, 100, 2007037);
    	logger.info(is.size());
    }
    
    @Test
    public void testGetCountRecords() throws Exception {
    	logger.info("get in testGetCountRecords");
    	int a = invoiceService.getCountOfInvoiceGroup(2007037);
    	logger.info(a);
    }
    
    @Test
    public void testGetRecordsByRequiredId() throws Exception {
    	logger.info("get in testGetRecordsByRequiredId");
    	List<Invoice> is = invoiceService.getRecordsByRequiredId(2007037);
    	logger.info(is.size());
    }
}  