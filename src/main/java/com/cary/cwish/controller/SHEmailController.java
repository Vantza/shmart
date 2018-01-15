package com.cary.cwish.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cary.cwish.pojo.ContractProcessPush;
import com.cary.cwish.pojo.RetireProcessPush;
import com.cary.cwish.service.ContractProcessPushService;
import com.cary.cwish.service.RetireProcessPushService;
import com.cary.cwish.utils.ExcelOperation;
import com.cary.cwish.utils.MailService;
import com.cary.cwish.utils.WishConstant;

@Controller
@RequestMapping("/SHEmail")
public class SHEmailController {
	
	private static Logger logger = Logger.getLogger(SHEmailController.class);
	
	@Resource
	ContractProcessPushService contractProcessPushService;
	
	@Resource
	RetireProcessPushService retireProcessPushService;
	
	@RequestMapping(value= "/")
	public ModelAndView getReimbursementRecords(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(WishConstant.SHEMAIL);
		
		return mav;
	}
	
	/**
	 * 查询请求
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/listRecords")
	public void getlistRecords(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("UTF-8");
		List<ContractProcessPush> cpps;
		List<RetireProcessPush> rpps;
		JSONObject jsonObject = new JSONObject();
		
		logger.info("SearchType is : " + req.getParameter("SearchType"));
		
		if (req.getParameter("SearchType")!= null && req.getParameter("SearchType").equals("contractProcess")) {
			logger.info("get into list contract process records page");
			cpps = contractProcessPushService.getContractProcessPushList();
			jsonObject.put("ContractProcessPushList", cpps);
		}
		
		if (req.getParameter("SearchType")!= null && req.getParameter("SearchType").equals("retireProcess")){
			logger.info("get into list retire process records page");
			rpps = retireProcessPushService.getRetireProcessPushList();
			jsonObject.put("RetireProcessPushList", rpps);
		}
		
		res.getWriter().write(jsonObject.toString());
	}
	
	
	/**
	 * 保存请求
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/saveRecordsToExcel")
	public void saveRecordsToExcel(HttpServletRequest req, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		logger.info("SaveType is : " + req.getParameter("saveType"));
		List<ContractProcessPush> cpps;
		List<RetireProcessPush> rpps;
		ExcelOperation eo = new ExcelOperation();
		String fileName = null;
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		fileName = df.format(day);
		logger.info("现在时间是 ： " +  df.format(day));
		
		if (req.getParameter("saveType")!= null && req.getParameter("saveType").equals("contractProcess")) {
			logger.info("start to save contract process info");			
			
			fileName = WishConstant.SAVE_FOLDER + "cpps/签约流程" + fileName + ".xls";
			cpps = contractProcessPushService.getContractProcessPushList();
			eo.writeListOfContractProcessToExcel(fileName, cpps);
			jsonObject.put("saveStatus", "success");
		}
		
		if (req.getParameter("saveType")!= null && req.getParameter("saveType").equals("retireProcess")) {
			logger.info("start to save retire process info");
			
			fileName = WishConstant.SAVE_FOLDER + "rpps/退租流程" + fileName + ".xls";
			rpps = retireProcessPushService.getRetireProcessPushList();
			eo.writeListOfRetireProcessToExcel(fileName, rpps);
			jsonObject.put("saveStatus", "success");
		}
		
		jsonObject.put("saveStatus", "fail");
		res.getWriter().write(jsonObject.toString());
	}
	
	
	/**
	 * 邮件发送请求
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/sendReminderEmail")
	public void sendReminderEmail(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("start to send mails");
		//save data to excel for attachments of emails
		res.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		logger.info("SaveType is : " + req.getParameter("saveType"));
		List<ContractProcessPush> cpps;
		List<RetireProcessPush> rpps;
		ExcelOperation eo = new ExcelOperation();
		String fileName = null;
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		fileName = df.format(day);
		logger.info("现在时间是 ： " +  df.format(day));
		
		
		//发送签约流程提醒邮件
		if (req.getParameter("saveType")!= null && req.getParameter("saveType").equals("contractProcess")) {
			logger.info("start to save contract process info");			
			
			fileName = WishConstant.SAVE_FOLDER + "cpps/签约流程" + fileName + ".xls";
			cpps = contractProcessPushService.getContractProcessPushList();
			eo.writeListOfContractProcessToExcel(fileName, cpps);
			
			//send emails 
			MailService.createMailSender();
			String[] to = MailService.buildContractProcessPushEmailAddressOfTo(cpps);
			//String[] to = {"cary.cao@shanghaimart.com"};
			String[] cc = MailService.buildContractProcessPushEmailAddressOfCc();
			//String[] cc = {"544082780@qq.com"};
			File attachment = new File(fileName);
			String attachFileName = attachment.getName();
			String subject = "签约流程审批提醒 - 7个工作日     " + df.format(day);
			String html = MailService.buildContractProcessPushHTML(cpps);
			logger.info("start to send contract process info emails");
			MailService.sendHtmlMail(to, cc, attachFileName, attachment, subject, html);
			logger.info("contract process info emails sent successfully!!");
			jsonObject.put("emailStatus", "success");
			res.getWriter().write(jsonObject.toString());
		}
		
		//发送退租流程提醒邮件
		if (req.getParameter("saveType")!= null && req.getParameter("saveType").equals("retireProcess")) {
			logger.info("start to save retire process info");			
			
			fileName = WishConstant.SAVE_FOLDER + "rpps/退租流程" + fileName + ".xls";
			rpps = retireProcessPushService.getRetireProcessPushList();
			eo.writeListOfRetireProcessToExcel(fileName, rpps);
			
			//send emails 
			MailService.createMailSender();
			String[] to = MailService.buildRetireProcessPushEmailAddressOfTo(rpps);
			//String[] to = {"cary.cao@shanghaimart.com"};
			String[] cc = MailService.buildContractProcessPushEmailAddressOfCc();
			//String[] cc = {"544082780@qq.com"};
			File attachment = new File(fileName);
			String attachFileName = attachment.getName();
			String subject = "退租流程审批提醒 - 7个工作日     " + df.format(day);
			String html = MailService.buildRetireProcessPushHTML(rpps);
			logger.info("start to send retire process info emails");
			MailService.sendHtmlMail(to, cc, attachFileName, attachment, subject, html);
			logger.info("retire process info emails sent successfully!!");
			jsonObject.put("emailStatus", "success");
			res.getWriter().write(jsonObject.toString());
		}
		
		
		
	}
}
