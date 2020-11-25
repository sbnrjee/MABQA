package pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import utils.DBConn;
import wrappers.AvonWrapper;

public class FlashSalesPage extends AvonWrapper{
	
	private static Properties prop;
	DBConn db;
	String userName,loginType,dateFormat,currencySeparator,secondCurrencySeparator;
	
	public Map<String,String> capData1 = new HashMap<String,String>();
	public FlashSalesPage (RemoteWebDriver driver, ExtentTest test, String mrkt,  Map<String,String> capData1)
	{
		this.driver = driver;
		this.test = test;
		
		this.market=mrkt;
		this.capData1=capData1;
		if(market.equals("hungary")) {

			dateFormat="yyyy.MM.dd"; 
		}else{
			dateFormat="dd.MM.yyyy"; 
		}
		
		if(market.equals("romania")) {

			currencySeparator="."; 
		
		}else if(market.equals("hungary")){
			currencySeparator=" "; 
		}
		else{
			currencySeparator=","; 
			secondCurrencySeparator=".";
		}
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/FlashSalesPage.properties")));
			db= new DBConn(mrkt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FlashSalesPage validateUI()
	{
		
		return this;
	}
	
	public FlashSalesPage viewCart()
	{
		pageWaitMax();
		if(verifyElementIsDisplayed(prop.getProperty("viewCart"))) {
			click(prop.getProperty("viewCart"));
			verifyStep("Navigated to Flash Sales CART", "PASS");
		}
		else {
			verifyStep("Cart CTA is not displayed", "FAIL");
		}
		return this;
	}
	
	public FlashSalesPage checkout() {
		
		if(verifyElementIsDisplayed(prop.getProperty("checkoutFromFlashSales"))) {
			click(prop.getProperty("checkoutFromFlashSales"));
			verifyStep("Checkout from Flash sales", "PASS");
		}
		else {
			verifyStep("Submit and Pay is not displayed", "FAIL");
		}
		
		return this;
		
	}
	
	
}
