package com.cary.cwish.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cary.cwish.pojo.ContractProcessPush;
import com.cary.cwish.pojo.Invoice;
import com.cary.cwish.pojo.ModifyContractPush;
import com.cary.cwish.pojo.RetireProcessPush;
import com.cary.cwish.pojo.RetireStartPush;

public class ExcelOperation {
	private static Logger logger = Logger.getLogger(ExcelOperation.class);
	
	/**
	 * build excel files
	 * @param fileName
	 * @param invoices
	 * @throws Exception
	 */
	public static void writeInvoicesToExcel(String fileName, List<Invoice> invoices) throws Exception {
		Workbook workbook = null;
		logger.info("Invoices size : " + invoices.size());
		if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }
		
		Sheet sheet = workbook.createSheet("INVOICE_VAT_EXP");
		
		// Here is to build header of excel
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("发票种类");
		header.createCell(1).setCellValue("单据号");
		header.createCell(2).setCellValue("单据日期");
		header.createCell(3).setCellValue("客户编号");
		header.createCell(4).setCellValue("客户名称");
		header.createCell(5).setCellValue("客户税号");
		header.createCell(6).setCellValue("客户地址电话");
		header.createCell(7).setCellValue("客户银行及账号");
		header.createCell(8).setCellValue("备注");
		header.createCell(9).setCellValue("专用发票红票通知单号");
		header.createCell(10).setCellValue("普通发票红票对应正数发票代码");
		header.createCell(11).setCellValue("普通发票红票对应正数发票号码");
		header.createCell(12).setCellValue("开票人");
		header.createCell(13).setCellValue("复核人");
		header.createCell(14).setCellValue("收款人");
		header.createCell(15).setCellValue("销方银行及帐号");
		header.createCell(16).setCellValue("销方地址电话");
		header.createCell(17).setCellValue("商品编号");
		header.createCell(18).setCellValue("商品名称");
		header.createCell(19).setCellValue("规格型号");
		header.createCell(20).setCellValue("计量单位");
		header.createCell(21).setCellValue("数量");
		header.createCell(22).setCellValue("金额（含税）");
		header.createCell(23).setCellValue("税率");
		header.createCell(24).setCellValue("税额");
		header.createCell(25).setCellValue("折扣金额(含税)");
		header.createCell(26).setCellValue("折扣税额");
		header.createCell(27).setCellValue("单价");
		header.createCell(28).setCellValue("折扣率");
		header.createCell(29).setCellValue("收货人");
		header.createCell(30).setCellValue("收货人纳税人识别号");
		header.createCell(31).setCellValue("发货人");
		header.createCell(32).setCellValue("发货人纳税人识别号");
		header.createCell(33).setCellValue("起运地、经由、到达地");
		header.createCell(34).setCellValue("车种车号");
		header.createCell(35).setCellValue("车船吨位");
		header.createCell(36).setCellValue("运输货物信息");
		header.createCell(37).setCellValue("编码版本号");
		header.createCell(38).setCellValue("税收分类编码");
		header.createCell(39).setCellValue("是否享受优惠政策");
		header.createCell(40).setCellValue("享受税收优惠政策内容");
		header.createCell(41).setCellValue("零税率标识");
		
