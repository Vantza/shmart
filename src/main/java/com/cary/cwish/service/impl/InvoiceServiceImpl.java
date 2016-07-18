package com.cary.cwish.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cary.cwish.dao.InvoiceDao;
import com.cary.cwish.pojo.Invoice;
import com.cary.cwish.service.InvoiceService;

@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {
	@Resource
	private InvoiceDao invoiceDao;

	@Override
	public Invoice getInvoiceByID(int id) throws Exception {
		return invoiceDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Invoice> getHundredRecords(int startNum, int endNum, int requiredId) throws Exception {
		return invoiceDao.selectHundredRecords(startNum, endNum, requiredId);
	}

	@Override
	public int getCountOfInvoiceGroup(int requiredId) throws Exception {
		return invoiceDao.selectCountRecords(requiredId);
	}

	@Override
	public List<Invoice> getRecordsByRequiredId(int requiredId) throws Exception {
		return invoiceDao.selectRecordsByRequiredId(requiredId);
	}

}
