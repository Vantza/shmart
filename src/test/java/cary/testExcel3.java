package cary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


public class testExcel3 {
	static Workbook workbook = null;
	public static void main(String[] args) throws InvalidFormatException, IOException {
		OutputStream os=null;
		testExcel3 obj = new testExcel3();
		List<Sheet> sheetList = new ArrayList<Sheet>();
        // 此处为我创建Excel路径
        File file = new File("C:/Users/cary.cao/Desktop/cycycy.xls");  
        sheetList = obj.readExcelSheetPOI(file);
        Sheet sheet = sheetList.get(0);
        obj.myMergedRegion(sheet);
        
        os=new FileOutputStream(file);
        workbook.write(os);
        System.out.println("finish!");
	}
	
	
	//合并单元格求和
	public void myMergedRegion(Sheet sheet) {
		List<String> list = getTenantList(sheet,2);
        Map<String, Integer> map = getRepeatStringAndNo(list);
        List<String> NRlist = getNoRepeatTenantList(sheet, 2);
        
        CellRangeAddress cra = null;
        for (String name : NRlist) {
        	cra = new CellRangeAddress(
        			list.indexOf(name),
        			list.lastIndexOf(name),
        			11,
        			11);
        	sheet.addMergedRegion(cra);
        	double d = 0;
        	for (int i=list.indexOf(name); i<list.lastIndexOf(name)+1; i++) {
        		d = d + sheet.getRow(i).getCell(10).getNumericCellValue(); 
        	}
        	System.out.println(d+":"+list.indexOf(name));
        	sheet.getRow(list.indexOf(name)).getCell(11).setCellValue(d);
        }
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
	
	// get useful info list
	public List<String> getTenantList(Sheet sheet, int Index) {
		List<String> list = new ArrayList<String>();
		for(int i=0; i<sheet.getLastRowNum()+1;i++) {
			if (sheet.getRow(i).getCell(Index) != null) {
				if (sheet.getRow(i).getCell(Index).getStringCellValue() != null) {
					list.add(sheet.getRow(i).getCell(Index).getStringCellValue());
				} else {
					list.add("null");
				}
			}
			//System.out.println(i + sheet.getRow(i).getCell(Index).getStringCellValue());
		}
		return list;
	}
	
	// get no repeat info list
		public List<String> getNoRepeatTenantList(Sheet sheet, int Index) {
			List<String> list = new ArrayList<String>();
			for(int i=0; i<sheet.getLastRowNum()+1;i++) {
				if (sheet.getRow(i).getCell(Index) != null) {
					if (!list.contains(sheet.getRow(i).getCell(Index).getStringCellValue())) {
						list.add(sheet.getRow(i).getCell(Index).getStringCellValue());
						//System.out.println(i + sheet.getRow(i).getCell(Index).getStringCellValue());
					}
				}
			}
			return list;
		}
	
	// get repeat string and times
	public Map<String, Integer> getRepeatStringAndNo(List<String> list) {
		HashMap<String,Integer> hashMap=new HashMap<String, Integer>();
		for(String string : list){  
            if(hashMap.get(string)!=null){  //hashMap包含遍历list中的当前元素  
                Integer integer=hashMap.get(string);  
                hashMap.put(string,integer+1);  
                //System.out.println("the element:"+string+" is repeat"+" "+hashMap.get(string));  
            }  
            else{  
                hashMap.put(string,1);
            }  
        } 
		return hashMap;
	}
	
	private Row createRow(Sheet sheet, Integer rowIndex) {  
        Row row = null;  
        if (sheet.getRow(rowIndex) != null) {  
            int lastRowNo = sheet.getLastRowNum();  
            sheet.shiftRows(rowIndex, lastRowNo, 1);  
        }  
        row = sheet.createRow(rowIndex); 
        row.createCell(0);
        row.createCell(2);
        row.getCell(0).setCellValue("");
        row.getCell(2).setCellValue("");
        return row;  
    }  
	
	//增加sheet2中的行数，保证租户名相同行数相同
	public List<Sheet> operateTenantList(Sheet sheet1, Sheet sheet2, Map<String, Integer> map1, Map<String, Integer> map2) {
		List<Sheet> sheetList = new ArrayList<Sheet>();
		//获取两个sheet的关键列
		List<String> NRlist1 = getNoRepeatTenantList(sheet1, 2);
		//List<String> list1 = getTenantList(sheet1, 2);
		//List<String> list2 = getTenantList(sheet2, 0);
		
		int rowNo1,rowNo2;
		for (String string : NRlist1) {
			if (map2.get(string) != null) {
				if (map1.get(string) >= map2.get(string)) {
					rowNo2 = getTenantList(sheet2, 0).lastIndexOf(string)+1;
					for (int i=0; i<(map1.get(string)-map2.get(string)); i++) {
						createRow(sheet2, rowNo2);
						//System.out.println(string+"差"+(map1.get(string)-map2.get(string))+"行");
					}
				} else if (map1.get(string) < map2.get(string)) {
					rowNo1 = getTenantList(sheet1, 2).lastIndexOf(string)+1;
					for (int j=0; j<(map2.get(string)-map1.get(string)); j++) {
						createRow(sheet1, rowNo1);
					}
				}
			} else {
				System.out.println(string+"在sheet2中不存在!");
			}
		}
		sheetList.add(sheet1);
		sheetList.add(sheet2);
		return sheetList;
	}
}
