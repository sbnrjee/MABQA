package pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import utils.DBConn;
import wrappers.AvonWrapper;

public class CartPage extends AvonWrapper{
	
	private static Properties prop;
	DBConn db;
	String userName,loginType,dateFormat,currencySeparator,secondCurrencySeparator;
	
	public Map<String,String> capData1 = new HashMap<String,String>();
	public CartPage (RemoteWebDriver driver, ExtentTest test, String mrkt,  Map<String,String> capData1)
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
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/PAOCartDisplay.properties")));
			db= new DBConn(mrkt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public CartPage validateUI()
	{
		
		return this;
	}
	public CartPage noOfCustomerInOrder() {
		
		pageWaitMid();
		if(getAllLinks(prop.getProperty("noOfCustomer")).size()>0) 
		{
			scrollToAnElement(prop.getProperty("noOfCustomer"));
		
		enterText(prop.getProperty("noOfCustomer"), "1");
		verifyStep("HandledNo of customer in current order "+market, "INFO");
		
		}
		{
			verifyStep("No Of customer is not in scope of current market: "+market, "INFO");
		}
		return this;
	}
	public CartPage navigateToCheckout() {
		
		scrollToAnElement(prop.getProperty("continueToCheckout"));
		if(verifyElementIsDisplayed(prop.getProperty("continueToCheckout"))) 
		{
			verifyStep("Navigate to Checkout CTA is displayed", "PASS");
			click(prop.getProperty("continueToCheckout"));
		}
		else
		{
			verifyStep("Navigate to Chekout CTA is not displayed", "FAIL");
		}
		
		
		return this;
	}
}
