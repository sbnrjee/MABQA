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
import org.springframework.core.annotation.Order;

import com.relevantcodes.extentreports.ExtentTest;

import utils.DBConn;
import wrappers.AvonWrapper;

public class OrderBuilderPage extends AvonWrapper{
	
	private static Properties prop;
	DBConn db;
	String userName,loginType,dateFormat,currencySeparator,secondCurrencySeparator;
	
	public Map<String,String> capData1 = new HashMap<String,String>();
	public OrderBuilderPage (RemoteWebDriver driver, ExtentTest test, String mrkt,  Map<String,String> capData1)
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
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/OrderBuilder.properties")));
			db= new DBConn(mrkt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		public OrderBuilderPage verifyUIElements() {
			
			return this;
		}
		
		public OrderBuilderPage productEntry(String line_nr) {
			
			List<WebElement> lineNrInput=getAllLinks(prop.getProperty("lineNrInput"));
			List<WebElement> prodName=getAllLinks(prop.getProperty("prodName"));
			for(int i=0;i<lineNrInput.size();i++) {
				if(lineNrInput.get(i).isDisplayed()) {
					lineNrInput.get(i).sendKeys(line_nr);
					verifyStep("Entered line_nr "+ line_nr +" to create an order", "PASS");
					pageWaitMid();
					if(prodName.get(i).getAttribute("value")!=null && prodName.get(i).getAttribute("value").isEmpty() ) 
					{
						verifyStep("Product Name is not displayed", "FAIL");
					}
					else {
						verifyStep("Product Name is "+prodName.get(i).getAttribute("value"), "PASS");
					}
					break;
				}
				
			}
			return this;
		}
		
		public OrderBuilderPage addProduct() {
			
			if(verifyElementIsDisplayed(prop.getProperty("addProductCTA")))
			{	
				scrollToAnElement(prop.getProperty("addProductCTA"));
				verifyStep("add products to order CTA is enabled ", "PASS");
				
				click(prop.getProperty("addProductCTA"));
				
			}
			else
			{
				verifyStep("add products to order CTA is not enabled ", "FAIL");
			}
			return this;
		}
		
		public OrderBuilderPage verifyProduceAdditonSuccessBanner() 
		{	pageWaitMin();
			if(verifyElementIsDisplayed(prop.getProperty("prodAddedBanner")))
			{
				verifyStep("Product addition successful banner is displayed", "PASS");
			}
			else if(verifyElementIsDisplayed(prop.getProperty("errorBanner"))) 
			{
				verifyStep("Product addition error banner is displayed", "FAIL");
			}
			else
			{
				verifyStep("Product addition successful banner is not displayed", "INFO");
			}
			
			
			return this;
		}
		public OrderBuilderPage verifyOrderNo() 
		{
			if(verifyElementIsDisplayed(prop.getProperty("orderNo")) && null!= getText(prop.getProperty("orderNo")))
			{
				verifyStep( "Order Number "+getText(prop.getProperty("orderNo")) +" generated Successfully", "PASS");
			}
			
			else
			{
				verifyStep( "Order Number not generated ", "FAIL");
				
			}
			
			//DB validation
			return this;
		}
		
		public OrderBuilderPage validateProductsInOrder() 
		{
			List<WebElement> productsinOrder=getAllLinks(prop.getProperty("productsInorder"));
			if(productsinOrder.size()>0)
			{
				scrollToAnElement(prop.getProperty("productsInorder"));
				verifyStep( "Successfully verified added product in the order", "PASS");
			}
			
			else
			{
				verifyStep( "Not able to verify added product in the order. Order Created without any products.", "FAIL");
				
			}
			
			// validation
			return this;
		}
		
		public OrderBuilderPage proceedToSalesTools() {
			scrollToAnElement(prop.getProperty("nxtPage"));
			if(verifyElementIsDisplayed(prop.getProperty("nxtPage"))) {
				verifyStep("Continue to Sales tool CTA is displayed and enabled", "PASS");
				
				click(prop.getProperty("nxtPage"));
			}
			else {
				verifyStep("Continue to Sales tool CTA is not displayed", "FAIL");
			}
			return this;
		}
}
