package pageobjects;

import java.io.File;
import utils.DBConn;
import utils.ReadExcel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;

import wrappers.AvonWrapper;

public class Loginpage extends AvonWrapper{
	
	private static Properties prop;
	DBConn db;
	String userName,loginType,dateFormat,currencySeparator,secondCurrencySeparator;
	
	public Map<String,String> capData1 = new HashMap<String,String>();
	public Loginpage(RemoteWebDriver driver, ExtentTest test, String mrkt,  Map<String,String> capData1)
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
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/homepage.properties")));
			db= new DBConn(mrkt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reportErrorMessage(String property){
	
			pageWaitMax();
			List<WebElement> list=getAllLinks(property);
			for(WebElement l:list){
				if(l.getText()!="" || l.getText()!=null){
				reportStep("The error message is : "+l.getText(), "PASS");}}
			
	
	}
		 
	
	
	
	public void verifyLogin(){
		if(checkTitle("MAB Dashboard")==false){
		reportErrorMessage(prop.getProperty("error_msg_list"));
		}
		
		else if(checkTitle("MAB Dashboard")==true){
			reportStep("The user is logged in successfully", "PASS");
		}
		else{
			reportStep("The user is not logged in ", "FAIL");
		}
		
	}
	public void acceptTermsConditions(){
		
	}
	
	
	
	
	
	

/*public Loginpage loginLanding() {
	System.out.println(driver.findElements(getLocator(prop.getProperty("AvonLogo"))).size());
	if(verifyElementIsDisplayed(prop.getProperty("AvonLogo"))) {
		verifyStep("Navigated to Avon Login Page", "PASS");
	}
	else {
		verifyStep("Navigated to Avon Login Page failed", "FAIL");
	}
	return this;
}	*/
public Loginpage cookieAcceptance() {
	if(verifyElementIsDisplayed(prop.getProperty("cookieAcceptanceButton"))) {
		verifyStep("Handling cookie acceptance button", "Info");
		click(prop.getProperty("cookieAcceptanceButton"));
	}
	return this;
}	
	
	public Loginpage loginAs(String OSAUser){
		if (verifyElementIsDisplayed(prop.getProperty("loginbutton")) && OSAUser!=null && !OSAUser.trim().isEmpty()) {
			enterText(prop.getProperty("username"), OSAUser);
		    pageWaitMin();
		    verifyStep("Entered Username ", "PASS");
		}
		else 
		{
			verifyStep("Page loading error or wrong data from DB", "FAIL");
			Assert.assertTrue(false);
		}
		    return this;
	}
	public Loginpage withPassword() {
			 
			enterText(prop.getProperty("password"), "123abc");
			pageWaitMin();
			verifyStep("Entered Password ", "PASS");
			return this;
	}

	public Loginpage login() {		
		
		if (verifyElementIsDisplayed(prop.getProperty("loginbutton"))) {
		click(prop.getProperty("loginbutton"));
		pageWaitMin();
		 verifyStep("Clicked login button ", "PASS");
		}
		else {
			verifyStep("Unable to click login button", "FAIL");
		}
		
	
		
		return this;
	}
	
	public DashboardPage validateSuccessfulLogin()
	{
		if(driver.getCurrentUrl().toLowerCase().contains("dashboard-landing")) {
			verifyStep("Login Successful, user landed on Dashboard page", "PASS");
		}
		else {
			verifyStep("Login failed. ", "FAIL");
		}
		return new DashboardPage(driver, test, capData1);
	}
		
	public Loginpage login(ReadExcel readExcel)
	{
		verifyStep("Login Page", "PASS");

		enterText(prop.getProperty("username"), readExcel.getValue("DifferentUser"));
		enterText(prop.getProperty("password"), readExcel.getValue("DifferentUserPassword"));


		System.out.println("going to click on login button");
		verifyElementIsPresent(prop.getProperty("loginbutton"), 20);

		click(prop.getProperty("loginbutton"));


		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		verifyStep("login", "PASS");
		return this;
	}
			
	
	
	
	
	
	
	
}

	
	
	

