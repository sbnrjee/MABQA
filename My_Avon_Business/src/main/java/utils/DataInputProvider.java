package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataInputProvider {
	

	public static String[][] getSheet(String folderName, String dataSheetName, String applicationType) {

		String[][] data = null;
		FileInputStream fis;

		try {
			if(applicationType.equalsIgnoreCase("angular")) {
			fis = new FileInputStream(new File("./data/angular/"+folderName+"/"+dataSheetName+".xlsx"));
			}else
			{
				fis = new FileInputStream(new File("./data/magnolia/"+dataSheetName+".xlsx"));
			}
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);	

			// get the number of rows
			int rowCount = sheet.getLastRowNum();

			// get the number of columns
			int columnCount = sheet.getRow(0).getLastCellNum();
			data = new String[rowCount][columnCount];
			DataFormatter df= new DataFormatter();

			// loop through the rows
			for(int i=1; i <rowCount+1; i++){
				try {
					XSSFRow row = sheet.getRow(i);
					for(int j=0; j <columnCount; j++){ // loop through the columns
						try {
							String cellValue = "";
							
							try{
								
								cellValue = df.formatCellValue(row.getCell(j));
								
								System.out.println(cellValue);
								
							}catch(NullPointerException e){

							}

							data[i-1][j]  = cellValue; // add to the data array
						} catch (Exception e) {
 							e.printStackTrace();
						}				
					}

				} catch (Exception e) {
 					e.printStackTrace();
				}
			}
			fis.close();
			workbook.close();
		} catch (Exception e) {
 			e.printStackTrace();
		}

		return data;
	
	}
	
	public static String getData(int row, String Sheetname){
		String data = null;
		return data;
	}

	 public static String getValue(String excelName,int sheetNumber, int row, int column) {

   	  String cellValue = "";
 		FileInputStream fis;
 		DataFormatter df= new DataFormatter();
 		try {
 			
 			fis = new FileInputStream(new File("./data/magnolia/"+excelName+".xlsx"));
 			
 			XSSFWorkbook workbook = new XSSFWorkbook(fis);
 			XSSFSheet sheet = workbook.getSheetAt(sheetNumber);	
 			XSSFRow rows = sheet.getRow(row);
 			cellValue = df.formatCellValue(rows.getCell(column));
 			
 			fis.close();
 			workbook.close();
 		} catch (Exception e) {
  			e.printStackTrace();
 		}

 		return cellValue;
 	
 	}
	 
	 public static int getRowCount(String excelName,int sheetNumber) {
		 FileInputStream fis;
		 int rowCount = 0;
			try {
				fis = new FileInputStream(new File("./data/magnolia/"+excelName+".xlsx"));
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workbook.getSheetAt(sheetNumber);	

				// get the number of rows
				rowCount = sheet.getLastRowNum();
				fis.close();
	 			workbook.close();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			return rowCount;

}
	 
	 public static List<String> getSheetsList(String excelName) throws IOException{
	       File src=new File("./data/magnolia/"+excelName+".xlsx");
	       FileInputStream fis=new FileInputStream(src);
	       
	       XSSFWorkbook book=new XSSFWorkbook(fis);
	       XSSFSheet sheet=book.getSheetAt(0);
	       
	       int row=sheet.getLastRowNum();
	       List <String> sheets=new ArrayList<String>();
	       for(int i=1;i<=row;i++){
	              sheets.add(sheet.getRow(i).getCell(0).getStringCellValue());
	              
	              
	       }
	       return sheets;
	}
	 
	 public static String getSheetName(String excelName,int i) {
		 FileInputStream fis;
		 String  sheetName = "";
			try {
				fis = new FileInputStream(new File("./data/magnolia/"+excelName+".xlsx"));
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workbook.getSheetAt(i);	

				// get the number of rows
				sheetName = sheet.getSheetName();
	 			workbook.close();
				}catch (Exception e) {
					
					e.printStackTrace();
				}
			return sheetName;

}
}
