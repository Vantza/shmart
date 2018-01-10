package cary;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cary.cwish.pojo.ContractProcessPush;
import com.cary.cwish.service.ContractProcessPushService;
import com.cary.cwish.utils.ExcelOperation;
import com.cary.cwish.utils.MailService;


@RunWith(SpringJUnit4ClassRunner.class)    //表示继承了SpringJUnit4ClassRunner类 
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"}) 
public class TestUtils {
	private static Logger logger = Logger.getLogger(TestUtils.class);  
	
	
	@Resource
    private ContractProcessPushService cpps = null;
	
//	@Test
//	public void testWriteListOfContractProcessToExcel() throws Exception {
//		ExcelOperation eo = new ExcelOperation();
//		List<ContractProcessPush> list = cpps.getContractProcessPushList();
//		eo.writeListOfContractProcessToExcel("C:/Users/cary.cao/Desktop/cycycy.xls", list);
//	}
	
	@Test
	public void testMailService() throws Exception {
		MailService.createMailSender();
		logger.info("...");
		String[] to = {"544082780@qq.com"};
		String[] cc = {"cary.cao@shanghaimart.com"};
		File file = new File("C:/Users/cary.cao/Desktop/cycycy.xls");  
		List<ContractProcessPush> list = cpps.getContractProcessPushList();
		String html = MailService.buildContractProcessPushHTML(list);
        String fileNm = file.getName();
		MailService.sendHtmlMail(to, cc, fileNm, file, "test email", html);
	}
	
//	@Test
//	public void testBuildContractProcessPushHTML() throws Exception {
//		List<ContractProcessPush> list = cpps.getContractProcessPushList();
//		String html = MailService.buildContractProcessPushHTML(list);
//		logger.info(html);
//	}
	

}
