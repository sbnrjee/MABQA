package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadWrite {
	public String Filepath="D://ALM_Updation//Test_Result_Status.xlsx";
	public String sheet="Test_Result";
	
	public void writeResultToXl(String testCaseName, String result, int row) throws IOException{
		System.out.println("write result to excel sheet...");
		System.out.println(WriteXLSXFile(Filepath, sheet, testCaseName, 0, row, 1));
		System.out.println(WriteXLSXFile(Filepath, sheet, result, 1, row, 0));
	}
	
public static String WriteXLSXFile(String Filepath,String sheet,String Value,int col,int row, int condition) throws IOException {
		
		
		String val=Value;
		
		 String mainSheet = sheet;
		
		 FileInputStream file = new FileInputStream(Filepath);
	     XSSFWorkbook wb = new XSSFWorkbook(file);
	     XSSFSheet sheet1 = wb.getSheet(mainSheet);
	     if (condition==1){
	    	 sheet1.createRow(row).createCell(col).setCellValue(val);
	     }
	     else{
	    	 sheet1.getRow(row).createCell(col).setCellValue(val);
	     }
	     
	     file.close();
	     FileOutputStream fileOut = new FileOutputStream(Filepath);
	     wb.write(fileOut);
	     wb.close();
	     
		    fileOut.flush();
		   
		   fileOut.close();
		
		  return val;
		
	}
}
