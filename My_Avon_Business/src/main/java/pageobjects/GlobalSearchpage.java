package pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import utils.DBConn;
import wrappers.AvonWrapper;

public class GlobalSearchpage extends AvonWrapper{
	
	private static Properties prop;
	DBConn db;
	String userName,loginType,dateFormat,currencySeparator,secondCurrencySeparator;
	
	public Map<String,String> capData1 = new HashMap<String,String>();
	public GlobalSearchpage (RemoteWebDriver driver, ExtentTest test, String mrkt,  Map<String,String> capData1)
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
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/GlobalSearch.properties")));

			db= new DBConn(mrkt);

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
	
	//Verifying Global seach is configured
	public GlobalSearchpage verifyGlobalSearchUI() {
		if(verifyElementIsDisplayed(prop.getProperty("globalSearchIcon"))) {
			verifyStep("Global Seach button is displayed", "PASS");
		}
		else {
			verifyStep("Global Seach button is not displayed", "FAIL");
		}
		
		return this;
	}
	
	//Clicking global search icon to open search 
	public GlobalSearchpage startSearch() {
		click(prop.getProperty("globalSearchIcon"));
		return this;
		
	}
	
	//Validate all UI component
	public GlobalSearchpage verifyGlobalSearchUIComponent() {
		
		if(verifyElementIsDisplayed(prop.getProperty("globalSearchTextbox"))) {
			
			verifyStep("Search Textbox is displayed","PASS");
		}
		else {

			verifyStep("Search Textbox is not displayed","FAIL");
		}
		
		if(verifyElementIsDisplayed(prop.getProperty("startSearch"))) {
			
			verifyStep("Search icon is displayed","PASS");
		}
		else {

			verifyStep("Search icon is not displayed","FAIL");
		}
		enterText(prop.getProperty("globalSearchTextbox"), "Avon");
		pageWaitMin();
      if(verifyElementIsDisplayed(prop.getProperty("clearSearchString"))) {
			
    	  verifyStep("Clear Search String is displayed","PASS");
		}
		else {

			verifyStep("Clear Search String is not displayed","FAIL");
		}
      	
       if(verifyElementIsDisplayed(prop.getProperty("suggestion"))&& getAllLinks(prop.getProperty("suggestion")).size()>0) {
    	   verifyStep("Suggestion on entering keyword is displayed", "PASS");
       }
       else {
    	   verifyStep("Suggestion on entering keyword is not displayed", "FAIL");
    	      
       }
		return this;
	}
	
	//Search Functionality 
	
	public GlobalSearchpage searchByProductLineNumber(String line_nr) {
		
		
		enterText(prop.getProperty("globalSearchTextbox"), line_nr);
		click(prop.getProperty("startSearch"));
		verifyStep("Searching with Line_NR:"+line_nr, "PASS");
		pageWaitMin();
		
		return this;
	}
	
	
	public GlobalSearchpage searchByProductName(String prod_name) {
		enterText(prop.getProperty("globalSearchTextbox"), prod_name);
		click(prop.getProperty("startSearch"));
		verifyStep("Searching with prod_name: "+prod_name, "PASS");
		pageWaitMin();
		
		return this;
	
	}
	
	
	public GlobalSearchpage searchByPartialProductName(String partial_prod_nm) 
	{
		enterText(prop.getProperty("globalSearchTextbox"), partial_prod_nm);
		click(prop.getProperty("startSearch"));
		verifyStep("Searching with partial_prod_name: "+partial_prod_nm, "PASS");
		pageWaitMin();
		
		return this;
	}
	
	
	public GlobalSearchpage searchByProductCategory(String prod_category)
	{
		enterText(prop.getProperty("globalSearchTextbox"), prod_category);
		click(prop.getProperty("startSearch"));
		verifyStep("Searching with partial_prod_name: "+prod_category, "PASS");
		pageWaitMin();
		
		return this;
	}

	public GlobalSearchpage searchByPageKeyWord(String page_keyword) 
	{
		enterText(prop.getProperty("globalSearchTextbox"), page_keyword);
		click(prop.getProperty("startSearch"));
		verifyStep("Searching with partial_prod_name: "+page_keyword, "PASS");
		pageWaitMin();
		
		return this;
	}
	
	
	public GlobalSearchpage zeroSearchResultValidation(String invalid_prod_input)
	{
		enterText(prop.getProperty("globalSearchTextbox"), invalid_prod_input);
		click(prop.getProperty("startSearch"));
		verifyStep("Searching with invalid_prod_input: "+invalid_prod_input, "PASS");
		pageWaitMin();
		
		return this;
	}
	
	
	public GlobalSearchpage typeAhead_Validation(String partial_prod_nm)
	{
		
		enterText(prop.getProperty("globalSearchTextbox"), "partial_prod_nm");
		pageWaitMin();
		return this;
	}
	
	public GlobalSearchpage searchByFSC(String FSC)
	{
		
		enterText(prop.getProperty("globalSearchTextbox"), FSC);
		click(prop.getProperty("startSearch"));
		verifyStep("Searching with FSC: "+FSC, "PASS");
		pageWaitMin();
		
		return this;
	}
	
	public GlobalSearchpage searchBySetProduct(String set_prod) {
		
		enterText(prop.getProperty("globalSearchTextbox"), set_prod);
		click(prop.getProperty("startSearch"));
		verifyStep("Searching with set_prod: "+set_prod, "PASS");
		pageWaitMin();
		
		return this;	
	}
	
	
	public GlobalSearchpage validateResult() {
		
		if(verifyElementIsDisplayed(prop.getProperty("srch_resultset"))&& getAllLinks(prop.getProperty("srch_resultset_prodNm")).size()>0)
		{
			verifyStep("Search Result displayed", "PASS");
		}
		return this;
	}

}

	


