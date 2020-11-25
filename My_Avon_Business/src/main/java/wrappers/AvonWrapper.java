package wrappers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.LogStatus;


import utils.DataInputProvider;
import utils.ExcelReadWrite;
import utils.ReadExcel;
import utils.Reporter;

public class AvonWrapper extends GenericWrappers {
	
	public String browserName;
	public String dataSheetName;
	public String market;
	public String applicationType;
	
	public HashMap<String, String> reportUrl=new HashMap<>();
	
	//public static int row=1;
	
		
	@BeforeSuite(alwaysRun=true)
	public void beforeSuite() {
		startResult();
		
		//new DBConn().connectToDB();
	}
	
	
	@BeforeTest
	public void beforeTest(){
		
		
	}
	

	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(){
//		test = startTestCase(testCaseName, testDescription);
//		test.assignCategory(category);
//		test.assignAuthor(authors);
//		loadObjects(market,applicationType);
//		invokeApp(browserName);
	}
		
	@AfterSuite(alwaysRun=true)
	public void afterSuite() {
		endResult();
/*		
		//run bat file to attach result to QC
		try{
			Runtime.
			   getRuntime().
			   exec("cmd /c start D:\\ALM_Updation\\UploadToQC.bat");
		}
		catch(Exception e){
		}
		*/
		
	}

	@AfterTest(alwaysRun=true)
	public void afterTest(){
		
	/*	Properties prop=new Properties();
		FileWriter fw;
		
		try {
			fw = new FileWriter(new File("./reports/ReportUrl.properties"),true);
		
		BufferedWriter bw=new BufferedWriter(fw);
				
			 for(Map.Entry m:reportUrl.entrySet()){    
			//  prop.setProperty(m.getKey().toString(), m.getValue().toString());
			  bw.write((m.getKey().toString()+"="+ m.getValue().toString()));
			  bw.write("\n");
		      } 
			 bw.close();
			 fw.close();*/
		unloadObjects();
		
	}
	
	@AfterMethod(alwaysRun=true)
	public void afterMethod() throws IOException{

		org.openqa.selenium.Capabilities capabilities = driver.getCapabilities();

		String deviceName = (String) capabilities.getCapability("deviceName");

		String reportPdfUrl = (String) capabilities.getCapability("testGridReportUrl");
	//	String reportPdfUrl2 = (String) capabilities.getCapability("singleTestReportUrl");

		//System.out.println("Report PDF Url of device " + deviceName + " is : " + reportPdfUrl2);
		
		reportUrl.put(testCaseName+"-"+capData.get("Market")+"-"+capData.get("DeviceName"), reportPdfUrl);
		endTestcase();
		quitBrowser();
		
		
		
	}
	
	
	@DataProvider(name="fetchData")
	public Object[][] getData(){
			//System.out.println(market);
		return DataInputProvider.getSheet(market,dataSheetName,applicationType);		
		
	}	
	
	@AfterMethod(alwaysRun=true)
	@Parameters({"Automation","Browser","Market","PlatformName","PlatformVersion","DeviceName","udid","AppiumVersion","DeviceOrientation","Application"})
	public void setDataAgain(String automation,String browser, String mrkt,String platformNam, String platformVer, String deviceNam, String udid, String appiumVer, String devOrientation,String application) throws FileNotFoundException, IOException
	{
		capData.remove(capData);
		capData.put("Automation", automation);
		capData.put("Browser", browser);
		capData.put("Market", mrkt);
		
		capData.put("PlatformName", platformNam);
		capData.put("PlatformVersion", platformVer);
		capData.put("DeviceName", deviceNam);
		capData.put("udid", udid);
		capData.put("AppiumVersion", appiumVer);
		capData.put("DeviceOrientation", devOrientation);
		capData.put("TestCaseName", testCaseName);
		
	}
	

}






