package com.cary.cwish.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cary.cwish.dao.RetireProcessPushDao;
import com.cary.cwish.pojo.RetireProcessPush;
import com.cary.cwish.service.RetireProcessPushService;


@Service("retireProcessPushService")
public class RetireProcessPushServiceImpl implements RetireProcessPushService{

	@Resource
	private RetireProcessPushDao rpp;
	
	@Override
	public List<RetireProcessPush> getRetireProcessPushList() {
		return rpp.selectAllRetireProcessPush();
	}
	
}
