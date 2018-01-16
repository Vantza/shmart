package com.cary.cwish.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cary.cwish.dao.ModifyContractPushDao;
import com.cary.cwish.pojo.ModifyContractPush;
import com.cary.cwish.service.ModifyCotractPushService;

@Service("modifyContractPushService")
public class ModifyContractPushServiceImpl implements ModifyCotractPushService {
	@Resource
	private ModifyContractPushDao mcpd;

	@Override
	public List<ModifyContractPush> getModifyContractPushList() {
		 return mcpd.selectModifyContractPush();
	}
}
