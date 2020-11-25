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

public class OrderTrackingPage extends AvonWrapper{
	
	private static Properties prop;
	DBConn db;
	String userName,loginType,dateFormat,currencySeparator,secondCurrencySeparator;
	
	public Map<String,String> capData1 = new HashMap<String,String>();
	public OrderTrackingPage(RemoteWebDriver driver, ExtentTest test, String mrkt,  Map<String,String> capData1)
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
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/PAOStartOrder.properties")));
			db= new DBConn(mrkt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public OrderTrackingPage verifyUIElements() {
		
		return this;
	}
	
	public OrderTrackingPage StartAnOrder() {
		if(verifyElementIsDisplayed(prop.getProperty("startAnOrderCTA"))) 
		{
			verifyStep("Start An Order button is displayed", "PASS");
			click(prop.getProperty("startAnOrderCTA"));
		}
		else {
			verifyStep("Start an Order button is not displayed", "FAIL");
		}
		
		return this;
	}
	
	public OrderTrackingPage trackOrder(String orderNumber)
	{
		click(prop.getProperty("searchDropdown"));
		click(prop.getProperty("searchByOrderNumber"));
		enterText(prop.getProperty("searchTxt"), orderNumber);
		pageWaitMin();
		int size=getAllLinks(prop.getProperty("orderCards")).size();
		if((size==1) && (getText(prop.getProperty("noOfOrd")).replaceAll("[^0-9]", "").equals("1"))) {
			
			verifyStep("Order card is displayed", "PASS");
		}
		else {
			verifyStep("Multiple order card is displayed", "FAIL");
		}
		return this;
	}
}
