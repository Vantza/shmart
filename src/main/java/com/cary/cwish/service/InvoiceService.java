package com.cary.cwish.service;

import java.util.List;

import com.cary.cwish.pojo.Invoice;

public interface InvoiceService {
	public Invoice getInvoiceByID(int id) throws Exception;
	
	public List<Invoice> getHundredRecords(int startNum) throws Exception;
	
	public int getCountOfInvoiceGroup() throws Exception;
}
