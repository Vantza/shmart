package com.cary.cwish.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cary.cwish.dao.RetireStartPushDao;
import com.cary.cwish.pojo.RetireStartPush;
import com.cary.cwish.service.RetireStartPushService;

@Service("retireStartPushService")
public class RetireStartPushServiceImpl implements RetireStartPushService{
	@Resource
	RetireStartPushDao rspd;

	@Override
	public List<RetireStartPush> getRetireStartPushServiceList() {
		return rspd.selectRetireStartPush();
	}
	
}
