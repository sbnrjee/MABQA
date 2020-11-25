package wrappers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.google.common.base.CharMatcher;

import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.TouchShortcuts;
import io.appium.java_client.remote.MobileCapabilityType;
import utils.ReadExcel;
//import junit.framework.Assert;
import utils.Reporter;

public class GenericWrappers extends Reporter implements Wrappers {

	public RemoteWebDriver driver;
	public WebDriverWait wait;
	protected Properties prop;
	public String sUrl,primaryWindowHandle,sHubUrl,sHubPort;
	public  String platformName;
	public  String platformVersion;
	public  String deviceName;
	public  String udid;
	public  String appiumVersion, deviceOrientation;
	public Map<String,String> capData = new HashMap<String,String>();
	
	
	public GenericWrappers(RemoteWebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test=test;
   }

	public GenericWrappers() {
		
	}


	public void loadObjects(String mrkt,String application) {
		prop = new Properties();
		try {
			if(application.equals("angular"))
			prop.load(new FileInputStream(new File("./src/main/resources/"+application+"/Avon MAB/"+mrkt+"/config.properties")));
			else if(application.equals("magnolia"))
			prop.load(new FileInputStream(new File("./src/main/resources/"+application+"/config.properties")));
			else
				System.out.println("Application type "+application+" is incorrect!!!");
			sUrl = prop.getProperty("URL");
			System.out.println(mrkt+"  "+sUrl);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	public void unloadObjects() {
		prop = null;
	}

	
	public void invokeApp(String browser) {
		try{
			invokeApp(browser,false);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error while invoking app");
		}
		
	}

	
	public void invokeApp(String browser, boolean bRemote) {
		URL urlObj=null;
		DesiredCapabilities capabilities=new DesiredCapabilities();
		
		
		platformName=capData.get("PlatformName");
		String host = "avon.perfectomobile.com";
		try {
			if(capData.get("Automation").equalsIgnoreCase("appium")){
				
				urlObj = new URL("http://"+"127.0.0.1"+ ":"+capData.get("Port")+"/wd/hub");
				System.out.println(capData);
				switch(capData.get("PlatformName").toLowerCase()){			
				case "android":
					System.out.println("inside android appium");
					
					capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, capData.get("PlatformName"));
					capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, capData.get("PlatformVersion"));
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capData.get("DeviceName"));	
					
					capabilities.setCapability(MobileCapabilityType.UDID, capData.get("udid"));
					capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, capData.get("Browser"));
					if( capData.get("DeviceName").toLowerCase().contains("tab")){
						capData.put("PlatformName","");
					}
					break;
				case "ios":
					capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, capData.get("PlatformName"));
					capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, capData.get("PlatformVersion"));
					capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, capData.get("DeviceName"));		
					capabilities.setCapability(MobileCapabilityType.UDID, capData.get("udid"));
					capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, capData.get("Browser"));
					capabilities.setCapability("safariAllowPopups", "true");
					capabilities.setCapability("safariOpenLinksInBackground", "true");
					if( capData.get("DeviceName").toLowerCase().contains("ipad")){
						capData.put("PlatformName","");
					}
					
					break;
				
					
				default:
					break;

				}
				System.out.println(capData.get("Browser"));
				driver = new RemoteWebDriver(urlObj, capabilities);
				driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(100, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
				driver.get(sUrl);
				
				
			}
			else if(capData.get("Automation").equalsIgnoreCase("saucelabs")){
				urlObj = new URL("https://in-cjb.headspin.io:7004/v0/3d9a732aa4154bc0a61c803e052b20a5/wd/hub%22");
				DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
				desiredCapabilities.setCapability("udid", "RZ8M20RWJAB");
				desiredCapabilities.setCapability("deviceName", "SM-G975F");
				desiredCapabilities.setCapability("platformName", "Android");
				//desiredCapabilities.setCapability("appPackage", "com.avon.avonon.uat");//com.avon.avonon.uat
				//desiredCapabilities.setCapability("appActivity", "com.avon.avonon.presentation.screens.splash.SplashActivity");
				desiredCapabilities.setCapability("automationName", "UiAutomator2");
				desiredCapabilities.setCapability("noReset", true);
				desiredCapabilities.setCapability("autoAcceptAlerts", true);
				desiredCapabilities.setCapability("platformName", "Android");	
				
				
				
			}
			else if(capData.get("Automation").equalsIgnoreCase("saucelabs")){
				urlObj = new URL("http://Sradha:34d9726d-e250-4e8f-96d0-36a54a77d975@ondemand.saucelabs.com:80/wd/hub");
			
				switch(capData.get("PlatformName").toLowerCase()){			
				case "android":
					
					capabilities.setCapability("appiumVersion", capData.get("AppiumVersion"));
					capabilities.setCapability("deviceName",capData.get("DeviceName"));
					capabilities.setCapability("deviceOrientation", capData.get("DeviceOrientation"));
					capabilities.setCapability("browserName", capData.get("Browser"));
					capabilities.setCapability("platformVersion", capData.get("PlatformVersion"));
					capabilities.setCapability("platformName", capData.get("PlatformName"));
					capabilities.setCapability("name", capData.get("TestCaseName"));
					break;
				case "ios":
					capabilities.setCapability("appiumVersion", capData.get("AppiumVersion"));
					capabilities.setCapability("deviceName",capData.get("DeviceName"));
					capabilities.setCapability("deviceOrientation", capData.get("DeviceOrientation"));
					capabilities.setCapability("browserName", capData.get("Browser"));
					capabilities.setCapability("platformVersion", capData.get("PlatformVersion"));
					capabilities.setCapability("platformName", capData.get("PlatformName"));
					capabilities.setCapability("name", capData.get("TestCaseName"));
					break;
				case "ipad":
					capabilities.setCapability("appiumVersion", capData.get("AppiumVersion"));
					capabilities.setCapability("deviceName",capData.get("DeviceName"));
					capabilities.setCapability("deviceOrientation", capData.get("DeviceOrientation"));
					capabilities.setCapability("browserName", capData.get("Browser"));
					capabilities.setCapability("platformVersion", capData.get("PlatformVersion"));
					capabilities.setCapability("platformName", capData.get("PlatformName"));
					capabilities.setCapability("name", capData.get("TestCaseName"));
					capabilities.setCapability("safariAllowPopups", "true");
					capabilities.setCapability("safariOpenLinksInBackground", "true");
					break;
				case "desktop":
					
					switch (capData.get("Browser").toLowerCase()) {
					case "chrome":
						capabilities = DesiredCapabilities.chrome();
						break;
					case "firefox":
						capabilities = DesiredCapabilities.firefox();
						break;
					case "safari":
						capabilities = DesiredCapabilities.safari();
						break;
					case "ie":
						capabilities = DesiredCapabilities.internetExplorer();
						break;
					default:
						break;
					}
					
					capabilities.setCapability("platformVersion", capData.get("PlatformVersion"));
					capabilities.setCapability("platformName", capData.get("PlatformName"));
					capabilities.setCapability("name", capData.get("TestCaseName"));
					break;
				default:
					System.out.println("Specify correct platformType in testType parameters in test.properties file");
					
				}
			driver = new RemoteWebDriver(urlObj, capabilities);
			
			driver.manage().timeouts().setScriptTimeout(100, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			driver.get(sUrl);
				
			}else if(capData.get("Automation").equalsIgnoreCase("selenium")){
				if(capData.get("Browser").equalsIgnoreCase("chrome")||capData.get("Browser").equalsIgnoreCase("no browser")){
					String OS = System.getProperty("os.name").toLowerCase(); 
                    String executable = "chromedriver.exe"; // byDefault Windows    
                          
                    if  (OS.indexOf("linux") >= 0){ 
                     executable = "chromedriver"; 
                   }
					
					System.setProperty("webdriver.chrome.driver", "./drivers/"+executable);
					driver = new ChromeDriver();
					System.out.println("created chrome driver");
				}
				else if(capData.get("Browser").equalsIgnoreCase("internetexplorer")){
					
					System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
					
                    DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
                    caps.setCapability("javascriptEnabled", true);
                    caps.setCapability("nativeEvents", true);
                    caps.setCapability("requireWindowFocus",true);
                    caps.setCapability("ignoreProtectedModeSettings",true);
                   caps.setCapability("initialBrowserUrl","http://gmail.com");
                    caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
                    driver=new InternetExplorerDriver(caps);
                    driver.manage().window().maximize();
                    driver.manage().deleteAllCookies();
                    System.out.println("created IE driver");
				}
				else if(capData.get("Browser").equalsIgnoreCase("opera")){
				
					System.setProperty("webdriver.opera.driver", "./drivers/operadriver.exe");
					driver = new OperaDriver();
				}
				else if(capData.get("Browser").equalsIgnoreCase("phantomjs")){
					
					 String OS = System.getProperty("os.name").toLowerCase(); 
	                    String executable = "phantomjs.exe"; // byDefault Windows    
	                          
	                    if  (OS.indexOf("linux") >= 0){ 
	                     executable = "phantomjs"; 
	                   } 
					DesiredCapabilities dc = new DesiredCapabilities();
					dc.setJavascriptEnabled(true); // not really needed: JS enabled by default
                    dc.setCapability("takesScreenshot", true);
                    dc.setCapability("screenrecorder", true);
                    dc.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--webdriver-loglevel=NONE"});
                    dc.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { "--ignore-ssl-errors=yes", "--load-images=yes","--web-security=true" });
                    dc.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, "--logLevel=NONE");
                    dc.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
                    dc.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, true);
                    dc.setCapability("phantomjs.page.settings.resourceTimeout", 50000);
                  
                    dc.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "./drivers/"+executable );
                    driver = new PhantomJSDriver(dc);
					
					
				}
				else{
					String OS = System.getProperty("os.name").toLowerCase(); 
                    String executable = "geckodriver"; // byDefault Windows    
                          
                    if  (OS.indexOf("linux") >= 0){ 
                     
                   }
					System.setProperty("webdriver.gecko.driver", "./drivers/"+executable);				
					
					FirefoxOptions options = new FirefoxOptions();
                    /*if  (!(OS.indexOf("linux") >= 0)){ 
                    	
    					options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
    					
                   }
					*/
                    driver =  new FirefoxDriver(options);
					
					System.out.println("created firefox driver");
					
				}
				
				driver.manage().timeouts().setScriptTimeout(100, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
				System.out.println("invoking browser");
				driver.get(sUrl);
				driver.manage().window().maximize();
				
			}
			
			else if(capData.get("Automation").equalsIgnoreCase("perfecto"))
			{
				
				Properties perfectoProp=new Properties();
				perfectoProp.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/perfecto.properties")));
				
				urlObj = new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"); 
				
				capabilities.setCapability("user", perfectoProp.getProperty("perfectoUsername"));
				capabilities.setCapability("password", perfectoProp.getProperty("perfectoPassword"));
				capabilities.setCapability("deviceName", capData.get("udid"));
			     capabilities.setCapability("automationName", "appium");
			     
			     if( capData.get("DeviceName").toLowerCase().contains("tab")){
						capData.put("PlatformName","");
					}
			     if( capData.get("DeviceName").toLowerCase().contains("ipad")){
						capData.put("PlatformName","");
					}
			     
			     	driver = new RemoteWebDriver(urlObj, capabilities);
					driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
					driver.manage().timeouts().setScriptTimeout(100, TimeUnit.SECONDS);
					driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
					driver.get(sUrl);
					primaryWindowHandle = driver.getWindowHandle();	
			     
			}
			
			
			reportStep("The browser:" + capData.get("Browser") + " launched successfully", "PASS");

		} catch (Exception e) {
			e.printStackTrace();
			reportStep("The browser:" + capData.get("Browser") + " could not be launched", "FAIL");
		}		
	}

	  
	  public String returnCurrentlySelectedText(String  property){
		  String  currentlySelectedText="";
		  WebElement element = null;
		  try {

			  element=	  driver.findElement(By.xpath(property));
			  Select dropdown = new Select(element);
			  currentlySelectedText= dropdown.getFirstSelectedOption().getText();
			
		} catch (ElementNotFoundException e) {
			verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
			Assert.assertTrue( false,element+"Element not found\n"+e.getMessage());
			
		}  
		 catch(ElementNotSelectableException e) 
		  {
			 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
			 Assert.assertTrue( false,element+"Element not Selectable\n"+e.getMessage());
		  }
		  catch (ElementNotVisibleException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
		}
		  catch (TimeoutException e) {
				// TODO: handle exception
				verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage() );
			}
		  catch (Exception e) {
			// TODO: handle exception
			  verifyStep(e.getMessage(), "FAIL");
			  Assert.assertTrue(false,e.getMessage());
		}
		  return currentlySelectedText;
		  }
	  public boolean isClickable(WebElement element, WebDriver driver){

		WebDriverWait wait= new WebDriverWait(driver, 5);
		try{	
		wait.until(ExpectedConditions.elementToBeClickable(element));
		 return true;
		}		
		catch(Exception e){
			return false;
		}
		 
	  }
	
	  public void click(String property) {
		  JavascriptExecutor js = (JavascriptExecutor) driver;
		   

		  By byProperty=getLocator(property);
		  WebElement element = null;
		  try {
			   element=driver.findElement(byProperty);
			// scrollToAnElement(element);
			 if(isClickable(element, driver))
				 element.click();
			 else  js.executeScript("arguments[0].click()", element);
			
			  
		  } 
		  catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
			}  
		  catch (TimeoutException e) {
				// TODO: handle exception
				verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
			}
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
			  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage(), "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}	  
			
		

		
	}
	  

	
	public String click(String property, int index)  {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		   

		  
		WebElement element = null;
		String text = "";
		
            try {
                element=driver.findElements(getLocator(property)).get(index);
                text=element.getText();
              
                if(isClickable(element, driver))
   				 element.click();
   			 else 
   				 js.executeScript("arguments[0].click()", element);
   			
   			 
                
         } 

			 
		  
		 catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
			}  
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
		}
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
			  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage(), "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}	  
		
			
			return text;		
	     
	
	}
	
	/*
	public void click(String property, int index) {
		String objectName=null;
		try{
			objectName=driver.findElements(getLocator(property)).get(index).getText();
			driver.findElements(getLocator(property)).get(index).click();
			reportStep("The element : "+objectName+" is clicked.", "PASS");

		} catch (Exception e) {
			e.printStackTrace();
			reportStep("The element : "+objectName+" could not be clicked.", "FAIL");
			
		}
	}
	*/
	public void clickOnPage() {
		
		WebElement element = null;
		try
		{
			element=driver.findElement(By.xpath("//html"));
			element.click();
		}
		 catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage() );
			}  
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
		}
			  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage(), "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}	  
		
		
		
	}
	
	public void enterText(String property, String data)  {
					
		WebElement element = null;
		try {
			element=driver.findElement(getLocator(property));
			element.clear();
			element.sendKeys(data);
		} 
		
		catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
			}  
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
		}
		  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage(), "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}	  
	
		
	
	}

	public void enterText(String property, int index, String data)  {
		
		WebElement element = null;
		
		try {
			element=driver.findElements(getLocator(property)).get(index);
			element.sendKeys(data);
		} 
		catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
			}  
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
		}
		  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage(), "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}	
		
	}
	
	
	
	
	public void mouseOver(String property) {
	
		WebElement element = null;
		try
		{
			element=driver.findElement(getLocator(property));
			new Actions(driver).moveToElement(driver.findElement(getLocator(property))).build().perform();
		}
			
		catch(ElementNotFoundException e)
		{
			 verifyStep(element+"Element not Found\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Found\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch(ElementNotSelectableException e) 
		  {
			 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Selectable\n"+e.getMessage());
			softAssert.assertAll();
		  }
		  catch (ElementNotVisibleException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
				 softAssert.assertTrue(false, element+"Element not Visible\n"+e.getMessage());
				softAssert.assertAll();
		}
	  catch (ElementNotInteractableException e) {
		// TODO: handle exception
		  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
		  SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Interatable\n"+e.getMessage());
			softAssert.assertAll();
	}
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Time out error\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch (Exception e) {
			// TODO: handle exception
			  verifyStep(e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
			  softAssert.assertTrue(false,e.getMessage());
		}	
	
	}
	
	public boolean verifyText(String property, String text) {
	
		WebElement element = null;
		String sText="";
		try
		{
			element=driver.findElement(getLocator(property));
			 sText = element.getText();
		}
		catch(ElementNotFoundException e)
		{
			 verifyStep(element+"Element not Found\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Found\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch(ElementNotSelectableException e) 
		  {
			 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Selectable\n"+e.getMessage());
			softAssert.assertAll();
		  }
		  catch (ElementNotVisibleException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
				 softAssert.assertTrue(false, element+"Element not Visible\n"+e.getMessage());
				softAssert.assertAll();
		}
	  catch (ElementNotInteractableException e) {
		// TODO: handle exception
		  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
		  SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Interatable\n"+e.getMessage());
			softAssert.assertAll();
	}
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Time out error\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch (Exception e) {
			// TODO: handle exception
			  verifyStep(e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
			  softAssert.assertTrue(false,e.getMessage());
		}	

			finally {
				 if (sText.equalsIgnoreCase(text)){

                                   return true;

              }else{

            	  System.out.println("FAIL :sText:"+ sText);
  				System.out.println("FAIL : text:"+ text);
  				  verifyStep("Values" +sText+ " and "+text+" are different","FAIL");

                     return false;

              }
			}
			
		
	}
	
	public boolean verifyText(String property,int index, String text) throws Exception{
		
		WebElement element = null;
		String sText="";
		try
		{
			
			element= driver.findElements(getLocator(property)).get(index);
			sText = driver.findElements(getLocator(property)).get(index).getText();
			
		}
		catch(ElementNotFoundException e)
		{
			 verifyStep(element+"Element not Found\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Found\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch(ElementNotSelectableException e) 
		  {
			 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Selectable\n"+e.getMessage());
			softAssert.assertAll();
		  }
		  catch (ElementNotVisibleException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
				 softAssert.assertTrue(false, element+"Element not Visible\n"+e.getMessage());
				softAssert.assertAll();
		}
	  catch (ElementNotInteractableException e) {
		// TODO: handle exception
		  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
		  SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Interatable\n"+e.getMessage());
			softAssert.assertAll();
	}
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Time out error\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch (Exception e) {
			// TODO: handle exception
			  verifyStep(e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
			  softAssert.assertTrue(false,e.getMessage());
		}	
			
		finally {
			
			
			
			if (sText.equalsIgnoreCase(text)){
				
				return true;
				
			}else{
				System.out.println("FAIL :sText:"+ sText);
				System.out.println("FAIL : text:"+ text);
				  verifyStep("Values" +sText+ " and "+text+" are different","FAIL");
				return false;
			}

		}
					
	}
public boolean verifyTextForPrice(String property,int index, String text) throws Exception{
		
		WebElement element = null;
		String sText="";
		try
		{
			
			element= driver.findElements(getLocator(property)).get(index);
			sText = driver.findElements(getLocator(property)).get(index).getText().replace(",", "");
			
		}
		catch(ElementNotFoundException e)
		{
			 verifyStep(element+"Element not Found\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Found\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch(ElementNotSelectableException e) 
		  {
			 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Selectable\n"+e.getMessage());
			softAssert.assertAll();
		  }
		  catch (ElementNotVisibleException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
				 softAssert.assertTrue(false, element+"Element not Visible\n"+e.getMessage());
				softAssert.assertAll();
		}
	  catch (ElementNotInteractableException e) {
		// TODO: handle exception
		  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
		  SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Interatable\n"+e.getMessage());
			softAssert.assertAll();
	}
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Time out error\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch (Exception e) {
			// TODO: handle exception
			  verifyStep(e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
			  softAssert.assertTrue(false,e.getMessage());
		}	
			
		finally {
			
			
			
			if (sText.equalsIgnoreCase(text)){
				
				return true;
				
			}else{
				System.out.println("FAIL :sText:"+ sText);
				System.out.println("FAIL : text:"+ text);
				  verifyStep("Values" +sText+ " and "+text+" are different","FAIL");
				return false;
			}

		}
					
	}
	public boolean verifyTextContains(String property, String text)  {

		WebElement element = null;
		String sText="";
		try
		{
			element=driver.findElement(getLocator(property));
			sText=element.getText();
		}
		catch(ElementNotFoundException e)
		{
			 verifyStep(element+"Element not Found\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Found\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch(ElementNotSelectableException e) 
		  {
			 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Selectable\n"+e.getMessage());
			softAssert.assertAll();
		  }
		  catch (ElementNotVisibleException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
				 softAssert.assertTrue(false, element+"Element not Visible\n"+e.getMessage());
				softAssert.assertAll();
		}
	  catch (ElementNotInteractableException e) {
		// TODO: handle exception
		  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
		  SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Interatable\n"+e.getMessage());
			softAssert.assertAll();
	}
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Time out error\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch (Exception e) {
			// TODO: handle exception
			  verifyStep(e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
			  softAssert.assertTrue(false,e.getMessage());
		}		
		finally {
			if (sText.contains(text)){
				return true;
			}else{
				System.out.println("FAIL :sText:"+ sText);
				System.out.println("FAIL : text:"+ text);
				  verifyStep("Value" +sText+ " doesn't contain "+text+" ","FAIL");
				return false;
			}
		
		}
			
	}
	
	public boolean verifyObjecthasvalue(String property)  throws Exception{
		String sText=null;
		WebElement element = null;
		
		try
		{
			element=driver.findElement(getLocator(property));
			sText=element.getAttribute("value");
		}
		catch(ElementNotFoundException e)
		{
			 verifyStep(element+"Element not Found\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Found\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch(ElementNotSelectableException e) 
		  {
			 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
			 SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Selectable\n"+e.getMessage());
			softAssert.assertAll();
		  }
		  catch (ElementNotVisibleException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
				 softAssert.assertTrue(false, element+"Element not Visible\n"+e.getMessage());
				softAssert.assertAll();
		}
	  catch (ElementNotInteractableException e) {
		// TODO: handle exception
		  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
		  SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Element not Interatable\n"+e.getMessage());
			softAssert.assertAll();
	}
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			SoftAssert softAssert=new SoftAssert();
			 softAssert.assertTrue(false, element+"Time out error\n"+e.getMessage());
			softAssert.assertAll();
		}
		catch (Exception e) {
			// TODO: handle exception
			  verifyStep(e.getMessage(), "FAIL");
			  SoftAssert softAssert=new SoftAssert();
			  softAssert.assertTrue(false,e.getMessage());
		}	
		finally {
			if (sText!=null){
				return true;
			}
			else{
				return false;
				
			}
		}
			
			
		
		
	}
	public boolean verifyElement(String property) {
		boolean present=true;
		try{
			driver.findElement(getLocator(property));
			return present;
			
		}catch(Exception e){			
			present=false;
			return present;
		}
		
		
	}
	
	public boolean verifyElementIsDisplayed(String property){
        boolean present=true;
        try{
               driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
               present=driver.findElement(getLocator(property)).isDisplayed();
               
               //verifyStep("Element "+property+" is present in the page", "PASS");
        }catch(Exception e){
               //verifyStep("Element "+property+" is not present in the page", "FAIL");
               present=false;
               
        }
        return present;
        
 }

	
	public boolean verifyElementIsDisplayed(String property,int index){
		boolean present=true;
		try{
			present=driver.findElements(getLocator(property)).get(index).isDisplayed();
			//verifyStep("Element "+property+" is present in the page", "PASS");
		}catch(Exception e){
			//verifyStep("Element "+property+" is not present in the page", "FAIL");
			present=false;
			
		}
		return present;
		
	}
	
	public boolean verifyElement(String property,String availability) {
	try{
				if(availability=="true"){
					driver.findElement(getLocator(property));
					return true;
				}
				else{
					try{
						driver.findElement(getLocator(property));
						return false;
					}catch(Exception e){
						return true;
					}			
				}	
	}catch(Exception e){
				
		return false;
	}
	}
	
	
	public boolean verifyElement(String property,int index) {
        boolean present=true;
        try{
              driver.findElements(getLocator(property)).get(index);
              return present;
             
        }catch(Exception e){               
              present=false;
              return present;
        }
       
       
  }
	
	public void verifyElementIsPresent(String property)
	{
		verifyElementIsPresent(property,20);
	}
	
	public void verifyElementIsPresent(String property, long  timeoutInSecs)

	{



	try


	{


	//element=driver.findElement(getLocator(property));

	long startTime =  System.currentTimeMillis();

	//System.out.println("before");

	//System.out.println(startTime);

	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 

	while(System.currentTimeMillis()<(startTime+(timeoutInSecs*1000))) {


	//System.out.println("looped");

	//System.out.println("**"+System.currentTimeMillis());

	if(verifyElement(property))

	{

	driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

	return;

	} 




	//System.out.println(System.currentTimeMillis());

	}



	//System.out.println("not found ");

	verifyStep(property+"Element not found\n", "FAIL");


	Assert.assertTrue(false,property+" is displayed or not validation");



	// WebDriverWait wait=new fluentWebDriverWait(driver, 60);

	//

	// wait.until(ExpectedConditions.visibilityOf(element));

	// new WebDriverWait(driver, Integer.parseInt(strTimeout)).until(ExpectedConditions.visibilityOf(element));

	// System.out.println("strted fluent");

	//FluentWait<WebDriver> wait= new FluentWait<WebDriver>(driver);

	// 

	//wait.withTimeout(60, TimeUnit.SECONDS);

	//wait.pollingEvery(1, TimeUnit.SECONDS);

	//

	// wait.until(ExpectedConditions.visibilityOf(element));

	// System.out.println("ended fluent"); 

	}

	catch (ElementNotFoundException e)


	  {

	e.printStackTrace();


	    verifyStep(property+"Element not found\n"+e.getMessage(), "FAIL");


	Assert.assertTrue(false,property+"Element not found\n"+e.getMessage());


	}  


	catch (TimeoutException e) {


	// TODO: handle exception


	verifyStep(property+"Time out error\n"+e.getMessage(), "FAIL");


	  Assert.assertTrue(false,property+"Time out error\n"+e.getMessage());


	}


	catch(ElementNotSelectableException e) 


	  {


	verifyStep(property+"Element not Selectable\n"+e.getMessage(), "FAIL");


	Assert.assertTrue(false,getLocator(property)+"Element not Selectable\n"+e.getMessage());


	  }


	  catch (ElementNotVisibleException e) {


	// TODO: handle exception


	  verifyStep(property+"Element not Visible\n"+e.getMessage(), "FAIL");


	  Assert.assertTrue(false,property+"Element not Visible\n"+e.getMessage());


	}


	  catch (ElementNotInteractableException e) {


	// TODO: handle exception


	  verifyStep(property+"Element not Interatable\n"+e.getMessage(), "FAIL");


	  Assert.assertTrue(false,property+"Element not Interatable\n"+e.getMessage());


	}



	  catch (Exception e) {


	// TODO: handle exception

	  e.printStackTrace();

	  verifyStep(e.getMessage(), "FAIL");


	  Assert.assertTrue(false,e.getMessage());


	} 






	}




public void verifyElementIsNotPresent(String property, long  timeoutInSecs){

try

{

//element=driver.findElement(getLocator(property));

long startTime =  System.currentTimeMillis();
System.out.println("before");
System.out.println(startTime);
driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
while(System.currentTimeMillis()<(startTime+(timeoutInSecs*1000))) {
System.out.println("looped");
System.out.println("**"+System.currentTimeMillis());
if(verifyElement(property))
{
driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
return;
} 
System.out.println(System.currentTimeMillis());
}
System.out.println("not found ");

verifyStep(property+"Element not found\n", "PASS");
Assert.assertTrue(true,property+" is displayed or not validation");

}
catch (ElementNotFoundException e)
 {
e.printStackTrace();
    verifyStep(property+"Element not found\n"+e.getMessage(), "PASS");
Assert.assertTrue(true,property+"Element not found\n"+e.getMessage());
}  
catch (TimeoutException e) {
// TODO: handle exception
verifyStep(property+"Time out error\n"+e.getMessage(), "FAIL");
 Assert.assertTrue(false,property+"Time out error\n"+e.getMessage());
}
catch(ElementNotSelectableException e)  {
verifyStep(property+"Element not Selectable\n"+e.getMessage(), "FAIL");
Assert.assertTrue(false,getLocator(property)+"Element not Selectable\n"+e.getMessage());
  }
  catch (ElementNotVisibleException e) {
// TODO: handle exception
  verifyStep(property+"Element not Visible\n"+e.getMessage(), "FAIL");
  Assert.assertTrue(false,property+"Element not Visible\n"+e.getMessage());
}
  catch (ElementNotInteractableException e) {
// TODO: handle exception
  verifyStep(property+"Element not Interatable\n"+e.getMessage(), "FAIL");
  Assert.assertTrue(false,property+"Element not Interatable\n"+e.getMessage());
}

  catch (Exception e) {
// TODO: handle exception
e.printStackTrace();
verifyStep(e.getMessage(), "FAIL");
Assert.assertTrue(false,e.getMessage());

} 

}



	
	public void verifyTextOnPage(String value) {

      
    	  if(driver.getPageSource().contains(value)){
    		  
    		  verifyStep(value+" is displayed on page", "PASS");
			  
          }   
    	  else
    	  {
    		  verifyStep(value+" is displayed on page", "FAIL");
		  }
      
	}

	public void verifyPage(String value){
       
              //WebDriverWait wait = new WebDriverWait(driver, 30 /*timeout in seconds*/);
              String currentUrl=driver.getCurrentUrl();
              //int customwait=0;
              if (currentUrl.contains(value)==true){
                    verifyStep("Navigated to "+value+" page", "PASS");
              }
              else
              {
            	  verifyStep("Did not Navigate to "+value+" page", "FAIL");
            	  Assert.assertTrue(false,"Did not Navigate to "+value+" page");
              }
              
        
  }

	
	public String getText(String property){
		String bReturn = "";
		
		WebElement element = null;
		
		
		try{
			element=driver.findElement(getLocator(property));
			return element.getText();
		} 
		catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
			}  
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
		}
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
		
		  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage()+"\nThe element with xpath: "+property+" could not be found.", "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}
		
		return bReturn; 
	}

	public String getText(String property,int index){
		String bReturn = "";
		
		WebElement element = null;
		
		
		try{
			element=driver.findElements(getLocator(property)).get(index);
			return element.getText();
		} 
		catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
			}  
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
		}
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
		
		  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage()+"\nThe element with xpath: "+property+" could not be found.", "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}
		
		return bReturn; 
	}
	public boolean verifyTitle(String title){
		boolean bReturn = false;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			if (driver.getTitle().equalsIgnoreCase(title)){
				verifyStep(title+"validation passed", "PASS");

				bReturn = true;
			}else{
				 verifyStep(title+"validation failed", "FAIL");
				  SoftAssert softAssert=new SoftAssert();
				  softAssert.assertTrue(false,"Title");
				bReturn = false;
			}
			
				
		
		return bReturn;
	}

	
	
	public void quitBrowser() {
		try {
			
			//acceptifAlertPresent();
			driver.close();
			//acceptifAlertPresent();
			//driver.findElement(By.xpath("//a[contains(@onclick,'home.page') or contains(@href,'home.page')]")).click();
			driver.quit();
			System.out.println("closed all webdriver");
			
		} catch (Exception e) {
			//System.out.println("browser could not be closed");
			//reportStep("The browser:"+driver.getCapabilities().getBrowserName()+" could not be closed.", "FAIL");
		} 

	}
	
	public void endAll(){
	try{
		while(isAlertPresent()){
			System.out.println("Alert is present");
			acceptAlert();
			driver.quit();
		}
		
	}catch(Exception e){
		System.out.println("error while handling popups");
	}
	
	}

	
	public void selectVisibileText(String property, String value) throws Exception  {
		
		WebElement element=driver.findElement(getLocator(property));
		try
		{
			Select select=new Select(driver.findElement(getLocator(property)));
			select.selectByVisibleText(value);
		}
		catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage() );
			}  
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
		}
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
		
		  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage()+"\nThe element with xpath: "+property+" could not be found.", "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}
			
		
	}


	public void selectIndex(String property, String value) {
		
		WebElement element=driver.findElement(getLocator(property));
		try
		{
			Select select=new Select(element);
			select.selectByIndex(Integer.parseInt(value));
			
		}
		catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
			}  
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
		}
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
		
		  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage()+"\nThe element with xpath: "+property+" could not be found.", "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}
			
		
	}

	public void switchToParentWindow() {
		try {
			Set<String> winHandles = driver.getWindowHandles();
			for (String wHandle : winHandles) {
				driver.switchTo().window(wHandle);
				break;
			}
		} catch (Exception e) {
			verifyStep(e.getMessage()+"The window could not be switched to the first window.", "FAIL");
			//reportStep("The window could not be switched to the first window.", "FAIL");
			Assert.assertTrue(false,"The window could not be switched to the first window.");
		}
	}

	public void switchToLastWindow() {
		try {
			Set<String> winHandles = driver.getWindowHandles();
			for (String wHandle : winHandles) {
				driver.switchTo().window(wHandle);
			}
		} catch (Exception e) {
			verifyStep(e.getMessage()+"The window could not be switched to the last window.", "FAIL");
			//reportStep("The window could not be switched to the first window.", "FAIL");
			Assert.assertTrue(false,"The window could not be switched to the first window.");
			//reportStep("The window could not be switched to the last window.", "FAIL");
		}
	}
	
	public void switchToLatestWindow() {
		try {
			for(String winHandle : driver.getWindowHandles()){
				
			     driver.switchTo().window(winHandle);
			}
		} catch (Exception e) {
			verifyStep(e.getMessage()+"The window could not be switched to the last window.", "FAIL");
			//reportStep("The window could not be switched to the first window.", "FAIL");
			Assert.assertTrue(false,"The window could not be switched to the Latest window.");
			//reportStep("The window could not be switched to the last window.", "FAIL");
		}
	}
	
	public void closeLastWindow(){
		try {
			Set<String> winHandles = driver.getWindowHandles();
			for (String wHandle : winHandles) {
				driver.switchTo().window(wHandle).close();
			}
		} catch (Exception e) 
		{
			verifyStep(e.getMessage()+"could not close the last open window.", "FAIL");
			//reportStep("The window could not be switched to the first window.", "FAIL");
			Assert.assertTrue(false,"Tcould not close the last open window.");
			//reportStep("could not close the last open window.", "FAIL");
		}
	}

	public void switchtoFrame(String property){
		try{
			driver.switchTo().frame(driver.findElement(getLocator(property)));
		}
		catch(Exception e){
			//reportStep("could not identify the frame.", "FAIL");
			verifyStep(e.getMessage()+"could not identify the frame.", "FAIL");
			//reportStep("The window could not be switched to the first window.", "FAIL");
			Assert.assertTrue(false,"could not identify the frame.");
		}
	}
	
	public void PreviousPage(){
		try{
			driver.navigate().back();
		}catch(Exception e){
			verifyStep(e.getMessage()+"Failed to navigate to previous page"+e.getMessage(), "FAIL");
			//reportStep("The window could not be switched to the first window.", "FAIL");
			Assert.assertTrue(false,"Failed to navigate to previous page.");
			//reportStep("Failed to navigate to previous page", "FAIL");
		}
	}
	
	public void acceptAlert() {
		try {
			//check alert is present
			// if not present wait for 1 sec
			int customwait=0;
			while (isAlertPresent()==false && customwait!=30){
				Thread.sleep(1000);
				customwait=customwait+1;
			}
			driver.switchTo().alert().accept();
			//System.out.println("alert is clicked");
			
		
		} catch (NoAlertPresentException e) {
			verifyStep(e.getMessage()+"No Alert is Present", "FAIL");
			//reportStep("The window could not be switched to the first window.", "FAIL");
			Assert.assertTrue(false,"No Alert is Present");
		} catch (Exception e) {
			verifyStep(e.getMessage()+"No Alert is Present", "FAIL");
			//reportStep("The window could not be switched to the first window.", "FAIL");
			Assert.assertTrue( false,"No Alert is Present");
		}

	}
	
	public void acceptErrorMessagePopup() {
		try {
			//check alert is present
			// if not present wait for 1 sec
			int customwait=0;
			while (isAlertPresent()==false && customwait!=5){
				Thread.sleep(1000);
				customwait=customwait+1;
			}
			driver.switchTo().alert().accept();
			System.out.println("alert is clicked");
			verifyStep("Error message is displayed", "PASS");
			
		
		} catch (NoAlertPresentException e) {
			//System.out.println("alert is not clicked");
			verifyStep("alert is not present "+e.getMessage(), "FAIL");
			Assert.assertTrue(false,"alert is not Present");
			
		} catch (Exception e) {
			verifyStep("alert is not displayed "+e.getMessage(), "FAIL");
			Assert.assertTrue(false,"alert is not displayed");
			
		}

	}
	
	public void acceptifAlertPresent() {
		try {
			//check alert is present
			// if not present wait for 1 sec
			int customwait=0;
			while (isAlertPresent()==false && customwait!=5){
				Thread.sleep(1000);
				customwait=customwait+1;
			}
			driver.switchTo().alert().accept();
			//System.out.println("alert is clicked");
			driver.switchTo().defaultContent();
			
		
		} catch (NoAlertPresentException e) {
			//System.out.println("alert is not clicked");
			verifyStep("alert  is not present "+e.getMessage(), "FAIL");
			Assert.assertTrue(false,"alert is not present");
			
		} catch (Exception e) {
			//System.out.println("alert is not clicked");
			verifyStep("alert  is not displayed "+e.getMessage(), "FAIL");
			Assert.assertTrue(false,"alert is not displayed");
			//System.out.println("alert is not present");
			
		}

	}

	public String getAlertText() {		
		String text = null;
		try {
			driver.switchTo().alert().dismiss();
		} catch (NoAlertPresentException e) {
			verifyStep("alert  is not present"+e.getMessage(), "FAIL");
			Assert.assertTrue(false,"alert is not present");
			
		} catch (Exception e) {
			
			verifyStep("alert  is not displayed"+e.getMessage(), "FAIL");
			Assert.assertTrue(false,"alert is not displayed");
			//System.out.println("alert is not present");
			
		}
		return text;

	}

	public void dismissAlert() {
		try {
			driver.switchTo().alert().dismiss();
			reportStep("The alert found.", "PASS");
		} catch (NoAlertPresentException e) {
			verifyStep("alert  is not present"+e.getMessage(), "FAIL");
			Assert.assertTrue(false,"alert is not present");
		} catch (Exception e) {
			verifyStep("alert  is not displayed"+e.getMessage(), "FAIL");
			Assert.assertTrue(false,"alert is not displayed");
		}

	}
	
	
	public boolean isAlertPresent(){
	    try{
	        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	        //Alert a;
	        driver.switchTo().alert();
	        //a.accept();
	        System.out.println("alert is present");
	        return true;
	    }
	    catch (NoAlertPresentException e) {
	    	
	        return false;
	    }
	   
	   
	}
	
	
	public long takeSnap(){
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
		try {
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE) , new File("./reports/images/"+number+".jpg"));
		} 
		catch (WebDriverException e) {
	//		reportStep("The browser has been closed.", "FAIL");
			
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			reportStep("The snapshot could not be taken", "WARN");
		}
		return number;
	}

	public void pageWaitMid() {
		 
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void pageWaitMax() {
		 
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void pageWaitMin() {
		 
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void customPageWait(long timeSec){
		try {
			Thread.sleep(timeSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void customWaitUntil(String property ) {
	
			FluentWait<RemoteWebDriver> fluentWait = new FluentWait<>(driver) 
			.withTimeout(45, TimeUnit.SECONDS)
			.pollingEvery(200, TimeUnit.MILLISECONDS);
			fluentWait.until(ExpectedConditions.presenceOfElementLocated(getLocator(property)));
			String url=driver.getCurrentUrl();
			String page = url.substring(url.lastIndexOf("/")+1);
		
		
	}
	public void waitUntilVisibilityOf(String property) {
		WebElement element=driver.findElement(getLocator(property));
		
	    wait = new WebDriverWait(driver, 45 /*timeout in seconds*/);
	    
	        wait.until(ExpectedConditions.visibilityOf(element));
	      
	   
	}
	
	public void waitUntilVisibilityOf(String property, int index) {
		WebElement element=driver.findElements(getLocator(property)).get(index);
		
	    wait = new WebDriverWait(driver, 45 /*timeout in seconds*/);
	
	        wait.until(ExpectedConditions.visibilityOf(element));
	        
	    
	}
	
	public void waitUntilElementExist(String property, String text){
	WebElement element=driver.findElement(getLocator(property));
		
	    wait = new WebDriverWait(driver, 45 /*timeout in seconds*/);
	    wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	       
	    
	}
	
	public void waitUntilElementExist(String property,int index, String text) {
		WebElement element=driver.findElements(getLocator(property)).get(index);
			
		    wait = new WebDriverWait(driver, 45 /*timeout in seconds*/);
		 
		  wait.until(ExpectedConditions.textToBePresentInElement(element, text));
		    
		}
	
	public void waitUntilStaleElementExist(String property) {
			WebElement element=driver.findElement(getLocator(property));
		
	    	wait = new WebDriverWait(driver, 45 /*timeout in seconds*/);
	  
	        wait.until(ExpectedConditions.stalenessOf(element));
	       
	    
	}
	
	//New WebDriverWait(driver, TimeSpan.FromSeconds(timeOut))
	//.Until(ExpectedConditions.ElementExists((By.Id(InsertElementIdHere)))) ;
	
	
	
	public void waitTillNavigatedToPage(String page) throws Exception{
	
			//WebDriverWait wait = new WebDriverWait(driver, 30 /*timeout in seconds*/);
			String currentUrl=driver.getCurrentUrl();
			int customwait=0;
			while (currentUrl.contains(page)==false && customwait!=30){
				Thread.sleep(1000);
				customwait=customwait+1;
			}
			
		
	}
	
	
	public void waitTillpageisloaded(String property) {
	
			FluentWait<RemoteWebDriver> fluentWait = new FluentWait<>(driver) 
			.withTimeout(30, TimeUnit.SECONDS)
			.pollingEvery(200, TimeUnit.MILLISECONDS);
			fluentWait.until(ExpectedConditions.presenceOfElementLocated(getLocator(property)));
			String url=driver.getCurrentUrl();
			String page = url.substring(url.lastIndexOf("/")+1);
			reportStep(page + " loaded successfully", "PASS");
				
		
	}
	
	public String getAttributeValue(String property, String attribute) {
		WebElement element = null;
		String value="";
		try
		{
			 element=driver.findElement(getLocator(property));
			 value=driver.findElement(getLocator(property)).getAttribute(attribute);
		}
		catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
			}  
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
		}
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
		
		  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage(), "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}	
		finally
		{
			return value;
		}
			//String value=driver.findElement(getLocator(property)).getAttribute(attribute);
		
			//return value;
		
		
	}
	
	
	public String getCssValue(String property, String attribute) {
		WebElement element = null;
		String value="";
		try
		{
			element=driver.findElement(getLocator(property));
			value=driver.findElement(getLocator(property)).getCssValue(attribute);	
		}
		catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
			}  
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
		}
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
		
		  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage(), "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}	
		finally
		{
			return value;
		}
		
		
			
	
		
	}
	
	public By getLocator(String property){
		String locator =property;
		// Split the value which contains locator type and locator value

				String locatorType = locator.split("===")[0];
				String locatorValue = locator.split("===")[1];
				// Return a instance of By class based on type of locator
				if (locatorType.toLowerCase().equals("id"))	
					return By.id(locatorValue);
				else if (locatorType.toLowerCase().equals("name"))
					return By.name(locatorValue);
				else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
					return By.className(locatorValue);
				else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
					return By.className(locatorValue);
				else if ((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
					return By.linkText(locatorValue);
				else if (locatorType.toLowerCase().equals("partiallinktext"))
					return By.partialLinkText(locatorValue);
				else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
					return By.cssSelector(locatorValue);
				else if (locatorType.toLowerCase().equals("xpath"))
					return By.xpath(locatorValue);
				else
					return null;
			}

	public void leavePageWindowPopUP(){
		try{
			driver.switchTo().activeElement().sendKeys(Keys.ENTER);
			//Runtime.getRuntime().exec("D:\\Clone\\Prodver3\\drivers\\autoEnter.exe");

		}catch(Exception e){
			e.printStackTrace();
			//System.out.println("window pop up handler error");
		}
		
	}
	
	public void stopPageLoad(){
		try{
			System.out.println("inside stop load page");
			driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
			System.out.println("page load is stoped");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("could not stop page load");
		}
		
	}
	
	public void check500() {
		try{
			driver.findElement(By.cssSelector("div#500.error"));
			reportStep("500 page displayed!", "FAIL");
		}catch(Exception e){
			e.printStackTrace();
			verifyStep("Page loaded successfully", "PASS");
		}
	    
	}
	
	
	
	public List<WebElement> getAllLinks(String property){
		List<WebElement> Alllinks=driver.findElements(getLocator(property));
		return Alllinks;
	}
	
		
	public void verifyAllLinks(String property){
		
		List<WebElement> AllLinks=driver.findElements(getLocator(property));
		List<String> xpaths = new ArrayList<String>();
		for (WebElement link : AllLinks) {
			String href=link.getAttribute("href");
			if(href.contains("'")){
				href = href.split("'")[1];
			}
			String xpath="//*[contains(@href,'"+href+"')]";
			xpaths.add(xpath);
			
		}
		System.out.println(xpaths);
		for (String xpath:xpaths){
			try{
				String currentUrl=driver.getCurrentUrl();
				driver.findElement(By.xpath(xpath)).click();
				int customwait=0;
				while (driver.getCurrentUrl().equals(currentUrl) || customwait==60){
					Thread.sleep(1000);
					customwait=customwait+1;
				}
				if(driver.getCurrentUrl().equals(currentUrl))
					reportStep("The element : "+xpath+" is not clicked or not loaded.", "FAIL");
				else
					reportStep("The element : "+xpath+" is clicked and loaded.", "PASS");
			}
			catch(Exception e){
				e.printStackTrace();
				reportStep("The element : "+xpath+" could not be clicked.", "FAIL");
			}
			
			check500();
			PreviousPage();
		}
	}
	
	
	
	public String genRandomEmail() {
	    String randomText = "abcdefghijklmnopqrstuvwxyz";
	    int length = 8;
	    String temp = RandomStringUtils.random(length, randomText);
	    String email=temp+"@gmail.com";
	    return email;
	}
	
	public String getSysDate(){
		String timeStamp = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
	
	public String getLastWeekDate(){

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date todate1 = cal.getTime();    
        String fromdate = new SimpleDateFormat("dd.MM.yyyy").format(todate1);
        return fromdate;	
		
		
	}
	

	public String genRandomNumber(int length) {
	      String randomNumber = "0123456779";
	      String temp = RandomStringUtils.random(length, randomNumber);
	      return temp;
	  }

	
	 
	 public boolean isSelected(String property){
		 boolean value=false;
		 WebElement element = null;
		 try {
			 element=driver.findElement(getLocator(property));
			 value=driver.findElement(getLocator(property)).isSelected();
			 
		 }
		 catch (ElementNotFoundException e)
		  {
			    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
			}  
		catch (TimeoutException e) {
			// TODO: handle exception
			verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
		}
			 catch(ElementNotSelectableException e) 
			  {
				 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
				 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
			  }
			  catch (ElementNotVisibleException e) {
				// TODO: handle exception
				  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
				  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
			}
		  catch (ElementNotInteractableException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
		}
		
		  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage(), "FAIL");
				  Assert.assertTrue(false,e.getMessage());
			}	
		
		 finally
		 {
			 return value;
		 }
		
	 }
	 
		//new
		
	    public void selectFromList(String property,String value){
		try{
			List<WebElement> options = driver.findElements(getLocator(property));
			// Loop through the options and select the one that matches 'value'
	        for (WebElement opt : options) {
	            if (opt.getText().equals(value)) {
	                opt.click();
	               return;
	            }
	        }
		
			
			reportStep("The element "+property+" is selected with value :"+value, "PASS");
		}
		catch(Exception e){
			verifyStep("The value: "+value+" could not be selected."+e.getMessage(), "FAIL");
			Assert.assertTrue(false,"The value: "+value+" could not be selected."+e.getMessage());
			//reportStep("The value: "+value+" could not be selected.", "FAIL");
		}
	}

	    public boolean checkTitle(String title){
			boolean bReturn = false;
			try{
				System.out.println("**********************");
				System.out.println(driver.getTitle());
				System.out.println(title);
				if (driver.getTitle().equalsIgnoreCase(title)){
					
					
					bReturn = true;
				}
					
			}catch (Exception e) {
				verifyStep("Unknown exception occured while verifying the title"+e.getMessage(), "FAIL");
				Assert.assertTrue(false,"Unknown exception occured while verifying the title"+e.getMessage());
				//reportStep("Unknown exception occured while verifying the title", "FAIL");
			}
			finally {
				return bReturn;
			}
			//return bReturn;
		}
	    
  public String getResource(String url){
			
			String value="";
			String word[]=url.split("^w{3}.|^https://|^http://");
			for(String w:word){
		    String s[]=w.split("[.]");
		        
		      value=s[1];
			
		}
			return value;
}
  public void keypadDown(String property) {
	  WebElement element=driver.findElement(By.xpath(property));
		element.sendKeys(Keys.ENTER);
	}
  
  public void tapRandom(){		
		TouchAction ta = new TouchAction((MobileDriver) driver);
		ta.tap (200, 75).perform();	
	}
  public void selectDropDownByValue(String  property,String value){
	  WebElement element=
			  driver.findElement(By.xpath(property));
	  Select dropdown = new Select(element);
	  dropdown.selectByValue(value);
	  }

  public void clearElement(String  property){

	  WebElement element = null;

	  try{


	  element=driver.findElement(getLocator(property));

	    element.clear();

	  }

	  catch (ElementNotFoundException e)

	    {

	      verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");

	  Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());

	  }  

	  catch (TimeoutException e) {

	  // TODO: handle exception

	  verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");

	    Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());

	  }

	  catch(ElementNotSelectableException e) 

	    {

	  verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");

	  Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());

	    }

	    catch (ElementNotVisibleException e) {

	  // TODO: handle exception

	    verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");

	    Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());

	  }

	    catch (ElementNotInteractableException e) {

	  // TODO: handle exception

	    verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");

	    Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());

	  }

	    catch (Exception e) {

	  // TODO: handle exception

	    verifyStep(e.getMessage(), "FAIL");

	    Assert.assertTrue(false,e.getMessage());

	  } 

	   

	    }
  
  
  public void clearElement(String  property,int index){

	  WebElement element = null;

	  try{


	  element=driver.findElements(getLocator(property)).get(index);

	    element.clear();

	  }

	  catch (ElementNotFoundException e)

	    {

	      verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");

	  Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());

	  }  

	  catch (TimeoutException e) {

	  // TODO: handle exception

	  verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");

	    Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());

	  }

	  catch(ElementNotSelectableException e) 

	    {

	  verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");

	  Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());

	    }

	    catch (ElementNotVisibleException e) {

	  // TODO: handle exception

	    verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");

	    Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());

	  }

	    catch (ElementNotInteractableException e) {

	  // TODO: handle exception

	    verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");

	    Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());

	  }

	    catch (Exception e) {

	  // TODO: handle exception

	    verifyStep(e.getMessage(), "FAIL");

	    Assert.assertTrue(false,e.getMessage());

	  } 

	   

	    }
  public void swipeFromBottomToTop() {
		try {
			Thread.sleep(2000);
			org.openqa.selenium.Dimension scrnSize = driver.manage().window().getSize();
			int startx = (int) (scrnSize.width /2);
			int starty = (int) (scrnSize.height * 0.3);
			int endy = (int) (scrnSize.height * 0.6);
			//int endx = (int) (scrnSize.width /2);
			((TouchShortcuts) driver).swipe(startx, endy, startx, starty, 3000);
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
  

  public double convertToPriceInDouble(String priceStr) {
		double price = 0.0;
		if (!priceStr.equals("")) {
			priceStr = CharMatcher.DIGIT.retainFrom(priceStr);
			int length = priceStr.length();
			priceStr = priceStr.substring(0, length - 2) + "." + priceStr.substring(length - 2);
			price = Double.parseDouble(priceStr);
		}
		return price;
	}

  
  public void scrollToAnElement(WebElement element)  {


	//   WebElement element = driver.findElement(getLocator(Property));
	//  
	  try
	  {
		  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
		  Thread.sleep(500);
	  }
	 
	  
	  catch (ElementNotFoundException e)
	  {
		    verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
			Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
		}  
	catch (TimeoutException e) {
		// TODO: handle exception
		verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
		  Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
	}
		 catch(ElementNotSelectableException e) 
		  {
			 verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
			 Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
		  }
		  catch (ElementNotVisibleException e) {
			// TODO: handle exception
			  verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
			  Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
		}
	  catch (ElementNotInteractableException e) {
		// TODO: handle exception
		  verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
		  Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
	}
	
	  catch (Exception e) {
			// TODO: handle exception
			  verifyStep("Element not Found", "FAIL");
			  Assert.assertTrue(false,e.getMessage());
		}	
	  
	  
	  }
  
  public void enterTextUsingJavascript(String property, String data)  {
		
		WebElement element = null;
		try {
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript("arguments[0].textContent= arguments[1];", driver.findElement(getLocator(property)),data);

		  
		} 		
		
		  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage(), "FAIL");
				  Assert.assertTrue( false,e.getMessage());
			}	  
	
		
	
	}

