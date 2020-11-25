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

public class QuickViewPage extends AvonWrapper{
	private static Properties prop;
	public Map<String,String> capData1 = new HashMap<String,String>();
	DBConn db;
	String userName,loginType;
	public QuickViewPage(RemoteWebDriver driver, ExtentTest test, String mrkt, Map<String,String> capData1)
	{
		this.driver = driver;
		this.test = test;
		//this.userName=userNam;
		this.capData1=capData1;
		//this.loginType=loginTyp;
		this.market=mrkt;
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/QuickView.properties")));
			db= new DBConn(mrkt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public QuickViewPage verifyQuickViewIsDisplayed() 
	{
		
		if(verifyElementIsDisplayed(prop.getProperty("quickView"))) {
			
			verifyStep("QuickView is displayed", "PASS");
		}
		else {
			
			verifyStep("QuickView is not displayed", "FAIL");
		}
		
		return this;
	}
	
	public QuickViewPage UIValidation() 
	{
		
		if(verifyElementIsDisplayed(prop.getProperty("productNm"))) {
			
			verifyStep("Product Name is displayed", "PASS");
		}
		else {
			
			verifyStep("Product Name is not displayed", "FAIL");
		}

		if(verifyElementIsDisplayed(prop.getProperty("broucherPrice"))) {
			
			verifyStep("Broucher Price is displayed", "PASS");
		}
		else {
			
			verifyStep("Broucher Price is not displayed", "FAIL");
		}
		
		if(verifyElementIsDisplayed(prop.getProperty("discountedPrice"))) {
			
			verifyStep("Discounted price is displayed", "PASS");
		}
		else {
			
			verifyStep("Discounted Price is not displayed", "INFO");
		}
		if(verifyElementIsDisplayed(prop.getProperty("categoryTag"))) {
	
			verifyStep("Category Tag is displayed", "PASS");
		}
		else {
	
			verifyStep("Category Tag is not displayed", "FAIL");
		}

		if(verifyElementIsDisplayed(prop.getProperty("volumeInfo"))) {
	
			verifyStep("Volumne Info is displayed", "PASS");
		}
		else {
	
			verifyStep("Volumne Info is not displayed", "FAIL");
		}

		if(verifyElementIsDisplayed(prop.getProperty("availableShade"))) {
	
			verifyStep("Available Shades are displayed", "PASS");
			if(verifyElementIsDisplayed(prop.getProperty("shadeImage"))) {
				
				verifyStep("Shade images are displayed", "PASS");
			}
			else {
		
				verifyStep("Shade images are  not displayed", "FAIL");
			}
		}
		else {
	
			verifyStep("Available Shades are not displayed", "INFO");
		}

		

		if(verifyElementIsDisplayed(prop.getProperty("pdpViewLink"))) {
	
			verifyStep("View details link is displayed", "PASS");
		}
		else {
	
			verifyStep("View details link is not displayed", "FAIL");
		}
		
		if(verifyElementIsDisplayed(prop.getProperty("closeQuickView"))) {
			
			verifyStep("Close QuickView CTA is displayed", "PASS");
		}
		else {
	
			verifyStep("Close QuickView CTA is not displayed", "FAIL");
		}
		
		if(verifyElementIsDisplayed(prop.getProperty("addToNewOrder"))) {
	
			verifyStep("Create new order CTA is displayed", "PASS");
		}
		else {
	
			verifyStep("Create new order CTA is not displayed", "FAIL");
		}

		if(verifyElementIsDisplayed(prop.getProperty("addToCurrentOrder"))) {
	
			verifyStep("Add to current pending order CTA is displayed", "PASS");
		}	
		else {
	
			verifyStep("Add to current pending order CTA", "FAIL");
		}
		
		return this;
	}
	
	public QuickViewPage createNewOrder()
	{
		
		
		return this;
	}
	
	
	public QuickViewPage addToExistingOrder()
	{
		if(verifyElementIsDisplayed(prop.getProperty("availableShade"))) 
			
		{
			verifyStep("Selecting shade to order Product", "PASS");
			click(prop.getProperty("availableShade"));
			if(verifyElementIsDisplayed(prop.getProperty("increaseQTY"))) 
			{
				click(prop.getProperty("increaseQTY"));
				click(prop.getProperty("confirmQTY"));
				verifyStep("Shade product is selected", "PASS");
			}
			else {
				verifyStep("No Popup is displayed upon selecting shades", "FAIL");
			}
		}
		
		verifyStep("Adding product to existing order", "INFO");
		click(prop.getProperty("addToCurrentOrder"));
		
		if(verifyElementIsDisplayed(prop.getProperty("noQtyMentionedError")))
		{
			verifyStep("Error creating order, item size is not selected", "FAIL");
			
		}
		
		
		return this;
	}
}
