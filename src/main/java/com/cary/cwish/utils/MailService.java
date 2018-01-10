package com.cary.cwish.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.cary.cwish.pojo.ContractProcessPush;
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
        messageHelper.setFrom(WishConstant.EMAILFORM, "科传审批提醒邮件");
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
		int size = cpps.size();
		String[] to = new String[size];
		for (int i=0; i<size; i++) {
			to[i] = cpps.get(i).getEmail();
		}
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
			
			if (days > 7) {
				accTime = "<td style='color: red'>";
			} else {
				accTime = "<td>";
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
}
	