public void enterTextWithoutClear(String property, String data)  {
		
		WebElement element = null;
		try {
			
			
			driver.findElement(getLocator(property)).sendKeys(data);
		  
		} 		
		
		  catch (Exception e) {
				// TODO: handle exception
				  verifyStep(e.getMessage(), "FAIL");
				  Assert.assertTrue( false,e.getMessage());
			}	  
	
		
	
	}


public String getMarketId(String market)  {
    String marketid="";
    switch(market){
    case "poland":
         
          marketid="30";
          break;
         
         
    case "hungary":
               
                marketid="2";
                break;
    case "czech":
         
                marketid="24";
                break;
    case "romania":
         
                marketid="27";
                break;
    case "bulgaria":
               
                marketid="31";
                break;

}
    return marketid;
}


public String getAttributeValue(String property, String attribute,int index) {
	WebElement element = null;
	String value="";
	try
	{
		scrollToAnElement(property,index);
		element=driver.findElements(getLocator(property)).get(index);
		value=element.getAttribute(attribute);
	}
	catch (ElementNotFoundException e)
	{
		verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
	}
	catch (TimeoutException e) {
		// TODO: handle exception
		verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
	}
	catch(ElementNotSelectableException e)
	{
		verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
	}
	catch (ElementNotVisibleException e) {
		// TODO: handle exception
		verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
	}
	catch (ElementNotInteractableException e) {
		// TODO: handle exception
		verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
	}

	catch (Exception e) {
		// TODO: handle exception
		verifyStep(e.getMessage(), "FAIL");
		Assert.assertTrue(false,e.getMessage());
	}	
	finally
	{
		return value;
	}
		//String value=driver.findElement(getLocator(property)).getAttribute(attribute);
	
		//return value;
	
	
}


