package PAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.bcel.verifier.VerifierFactoryListModel;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;

import overlays.CartOverlay;
import overlays.FlashSalesOverlay;
import overlays.SalesToolsOverlay;
import pageobjects.CartPage;
import pageobjects.CheckoutPage;
import pageobjects.DashboardPage;
import pageobjects.FlashSalesPage;
import pageobjects.Loginpage;
import pageobjects.OffersPage;
import pageobjects.OrderBuilderPage;
import pageobjects.OrderConfirmationPage;
import pageobjects.ProductCatalogSearchPage;
import pageobjects.SalesToolsPage;
import pageobjects.SelectCustomerPage;
import pageobjects.OrderTrackingPage;
import utils.DBConn;
import utils.ReadExcel;
import wrappers.AvonWrapper;

public class PlaceAnOrderFlow extends AvonWrapper {
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
	
	@Test()
	public void startAnOrder() {
		
		db= new DBConn(market);
		testCaseName="MAB Order Placement Flow in "+market+" with "+browserName;
		startExtentReport(market, applicationType, browserName);
		String userName=db.OSAUser(getMarketId(market));
		/*String campaignNumber=;
		String CampaignYearNumber=;
		System.out.println(userName);*/

		new Loginpage(driver,test,market, capData)
			.cookieAcceptance()
			.loginAs(userName)
			.withPassword()
			.login()
			.validateSuccessfulLogin()
		
		
			.navigateToOrderTracking(market);
		
		new OrderTrackingPage(driver, test, market, capData)
			.StartAnOrder();
		new SelectCustomerPage(driver, test, market, capData)
			.selectCustomer();
		String line_nr=db.getSingleValidProduct(getMarketId(market), userName);
		new OrderBuilderPage(driver, test, market, capData)
			.productEntry(line_nr)
			.addProduct()
			.verifyProduceAdditonSuccessBanner()
			.verifyOrderNo()
			.validateProductsInOrder()
			.proceedToSalesTools();
		new SalesToolsOverlay(driver, test, market, capData)
			.handleOverlay();
		new SalesToolsPage(driver, test, market, capData)
			.navigateToOffers();
		new OffersPage(driver, test, market, capData)
			.navigateToCart();
		new CartOverlay(driver, test, market, capData)
		.handleOverlay();
		new CartPage(driver, test, market, capData)
			.noOfCustomerInOrder()
			.navigateToCheckout();
		new CheckoutPage(driver, test, market, capData)
			.taxConsent()
			.selectDeliveryMethod()
			.validateSelectedDeliveryMethod()
			.selectDeliveryAddress()
			.validateSelectedDeliveryAddress()
			.selectPaymentMethod()
			.validateSelectedPaymentMethod()
			.handleTermsAndCondition()
			.navigateToFlashSales();
		new FlashSalesOverlay(driver, test, market, capData)
			.navigateToFlashSales();
		new FlashSalesPage(driver, test, market, capData)
			.viewCart()
			.checkout();
			new OrderConfirmationPage(driver, test, market, capData)
			.verifyOrderDetails()
			.trackCurrentOrder();
		
		
	}
}