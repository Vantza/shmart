package com.cary.cwish.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cary.cwish.pojo.Invoice;
import com.cary.cwish.service.InvoiceService;
import com.cary.cwish.utils.ExcelOperation;
import com.cary.cwish.utils.WishConstant;

@Controller
@RequestMapping("/home")
public class HomePageController {
	@Resource
	InvoiceService invoiceService;
	
	private static Logger logger = Logger.getLogger(HomePageController.class);
	
	@RequestMapping(value= "/")
	public ModelAndView getHomePage(HttpServletRequest request, Model model) throws Exception{
		ModelAndView mav = new ModelAndView(WishConstant.HOME_PAGE);
		Invoice i = invoiceService.getInvoiceByID(2);
		mav.addObject("invoice", i);
        return mav;
	}
	
	@RequestMapping(value="/getRecords")
	public void getRecordExcel(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("UTF-8");
		JSONObject jsonObject;
		String fileName = null;
		String requiredId = req.getParameter("requiredId").trim();
		String reg="^\\d+$";
		long time = System.currentTimeMillis();
		List<String> excel;
		List<Invoice> insList = new ArrayList<Invoice>();
		
		logger.info(time);
		
		if (requiredId=="") {
			jsonObject = new JSONObject();
			jsonObject.put("failed", "requiredId不能为空！！");
			res.getWriter().write(jsonObject.toString());
		} else if (requiredId.matches(reg)) {
			int reqId = Integer.parseInt(requiredId);
			int totalCount = invoiceService.getCountOfInvoiceGroup(reqId);
			for (int i=0; i<totalCount/100+1; i++) {
				List<Invoice> ins = invoiceService.getHundredRecords(i*100+1, (i+1)*100, reqId);
				insList.addAll(ins);
				fileName = WishConstant.DOWNLOAD_DIRECTORY + "Invoice_" + reqId + "_" + i + "_" + time + ".xls";
				ExcelOperation.writeInvoicesToExcel(fileName, ins);
			}
			
			// Here is to return a list of file name for user to download
			excel = ExcelOperation.listTimeExcelNames(time);
			
			jsonObject = new JSONObject();
			jsonObject.put("excelName", excel);
			jsonObject.put("invoices", insList);
			jsonObject.put("success", "导出工作已经完成！！！");
			res.getWriter().write(jsonObject.toString());
		} else {
			jsonObject = new JSONObject();
			jsonObject.put("failed", "非法的requiredId！！！");
			res.getWriter().write(jsonObject.toString());
		}
	}
	
	@RequestMapping(value="/getFileInfo")
	public void getFileInfo(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		String requiredId = req.getParameter("requiredId").trim();
		int reqId = Integer.parseInt(requiredId);
		int totalCount = invoiceService.getCountOfInvoiceGroup(reqId);
		if (totalCount == 0) {
			jsonObject.put("info", "该请求编号没有获取到数据，请确认请求编号");
		} else {
			double fileCount = Math.ceil(totalCount/100.0);
			logger.info(fileCount);
			logger.info(totalCount);
			jsonObject.put("info", "共有" + fileCount + "个文件可以下载！请耐心等待！");
		}
		res.getWriter().write(jsonObject.toString());
	}
	
	@RequestMapping(value="/listRecords")
	public void getlistRecords(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		String requiredId = req.getParameter("requiredId").trim();
		int reqId = Integer.parseInt(requiredId);
		
		List<Invoice> ins = invoiceService.getRecordsByRequiredId(reqId);
		jsonObject.put("invoices", ins);
		res.getWriter().write(jsonObject.toString());
	}
}
