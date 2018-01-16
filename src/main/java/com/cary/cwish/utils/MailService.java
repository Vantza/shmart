package com.cary.cwish.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.From;

import org.springframework.beans.factory.parsing.AliasDefinition;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.cary.cwish.pojo.ContractProcessPush;
import com.cary.cwish.pojo.ModifyContractPush;
import com.cary.cwish.pojo.RetireProcessPush;
import com.cary.cwish.pojo.RetireStartPush;
import com.sun.istack.internal.logging.Logger;



public class MailService {
	private static JavaMailSenderImpl mailSender = createMailSender();
	
	/**
	 * 创建邮件发送器
	 * @return
	 */
	public static JavaMailSenderImpl createMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(WishConstant.HOST);
        sender.setPort(WishConstant.PORT);
        sender.setProtocol("smtp");
        sender.setUsername(WishConstant.USERNAME);
        sender.setPassword(WishConstant.PASSWORD);
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "25000");
        p.setProperty("mail.smtp.auth", "true");
        sender.setJavaMailProperties(p);
        return sender;
	}
	
	/**
	 * 发送邮件
	 * @param to 
	 * 			String[] array = new String[]
	 * 			{"sun111@163.com","sun222@sohu.com"};
	 * @param cc
	 * @param subject
	 * @param html
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendHtmlMail(String[] to, String[] cc, String attachFileName, File attachment, String subject, String html) throws MessagingException,UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(WishConstant.EMAILFORM, "科传系统提醒邮件");
        messageHelper.setTo(to);
        messageHelper.setCc(cc);
        if (attachFileName != null && attachment != null) {
        	messageHelper.addAttachment(attachFileName, attachment);
        }
        messageHelper.setSubject(subject);
        messageHelper.setText(html, true);
        mailSender.send(mimeMessage);
    }
	
	/**
	 * 构建签约流程提醒发送人邮件列表
	 * @param cpps
	 * @return
	 */
	public static String[] buildContractProcessPushEmailAddressOfTo(List<ContractProcessPush> cpps) {
		List<String> emails = new ArrayList<String>();
		for (ContractProcessPush cpp : cpps) {
			if (cpp.getEmail()!=null && !emails.contains(cpp.getEmail())) {
				emails.add(cpp.getEmail());
			}
		}
		
		String[] to = emails.toArray(new String[emails.size()]);
		
		return to;
	}
	
	
	/**
	 * 构建签约流程提醒抄送人邮件列表
	 * @return
	 */
	public static String[] buildContractProcessPushEmailAddressOfCc() {
		String[] cc = {
				"tommy.da@shanghaimart.com",
				"jianai.wang@shanghaimart.com",
				"bo.wang@tech-trans.com",
				"meng.huang@tech-trans.com",
				"cary.cao@shanghaimart.com"
		};
		return cc;
	}
	
	
	/**
	 * 构建签约流程邮件内容
	 * @param cpps
	 * @return
	 * @throws ParseException 
	 */
	public static String buildContractProcessPushHTML(List<ContractProcessPush> cpps) throws ParseException {
		String html;
		String table;
		String accTime;
		//构建邮件文字部分
		html = "<div style='font-size: 14px; font-family: 微软雅黑, 'Microsoft YaHei'; outline: none;'>"
				+ "<span>各位，</span><br>"
				+ "<p>科传要求合同在ERP中成立后的七个工作日内走完科传系统的流程审批，租约生效日期为红色的为超过期限的记录，还请优先审批超过期限记录。</p><br>"
				+ "<p>如果租约属于展示间转办公楼的租约，审批的时候注意以下： </p>"
				+ "<p>1、合同分类应该是办公楼租约 </p>"
				+ "<p>2、物业性质954Mart转Tower </p>"
				+ "<p>3、合同条款里面的费用编码应是Tower租金Tower管理费。 </p><br></div>";
		
		//构建邮件表格部分
		table = "<table cellpadding='0' cellspacing='0' border='1' style='text-align: center'>"
				+ "<colgroup><col width='196'>"
				+ "<col width='70'>"
				+ "<col width='80'>"
				+ "<col width='140'>"
				+ "<col width='75'>"
				+ "<col width='75'>"
				+ "<col width='80'>"
				+ "<col width='100'></colgroup>"
				+ "<tbody><tr height='25'><td><strong>科传系统待审批人</strong></td>"
				+ "<td><strong>创建人</strong></td>"
				+ "<td><strong>合同编号</strong></td>"
				+ "<td><strong>签约流程号</strong></td>"
				+ "<td><strong>审批状态</strong></td>"
				+ "<td><strong>租赁状态</strong></td>"
				+ "<td><strong>租约类型</strong></td>"
				+ "<td><strong>原系统租约生效日期</strong></td></tr>";
		
		for (ContractProcessPush cpp : cpps ) {
			long diff;
			long days = 0;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = df.parse(cpp.getAccepttime());     
			Date d2 = new Date();     
			diff = d2.getTime() - d1.getTime();     
			days = diff / (1000 * 60 * 60 * 24);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d2);
			
			//排除双休日
			if (calendar.DAY_OF_WEEK < 2) {
				if (days > 10) {
					accTime = "<td style='color: red'>";
				} else {
					accTime = "<td>";
				}
			} else {
				if (days > 8) {
					accTime = "<td style='color: red'>";
				} else {
					accTime = "<td>";
				}
			}
			
						
			table = table + "<tr height='25'><td>" + cpp.getProcessPoint() + "</td>"
					+ "<td>" + cpp.getCreator() + "</td>"
					+ "<td>" + cpp.getLeaseNumber() + "</td>"
					+ "<td>" + cpp.getBpmsn() + "</td>"
					+ "<td>" + cpp.getProValue() + "</td>"
					+ "<td>" + cpp.getRentValue() + "</td>"
					+ "<td>" + cpp.getRentType() + "</td>"
					+ accTime + cpp.getAccepttime() + "</td></tr>";
		}
		
		table = table + "</tbody></table><br>";
		html = html + table;
		
		return html;
	}

	
	/**
	 * 构建退租流程提醒邮件提醒列表 
	 * 每个邮箱不重复出现
	 * @param rpps
	 * @return
	 */
	public static String[] buildRetireProcessPushEmailAddressOfTo(List<RetireProcessPush> rpps) {
		List<String> emails = new ArrayList<String>();
		for (RetireProcessPush rpp : rpps) {
			if (rpp.getEmail() != null && rpp.getEmail().equals("工程维修部办理退租")) {
				if (!emails.contains("feng.ding@shanghaimart.com") && !emails.contains("glf@shanghaimart.com") && !emails.contains("tyy@shanghaimart.com")) {
					emails.add("feng.ding@shanghaimart.com");
					emails.add("glf@shanghaimart.com");
					emails.add("tyy@shanghaimart.com");
				}
			}
			
			if (rpp.getEmail() != null && rpp.getEmail().equals("工程弱电部办理退租")) {
				if (!emails.contains("pete@shanghaimart.com") && !emails.contains("gw@shanghaimart.com") && !emails.contains("jean@shanghaimart.com")) {
					emails.add("pete@shanghaimart.com");
					emails.add("gw@shanghaimart.com");
					emails.add("jean@shanghaimart.com");
				}
			}
			
			if (rpp.getEmail() != null && !emails.contains(rpp.getEmail()) && !rpp.getEmail().equals("工程维修部办理退租") && !rpp.getEmail().equals("工程弱电部办理退租")) {
				emails.add(rpp.getEmail());
			}
		}
		emails.add("cary.cao@shanghaimart.com");
		String[] to = emails.toArray(new String[emails.size()]); 
		return to;
	}

	/**
	 * 构建退租提醒邮件内容部分
	 * @param rpps
	 * @return
	 * @throws ParseException
	 */
	public static String buildRetireProcessPushHTML(List<RetireProcessPush> rpps) throws ParseException {
		String html;
		String table;
		String accTime;
		
		//构建邮件文字部分
		html = "<div style='font-size: 14px; font-family: 微软雅黑, 'Microsoft YaHei'; outline: none;'>"
				+ "<span>各位，</span><br>"
				+ "<p>科传要求退租在ERP中成立后的七个工作日内走完科传系统的流程审批，租约生效日期为红色的为超过期限的记录，还请优先审批超过期限记录。</p><br>"
				+ "<p>谢谢配合! </p>";
		
		//构建邮件表格部分
		table = "<table cellpadding='0' cellspacing='0' border='1' style='text-align: center'>"
				+ "<colgroup><col width='196'>"
				+ "<col width='120'>"
				+ "<col width='140'>"
				+ "<col width='80'>"
				+ "<col width='105'>"
				+ "<col width='180'></colgroup>"
				+ "<tbody><tr height='25'><td><strong>科传系统审批环节</strong></td>"
				+ "<td><strong>审批环节操作人</strong></td>"
				+ "<td><strong>单元号</strong></td>"
				+ "<td><strong>合同编号</strong></td>"
				+ "<td><strong>原系统流程状态</strong></td>"
				+ "<td><strong>原系统流程审批通过日期</strong></td></tr>";
		
		for (RetireProcessPush rpp : rpps ) {
			long diff;
			long days = 0;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = df.parse(rpp.getOriginalPassTime());     
			Date d2 = new Date();     
			diff = d2.getTime() - d1.getTime();     
			days = diff / (1000 * 60 * 60 * 24);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d2);
			
			//排除双休日
			if (calendar.DAY_OF_WEEK < 2) {
				if (days > 10) {
					accTime = "<td style='color: red'>";
				} else {
					accTime = "<td>";
				}
			} else {
				if (days > 8) {
					accTime = "<td style='color: red'>";
				} else {
					accTime = "<td>";
				}
			}
			
						
			table = table + "<tr height='25'><td>" + rpp.getProcessPoint() + "</td>"
					+ "<td>" + rpp.getOperator() + "</td>"
					+ "<td>" + rpp.getUnits() + "</td>"
					+ "<td>" + rpp.getLeaseNumber() + "</td>"
					+ "<td>" + rpp.getOriginalStatus() + "</td>"
					+ accTime + rpp.getOriginalPassTime() + "</td></tr>";
		}
		
		table = table + "</tbody></table><br>";
		html = html + table;
		
		return html;
	}
	
	/**
	 * 构建签约流程未提交邮件发送人列表
	 * @param mcps
	 * @return
	 */
	public static String[] buildModifyContractPushEmailAddressOfTo(List<ModifyContractPush> mcps) {
		List<String> emails = new ArrayList<String>();
		for (ModifyContractPush mcp : mcps) {
			if (mcp.getEmail()!=null && !emails.contains(mcp.getEmail())) {
				emails.add(mcp.getEmail());
			}
		}
		
		String[] to = emails.toArray(new String[emails.size()]);
		
		return to;
	}

	/**
	 * 构建签约流程未提交提醒邮件内容部分
	 * @param mcps
	 * @return
	 * @throws ParseException
	 */
	public static String buildModifyContractPushHTML(List<ModifyContractPush> mcps) throws ParseException {
		String html;
		String table;
		
		//构建邮件文字部分
		html = "<div style='font-size: 14px; font-family: 微软雅黑, 'Microsoft YaHei'; outline: none;'>"
				+ "<span>各位，</span><br>"
				+ "<p>以下合同为原ERP系统中已成立，而科传系统中处于修改状态（被退回）的合同，还请各位根据审批人退回原因进行修改并重新提交，如有问题，请随时联系，谢谢。</p><br>";
		
		//构建邮件表格部分
		table = "<table cellpadding='0' cellspacing='0' border='1' style='text-align: center'>"
				+ "<colgroup><col width='196'>"
				+ "<col width='120'>"
				+ "<col width='140'>"
				+ "<col width='80'>"
				+ "<col width='105'>"
				+ "<col width='105'>"
				+ "<col width='105'>"
				+ "<col width='180'></colgroup>"
				+ "<tbody><tr height='25'><td><strong>科传系统合同状态</strong></td>"
				+ "<td><strong>创建人</strong></td>"
				+ "<td><strong>合同编号</strong></td>"
				+ "<td><strong>签约流程号</strong></td>"
				+ "<td><strong>审批状态</strong></td>"
				+ "<td><strong>租赁状态</strong></td>"
				+ "<td><strong>租约类型</strong></td>"
				+ "<td><strong>原系统租约生效日期</strong></td></tr>";
		
		for (ModifyContractPush mcp : mcps) {			
			table = table + "<tr height='25'><td>" + mcp.getOperator() + "</td>"
					+ "<td>" + mcp.getCreator() + "</td>"
					+ "<td>" + mcp.getLeaseNumber() + "</td>"
					+ "<td>" + mcp.getBpmsn() + "</td>"
					+ "<td>" + mcp.getProValue() + "</td>"
					+ "<td>" + mcp.getRentValue() + "</td>"
					+ "<td>" + mcp.getRentType() + "</td>"
					+ "<td>" + mcp.getAccepttime() + "</tr>";
		}
		
		table = table + "</tbody></table><br>";
		html = html + table;
		
		return html;
	}

	/**
	 * 构建退租流程未提交邮件发送人列表
	 * @param rsps
	 * @return
	 */
	public static String[] buildRetireStartPushEmailAddressOfTo(List<RetireStartPush> rsps) {
		List<String> emails = new ArrayList<String>();
		for (RetireStartPush rsp : rsps) {
			if (rsp.getEmail()!=null && !emails.contains(rsp.getEmail())) {
				emails.add(rsp.getEmail());
			}
		}
		
		String[] to = emails.toArray(new String[emails.size()]);
		
		return to;
	}


	public static String buildRetireStartPushHTML(List<RetireStartPush> rsps) {
		String html;
		String table;
		
		//构建邮件文字部分
		html = "<div style='font-size: 14px; font-family: 微软雅黑, 'Microsoft YaHei'; outline: none;'>"
				+ "<span>各位，</span><br>"
				+ "<p>以下合同为原ERP系统中已成立，而科传系统中处于新建/修改状态的退租申请，还请各位根据审批人退回原因进行修改并重新提交，如有问题，请随时联系，谢谢。</p><br>";
		
		//构建邮件表格部分
		table = "<table cellpadding='0' cellspacing='0' border='1' style='text-align: center'>"
				+ "<colgroup><col width='196'>"
				+ "<col width='120'>"
				+ "<col width='120'>"
				+ "<col width='120'>"
				+ "<col width='105'>"
				+ "<col width='180'></colgroup>"
				+ "<tbody><tr height='25'><td><strong>退租创建人</strong></td>"
				+ "<td><strong>单元号</strong></td>"
				+ "<td><strong>合同编号</strong></td>"
				+ "<td><strong>退租创建日期</strong></td>"
				+ "<td><strong>原系统流程状态</strong></td>"
				+ "<td><strong>原系统流程审批通过日期</strong></td></tr>";
		
		for (RetireStartPush rsp : rsps ) {			
			table = table + "<tr height='25'><td>" + rsp.getCreator() + "</td>"
					+ "<td>" + rsp.getUnits() + "</td>"
					+ "<td>" + rsp.getLeaseNumber() + "</td>"
					+ "<td>" + rsp.getCreateAt() + "</td>"
					+ "<td>" + rsp.getStatus() + "</td>"
					+ "<td>" + rsp.getPassTime() + "</td></tr>";
		}
		
		table = table + "</tbody></table><br>";
		html = html + table;
		
		return html;
	}

}
	