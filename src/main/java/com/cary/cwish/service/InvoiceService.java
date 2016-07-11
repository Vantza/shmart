package com.cary.cwish.service;

import com.cary.cwish.pojo.Invoice;

public interface InvoiceService {
	public Invoice getInvoiceByID(int id) throws Exception;
}
