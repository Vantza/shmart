package cary;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cary.cwish.pojo.ContractProcessPush;
import com.cary.cwish.service.ContractProcessPushService;
import com.cary.cwish.utils.ExcelOperation;


@RunWith(SpringJUnit4ClassRunner.class)    //表示继承了SpringJUnit4ClassRunner类 
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"}) 
public class TestUtils {
	@Resource
    private ContractProcessPushService cpps = null;
	
	@Test
	public void testWriteListOfContractProcessToExcel() throws Exception {
		ExcelOperation eo = new ExcelOperation();
		List<ContractProcessPush> list = cpps.getContractProcessPushList();
		eo.writeListOfContractProcessToExcel("C:/Users/cary.cao/Desktop/cycycy.xls", list);
	}
}
