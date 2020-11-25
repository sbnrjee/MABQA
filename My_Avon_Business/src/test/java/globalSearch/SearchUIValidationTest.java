package globalSearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.bcel.verifier.VerifierFactoryListModel;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;

import pageobjects.DashboardPage;
import pageobjects.GlobalSearchpage;
import pageobjects.Loginpage;
import utils.DBConn;
import utils.ReadExcel;
import wrappers.AvonWrapper;

public class SearchUIValidationTest extends AvonWrapper {
	DBConn db;

	@BeforeClass
	@Parameters({"Automation","Browser","Market","PlatformName","PlatformVersion","DeviceName","udid","AppiumVersion","DeviceOrientation","Application"})
	public void setData(String automation,String browser, String mrkt,String platformNam, String platformVer, String deviceNam, String udid, String appiumVer, String devOrientation,String application) throws FileNotFoundException, IOException {
		testCaseName=" ";

		category="Functional";
		browserName=browser;
		market=mrkt;
		applicationType=application;
		authors="MAB AUtomation Team";
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
		
		ReadExcel readExcel=new ReadExcel();
		 db= new DBConn(market);
		try {
			readExcel.openExcel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
		@Test()
		public void globalSearchUIValidation() throws Exception
		{
				
				testCaseName="Verify global search UI -"+market+"-"+browserName;
				startExtentReport(market, applicationType, browserName);
				//ReadExcel readExcel=new ReadExcel();
				//readExcel.excelRead("profile",capData);
				String userName=db.OSAUser(getMarketId(market));
				//String loginType=readExcel.getValue("loginType");
				
				
				new Loginpage(driver,test,market, capData)
				//.login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);
				//.loginLanding()
				.cookieAcceptance()
				.loginAs(userName)
					.withPassword()
					.login();
				new DashboardPage(driver, test, capData)
				.navigateToProductCatalogue(market);;
				new GlobalSearchpage(driver, test, market, capData)
				.verifyGlobalSearchUIComponent();
				
		}
}