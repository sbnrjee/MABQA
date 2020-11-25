package pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import utils.DBConn;
import wrappers.AvonWrapper;

public class SelectCustomerPage extends AvonWrapper{
	
	private static Properties prop;
	DBConn db;
	String userName,loginType,dateFormat,currencySeparator,secondCurrencySeparator;
	
	public Map<String,String> capData1 = new HashMap<String,String>();
	public SelectCustomerPage (RemoteWebDriver driver, ExtentTest test, String mrkt,  Map<String,String> capData1)
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
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/selectCustomerPage.properties")));
			db= new DBConn(mrkt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SelectCustomerPage verifyUIElements() {
		
		return this;
	}
	
	public SelectCustomerPage selectCustomer() {
		List<WebElement>connections=getAllLinks(prop.getProperty("connectionList"));
		
		if(connections.size()==0) {
			verifyStep("No Connection available for Current User, Creating Order for self", "PASS");
			orderForMyself();
		}
		else {
			verifyStep("Connection available for Current user", "PASS");
			if(getAttributeValue(prop.getProperty("selectCustomerCTA"), "class").contains("disabled")){
				verifyStep("Select Customer CTA is disabled before Customer Selection", "PASS");
			}
			else {
				verifyStep("Select Customer CTA is not disabled before Customer Selection", "FAIL");
				
			}
			//pending validations are futher coding required to remove hardcoding
			
			
			
			
			connections.get(0).click();
			
			if(getAttributeValue(prop.getProperty("selectCustomerCTA"), "class").contains("disabled")){
				verifyStep("Select Customer CTA is disabled after Customer Selection", "FAIL");
			}
			else {
				verifyStep("Select Customer CTA is enabled after Customer Selection", "PASS");
				click(prop.getProperty("selectCustomerCTA"));
			}
		}
		
		
		return this;
	}
	
	public SelectCustomerPage orderForMyself() {
		
		
		
		if(verifyElementIsDisplayed(prop.getProperty("orderForMyself"))) 
		{
			verifyStep("Order for Myself button is displayed", "PASS");
			click(prop.getProperty("orderForMyself"));
		}
		else {
			verifyStep("Order for Myself button is not displayed", "FAIL");
		}
		
		return this;
	}
}
