package com.cary.cwish.service;

import java.util.List;

import com.cary.cwish.pojo.Invoice;

public interface InvoiceService {
	public Invoice getInvoiceByID(int id) throws Exception;
	
	public List<Invoice> getHundredRecords(int startNum, int endNum, int requiredId) throws Exception;
	
	public int getCountOfInvoiceGroup(int requiredId) throws Exception;
	
	public List<Invoice> getRecordsByRequiredId(int requiredId) throws Exception;
}
