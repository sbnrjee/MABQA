package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public abstract class Reporter {
	public ExtentTest test;
	public static ExtentReports extent;
	public String testCaseName, testDescription, category, authors;
	public static ArrayList<String> screenshots=new ArrayList<String>();
	public static String runResult="No Run";
	

	
	public void reportStep(String desc, String status) {
		
		String imageid=null;
		long snapNumber = 100000l;
		
		try {
			
			snapNumber= takeSnap();
			imageid="./reports/images/"+snapNumber+".jpg";
			screenshots.add(imageid);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
			// Write if it is successful or failure or information
			if(status.toUpperCase().equals("PASS")){
				this.test.log(LogStatus.PASS, desc+test.
						addScreenCapture("./../reports/images/"+snapNumber+".jpg"));
						runResult="Passed";
			}else if(status.toUpperCase().equals("FAIL")){
				this.test.log(LogStatus.FAIL, desc+test.addScreenCapture("./../reports/images/"+snapNumber+".jpg"));
				runResult="Failed";
				//throw new RuntimeException("FAILED");
			}else if(status.toUpperCase().equals("INFO")){
				this.test.log(LogStatus.INFO, desc);
				runResult="Not Completed";
			}
		
		
		
	}

	public void verifyStep(String desc, String status){	
		long snapNumber = 100000l;
		String imageid=null;
		try {
			snapNumber= takeSnap();
			imageid="./reports/images/"+snapNumber+".jpg";
			screenshots.add(imageid);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
			// Write if it is successful or failure or information
			if(status.toUpperCase().equals("PASS")){
				this.test.log(LogStatus.PASS, desc+test.
				addScreenCapture("./../reports/images/"+snapNumber+".jpg"));
				runResult="Passed";
			}else if(status.toUpperCase().equals("FAIL")){
				this.test.log(LogStatus.FAIL, desc+test.addScreenCapture("./../reports/images/"+snapNumber+".jpg"));
				runResult="Failed";
				//throw new RuntimeException("FAILED");
			}else if(status.toUpperCase().equals("INFO")){
				this.test.log(LogStatus.INFO, desc);
				runResult="Not Completed";
			}
		
		
	}
	
	public abstract long takeSnap();
	
	

	public ExtentReports startResult(){
		try{
			extent = new ExtentReports("./reports/result.html", true);
			extent.loadConfig(new File("./src/main/resources/extent-config.xml"));
		}catch(Exception e){
			System.out.println("exception throwed in startResult method");
		}
		
		return extent;
		
	}

	public ExtentTest startTestCase(String testCaseName, String testDescription){
		try{
			test = extent.startTest(testCaseName, testDescription);
		}catch(Exception e){
			System.out.println("Exception throwd in startTestCase method");
		}
		return test;
	}

	public void endResult(){
		try{
			extent.flush();
			System.out.println("result has been saved");
		}
		catch(Exception e){
			System.out.println("Exception throwed while endResult method");
		}
		
	}
	
	public void endTestcase(){
		try{
			
			new Attachment().createDoc(testCaseName, screenshots);
			extent.endTest(test);
			screenshots.clear();
		}catch(Exception e){
			System.out.println("Exception throwed while endTestcae method");
		}
		
	}
	
	public String getTestRunStatus(){
		return runResult;
	}
	
	public void refreshRunStatus(){
		runResult=null;
	}
	
	
	
	
}
