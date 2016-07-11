package com.cary.cwish.service.impl;

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

}
