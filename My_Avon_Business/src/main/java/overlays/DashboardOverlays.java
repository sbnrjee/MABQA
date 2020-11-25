package overlays;

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


public class DashboardOverlays extends AvonWrapper{
	
	private static Properties prop;
	DBConn db;
	String userName,loginType,dateFormat,currencySeparator,secondCurrencySeparator;
	
	public Map<String,String> capData1 = new HashMap<String,String>();
	public DashboardOverlays (RemoteWebDriver driver, ExtentTest test, String mrkt,  Map<String,String> capData1)
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
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/DashboardOverlay.properties")));
			db= new DBConn(mrkt);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public DashboardOverlays skipAppTourOverlay()
	{
		
		if(verifyElementIsDisplayed(prop.getProperty("appTourOverlay"))) 
		{
			verifyStep("App Tour Overlay is displayed", "INFO");
			click(prop.getProperty("appTourOverlaySkip"));
			verifyStep("Skipped App Tour Overlay for current session", "PASS");
		}
		
		return this;
	}

	public DashboardOverlays skipContactInformationOverlay()
	{
		if(verifyElementIsDisplayed(prop.getProperty("contactInformationOverlay"))) 
		{
			verifyStep("Contact Information Overlay is displayed", "INFO");
			click(prop.getProperty("contactInformationOverlayskip"));
			verifyStep("Skipped Contact Information Overlay for current session", "PASS");
		}
		
		return this;
	}
	
}
