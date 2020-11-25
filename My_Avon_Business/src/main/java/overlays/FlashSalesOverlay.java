package overlays;

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

public class FlashSalesOverlay extends AvonWrapper{
	
	private static Properties prop;
	DBConn db;
	String userName,loginType,dateFormat,currencySeparator,secondCurrencySeparator;
	
	public Map<String,String> capData1 = new HashMap<String,String>();
	public FlashSalesOverlay (RemoteWebDriver driver, ExtentTest test, String mrkt,  Map<String,String> capData1)
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
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/FlasSalesDialog.properties")));
			db= new DBConn(mrkt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FlashSalesOverlay validateUI() 
	{
		if(verifyElementIsDisplayed(prop.getProperty("continueWithCheckout"))) 
		{
			verifyStep("Continue without Flash sales is displayed", "PASS");
		}
		else
		{
			verifyStep("Continue without Flash sales is not displayed", "FAIL");
		}
		if(verifyElementIsDisplayed(prop.getProperty("FSNaviagation")) && getAttributeValue(prop.getProperty("FSNaviagation"), "src").isEmpty()==false && !prop.getProperty("FSNaviagation").equals(null)) 
		{
			verifyStep("Proceed to Flash sales is displayed", "PASS");
		}
		else
		{
			verifyStep("Proceed to Flash sales is not displayed", "FAIL");
		}
		
		
		
		return this;
	}
	
	public FlashSalesOverlay proceedWithCheckOut()
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		click(prop.getProperty("continueWithCheckout"));
		return this;
	}

	public FlashSalesOverlay navigateToFlashSales() 
	{
		click(prop.getProperty("FSNaviagation"));
		return this;
	}
}