public void scrollToAnElement(String property)  {


	WebElement element = driver.findElement(getLocator(property));
	try
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
		Thread.sleep(500);
	}
	catch (ElementNotFoundException e)
	{
		verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
	}
	catch (TimeoutException e) {
		// TODO: handle exception
		verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
	}
	catch(ElementNotSelectableException e)
	{
		verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
	}
	catch (ElementNotVisibleException e) {
		// TODO: handle exception
		verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
	}
	catch (ElementNotInteractableException e) {
		// TODO: handle exception
		verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
	}

	catch (Exception e) {
		// TODO: handle exception
		verifyStep(e.getMessage(), "FAIL");
		Assert.assertTrue(false,e.getMessage());
		}	
	 }
public void scrollToAnElement(String property,int index) throws InterruptedException {


	WebElement element = driver.findElements(getLocator(property)).get(index);
	try
	{
	 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
	 Thread.sleep(500);
	}
	
	catch (ElementNotFoundException e)
	{
		verifyStep(element+"Element not found\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not found\n"+e.getMessage());
	} 
	catch (TimeoutException e) {
		// TODO: handle exception
		verifyStep(element+"Time out error\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Time out error\n"+e.getMessage());
	}
	catch(ElementNotSelectableException e)
	{
		verifyStep(element+"Element not Selectable\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not Selectable\n"+e.getMessage());
	}
	catch (ElementNotVisibleException e) {
		// TODO: handle exception
		verifyStep(element+"Element not Visible\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not Visible\n"+e.getMessage());
	}
		catch (ElementNotInteractableException e) {
		// TODO: handle exception
		verifyStep(element+"Element not Interatable\n"+e.getMessage(), "FAIL");
		Assert.assertTrue(false,element+"Element not Interatable\n"+e.getMessage());
	}
	catch (Exception e)
	{
		// TODO: handle exception
		verifyStep(e.getMessage(), "FAIL");
		Assert.assertTrue(false,e.getMessage());
	}	
	}



	public void highlightElement(String property) {
	    try {
	            By byProperty=getLocator(property);
	            WebElement ele = driver.findElement(byProperty);
	            //Create object of a JavascriptExecutor interface
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	          
	            js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
	    		}	
	    		catch (Exception e) {
	                  verifyStep(e.getMessage(), "FAIL");
	                  Assert.assertTrue( false,e.getMessage());
	         }      
	}
	
	public void highlightElement(String property,int index) {
	    try {
	            By byProperty=getLocator(property);
	            WebElement ele = driver.findElements(byProperty).get(index);
	            //Create object of a JavascriptExecutor interface
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	          
	            js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
	    		}	
	    		catch (Exception e) {
	                  verifyStep(e.getMessage(), "FAIL");
	                  Assert.assertTrue( false,e.getMessage());
	         }      
	}


	public  void switchToContext(String context) {


		Map<String, String> map = new HashMap<String, String>();


		map.put("name", context);


		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);


		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT,  map);


		}

		public void switchToWindow(String windowName) {

		try {

		for(String winHandle : driver.getWindowHandles()){

		System.out.println(driver.getTitle());

		    driver.switchTo().window(winHandle);

		     

		    if(driver.getTitle().equalsIgnoreCase(windowName))

		    break;

		}

		} catch (Exception e) {

		verifyStep(e.getMessage()+"The window could not be switched to the last window.", "FAIL");

		//reportStep("The window could not be switched to the first window.", "FAIL");

		Assert.assertTrue(false,"The window could not be switched to the Latest window.");

		//reportStep("The window could not be switched to the last window.", "FAIL");

		}

		}
	
		public void randomDropdown(String property) {
			WebElement comboBoxElemenet;
			comboBoxElemenet = driver.findElement(getLocator(property));
			Select comboBox= new Select(comboBoxElemenet );
			int randomIndex = new Random().nextInt(comboBox.getOptions().size());
			while(randomIndex==0)
				randomIndex = new Random().nextInt(comboBox.getOptions().size());
			comboBox.selectByIndex(randomIndex);  
		}
		
		public void waitTillNavigatedToPageTitle(String title){
			
			//WebDriverWait wait = new WebDriverWait(driver, 30 /*timeout in seconds*/);
			String title1=driver.getTitle();
			int customwait=0;
			while (title1.contains(title)==false && customwait!=30){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				customwait=customwait+1;
			}
			
		
	}
		
		
		public void startExtentReport(String market,String applicationType,String browserName)  {
			test = startTestCase(testCaseName, testDescription);
			test.assignCategory(category);
			test.assignAuthor(authors);
			loadObjects(market,applicationType);
			invokeApp(browserName);
			}
		
		
		
		
		
		public void connectToVpn(ReadExcel readExcel,String browserName)
		{
			//AndroidDriver driv=(AndroidDriver) driver;
			//System.out.println(driv);
//			driv.startActivity("com.cisco.anyconnect.vpn.android.avf", "com.cisco.anyconnect.ui.PrimaryActivit");
//			PerfectoApplicationSteps.startAppById("com.cisco.anyconnect.vpn.android.avf");
			driver.quit();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			DesiredCapabilities desc=new DesiredCapabilities();
			desc.setCapability(MobileCapabilityType.UDID, capData.get("udid"));
			desc.setCapability(MobileCapabilityType.DEVICE_NAME,  capData.get("DeviceName"));
			desc.setCapability(MobileCapabilityType.PLATFORM_VERSION, capData.get("PlatformVersion"));
			desc.setCapability(MobileCapabilityType.PLATFORM_NAME, capData.get("PlatformName"));
			desc.setCapability("appPackage", "com.cisco.anyconnect.vpn.android.avf");
			desc.setCapability("appActivity", "com.cisco.anyconnect.ui.PrimaryActivity");
			desc.setCapability("user", "anand.mohandas@avon.com");
			desc.setCapability("password", "Perfecto123");
			desc.setCapability("deviceName", capData.get("udid"));
			desc.setCapability("automationName", "appium");
			
			URL urlObj=null;
			try {
				urlObj = new URL("https://avon.perfectomobile.com/nexperience/perfectomobile/wd/hub");
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			RemoteWebDriver android=new RemoteWebDriver(urlObj,desc);
			
			
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, String> map = new HashMap<String, String>();


			map.put("name", "NATIVE_APP");


			RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(android);


			executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT,  map);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			android.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			if(android.findElement(By.id("com.cisco.anyconnect.vpn.android.avf:id/cb_vpntoggle")).getText().equalsIgnoreCase("off"))
			{
				android.findElement(By.id("com.cisco.anyconnect.vpn.android.avf:id/cb_vpntoggle")).click();
				android.findElement(By.id("com.cisco.anyconnect.vpn.android.avf:id/et_PromptEntry_Input")).clear();
				
				android.findElement(By.id("com.cisco.anyconnect.vpn.android.avf:id/et_PromptEntry_Input")).sendKeys(readExcel.getValue("AnyConnectUserName"));
				android.findElement(By.id("com.cisco.anyconnect.vpn.android.avf:id/et_password")).sendKeys(readExcel.getValue("AnyConnectPassword"));
				android.findElement(By.id("android:id/button1")).click();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				android.findElement(By.id("android:id/button1")).click();
			}
			
			android.quit();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//this.driver=android;
			invokeApp(browserName);
			
		}

		
		
}
