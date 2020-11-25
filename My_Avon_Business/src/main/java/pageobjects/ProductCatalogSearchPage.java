package pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.xmlbeans.impl.xb.xsdschema.LengthDocument;
import org.jsoup.select.Evaluator.IsEmpty;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.CharMatcher;
import com.relevantcodes.extentreports.ExtentTest;

import utils.DBConn;
import utils.DataInputProvider;
import utils.DateFormat;
import utils.ReadExcel;
import wrappers.AvonWrapper;

public class ProductCatalogSearchPage extends AvonWrapper{
	private static Properties prop;
	public Map<String,String> capData1 = new HashMap<String,String>();
	DBConn db;
	String userName,loginType;
	public ProductCatalogSearchPage(RemoteWebDriver driver, ExtentTest test, String mrkt, Map<String,String> capData1)
	{
		this.driver = driver;
		this.test = test;
		//this.userName=userNam;
		this.capData1=capData1;
		//this.loginType=loginTyp;
		this.market=mrkt;
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/ProductCatalogSearch.properties")));
			db= new DBConn(mrkt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//TC01,TC02,TC03,TC04,TC05
	/* TODO :
	 * plus option is not available close to the customer name in the demo page

	 * */
	public ProductCatalogSearchPage verifyProductCatalog(ReadExcel readExcel) throws InterruptedException {
		
		
		
		scrollToAnElement(driver.findElement(getLocator(prop.getProperty("ProdTitle"))));
		click(prop.getProperty("ProdTitle"));
		
		
		for(int i=0;i<getAllLinks(prop.getProperty("ColorVarient")).size();i++){
			if(prop.getProperty("").equalsIgnoreCase(readExcel.getValue("Color"))) {
				click(prop.getProperty("ColorVarient"),i);
			}
		}
		if(verifyElementIsDisplayed(prop.getProperty("SizeVarients"))) {
			click(prop.getProperty("SizeVarientAdditionBtn"));
			click(prop.getProperty("DoneBtn"));
		}
		click(prop.getProperty("AddToOrderBtn"));
		click(prop.getProperty("CreateANewOrderBtn"));
		click(prop.getProperty("NextBtn"));
		if(driver.findElement(By.xpath("ConfirmAddToOrder")).isEnabled()) {
			verifyStep("Confirm add to order is active", "FAIL");
		}else {
			verifyStep("Confirm add to order is inactive", "PASS");
			for(int j=0;j<getAllLinks(prop.getProperty("CustomerNameCards")).size();j++) {
			click(prop.getProperty("CustomerNameCards"),j);
			}
		}
			click(prop.getProperty("ConfirmAddToOrder"));	
			verifyElementIsPresent(prop.getProperty("TickMarkOnBtn"));
		click(prop.getProperty("CreateOrderForMyselfBtn"));
		
		/* TODO :
		TC05 need to check the max quantity allowed for a specific product
		and available quantity of the specific product(now its not available in demo page*/
		
		return this;
		
	}
	
	
	
//TC09,TC010,TC011,TC012,TC027	
	/*plus option is not available close to the customer name in the demo page
	 *  TODO :
	 *  TC12 need to check the max quantity allowed for a specific product and available quantity of the specific product(now its not available in demo page
	 * 
	 * */
	public ProductCatalogSearchPage verifyProductCatalogViaPDP(ReadExcel readExcel) throws InterruptedException {
		

		click(prop.getProperty("ProductsDropdwn"));
		for(int i=0;i<getAllLinks(prop.getProperty("PrdtCategory")).size();i++) {
			click(prop.getProperty("PrdtCategory"),i);
			for(int j=0;j<getAllLinks(prop.getProperty("prdtCategorySub")).size();j++) {
				click(prop.getProperty("prdtCategorySub"),j);
				click(prop.getProperty("prdtCategorySub2"));
			}
		}
		if(verifyElement(prop.getProperty("ProductCategoryList"))) {
			for(int i=0;i<getAllLinks(prop.getProperty("ProductCategoryList")).size();i++){
			
				click(prop.getProperty("ProductCategoryList"),i);
			}
		}
		
		List<String> rows = new ArrayList<String>();
		List<WebElement> rows1 = new ArrayList<WebElement>();
		rows1=getAllLinks(prop.getProperty("Products"));
		for(WebElement items:rows1)
		{
			rows.add(items.getText());
		} 
		
		click(prop.getProperty("ProdTitle"));
		for(int i=0;i<getAllLinks(prop.getProperty("ColorVarient")).size();i++){
			if(prop.getProperty("").equalsIgnoreCase(readExcel.getValue("Color"))) {
				click(prop.getProperty("ColorVarient"),i);
			}
		}
		if(verifyElementIsDisplayed(prop.getProperty("SizeVarients"))) {
			click(prop.getProperty("SizeVarientAdditionBtn"));
			click(prop.getProperty("DoneBtn"));
		}
		click(prop.getProperty("AddToOrderBtn"));
		click(prop.getProperty("CreateANewOrderBtn"));
		click(prop.getProperty("NextBtn"));
		if(driver.findElement(By.xpath("ConfirmAddToOrder")).isEnabled()) {
			verifyStep("Confirm add to order is active", "FAIL");
		}else {
			verifyStep("Confirm add to order is inactive", "PASS");
			for(int j=0;j<getAllLinks(prop.getProperty("CustomerNameCards")).size();j++) {
			click(prop.getProperty("CustomerNameCards"),j);
			}
		}
			click(prop.getProperty("ConfirmAddToOrder"));	
			verifyElementIsPresent(prop.getProperty("TickMarkOnBtn"));
		click(prop.getProperty("CreateOrderForMyselfBtn"));
		
//TC027
		//REVISIT THE CODE ONCE THE DEPLOYMENT IS COMPLETED
		click(prop.getProperty("ExistingCustomerOrders"));
		click(prop.getProperty("ExistingOrderEditOption"));
		List<String> view = new ArrayList<String>();
		List<WebElement>selected_Items =new ArrayList<WebElement>();   
		selected_Items=getAllLinks(prop.getProperty("Products"));
	  for (WebElement sel_items : selected_Items){
		  view.add(sel_items.getText());
	  }
	  if(rows.equals(selected_Items)){
		  verifyStep("Selected item is successfully added to the cart", "PASS");
	  }else{
		  verifyStep("Selected item is not added to the cart", "FAIL");
	  }
	  
	  
		return this;
		
	}
	
	
	//TC21,TC22,TC023,TC024,TC025,TC026,TC042
	
	public ProductCatalogSearchPage verifyOpenExistingPendingOrders(ReadExcel readExcel) throws InterruptedException {
		
		
		verifyElementIsPresent(prop.getProperty("CreateOrderForMyselfBtn"));
		verifyElementIsPresent(prop.getProperty("ExistingCustomerOrders"));
		
		if (verifyElement(prop.getProperty("ExistingCustomerOrders"))) {
			
			
			verifyElementIsPresent(prop.getProperty("CustomerNames"));
			
			for(int j=0;j<getAllLinks(prop.getProperty("ExistingCustomerOrders")).size();j++) {
				click(prop.getProperty("ExistingCustomerOrders"),j);
				verifyElementIsPresent(prop.getProperty("SelectedExistingCustomer"));
			}
			
			click(prop.getProperty("ContinueBtn"));
		}
		
		
		//TC023,TC042
		List<String> threeCharacterSearch = new ArrayList<String>();
		List<WebElement> threeCharacterSearch1 = new ArrayList<WebElement>();
		threeCharacterSearch1=getAllLinks(prop.getProperty("SearchSuggestions"));
		for(WebElement e:threeCharacterSearch1)
		{
			threeCharacterSearch.add(e.getText());
		}
		
		List<String> moreThanThreeCharacterSearch = new ArrayList<String>();
		List<WebElement> moreThanThreeCharacterSearch1 = new ArrayList<WebElement>();
		moreThanThreeCharacterSearch1=getAllLinks(prop.getProperty("SearchSuggestions"));
		for(WebElement e:moreThanThreeCharacterSearch1)
		{
			moreThanThreeCharacterSearch.add(e.getText());
		}
		
		if(threeCharacterSearch.equals(moreThanThreeCharacterSearch))
		{
			verifyStep("Application didn't update the list dynamically when adding more character", "FAIL");
		}else {
			verifyStep("Application dynamically update the list when adding more character", "PASS");
		}
		
//TC042
		
		if(verifyElementIsDisplayed(prop.getProperty("SearchSuggestions"))) {
			verifyStep("Search results are displayed", "PASS");
		}else {
			verifyStep("Search results are not displayed", "FAIL");
		}
		
		
//TC024
		
		click(prop.getProperty("SearchSuggestionClose"));
		click(prop.getProperty("ThreeDots"));
		click(prop.getProperty("AddCustomerToThisOrder"));
		int custname=getAllLinks(prop.getProperty("CustNames")).size();
		for(int i=0;i<custname;i++) {
			click(prop.getProperty("PlusSign"), i);
		}
		if(verifyElement(prop.getProperty("SelectedExistingCustomer"))) {
			
			click(prop.getProperty("AddConnBtn"));
		}
//TC025,TC026
		click(prop.getProperty("ThreeDots"));
		click(prop.getProperty("AddCustomerToThisOrder"));
		click(prop.getProperty("AddConnection"));
		enterText(prop.getProperty("FirstName"), readExcel.getValue("firstName"));	
		enterText(prop.getProperty("Surname"), readExcel.getValue("surName"));
		enterText(prop.getProperty("EmailAddress"), readExcel.getValue("emailId"));
		enterText(prop.getProperty("PhnNumber"), readExcel.getValue("phnNum"));
		click(prop.getProperty("ChckBox"));
		click(prop.getProperty("SaveBtn"));
		verifyElementIsDisplayed(prop.getProperty("MinusSign")); // need to find Xpath of "MinusSign"
		
		return this;
		
	}	
	
	
	//TC16,TC17,TC18,TC19
	public ProductCatalogSearchPage validateAddToOrderBtnBehaviour(ReadExcel readExcel) {
		
		List<String> cond=new ArrayList<String>();
		Map<Integer,List<String>> dbValues=new TreeMap<Integer,List<String>>();
		String query="";
		
		List<String> colDetails=new ArrayList<String>();
		
		query=readExcel.getValue("query");

		cond.add(getMarketId(market));

		cond.add(getText(prop.getProperty("ProdQty")));//qty>=1

		verifyElementIsPresent(prop.getProperty("ProdTitle"));

		dbValues=db.getMultipleDbRowDetails(query, cond);
		
		List<WebElement> prdtTitle=getAllLinks(prop.getProperty("ProdTitle"));
		List<WebElement> prodQty=getAllLinks(prop.getProperty("ProdQty"));
		System.out.println("dbvalues:"+dbValues);
		HashMap<String, String> hashmap = new HashMap<>();
		for(int i=0;i<prdtTitle.size();i++)
		{
			hashmap.put(prdtTitle.get(i).getText(), prodQty.get(i).getText());
			System.out.println("hashmap values:"+hashmap);
		}
				
		//get all prdtTitle in a list
		//get all prodPrice in a list
		//add the prdtTitle as key and prodPrice as value to hashmap
		//hashmap1.get(prdtTitle)
		//temp.get()
		
		for(Entry<Integer,List<String>>entry:dbValues.entrySet())
		{
			colDetails.addAll(entry.getValue());
			String prdtTitleDB=colDetails.get(0);
			String prodQtyDB=colDetails.get(1);
		    if(hashmap.get(prdtTitleDB).equals(prodQtyDB)) {
		    			
		            driver.findElement(By.xpath("//*[contains(lower-case(text()),prdtTitleDB)]")).click();
		            	verifyElementIsPresent(prop.getProperty("ColorVarient"));
		            	int colorSize=getAllLinks(prop.getProperty("ColorVarient")).size();
		            	if(colorSize>1){
		            		for(int i=0;i<colorSize;i++){
		            			click(prop.getProperty("ColorVarient"),i);
		            		}
		            		if(verifyElementIsDisplayed(prop.getProperty("SizeVarients"))) {
		            			click(prop.getProperty("SizeVarientAdditionBtn"));
		            			click(prop.getProperty("DoneBtn"));
		            		}
		            		click(prop.getProperty("AddToOrderBtn"));	
	//TC018
		            		click(prop.getProperty("CloseBtn"));
		            		click(prop.getProperty("ClearAllSelectionOptn"));
		            		verifyElementIsNotPresent(prop.getProperty("SelectedVarient"), 5);
		            		
		            		//TODO TC19 Verify the error message shows that the shade has to be selected first
//-----------------------------		            		
		            		click(prop.getProperty("SizeVarientAdditionBtn"));
		            		click(prop.getProperty("DoneBtn"));
		            		
		            		
		            		click(prop.getProperty("NextBtn"));
		            		for(int j=0;j<getAllLinks(prop.getProperty("CustomerNameCards")).size();j++) {
		            			click(prop.getProperty("CustomerNameCards"),j);
		            		}
		            		click(prop.getProperty("ConfirmAddToOrder"));	
		            		verifyElementIsPresent(prop.getProperty("TickMarkOnBtn"));
		            		if(prop.getProperty("ProdQty").equals("0")) {
		            			verifyStep("Qty box is zero on adding a product to the order", "PASS");
		            		}else {
		            			verifyStep("Qty box is not zero on adding a product to the order", "FAIL");
		            		}
		            	}
		            	
		    	}
		}

		return this;
		
	}
	
	
	//TC035,36,37
	//TODO need to add quick view codes
	public ProductCatalogSearchPage verifyPDPSecondTime(ReadExcel readExcel) throws InterruptedException {
		
		List<String> cond=new ArrayList<String>();
		Map<Integer,List<String>> dbValues=new TreeMap<Integer,List<String>>();
		String query="";
		
		List<String> colDetails=new ArrayList<String>();
		
		query=readExcel.getValue("query");

		cond.add(getMarketId(market));

		cond.add(getText(prop.getProperty("ordernumBer")));

		verifyElementIsPresent(prop.getProperty("orderHistory"));

		dbValues=db.getMultipleDbRowDetails(query, cond);
		
		List<WebElement> orderid=getAllLinks(prop.getProperty("ordernumBer"));
		List<WebElement> status=getAllLinks(prop.getProperty("sTatus"));
		System.out.println("dbvalues:"+dbValues);
		HashMap<String, String> hashmap = new HashMap<>();
		for(int i=0;i<orderid.size();i++)
		{
			hashmap.put(orderid.get(i).getText(), status.get(i).getText());
			System.out.println("hashmap values:"+hashmap);
		}
				
		//get all order id in a list
		//get all status in a list
		//add the order id as key and status as value to hashmap
		//hashmap1.get(orderId)
		//temp.get()
		
		for(Entry<Integer,List<String>>entry:dbValues.entrySet())
		{
			colDetails.addAll(entry.getValue());
			String orderIdDB=colDetails.get(0);
			String statusDB=colDetails.get(1);
			if(statusDB.equalsIgnoreCase("Pending")) {
		            if (hashmap.get(orderIdDB).equals(statusDB)) {
		            	
		            		driver.findElement(By.xpath("//*[contains(lower-case(text()),orderIdDB)]")).click();
		            		verifyStep("DB validation is done", "PASS");
		            		try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								}
		            		verifyElementIsPresent(prop.getProperty("orderInfo"));
		            		click(prop.getProperty("orderDetailsBackButtonMobile"));
		                
		            }
		            else {
		            	verifyStep("DB validation is not done", "FAIL");
		            	
		            }
			}
			
		}
		
		
		return this;
		
	}
	
	//TC013,TC39 need to rework after deployment
	public ProductCatalogSearchPage verifyProductCatalogViaPDPRep(ReadExcel readExcel) throws InterruptedException {
		

		click(prop.getProperty("ProductsDropdwn"));
		for(int i=0;i<getAllLinks(prop.getProperty("PrdtCategory")).size();i++) {
			click(prop.getProperty("PrdtCategory"),i);
			for(int j=0;j<getAllLinks(prop.getProperty("prdtCategorySub")).size();j++) {
				click(prop.getProperty("prdtCategorySub"),j);
				click(prop.getProperty("prdtCategorySub2"));
			}
		}
		if(verifyElement(prop.getProperty("ProductCategoryList"))) {
			for(int i=0;i<getAllLinks(prop.getProperty("ProductCategoryList")).size();i++){
			
				click(prop.getProperty("ProductCategoryList"),i);
			}
		}
		
		List<String> rows = new ArrayList<String>();
		List<WebElement> rows1 = new ArrayList<WebElement>();
		rows1=getAllLinks(prop.getProperty("Products"));
		for(WebElement items:rows1)
		{
			rows.add(items.getText());
		} 
		
		click(prop.getProperty("ProdTitle"));
		for(int i=0;i<getAllLinks(prop.getProperty("ColorVarient")).size();i++){
			if(prop.getProperty("").equalsIgnoreCase(readExcel.getValue("Color"))) {
				click(prop.getProperty("ColorVarient"),i);
			}
		}
		if(verifyElementIsDisplayed(prop.getProperty("SizeVarients"))) {
			click(prop.getProperty("SizeVarientAdditionBtn"));
			click(prop.getProperty("DoneBtn"));
		}
		click(prop.getProperty("AddToOrderBtn"));
		click(prop.getProperty("CreateANewOrderBtn"));
		click(prop.getProperty("NextBtn"));
		if(driver.findElement(By.xpath("ConfirmAddToOrder")).isEnabled()) {
			verifyStep("Confirm add to order is active", "FAIL");
		}else {
			verifyStep("Confirm add to order is inactive", "PASS");
		}
		
			
			for(int j=0;j<getAllLinks(prop.getProperty("CustomerNameCards")).size();j++) {
			click(prop.getProperty("CustomerNameCards"),j);
			}
			click(prop.getProperty("ConfirmAddToOrder"));	
			verifyElementIsPresent(prop.getProperty("TickMarkOnBtn"));
		click(prop.getProperty("CreateOrderForMyselfBtn"));
	  
	  
		return this;
		
	}
		
	
	public void navigateToProduct(ReadExcel readExcel)
	{
		
		if(!capData1.get("PlatformName").equals(""))
		{
			verifyElementIsPresent(prop.getProperty("productTitle"));
			if(!(capData1.get("PlatformName").equalsIgnoreCase("")))
				click(prop.getProperty("productTitle"));
			verifyStep("PDP Page", "PASS");
			StringTokenizer stProduct=new StringTokenizer(readExcel.getValue("Products"), ">");
			int i=1;
			String temp=stProduct.nextToken().toString();
			while(i<=getAllLinks("xpath===(//div[@id='plpNav-menu'])[1]/div/ul/li").size())
			{
				if(temp.equalsIgnoreCase(getText("xpath===(//div[@id='plpNav-menu'])[1]/div/ul/li["+i+"]/div[1]").trim()))
				{
					click("xpath===(//div[@id='plpNav-menu'])[1]/div/ul/li["+i+"]/div[1]");
					break;
				}
				i++;
					
			}
			int count=1;
			i=1;
			temp=stProduct.nextToken().toString();
			boolean flag=true;
			while(flag)
			{
				System.out.println(temp);
				if(temp.equalsIgnoreCase(getText("xpath===(//div[@class='nav-level open'])["+count+"]/ul/li["+i+"]/div[1]")))
				{
					click("xpath===(//div[@class='nav-level open'])["+count+"]/ul/li["+i+"]/div[1]");
					count++;
					i=1;
					if(stProduct.hasMoreTokens())
					{
						flag=true;
						temp=stProduct.nextToken().toString();
						verifyStep("PDP Page", "PASS");
					}
					else
						flag=false;
					
				}
				else
				{
					i++;
				}
			}
			
			
			click(prop.getProperty("productImg"));
			pageWaitMid();
			
		}
		else
		{
			int flag=0;



			String [] options=readExcel.getValue("Products").split(">");

			List<WebElement>optionToBeSelected=new ArrayList<>();


			for (int j = 0; j < options.length; j++) {

			if(flag==1) {break;}

			if(verifyElementIsDisplayed(prop.getProperty("mainSelection_NotSelected")) || verifyElementIsDisplayed(prop.getProperty("mainSelection_Selected"))) {

			optionToBeSelected.addAll(getAllLinks(prop.getProperty("mainSelection_Selected")));

			optionToBeSelected.addAll(getAllLinks(prop.getProperty("mainSelection_NotSelected")));

			for (int j2 = 0; j2 < optionToBeSelected.size(); j2++) {

			if(flag==1) {break;}

			for (int i = 0; i < options.length; i++) {

			if(flag==1) {break;}

			if(optionToBeSelected.get(j2).getText().equalsIgnoreCase(options[i])) {

			optionToBeSelected.get(j2).click();

			try {

			Thread.sleep(3000);

			} catch (InterruptedException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			}


			if(verifyElementIsDisplayed(prop.getProperty("2ndSelection"))) {

			optionToBeSelected.clear();

			optionToBeSelected.addAll(getAllLinks(prop.getProperty("2ndSelection")));

			for (int j3 = 0; j3 < optionToBeSelected.size(); j3++) {

			if(flag==1) {break;}

			for ( i = 0; i < options.length; i++) {

			if(flag==1) {break;}


			if(optionToBeSelected.get(j3).getText().equalsIgnoreCase(options[i])) {

			optionToBeSelected.get(j3).click();

			try {

			Thread.sleep(5000);

			} catch (InterruptedException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			}

			if(verifyElementIsDisplayed(prop.getProperty("3rdSelection"))) {

			optionToBeSelected.clear();

			optionToBeSelected.addAll(getAllLinks(prop.getProperty("3rdSelection")));

			for (int j4 = 0; j4 < optionToBeSelected.size(); j4++) {

			if(flag==1) {break;}

			for ( i = 0; i < options.length; i++) {

			if(flag==1) {break;}

			if(optionToBeSelected.get(j4).getText().equalsIgnoreCase(options[i])) {

			optionToBeSelected.get(j4).click();

			try {

			Thread.sleep(5000);

			} catch (InterruptedException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			}

			flag=1;

			break;


			}}}}}}}}}}}}}

			click(prop.getProperty("productImg"));
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			



		}
		
		
	}
	
	public void addProduct(ReadExcel readExcel)
	{
		
		
		/*WebDriverWait wait = new WebDriverWait(driver, 40);
		
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("addToOrderBtn")))));*/
		verifyStep("Adding Product", "PASS");
		if(verifyElementIsDisplayed(prop.getProperty("imageColor1")))
		{
			click(prop.getProperty("imageColor1"));
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		enterTextUsingJavascript(prop.getProperty("prodQuantity"), readExcel.getValue("productQuantity"));
		verifyStep("Adding Product", "PASS");
		
		if(verifyElementIsDisplayed(prop.getProperty("doneLink")))
			click(prop.getProperty("doneLink"));
		verifyStep("Adding Product", "PASS");
		if(verifyElementIsDisplayed(prop.getProperty("addToOrderBtn")))
				click(prop.getProperty("addToOrderBtn"));
		
		if(verifyElementIsDisplayed(prop.getProperty("selectOrder")))
				click(prop.getProperty("selectOrder"));
		pageWaitMin();
		verifyStep("Adding Product", "PASS");
		click(prop.getProperty("firstOrder"));
		pageWaitMin();
		if(!capData1.get("PlatformName").equals(""))
		{
			click(prop.getProperty("nextButton"));
			click(prop.getProperty("addCustomerToOrder"));
		}
	
		
		if(verifyElementIsDisplayed(prop.getProperty("firstConnection")))
		{
			click(prop.getProperty("firstConnection"));
			highlightElement(prop.getProperty("confirmOrder"));
			
//			HashMap<String, Object> params = new HashMap();
//			switchToContext("NATIVE_APP");
//			params.clear();
//			params.put("content" , "X");
//			params.put("timeout" , "25");
//			driver.executeScript("mobile:text:select", params);
			
			pageWaitMid();
			verifyStep("Adding Product", "PASS");
			click(prop.getProperty("confirmOrder"));
		}
		else
		{
			verifyStep("Adding Product", "PASS");
			click(prop.getProperty("adProductForMe"));
			
		}
	
		
		pageWaitMid();
		
		if(getAllLinks(prop.getProperty("hiddenMiniCart")).size()==0)
		{
			verifyStep("Minicart is successfully Expanded", "PASS");
		}
	else
		{
			verifyStep("Minicart is not Expanded", "FAIL");
		}
	}
	
	public void confirmAddToOrderBtnValidation(ReadExcel readExcel)
	{

		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("addToOrderBtn")))));
		
		if(verifyElementIsDisplayed(prop.getProperty("imageColor1")))
		{
			click(prop.getProperty("imageColor1"));
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int defaultQty=Integer.parseInt(getAttributeValue((prop.getProperty("prodQuantity")),"value"));
		
		if(defaultQty==1)
			verifyStep("Default Product quantity is 1", "PASS");
		else
			verifyStep("Default Product quantity validation failed Expected:1 Actual"+getText(prop.getProperty("prodQuantity")), "FAIL");
		
		enterTextUsingJavascript(prop.getProperty("prodQuantity"), readExcel.getValue("productQuantity"));
		
		if(verifyElementIsDisplayed(prop.getProperty("doneLink")))
			click(prop.getProperty("doneLink"));
		
		if(verifyElementIsDisplayed(prop.getProperty("imageColor1")))
		{
			if(getAllLinks(prop.getProperty("selectedColor")).size()==0)
				verifyStep("Selected color is not active", "PASS");
			else
				verifyStep("Selected color is  active", "FAIL");
		}
		
		click(prop.getProperty("addToOrderBtn"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		click(prop.getProperty("createNewOrderBtn"));

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("firstOrder")))));
		
		verifyElementIsPresent(prop.getProperty("firstOrder"));
		click(prop.getProperty("firstOrder"));
		click(prop.getProperty("nextButton"));
		
		verifyElementIsPresent(prop.getProperty("confirmOrderDisabled"));
		verifyStep("ConfirmOrder is Disabled", "PASS");
		click(prop.getProperty("firstConnection"));
		verifyElementIsPresent(prop.getProperty("confirmOrder"));
		
		verifyStep("Confirm Order is enabled after selecting the Connection", "PASS");
		
	}

	public void getLineNumberandUpdateDb(ReadExcel readExcel)
	{
		getAllLinks(prop.getProperty("shades")).get(0).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String lineNumber=getText(prop.getProperty("lineNumber"));
		
		List<String> cond=new ArrayList<>();
		cond.add(getMarketId(market));
		cond.add(lineNumber);
		cond.add(readExcel.getValue("campaignYear"));
		cond.add(readExcel.getValue("campaignNumber"));
		
		db.updateTable(readExcel.getValue("updateOrderQuery"),cond);	
	}
	
	public void addProductErrorValidation(ReadExcel readExcel)
	{
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("addToOrderBtn")))));
		
		if(verifyElementIsDisplayed(prop.getProperty("imageColor1")))
		{
			click(prop.getProperty("imageColor1"));
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		enterText(prop.getProperty("prodQuantity"), readExcel.getValue("productQuantity"));
		
		if(verifyElementIsDisplayed(prop.getProperty("doneLink")))
			click(prop.getProperty("doneLink"));
		
		click(prop.getProperty("addToOrderBtn"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		click(prop.getProperty("createNewOrderBtn"));

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("firstOrder")))));
		
		verifyElementIsPresent(prop.getProperty("firstOrder"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		click(prop.getProperty("firstOrder"));
		
		switchToContext("NATIVE_APP");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.id("com.android.chrome:id/infobar_close_button")).click();
		switchToContext("WEBVIEW_1");
		
		click(prop.getProperty("nextButton"));
		
		click(prop.getProperty("firstConnection"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switchToContext("NATIVE_APP");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.id("com.android.chrome:id/infobar_close_button")).click();
		switchToContext("WEBVIEW_1");
		
		click(prop.getProperty("confirmOrder"));
		String errMsg=getText(prop.getProperty("errMsg"));
		
		if(errMsg.toLowerCase().contains((readExcel.getValue("ConfirmOrderErrMsg").toLowerCase())))
			verifyStep("Error MessageValidated", "PASS");
		else
			verifyStep("Error Message Validated Expected:"+readExcel.getValue("ConfirmOrderErrMsg")+" Actual:"+errMsg, "FAIL");
	}
	
	
	public void addToSelectedOrder(ReadExcel readExcel)
	{
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("addToBtn")))));
		
		int itemCountBefore=Integer.parseInt(getText(prop.getProperty("cartItemCount")));
		
		verifyStep("Product Selected", "PASS");
		if(verifyElementIsDisplayed(prop.getProperty("imageColor1")))
		{
			click(prop.getProperty("imageColor1"));
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		enterText(prop.getProperty("prodQuantity"), readExcel.getValue("productQuantity"));
		
		if(verifyElementIsDisplayed(prop.getProperty("doneLink")))
			click(prop.getProperty("doneLink"));
		verifyStep("Product Selected", "PASS");
		click(prop.getProperty("addToBtn"));
		
		
		verifyElementIsNotPresent(prop.getProperty("hiddenMiniCart"), 10);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int itemCountAfter=Integer.parseInt(getText(prop.getProperty("cartItemCount")));
		
		if((itemCountBefore+Integer.parseInt(readExcel.getValue("productQuantity")))==itemCountAfter)
			verifyStep("Cart Item Count Validated", "PASS");
		else
			verifyStep("Cart Item Count Validated Expected:"+ itemCountBefore+Integer.parseInt(readExcel.getValue("productQuantity"))+" Actual:"+itemCountAfter, "FAIL");
		verifyStep("Add to Selected Order Validated", "PASS");
	}
	public void closeMiniCart()
	{
		
		click(prop.getProperty("miniCart_Img"));
	}
	
	public void pdpSelectOrder(ReadExcel readExcel)
	{
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("addToBtn")))));
		
		int itemCountBefore=Integer.parseInt(getText(prop.getProperty("cartItemCount")));
		
		if(verifyElementIsDisplayed(prop.getProperty("imageColor1")))
		{
			click(prop.getProperty("imageColor1"));
		}
		verifyStep("Adding Product", "PASS");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		enterText(prop.getProperty("prodQuantity"), readExcel.getValue("productQuantity"));
		
		if(verifyElementIsDisplayed(prop.getProperty("doneLink")))
			click(prop.getProperty("doneLink"));
		verifyStep("Adding Product", "PASS");
		click(prop.getProperty("selectOrder"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!capData1.get("PlatformName").equals(""))
			click(prop.getProperty("createNewOrderBtn"));
		else
			click("xpath===(//div[@class='avn-sec-btn hide-desk adnw-ord'])");

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("firstOrder")))));
		
		verifyElementIsPresent(prop.getProperty("firstOrder"));
		click(prop.getProperty("firstOrder"));
		if(!capData1.get("PlatformName").equals(""))
			click(prop.getProperty("nextButton"));
		
		if(verifyElementIsDisplayed(prop.getProperty("firstConnection")))
		{
			click(prop.getProperty("firstConnection"));
			highlightElement(prop.getProperty("confirmOrder"));
			verifyStep("Adding Product", "PASS");
//			HashMap<String, Object> params = new HashMap();
//			switchToContext("NATIVE_APP");
//			params.clear();
//			params.put("content" , "X");
//			params.put("timeout" , "25");
//			driver.executeScript("mobile:text:select", params);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			click(prop.getProperty("confirmOrder"));
		}
		else
		{
			verifyStep("Adding Product", "PASS");
			click(prop.getProperty("adProductForMe"));
			
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
int itemCountAfter=Integer.parseInt(getText(prop.getProperty("cartItemCount")));
		
		if((itemCountBefore+Integer.parseInt(readExcel.getValue("productQuantity")))==itemCountAfter)
			verifyStep("Cart Item Count Validated", "PASS");
		else
			verifyStep("Cart Item Count Validated Expected:"+ (itemCountBefore+Integer.parseInt(readExcel.getValue("productQuantity")))+" Actual:"+itemCountAfter, "FAIL");
		verifyStep("Add to Selected Order Validated", "PASS");
	}
	
	
	public void searchProductinPao(ReadExcel readExcel)
	{
		click(prop.getProperty("searchBtn"));
		enterText(prop.getProperty("searchText"), readExcel.getValue("SearchText"));
		
		getAllLinks(prop.getProperty("searchList")).get(0).click();
		verifyElementIsPresent(prop.getProperty("addToOrderBtn"));
	}
	
	public void createNewOrder(ReadExcel readExcel)
	{
		WebDriverWait wait = new WebDriverWait(driver, 80);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("btn_createnewOrder")))));
		click(prop.getProperty("btn_createnewOrder"));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("btnCreateOrderForMyself")))));
		if(verifyElement(prop.getProperty("btnCreateOrderForMyself"))) {
			click(prop.getProperty("btnCreateOrderForMyself"));
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("prodNumInputField")))));
		}
		if(driver.getCurrentUrl().contains("product-entry")) {
			verifyStep("Successfully navigated to Add-product page", "PASS");
		}
		else {
			verifyStep("Not navigated to Add-product page", "FAIL");
		}
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("prodNumInputField")))));

		enterText(prop.getProperty("prodNumInputField"), readExcel.getValue("ProdNumber"));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("prodNumInputGreenPlusSign")))));

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		click(prop.getProperty("prodNumInputGreenPlusSign"));
		
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		WebDriverWait wait1 = new WebDriverWait(driver, 4000);
		wait1.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("btnContinue")))));
		if(driver.findElementByXPath("//button[@class='saveLatr negBtn mab-prim-lnk']").isEnabled())
		{
			click(prop.getProperty("saveForLaterBTN"));
		}
		
		
		click(prop.getProperty("shopPrdtCatalog"));
		if(verifyElement(prop.getProperty("productTitle"))) {
			verifyStep("successfully navigated to product catalogue page", "PASS");
		}
		else {
			verifyStep("Unsuccessfully navigated to product catalogue page", "FAIL");
		}
		
		
	}

	
	public void addPrdtViaQuickViewFirstTime(ReadExcel readExcel)
	{
		navigateToProduct(readExcel);
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("addToOrderBtn")))));
		
		if(verifyElementIsDisplayed(prop.getProperty("imageColor1")))
		{
			click(prop.getProperty("imageColor1"));
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		enterTextUsingJavascript(prop.getProperty("prodQuantity"), readExcel.getValue("productQuantity"));
		
		if(verifyElementIsDisplayed(prop.getProperty("doneLink")))
			click(prop.getProperty("doneLink"));
		
		if(verifyElementIsDisplayed(prop.getProperty("addToOrderBtn")))
			click(prop.getProperty("addToOrderBtn"));
		if(verifyElementIsDisplayed(prop.getProperty("selectOrder")))
		{
			click(prop.getProperty("selectOrder"));
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(driver.findElementById("shpByCtlg").isEnabled()) {  //("confirmOrder")
			verifyStep("confirm add-to order is enabled", "FAIL");
		}
		else {
			verifyStep("confirm add-to order is disabled", "PASS.");
		}
		
		
		click(prop.getProperty("firstOrder"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		click(prop.getProperty("nextButton"));
		click(prop.getProperty("addCustomerToOrder"));
		click(prop.getProperty("firstConnection"));
		if(driver.findElementById("shpByCtlg").isEnabled()) {  //("confirmOrder")
			verifyStep("confirm add-to order is enabled", "PASS");
		}
		else {
			verifyStep("confirm add-to order is disabled", "FAIL");
		}
		highlightElement(prop.getProperty("confirmOrder"));
		click(prop.getProperty("confirmOrder"));
	}
	
	
	
	//TC21,22,23,24,25,26,27
		public ProductCatalogSearchPage no_qty_selected(ReadExcel readExcel) {
			
			//driver.get("https://qafmab.pl.avon.com/pl-home/product-catalog.html");
			navigateToProduct(readExcel);
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(getLocator(prop.getProperty("addToOrderBtn")))));
			
			if(verifyElementIsDisplayed(prop.getProperty("imageColor1")))
			{
				click(prop.getProperty("imageColor1"));
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			enterTextUsingJavascript(prop.getProperty("prodQuantity"), readExcel.getValue("productQuantity"));
			
			if(verifyElementIsDisplayed(prop.getProperty("doneLink")))
				click(prop.getProperty("doneLink"));
			
			click(prop.getProperty("addToOrderBtn"));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//TC21
			if(verifyElementIsDisplayed(prop.getProperty("add_new_order"))){
				verifyStep("New Order option is displayed", "PASS");
			}
			else {
				verifyStep("New Order option is not displayed", "FAIL");
			}
			
			
			click(prop.getProperty("firstOrder"));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			click(prop.getProperty("nextButton"));
			
			click(prop.getProperty("addCustomerToOrder"));
			
			//TC23
			enterText(prop.getProperty("search_box"), readExcel.getValue("3CharSearch"));
			List<String> threeCharacterSearch = new ArrayList<String>();
			List<WebElement> threeCharacterSearch1 = new ArrayList<WebElement>();
			threeCharacterSearch1=getAllLinks(prop.getProperty("SearchSuggestions"));
			for(WebElement e:threeCharacterSearch1)
			{
				threeCharacterSearch.add(e.getText());
			}
			enterText(prop.getProperty("search_box"), readExcel.getValue("MoreThan3CharSearch"));
			List<String> moreThanThreeCharacterSearch = new ArrayList<String>();
			List<WebElement> moreThanThreeCharacterSearch1 = new ArrayList<WebElement>();
			moreThanThreeCharacterSearch1=getAllLinks(prop.getProperty("SearchSuggestions"));
			for(WebElement e:moreThanThreeCharacterSearch1)
			{
				moreThanThreeCharacterSearch.add(e.getText());
			}
			
			if(threeCharacterSearch.equals(moreThanThreeCharacterSearch))
			{
				verifyStep("Application didn't update the list dynamically when adding more character", "FAIL");
			}else {
				verifyStep("Application dynamically update the list when adding more character", "PASS");
			}

			click(prop.getProperty("SearchSuggestionClose"));
//TC24
			click(prop.getProperty("firstConnection"));
			if(verifyElement(prop.getProperty("firstConnectionSelected"))) {
				verifyStep("the recepient is selected as active customer", "PASS");
			}
			else {
				verifyStep("the recepient is not selected as active customer", "FAIL");
			}
			highlightElement(prop.getProperty("confirmOrder"));
			
			
			//TC25
			
			click(prop.getProperty("new_conn"));
			verifyStep("Add new customer opened","PASS");
			//Tc26(TODO : data from excel, '- ' sign validation)
			enterText(prop.getProperty("new_conn_fname"), readExcel.getValue("FirstName_AddCust"));
			String fname=getText(prop.getProperty("new_conn_fname"));
			enterText(prop.getProperty("new_conn_lname"), readExcel.getValue("SurName_AddCust"));
			enterText(prop.getProperty("new_conn_nname"), readExcel.getValue("NickName_AddCust"));
			enterText(prop.getProperty("new_conn_email"), readExcel.getValue("Email_AddCust"));
			enterText(prop.getProperty("new_conn_phn"), readExcel.getValue("PhoneNum_AddCust"));
			
			
			if(readExcel.getValue("Gender_AddCust").equalsIgnoreCase("Male"))
				
			{
		
			click(prop.getProperty("new_conn_male"));
		
			}
		
			else if(readExcel.getValue("Gender_AddCust").equalsIgnoreCase("Female"))
		
			{
		
				click(prop.getProperty("new_conn_female"));
		
			}
			
			click(prop.getProperty("save"));
			
			enterText(prop.getProperty("search_box"), fname);
			List<String> newConn = new ArrayList<String>();
			List<WebElement> newConn1 = new ArrayList<WebElement>();
			newConn1=getAllLinks(prop.getProperty("SearchSuggestions"));
			for(WebElement e:newConn1)
			{
				newConn.add(e.getText());
			}
			
			if(fname.equals(newConn))
			{
				verifyStep("new customer successfully added", "PASS");
			}else {
				verifyStep("new customer is not added successfully", "FAIL");
			}
			
			
			click(prop.getProperty("confirmOrder"));
			
			
			
			//TC 22(TODO: customer from pending order , nickname)
			click(prop.getProperty("add_new_order"));
			if(verifyElementIsDisplayed(prop.getProperty("order_for_me"))){
				verifyStep("Order for myself option is displayed", "PASS");
			}
			else {
				verifyStep("Order for myself option is not displayed", "FAIL");
			}
			if(verifyElementIsDisplayed(prop.getProperty("new_conn"))){
				verifyStep("New connection option is displayed", "PASS");
			}
			else {
				verifyStep("New connection option is not displayed", "FAIL");
			}
			List<WebElement> conn=getAllLinks(prop.getProperty("existing_conn"));
			if(conn.size()>0) {
				verifyStep("Connection exist", "PASS");
				
			}
			else {
				verifyStep("Connection does not exist", "FAIL");
				
			}
			
				
				
				
			return this;
		}
		
		
		
		
		public ProductCatalogSearchPage landing() {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			if(verifyElementIsDisplayed(prop.getProperty("campaignCloseBanner"))) {
				verifyStep("Campaign close banner is displayed, CLosing campaign close banner", "PASS");
				click(prop.getProperty("closeCampaignCloseBannerBTN"));
				
				
				verifyStep("Campaign ending Banner is closed", "INFO");
			}
			
			return this;
		}
		
		//This method will validate if category is displayed properly
		public ProductCatalogSearchPage validateCategory() {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			if(verifyElementIsDisplayed(prop.getProperty("categoryPC"))){
				List<WebElement> cat= getAllLinks(prop.getProperty("categoryPC"));
				if(cat.size()>0)
				verifyStep("Product catalog Categories are displayed- total "+cat.size() +" categories are available", "PASS");
				
			}
			else {
				verifyStep("Product catalog  categories are not displayed- category is missing", "FAIL");
			}
			return this;
		}
		//This method will validate if subcategory is displayed properly
		public ProductCatalogSearchPage validateSubCategory() {
			List<WebElement> cat= getAllLinks(prop.getProperty("categoryPC"));
			System.out.println(cat.size()-2);
			Random rand = new Random();
			int counter= rand.nextInt(cat.size());
			
			Actions actions = new Actions(driver);
			//pageWaitMid();
			actions.moveToElement(cat.get(counter)).build().perform();
			pageWaitMid();
			
				verifyStep("Product catalog sub-Categories navigation successfull", "PASS");
				
			
			
			return this;
		}
		//This method will verify if Category name is displayed properly in breadcrumb
		public ProductCatalogSearchPage browsingProductFromCategory() {
			
			List<WebElement> cat= getAllLinks(prop.getProperty("categoryPC"));
			System.out.println(cat.size()-2);
			Random rand = new Random();
			int counter= rand.nextInt(cat.size());
			String category=cat.get(counter).getText();
			Actions actions = new Actions(driver);
			pageWaitMid();
			//scrollToAnElement(cat.get(counter));
			actions.moveToElement(cat.get(counter)).build().perform();
			pageWaitMin();
			
			verifyStep("trying to browsing products from Catagory "+category, "PASS");
			pageWaitMin();
			cat.get(counter).click();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			//System.out.println(prop.getProperty("categoryNameBreadCrumb"));
			//System.out.println(cat.get(counter).getText());
			if(getText(prop.getProperty("categoryNameBreadCrumb")).equalsIgnoreCase(category)) {
			verifyStep("Navigation to caegory "+category+ " is successful.", "PASS");
			}
			else {
				verifyStep("Navigation to caegory "+category+ " is not successful.", "FAIL");
				
			}
			return this;
			
		}
		
		public ProductCatalogSearchPage navigationToQuickView() {
			
			List<WebElement> prod=getAllLinks(prop.getProperty("productCard"));
			verifyStep("Total "+prod.size()+" products are displayed", "PASS");
			Random rand = new Random();
			int counter= rand.nextInt(prod.size());
			scrollToAnElement(prod.get(counter));
			
			Actions actions = new Actions(driver);
			pageWaitMid();
			//scrollToAnElement(cat.get(counter));
			actions.moveToElement(prod.get(counter)).build().perform();
			pageWaitMin();
			verifyStep("Scrolled to product - "+counter, "PASS");
			verifyStep("Navigating to Quick View", "PASS");
			click(prop.getProperty("zoomProductCardtoQuickView"), counter);
			pageWaitMin();
			verifyStep("Navigating to Quick View", "PASS");
			return this;
		}
	
}