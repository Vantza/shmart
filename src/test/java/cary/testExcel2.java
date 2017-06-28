package cary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


public class testExcel2 {
	static Workbook workbook = null;
	public static void main(String[] args) throws InvalidFormatException, IOException {
		testExcel2 obj = new testExcel2();
		List<Sheet> sheetList = new ArrayList<Sheet>();
        // 此处为我创建Excel路径
        File file = new File("C:/Users/cary.cao/Desktop/cycycy.xls");  
        sheetList = obj.readExcelSheetPOI(file);
        Sheet sheet = sheetList.get(0);
        int rowNo = sheet.getLastRowNum();
        for(int i=0; i<rowNo; i++) {
        	if (sheet.getRow(i).getCell(2) == null || sheet.getRow(i).getCell(8) == null) {
        		sheet.getRow(i).createCell(2).setCellValue("");
        		sheet.getRow(i).createCell(8).setCellValue("");
        	} else {
        		if (sheet.getRow(i).getCell(2).getStringCellValue().trim()!=sheet.getRow(i).getCell(8).getStringCellValue().trim()) {
            		if (!sheet.getRow(i).getCell(8).getStringCellValue().trim().isEmpty()) {
            			System.out.println("第"+(i+1)+"行 "+sheet.getRow(i).getCell(2).getStringCellValue()+" 与 "+
                        		sheet.getRow(i).getCell(8).getStringCellValue() + " 不一样");
            		}
            	}
        	}
        }
        System.out.println("finish");
	}
	
	// get sheet list
	public List<Sheet> readExcelSheetPOI(File file) throws InvalidFormatException, IOException {
		List<Sheet> list = new ArrayList<Sheet>();
        FileInputStream is = new FileInputStream(file); //文件流  
        workbook = WorkbookFactory.create(is); //这种方式 Excel 2003/2007/2010 都是可以处理的  
        int sheetCount = workbook.getNumberOfSheets();  //Sheet的数量
        
        for (int s=0; s<sheetCount; s++) {
        	list.add(workbook.getSheetAt(s));
        }
        return list;
    }
	
	
}
