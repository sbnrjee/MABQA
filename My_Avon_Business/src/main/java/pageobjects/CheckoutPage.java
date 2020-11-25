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

public class CheckoutPage extends AvonWrapper{
	
	private static Properties prop;
	DBConn db;
	String userName,loginType,dateFormat,currencySeparator,secondCurrencySeparator;
	
	public Map<String,String> capData1 = new HashMap<String,String>();
	public CheckoutPage (RemoteWebDriver driver, ExtentTest test, String mrkt,  Map<String,String> capData1)
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
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/PAOCheckout.properties")));
			db= new DBConn(mrkt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public CheckoutPage validateUI()
	{
		
		return this;
	}
	public CheckoutPage taxConsent() 
	{
		pageWaitMid();
		if(getMarketId(market).equals("30")) {
		
		
		scrollToAnElement(prop.getProperty("privateTax"));
		if (verifyElementIsDisplayed(prop.getProperty("privateTax")))
		{
			scrollToAnElement(prop.getProperty("privateTax"));
			verifyStep("Tax Consent -Personal is displayed", "PASS");
		}
		else 
		{
			verifyStep("Tax Consent -Personal is not displayed", "FAIL");
		}
		
		if (verifyElementIsDisplayed(prop.getProperty("businessTax")))
		{
			verifyStep("Tax Consent -Business is displayed", "PASS");
		}
		else 
		{
			verifyStep("Tax Consent -Business is not displayed", "FAIL");
		}
		click(prop.getProperty("privateTax"));
		}
		else {
			verifyStep("Tax COnsent is not in scope for current market: "+market, "INFO");
		}
		return this;
	}
	
	public CheckoutPage selectDeliveryMethod()
	{
		List<WebElement> deliveryMethod= getAllLinks(prop.getProperty("shippingMethod"));
		if(deliveryMethod.size()>0 && deliveryMethod.get(0).isDisplayed()) 
		{
		scrollToAnElement(deliveryMethod.get(0));
		verifyStep("Delivery Methods are displayed", "PASS");
		
		deliveryMethod.get(0).click();
		}
		else
		{
			verifyStep("Delivery Methods are not displayed and PreSelected", "INFO");
		}
		
		
		
		
		return this;
	}
	
	public CheckoutPage validateSelectedDeliveryMethod()
	{
		pageWaitMin();
		scrollToAnElement(prop.getProperty("selectedShippingMethod"));
		if(verifyElementIsDisplayed(prop.getProperty("selectedShippingMethod"))) {
			verifyStep("Selected Delivery method is displayed properly in UI", "PASS");
		}
		else {
			verifyStep("Selected Delivery method is not displayed properly in UI", "FAIL");
		}
		return this;
	}
	
	public CheckoutPage selectDeliveryAddress() 
	{
		pageWaitMid();
		List<WebElement> deliveryAddress= getAllLinks(prop.getProperty("ShippingAddress"));
		if(deliveryAddress.size()>0 && deliveryAddress.get(0).isDisplayed()) 
		{
		scrollToAnElement(deliveryAddress.get(0));
		verifyStep("Delivery Addresses are displayed", "PASS");
		
		deliveryAddress.get(0).click();
		}
		else
		{
			verifyStep("Delivery Addresses are not displayed and PreSelected", "INFO");
		}
		
		return this;
	}
	
	public CheckoutPage validateSelectedDeliveryAddress()
	{
		pageWaitMin();
		scrollToAnElement(prop.getProperty("selectedShippingAddress"));
		if(verifyElementIsDisplayed(prop.getProperty("selectedShippingAddress"))) {
			verifyStep("Selected Delivery Address is displayed properly in UI", "PASS");
		}
		else {
			verifyStep("Selected Delivery Address is not displayed properly in UI", "FAIL");
		}
		return this;
	}
	
	public CheckoutPage selectPaymentMethod()
	{
		List<WebElement> paymentMethod= getAllLinks(prop.getProperty("payemntMethod"));
		if(paymentMethod.size()>0 && paymentMethod.get(0).isDisplayed()) 
		{
		scrollToAnElement(paymentMethod.get(0));	
		verifyStep("Payment Methods are displayed", "PASS");
		
		paymentMethod.get(0).click();
		}
		else
		{
			verifyStep("Payment Methods are not displayed and PreSelected", "INFO");
		}
		
		return this;
	}
	
	
	public CheckoutPage validateSelectedPaymentMethod()
	{
		pageWaitMin();
		scrollToAnElement(prop.getProperty("selectedPaymentMethod"));
		if(verifyElementIsDisplayed(prop.getProperty("selectedPaymentMethod"))) {
			verifyStep("Selected payment method is displayed properly in UI", "PASS");
		}
		else {
			verifyStep("Selected payment method is not displayed properly in UI", "FAIL");
		}
		return this;
	}
	
	public CheckoutPage handleTermsAndCondition() {
		int count=getAllLinks(prop.getProperty("inactiveSubmitCTA")).size();
		if(count>0) 
		{
			scrollToAnElement(prop.getProperty("inactiveSubmitCTA"));
			click(prop.getProperty("t&cInput"));
			verifyStep("Terms and conditions checked", "PASS");
		}
		else
		{
			scrollToAnElement(prop.getProperty("activeSubmitCTA"));
			verifyStep("Terms and Conditions are preselected", "INFO");
		}
		return this;
	}
	
	public CheckoutPage navigateToFlashSales() {
		
		click(prop.getProperty("activeSubmitCTA"));
		verifyStep("Checkout Done, navigating to Flash Sales", "PASS");
		return this;
	}
	
	public CheckoutPage navigateToFalshSales() {
		scrollToAnElement(prop.getProperty("navigateToFlashSales"));
		if(verifyElementIsDisplayed(prop.getProperty("navigateToFlashSales"))) 
		{
			verifyStep("Navigate to Flash Sales CTA is displayed", "PASS");
			click(prop.getProperty("navigateToFlashSales"));
		}
		else
		{
			verifyStep("Navigate to Flash Sales CTA is not displayed", "FAIL");
		}
		
		
		return this;
	}

}
