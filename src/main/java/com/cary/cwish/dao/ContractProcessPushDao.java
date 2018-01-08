package com.cary.cwish.dao;

import java.util.List;

import com.cary.cwish.pojo.ContractProcessPush;

public interface ContractProcessPushDao {
	List<ContractProcessPush> selectByPrimaryKey(Integer id);
	
	List<ContractProcessPush> selectContractProcessPush();
}