		int rowIndex = 1;
		for (Invoice i : invoices) {
			String customerName = i.getCustomerName() == null ? null : i.getCustomerName().toString();
			String qty = i.getQty() == null ? "" : i.getQty().doubleValue()+"";
			String vatinclusiveAmt = i.getVatinclusiveAmt()==null?"":i.getVatinclusiveAmt().doubleValue()+"";
			String taxPercent = i.getTaxPercent()==null?"":i.getTaxPercent().doubleValue()+"";
			String taxAmt = i.getTaxAmt()==null?"":i.getTaxAmt().doubleValue()+"";
			String vatinclusiveDiscountamt = i.getVatinclusiveDiscountamt()==null?"":i.getVatinclusiveDiscountamt().doubleValue()+"";
			String taxDiscountamt = i.getTaxDiscountamt()==null?"":i.getTaxDiscountamt().doubleValue()+"";
			
			Row row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(i.getInvoiceType());
			row.createCell(1).setCellValue(i.getInvoiceSequence());
			row.createCell(2).setCellValue(i.getSequenceDate());
			row.createCell(3).setCellValue(i.getCustomerNumber());
			row.createCell(4).setCellValue(customerName);	// confusion
			row.createCell(5).setCellValue(i.getCustomerVatnumber());
			row.createCell(6).setCellValue(i.getCustomerVataddresstele());
			row.createCell(7).setCellValue(i.getCustomerVatbanknum());
			row.createCell(8).setCellValue(i.getComments());
			row.createCell(9).setCellValue(i.getSpecialinvoiceRednumber());
			row.createCell(10).setCellValue(i.getCommoninvoiceEquivalentcode());
			row.createCell(11).setCellValue(i.getCommoninvoiceEquivalentnumber());
			row.createCell(12).setCellValue(i.getCreatedUsername());
			row.createCell(13).setCellValue(i.getCheckedUsername());
			row.createCell(14).setCellValue(i.getReceiptUsername());
			row.createCell(15).setCellValue(i.getSaleBanknum());
			row.createCell(16).setCellValue(i.getSaleAddresstele());
			row.createCell(17).setCellValue(i.getItemNumber());
			row.createCell(18).setCellValue(i.getItemName());
			row.createCell(19).setCellValue(i.getSpecification());
			row.createCell(20).setCellValue(i.getUomCode());
			row.createCell(21).setCellValue(qty);	// confusion
			row.createCell(22).setCellValue(vatinclusiveAmt);	// confusion
			row.createCell(23).setCellValue(taxPercent);	// confusion
			row.createCell(24).setCellValue(taxAmt);	// confusion
			row.createCell(25).setCellValue(vatinclusiveDiscountamt);
			row.createCell(26).setCellValue(taxDiscountamt);
			row.createCell(27).setCellValue(i.getUnitprice());
			row.createCell(28).setCellValue("");
			row.createCell(29).setCellValue("");
			row.createCell(30).setCellValue("");
			row.createCell(31).setCellValue("");
			row.createCell(32).setCellValue("");
			row.createCell(33).setCellValue("");
			row.createCell(34).setCellValue("");
			row.createCell(35).setCellValue("");
			row.createCell(36).setCellValue("");
			row.createCell(37).setCellValue(1);
			row.createCell(38).setCellValue((i.getTaxPercent().doubleValue()==0.05)?"3040502029902":"3040801");
			row.createCell(39).setCellValue("");
			row.createCell(40).setCellValue("");
			row.createCell(41).setCellValue("");
		}
		FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        System.out.println(fileName + " written successfully");
	}

	/**
	 * list files by end of file names
	 * @param time
	 * @throws Exception
	 */
	public static List<String> listTimeExcelNames(long time) throws Exception {
		String path = WishConstant.DOWNLOAD_DIRECTORY;
		File file = new File(path);
		File[] tempList = file.listFiles();
		List<String> fileName = new ArrayList<String>();
		String ends = time + ".xls";
		
		for (int i=0; i<tempList.length; i++) {
			if (tempList[i].isFile()) {
				if (tempList[i].getName().endsWith(ends)) {
					fileName.add(tempList[i].getName());
				}
			}
		}
		return fileName;
	}
	
	
	/**
	 * write information of ContractProcess to excel file
	 * @param fileName
	 * @param cpps
	 * @throws Exception
	 */
	public void writeListOfContractProcessToExcel(String fileName, List<ContractProcessPush> cpps) throws Exception {
		Workbook workbook = null;
		logger.info("cpps' size : " + cpps.size());
		if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }
		
		Sheet sheet = workbook.createSheet("ContractProcess");
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("科传系统待审批人");
		header.createCell(1).setCellValue("创建人");
		header.createCell(2).setCellValue("合同编号");
		header.createCell(3).setCellValue("签约流程号");
		header.createCell(4).setCellValue("审批状态");
		header.createCell(5).setCellValue("租赁状态");
		header.createCell(6).setCellValue("租约类型");
		header.createCell(7).setCellValue("原系统租约生效日期");
		
		int rowIndex = 1;
		for (ContractProcessPush cpp : cpps) {
			Row row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(cpp.getProcessPoint());
			row.createCell(1).setCellValue(cpp.getCreator());
			row.createCell(2).setCellValue(cpp.getLeaseNumber());
			row.createCell(3).setCellValue(cpp.getBpmsn());
			row.createCell(4).setCellValue(cpp.getProValue());	
			row.createCell(5).setCellValue(cpp.getRentValue());
			row.createCell(6).setCellValue(cpp.getRentType());
			row.createCell(7).setCellValue(cpp.getAccepttime());
		}
		
		FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        logger.info(fileName + " written successfully");
	}
	
	
	/**
	 * write information of RetireProcess to excel file
	 * @param fileName
	 * @param rpps
	 * @throws Exception
	 */
	public void writeListOfRetireProcessToExcel(String fileName, List<RetireProcessPush> rpps) throws Exception {
		Workbook workbook = null;
		logger.info("rpps' size : " + rpps.size());
		if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }
		
		Sheet sheet = workbook.createSheet("RetireProcess");
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("科传系统审批环节");
		header.createCell(1).setCellValue("审批环节操作人");
		header.createCell(2).setCellValue("单元号");
		header.createCell(3).setCellValue("合同编号");
		header.createCell(4).setCellValue("退租创建日期");
		header.createCell(5).setCellValue("原系统流程状态");
		header.createCell(6).setCellValue("原系统流程审批通过日期");
		
		int rowIndex = 1;
		for (RetireProcessPush rpp : rpps) {
			Row row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(rpp.getProcessPoint());
			row.createCell(1).setCellValue(rpp.getOperator());
			row.createCell(2).setCellValue(rpp.getUnits());
			row.createCell(3).setCellValue(rpp.getLeaseNumber());
			row.createCell(4).setCellValue(rpp.getStartRetireTime());	
			row.createCell(5).setCellValue(rpp.getOriginalStatus());
			row.createCell(6).setCellValue(rpp.getOriginalPassTime());
		}
		
		FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        logger.info(fileName + " written successfully");
	}

	
	/**
	 * write information of ModifyContract to excel file
	 * @param fileName
	 * @param mcps
	 * @throws Exception
	 */
	public void writeListOfModifyContractToExcel(String fileName, List<ModifyContractPush> mcps) throws Exception {
		Workbook workbook = null;
		logger.info("mcps' size : " + mcps.size());
		if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }
		
		Sheet sheet = workbook.createSheet("ModifyContract");
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("科传系统合同状态");
		header.createCell(1).setCellValue("创建人");
		header.createCell(2).setCellValue("合同编号");
		header.createCell(3).setCellValue("签约流程号");
		header.createCell(4).setCellValue("审批状态");
		header.createCell(5).setCellValue("租赁状态");
		header.createCell(6).setCellValue("租约类型");
		header.createCell(7).setCellValue("原系统租约生效日期");
		
		int rowIndex = 1;
		for (ModifyContractPush mcp : mcps) {
			Row row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(mcp.getOperator());
			row.createCell(1).setCellValue(mcp.getCreator());
			row.createCell(2).setCellValue(mcp.getLeaseNumber());
			row.createCell(3).setCellValue(mcp.getBpmsn());
			row.createCell(4).setCellValue(mcp.getProValue());	
			row.createCell(5).setCellValue(mcp.getRentValue());
			row.createCell(6).setCellValue(mcp.getRentType());
			row.createCell(7).setCellValue(mcp.getAccepttime());
		}
		
		FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        logger.info(fileName + " written successfully");
	}

	/**
	 * write information of RetireStart to excel file
	 * @param fileName
	 * @param rsps
	 * @throws Exception
	 */
	public void writeListOfRetireStartToExcel(String fileName, List<RetireStartPush> rsps) throws Exception {
		Workbook workbook = null;
		logger.info("rsps' size : " + rsps.size());
		if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }
		
		Sheet sheet = workbook.createSheet("RetireStart");
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("退租创建人");
		header.createCell(1).setCellValue("单元号");
		header.createCell(2).setCellValue("合同编号");
		header.createCell(3).setCellValue("退租创建日期");
		header.createCell(4).setCellValue("原系统流程状态");
		header.createCell(5).setCellValue("原系统流程审批通过日期");
		
		int rowIndex = 1;
		for (RetireStartPush rsp : rsps) {
			Row row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(rsp.getCreator());
			row.createCell(1).setCellValue(rsp.getUnits());
			row.createCell(2).setCellValue(rsp.getLeaseNumber());
			row.createCell(3).setCellValue(rsp.getCreateAt());
			row.createCell(4).setCellValue(rsp.getStatus());	
			row.createCell(5).setCellValue(rsp.getPassTime());
		}
		
		FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        logger.info(fileName + " written successfully");
	}
}
