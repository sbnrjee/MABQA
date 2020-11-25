package quickView;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageobjects.DashboardPage;
import pageobjects.Loginpage;
import pageobjects.ProductCatalogSearchPage;
import pageobjects.QuickViewPage;
import utils.DBConn;
import utils.ReadExcel;
import wrappers.AvonWrapper;

public class QuickViewAddProductToActivePendingOrderTest extends AvonWrapper {
	DBConn db;
	@BeforeClass
	@Parameters({"Automation","Browser","Market","PlatformName","PlatformVersion","DeviceName","udid","AppiumVersion","DeviceOrientation","Application"})
	public void setData(String automation,String browser, String mrkt,String platformNam, String platformVer, String deviceNam, String udid, String appiumVer, String devOrientation,String application) throws FileNotFoundException, IOException {
		testCaseName=" ";

		testDescription=" ";
		category="Functional";
		browserName=browser;
		market=mrkt;
		applicationType=application;
		authors="MAB Automation Team";
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
		try {
			readExcel.openExcel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	@Test
	public void quickViewAddProductToActivePendingOrderTest() 
	{
		
		
		db= new DBConn(market);
		testCaseName="Verify profile links and update contact info-"+market+"-"+browserName;
		startExtentReport(market, applicationType, browserName);
		String userName=db.OSAUser(getMarketId(market));
		
		new Loginpage(driver,test,market, capData)
		//.login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);
		//.loginLanding()
		.cookieAcceptance()
		.loginAs(userName)
			.withPassword()
			.login();
		new DashboardPage(driver, test, capData)
		.navigateToProductCatalogue(market);;
		new ProductCatalogSearchPage(driver, test, market,  capData)
		.landing()
		.validateCategory()			
		.browsingProductFromCategory()
		.navigationToQuickView();
		new QuickViewPage(driver, test, market, capData)
			.verifyQuickViewIsDisplayed()
			.addToExistingOrder();
	}
}
