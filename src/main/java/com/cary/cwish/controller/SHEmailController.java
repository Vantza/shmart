package com.cary.cwish.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cary.cwish.pojo.ContractProcessPush;
import com.cary.cwish.service.ContractProcessPushService;
import com.cary.cwish.utils.ExcelOperation;
import com.cary.cwish.utils.WishConstant;
import com.sun.org.apache.xpath.internal.operations.And;

@Controller
@RequestMapping("/SHEmail")
public class SHEmailController {
	
	private static Logger logger = Logger.getLogger(SHEmailController.class);
	
	@Resource
	ContractProcessPushService ContractProcessPushService;
	
	@RequestMapping(value= "/")
	public ModelAndView getReimbursementRecords(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(WishConstant.SHEMAIL);
		
		return mav;
	}
	
	@RequestMapping(value="/listRecords")
	public void getlistRecords(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("UTF-8");
		List<ContractProcessPush> cpps = new ArrayList<ContractProcessPush>();
		JSONObject jsonObject = new JSONObject();
		
		logger.info("SearchType is : " + req.getParameter("SearchType"));
		
		if (req.getParameter("SearchType")!= null && req.getParameter("SearchType").equals("contractProcess")) {
			logger.info("get into list contract records page ");
			cpps = ContractProcessPushService.getContractProcessPushList();
			jsonObject.put("ContractProcessPushList", cpps);
		}		
		res.getWriter().write(jsonObject.toString());
	}
	
	@RequestMapping(value="/saveRecordsToExcel")
	public void saveRecordsToExcel(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("UTF-8");
		logger.info("SaveType is : " + req.getParameter("saveType"));
		List<ContractProcessPush> cpps = new ArrayList<ContractProcessPush>();		
		ExcelOperation eo = new ExcelOperation();
		String fileName = null;
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		fileName = df.format(day);
		logger.info("现在时间是 ： " +  df.format(day));
		
		if (req.getParameter("saveType")!= null && req.getParameter("saveType").equals("contractProcess")) {
			logger.info("start to save contract process info");			
			
			fileName = WishConstant.SAVE_FOLDER + "签约流程" + fileName + ".xls";
			cpps = ContractProcessPushService.getContractProcessPushList();
			eo.writeListOfContractProcessToExcel(fileName, cpps);
		}
		
	}
}
