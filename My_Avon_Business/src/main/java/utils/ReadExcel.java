package utils;


import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Base64;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import wrappers.GenericWrappers;


public class ReadExcel {


	HashMap<String, String> excelValue;

	Workbook wb;

	FileInputStream fs;
	FileOutputStream fo;


	public void openExcel() throws IOException

	{

		String testData=System.getProperty("user.dir");

		fs=new FileInputStream(testData+"//data//DataSheet.xlsx");
		
		wb=new XSSFWorkbook(fs);

	}


	public void excelRead(String testCaseName,Map<String, String> capData1) throws IOException {

		// TODO Auto-generated method stub
		openExcel();
		String sheeetName="generic";
		Sheet sh=wb.getSheet(sheeetName);
		String testSheet="";
		Sheet sprintSh;
		excelValue=new HashMap();

		int lastRow=sh.getLastRowNum();

		int rowNum;

		Row row = null;

		

		for(rowNum =1;rowNum<=lastRow;rowNum++)

		{

			row=sh.getRow(rowNum);

			if(row.getCell(0).getStringCellValue().equalsIgnoreCase(capData1.get("Market").toLowerCase()+"."+testCaseName))
			{
				testSheet=row.getCell(1).getStringCellValue();
				break;
			}

		}

		sprintSh=wb.getSheet(testSheet);
		
		Row row0=sprintSh.getRow(0);
		lastRow=sprintSh.getLastRowNum();
		for(rowNum =1;rowNum<=lastRow;rowNum++)

		{
			row=sprintSh.getRow(rowNum);

			if(row.getCell(0).getStringCellValue().equalsIgnoreCase(capData1.get("Market").toLowerCase()+"."+testCaseName))
			{
				break;
			}

		}
		for(int i=1;i<row.getLastCellNum();i++)

		{


			if(row.getCell(i).getCellType()==1)

				excelValue.put(row0.getCell(i).getStringCellValue(), row.getCell(i).getStringCellValue());

			else if(row.getCell(i).getCellType()==0)

				excelValue.put(row0.getCell(i).getStringCellValue(), row.getCell(i).getNumericCellValue()+"");

			else if(row.getCell(i).getCellType()==3)

				continue;

		}
		wb.close();
		fs.close();
		
	}


	public String getValue(String key) 

	{
		
		return excelValue.get(key);
	

	}

	public void writeExcel(String testCaseName,String key,String value,Map<String, String> capData1) throws IOException {

		// TODO Auto-generated method stub
		openExcel();
		String sheeetName="generic";
		Sheet sh=wb.getSheet(sheeetName);
		String testSheet="";
		Sheet sprintSh;
		

		int lastRow=sh.getLastRowNum();

		int rowNum;

		Row row = null;

		

		for(rowNum =1;rowNum<=lastRow;rowNum++)

		{

			row=sh.getRow(rowNum);

			if(row.getCell(0).getStringCellValue().equalsIgnoreCase(capData1.get("Market").toLowerCase()+"."+testCaseName))
			{
				testSheet=row.getCell(1).getStringCellValue();
				System.out.println(testSheet);
				break;
			}

		}

		sprintSh=wb.getSheet(testSheet);
		
		Row row0=sprintSh.getRow(0);
		lastRow=sprintSh.getLastRowNum();
		for(rowNum =1;rowNum<=lastRow;rowNum++)

		{
			row=sprintSh.getRow(rowNum);

			if(row.getCell(0).getStringCellValue().equalsIgnoreCase(capData1.get("Market").toLowerCase()+"."+testCaseName))
			{
				System.out.println(row);
				for(int i=1;i<row0.getLastCellNum();i++)

				{
					
					System.out.println(row0.getCell(i).getStringCellValue());
						if(row0.getCell(i).getStringCellValue().equals(key)){
							
							row.createCell(i).setCellValue(value);
							System.out.println(row.getCell(i).getStringCellValue());
							break;
						}
						
				}
				
			}

		}

		String testData=System.getProperty("user.dir");
		fo=new FileOutputStream(testData+"//data//DataSheet.xlsx");
		wb.write(fo);
		
		wb.close();
		fo.close();
		
	}

	public void closeExcel() throws IOException

	{

		wb.close();

		fs.close();

	}


}

