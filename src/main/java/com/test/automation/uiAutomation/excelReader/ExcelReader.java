package com.test.automation.uiAutomation.excelReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	private static Logger log = Logger.getLogger(ExcelReader.class.getName());
	
	String path;
	private FileInputStream fis = null;
	//private FileOutputStream fos;
	private XSSFWorkbook wb;
	
	public ExcelReader(String path) {
		
		this.path = path;
		try {
			fis = new FileInputStream(new File(path));
			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			log.error("Cannot find or open " + path);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			log.error("Excel Reader error");
			e.printStackTrace();
			System.exit(1);
		} finally {
			if(fis!= null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Object[][] getDataFromSheet(String sheetName) {
		Object[][] object = null;
		XSSFSheet sheet = wb.getSheet(sheetName);
		int lastRowNum = sheet.getLastRowNum();
		int lastCellNum = sheet.getRow(0).getLastCellNum();
		
		object = new Object[lastRowNum][lastCellNum];
		
		log.info("LAST CELL # : " + lastCellNum + " , LAST ROW # : " + lastRowNum);
		
		for(int i = 1; i <= lastRowNum; i++) {
			XSSFRow row = sheet.getRow(i);
			for(int j = 0; j < lastCellNum; j++) {
				object[i - 1][j] = getStringCellValue(row.getCell(j));
				
			}
		}
		return object;
	}
	
	@SuppressWarnings("deprecation")
	private String getStringCellValue(XSSFCell cell) {
		String result = null;
		if(cell == null) {
			log.error("cell == null");
		}
		switch(cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			result = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			result = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			result = "";
		default:
			break;
		}
		return result;
	}
	
	public String getStringCellValue(String sheetName, int row, int col) {
		String result = null;
		
			try {
				XSSFSheet sheet = wb.getSheet(sheetName);
				XSSFCell cell = sheet.getRow(row).getCell(col);
				result = getStringCellValue(cell);
			} catch(Exception e) {
				e.printStackTrace();
			}
			return result;
		
	}
	
}
