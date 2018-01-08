package com.cary.cwish.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.cary.cwish.dao.ContractProcessPushDao;
import com.cary.cwish.pojo.ContractProcessPush;
import com.cary.cwish.service.ContractProcessPushService;


@Service("contractProcessPushService")
public class ContractProcessPushServiceImpl implements ContractProcessPushService {
	@Resource
	private ContractProcessPushDao cppd;
	
	@Override
	public List<ContractProcessPush> getContractProcessPushList() {
		return cppd.selectContractProcessPush();
	}

}
