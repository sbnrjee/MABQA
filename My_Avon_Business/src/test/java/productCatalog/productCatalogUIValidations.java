package productCatalog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.bcel.verifier.VerifierFactoryListModel;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;

import pageobjects.DashboardPage;
import pageobjects.Loginpage;
import pageobjects.ProductCatalogSearchPage;
import utils.DBConn;
import utils.ReadExcel;
import wrappers.AvonWrapper;

public class productCatalogUIValidations extends AvonWrapper {
	DBConn db;
	@BeforeClass
	@Parameters({"Automation","Browser","Market","PlatformName","PlatformVersion","DeviceName","udid","AppiumVersion","DeviceOrientation","Application"})
	public void setData(String automation,String browser, String mrkt,String platformNam, String platformVer, String deviceNam, String udid, String appiumVer, String devOrientation,String application) throws FileNotFoundException, IOException {
		testCaseName=" ";

		testDescription=" ";
		category="Functional";
		browserName=browser;
		market=mrkt;
		applicationType=application;
		authors="Umaya";
		capData.put("Automation", automation);
		capData.put("Browser", browser);
		capData.put("Market", mrkt);
		capData.put("PlatformName", platformNam);
		capData.put("PlatformVersion", platformVer);
		capData.put("DeviceName", deviceNam);
		capData.put("udid", udid);
		capData.put("AppiumVersion", appiumVer);
		capData.put("DeviceOrientation", devOrientation);
		capData.put("TestCaseName", testCaseName);
		
		ReadExcel readExcel=new ReadExcel();
		try {
			readExcel.openExcel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//=========================Profile================================
	//TC 1
		@Test(priority=1,enabled=true)
		public void updateProfile() throws Exception
		{
				db= new DBConn(market);
				testCaseName="Verify profile links and update contact info-"+market+"-"+browserName;
				startExtentReport(market, applicationType, browserName);
				String userName=db.OSAUser(getMarketId(market));
				
				new Loginpage(driver,test,market, capData)
				//.login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);
				//.loginLanding()
				.cookieAcceptance()
				.loginAs(userName)
					.withPassword()
					.login();
				new DashboardPage(driver, test, capData)
				.navigateToProductCatalogue(market);;
				new ProductCatalogSearchPage(driver, test, market,  capData)
				.landing()
				.validateCategory()			
				.browsingProductFromCategory()
				.navigationToQuickView();
		}/*
		//=========================Product Catalog================================
				@Test(priority=2,enabled=false)
				public void order_prodCatalog() throws Exception
				{
						
						testCaseName="Order via product cataog--"+market+"-"+browserName;
						startExtentReport(market, applicationType, browserName);
						ReadExcel readExcel=new ReadExcel();
						readExcel.excelRead("productCatalog",capData);
						String userName=readExcel.getValue("username");
						String loginType=readExcel.getValue("loginType");
						
						
						new Loginpage(driver,test,capData)
						.login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);

						new DashboardPage(driver, test, capData)
						.navigateToProductCatalogue(market);
						new RegressionPage(driver, test, market, userName,  capData)
						.verifyProductCatalogOrdering(readExcel,capData);
			
				}
				//=========================OSA================================	
				@Test(priority=3,enabled=false)
				public void validateOSA() throws Exception
				{
						
						testCaseName="OValid OSA form submission-"+market+"-"+browserName;
						startExtentReport(market, applicationType, browserName);
						ReadExcel readExcel=new ReadExcel();
						readExcel.excelRead("productCatalog",capData);
						String userName=readExcel.getValue("username");
						String loginType=readExcel.getValue("loginType");
						new DashboardPage(driver, test, capData)
						
						.navigateToOSAPage(market);
						new RegressionPage(driver, test, market, userName,  capData)
						.validateOSA(readExcel,capData);
			
				}
				//=========================HELP &CONTACT US================================	
				@Test(priority=4,enabled=false)
				public void helpContact() {
					ReadExcel readExcel=new ReadExcel();
					testCaseName="Help Contact Us - "+market+"- with browser "+browserName;
					startExtentReport(market, applicationType, browserName);
					try {
						readExcel.excelRead("productCatalog",capData);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String userName=readExcel.getValue("username");
					String loginType=readExcel.getValue("loginType");


					new Loginpage(driver,test,capData)
					.login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);

					new DashboardPage(driver, test,capData)
					
					.navigateToHelp(market);
					
						try {
							new RegressionPage(driver, test, market,userName, capData)
							.choosetopic();
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					
				}
				//=========================Orders================================	
				@Test(priority=5,enabled=false)
				//TestCase: Create new Connection
				public void createNewConn() {
					ReadExcel readExcel=new ReadExcel();
					testCaseName="Create new connection in  "+market+" with browser "+browserName;
					startExtentReport(market, applicationType, browserName);
					try {
						readExcel.excelRead("productCatalog",capData);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String userName=readExcel.getValue("username");
					String loginType=readExcel.getValue("loginType");


					new Loginpage(driver,test,capData)
					.login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);

					new DashboardPage(driver, test,capData)
					
					.navigateToMyConn(market);
					new MyConnPage(driver, test, market,userName, capData)
					.createNewConnection();
				}
				
				@Test(priority=6,enabled=false)
				//TestCase: Create pending order for a new connection
				public void createPendingOrder() {
					try{
						ReadExcel readExcel=new ReadExcel();
						testCaseName="Create Pending Order with New Connection in market "+market+" with browser "+browserName;
						startExtentReport(market, applicationType, browserName);
						readExcel.excelRead("productCatalog",capData);
						String userName=readExcel.getValue("username");
						String loginType=readExcel.getValue("loginType");


						new Loginpage(driver,test,capData)
						.login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);

						new DashboardPage(driver, test,capData)
						
						.navigateToMyConn(market);
						
						new RegressionPage(driver, test, market,userName, capData)
						
						.goToChkout()
						.checkout();
						
					}
					catch(Exception e)
					{
						java.io.Writer writer=new StringWriter();
						e.printStackTrace(new PrintWriter(writer));
						String rep=writer.toString();
						verifyStep(rep, "FAIL");
						Assert.assertTrue(false);
					}
			}
				@Test(priority=7,enabled=true)
				public void CreateOrderForMyself() {
					try{
						ReadExcel readExcel=new ReadExcel();
						testCaseName="Create Order for myself  in market "+market+" with browser "+browserName;
						startExtentReport(market, applicationType, browserName);
						readExcel.excelRead("productCatalog",capData);
						String userName=readExcel.getValue("username");
						String loginType=readExcel.getValue("loginType");


						new Loginpage(driver,test,capData)
						.login(readExcel.getValue("username"), "1234abcd", readExcel.getValue("loginType"), market);

						new DashboardPage(driver, test,capData)
						
						.navigateToOrderTracking(market);
						
						new RegressionPage(driver, test, market,userName, capData)
						.create_order_for_myself()
						.productEntry()
						.chk()
						.checkout();
						
					}
					catch(Exception e)
					{
						java.io.Writer writer=new StringWriter();
						e.printStackTrace(new PrintWriter(writer));
						String rep=writer.toString();
						verifyStep(rep, "FAIL");
						Assert.assertTrue(false);
					}
				}
				@Test(priority=8,enabled=false)
				public void OrderForCust() {
					try{
						ReadExcel readExcel=new ReadExcel();
						testCaseName="Create Order and checkout in "+market+" with browser "+browserName;
						startExtentReport(market, applicationType, browserName);
						readExcel.excelRead("productCatalog",capData);
						String userName=readExcel.getValue("username");
						String loginType=readExcel.getValue("loginType");


						new Loginpage(driver,test,capData)
						.login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);

						new DashboardPage(driver, test,capData)
						
						.navigateToOrderTracking(market);
						
						new RegressionPage(driver, test, market,userName, capData)
						.create_order_for_myself()
						.productEntry()
						.chk()
						.checkout();
					}
					catch(Exception e)
					{
						java.io.Writer writer=new StringWriter();
						e.printStackTrace(new PrintWriter(writer));
						String rep=writer.toString();
						verifyStep(rep, "FAIL");
						Assert.assertTrue(false);
					}
				}
				
				//============================Customer Order Management===================================			
				@Test(priority=9,enabled=false)
			       public void OrdManagement() {
					   try {
			              ReadExcel readExcel=new ReadExcel();
			              testCaseName="Cutomer order management "+market+" with browser "+browserName;
			              startExtentReport(market, applicationType, browserName);
			           
			                     readExcel.excelRead("productCatalog",capData);
			              
			              String userName=readExcel.getValue("username");
			              String loginType=readExcel.getValue("loginType");


			              new Loginpage(driver,test,capData)
			              .login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);

			              new DashboardPage(driver, test,capData)
			              
			              .navigateToOrderTracking(market);
			              new CustOrdManagementPage(driver, test, market,userName, capData)
			              .acceptCustOrd();
			              
					   } catch (Exception e) {
		                    
		                     e.printStackTrace();
		              }
			       }
				//============================Incentives===================================
				@Test(priority=10,enabled=false)
				public void incentives()
				{
				try
				{
				ReadExcel readExcel=new ReadExcel();
				testCaseName="Checking the Incentives Flow -"+market+"-"+browserName;
				startExtentReport(market, applicationType, browserName);
				readExcel.excelRead("IncentivesAndLoyalty",capData);
				String userName=readExcel.getValue("username");
				String loginType=readExcel.getValue("loginType");
				new Loginpage(driver,test,capData)
				.login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);
				new DashboardPage(driver, test,capData)
		
				.navigateToIncentivesAndLoyalty(market);
				new IncentivesLoyaltyFlow(driver, test, market,userName, capData)
				 
				.entireFlowIncentives(readExcel);
				}
				catch(Exception e)
				{
				java.io.Writer writer=new StringWriter();
				e.printStackTrace(new PrintWriter(writer));
				String rep=writer.toString();
				verifyStep(rep, "FAIL");
				Assert.assertTrue(false);
				}
			}
				@Test(priority=11,enabled=false)
				public void productSearch()
				{
				try
				{
				ReadExcel readExcel=new ReadExcel();
				testCaseName="Product search -"+market+"-"+browserName;
				startExtentReport(market, applicationType, browserName);
				readExcel.excelRead("productCatalog",capData);
				String userName=readExcel.getValue("username");
				String loginType=readExcel.getValue("loginType");
				new Loginpage(driver,test,capData)
				.login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);
				
				new  RegressionPage(driver, test, market,userName, capData)
				 
				.searchOrder();
				}
				catch(Exception e)
				{
				java.io.Writer writer=new StringWriter();
				e.printStackTrace(new PrintWriter(writer));
				String rep=writer.toString();
				verifyStep(rep, "FAIL");
				Assert.assertTrue(false);
				}
			}
				@Test(priority=12, enabled=false)
				 public void paoOnBehalf_checkOutPage_Submit_Validations()
				 {
					  try
					  {
						 
							testCaseName="paoOnBehalf_checkOutPage_Submit-"+market+"-"+browserName;
							startExtentReport(market, applicationType, browserName);
							 ReadExcel readExcel=new ReadExcel();
							readExcel.excelRead("PAOplaceAnOrderOnBehalf",capData);
							


							new Loginpage(driver,test,capData)
							.login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);

							new DashboardPage(driver, test,capData)
							.verifyDashboardPage()
							.navigateToOrderTracking(market);
							
							
							new PAOPlaceOrderOnBehalfPage(driver, test, market, capData)
							.paoOnBehalf_checkOutPage_Submit(readExcel);
					  }
					  catch(Exception e)
					   {
					   java.io.Writer writer=new StringWriter();
					   e.printStackTrace(new PrintWriter(writer));
					   String rep=writer.toString();
					   verifyStep(rep, "FAIL");
					   Assert.assertTrue(false);
					   }
				 }
				 
				
				@Test(priority=9,enabled=false)
			       public void E2E() {
					   try {
			              ReadExcel readExcel=new ReadExcel();
			              testCaseName="Cutomer order management "+market+" with browser "+browserName;
			              startExtentReport(market, applicationType, browserName);
			           
			                     readExcel.excelRead("productCatalog",capData);
			              
			              String userName=readExcel.getValue("username");
			              String loginType=readExcel.getValue("loginType");


			              new Loginpage(driver,test,capData)
			              .login(readExcel.getValue("username"), readExcel.getValue("password"), readExcel.getValue("loginType"), market);

			            new DashboardPage(driver, test,capData)
			              
			              .navigateToProfile(market);
							new RegressionPage(driver, test, market,userName, capData)
							.verifyProfile();
							
							new DashboardPage(driver, test,capData)
				              
				             .navigateToHelp(market);
							new RegressionPage(driver, test, market,userName, capData)
							. choosetopic();
							
							new DashboardPage(driver, test,capData)
				              
				             .navigateToMyConn(market);
							String connection=new MyConnPage(driver, test, market, userName,capData)
							.createNewConnection();
							
							
							new DashboardPage(driver, test,capData)
				              
				            .navigateToProductCatalogue(market);
							new RegressionPage(driver, test, market,userName, capData)
							. verifyProductCatalogOrdering(readExcel, capData)
							.addACustomerToOrder(connection);
							
							
							
							
					   } catch (Exception e) {
		                    
		                     e.printStackTrace();
		              }
			       }
				@Test(priority=1,enabled=false)
				public void selfAppointedUser_Login() throws Exception
				{
					try
					{
						
						testCaseName="To validate login for self appointed user";
						startExtentReport(market, applicationType, browserName);
						
						readExcel.excelRead("Profile",capData);
						String userName=readExcel.getValue("username");
						String loginType=readExcel.getValue("loginType");
						
						//DBConn db=new DBConn(market);
						String userName="11323716";//db.returnSelfAppointedAccountNumbers().get(0);
						new RegressionPage(driver, test, market, userName,capData)
						.login(userName, "123abc")
						
						.verifyDashboardPage();
					}
					catch(Exception e)
					{
						java.io.Writer writer=new StringWriter();
						e.printStackTrace(new PrintWriter(writer));
						String rep=writer.toString();
						verifyStep(rep, "FAIL");
						Assert.assertTrue(false);
					}
					
				}
					
					@Test(priority=1,enabled=false)
					public void nonSelfAppointedUser_Login() throws Exception
					{
						try
						{
							
							testCaseName="To validate login for non self appointed user";
							startExtentReport(market, applicationType, browserName);
							
						
							
							//DBConn db=new DBConn(market);
							String userName="81175875";//db.returnSelfAppointedAccountNumbers().get(0);
						
							new RegressionPage(driver, test, market, userName, capData)
							.setFirstTimeUser()
							
							.login(userName, "123abc")
							
							.firstTimeUserChangePassword("123abc")
							.nonSelfAppointmentConsent()
							.submitGdprForm()
							.acceptConfirmation()
							
					
							.acceptTerms()
							
					
							.verifyDashboardPage();
						}
						catch(Exception e)
						{
							java.io.Writer writer=new StringWriter();
							e.printStackTrace(new PrintWriter(writer));
							String rep=writer.toString();
							verifyStep(rep, "FAIL");
							Assert.assertTrue(false);
						}

				}
				
					
					@Test(priority=1,enabled=false)
					public void changePassword() throws Exception
					{
						try
						{
							ReadExcel readExcel=new ReadExcel();
							testCaseName="To change password for a user from profile";
							startExtentReport(market, applicationType, browserName);
							
							readExcel.excelRead("validatePasswordDetails",capData);
							
							
							//DBConn db=new DBConn(market);
							String userName="11323716";//db.returnSelfAppointedAccountNumbers().get(0);
					
							new RegressionPage(driver, test, market, userName, capData)
							.login(userName, "123abc")
							.validatePasswordDetails(readExcel);
							
						}
						catch(Exception e)
						{
							java.io.Writer writer=new StringWriter();
							e.printStackTrace(new PrintWriter(writer));
							String rep=writer.toString();
							verifyStep(rep, "FAIL");
							Assert.assertTrue(false);
						}

				}
				
					

					@Test(priority=1,enabled=false)
					public void productEntryDetails() throws Exception
					{
						try
						{
							ReadExcel readExcel=new ReadExcel();
							testCaseName="To check product entry";
							startExtentReport(market, applicationType, browserName);
							
							//readExcel.excelRead("validatePasswordDetails",capData);
							
							
							//DBConn db=new DBConn(market);
							String userName="11323716";//db.returnSelfAppointedAccountNumbers().get(0);
					
							new RegressionPage(driver, test, market, userName, capData)
							.login(userName, "123abc")
							.createNewOrderNavigation(readExcel)
							.enterSingleProductTabularViewWithQTY("1")
							.updateOrder_allValidProducts()
							.orderSplit_custView();
							
						}
						catch(Exception e)
						{
							java.io.Writer writer=new StringWriter();
							e.printStackTrace(new PrintWriter(writer));
							String rep=writer.toString();
							verifyStep(rep, "FAIL");
							Assert.assertTrue(false);
						}

				}
				
					@Test(priority=1,enabled=false)
					public void productEntryMergeOrder() throws Exception
					{
						try
						{
							ReadExcel readExcel=new ReadExcel();
							testCaseName="To check product entry with merged orders";
							startExtentReport(market, applicationType, browserName);
							
							//to add query for username
							
							
							//DBConn db=new DBConn(market);
							String userName="11323716";//db.returnSelfAppointedAccountNumbers().get(0);
					
							new RegressionPage(driver, test, market, userName, capData)
							.login(userName, "123abc")
							.createNewOrderNavigation(readExcel)
							.enterSingleProductTabularViewWithQTY("1")
							.updateOrder_allValidProducts()
							.MergeOrders();
						}
						catch(Exception e)
						{
							java.io.Writer writer=new StringWriter();
							e.printStackTrace(new PrintWriter(writer));
							String rep=writer.toString();
							verifyStep(rep, "FAIL");
							Assert.assertTrue(false);
						}

				}
					
					@Test(priority=1,enabled=false)
					public void productEntryEditOrder() throws Exception
					{
						try
						{
							ReadExcel readExcel=new ReadExcel();
							testCaseName="To check add-remove functionality on order";
							startExtentReport(market, applicationType, browserName);
							
							//readExcel.excelRead("validatePasswordDetails",capData);
							
							
							//DBConn db=new DBConn(market);
							String userName="11323716";//db.returnSelfAppointedAccountNumbers().get(0);
					
							new RegressionPage(driver, test, market, userName, capData)
							.login(userName, "123abc")
							.createNewOrderNavigation(readExcel)
							.enterSingleProductTabularViewWithQTY("1")
							.updateOrder_allValidProducts()
							.editOrderAddQty(2)
							.editOrderRemoveQty(2);
							
						}
						catch(Exception e)
						{
							java.io.Writer writer=new StringWriter();
							e.printStackTrace(new PrintWriter(writer));
							String rep=writer.toString();
							verifyStep(rep, "FAIL");
							Assert.assertTrue(false);
						}

				}
					
					
					@Test(priority=1,enabled=false)
					public void productEntrySalesView() throws Exception
					{
						try
						{
							ReadExcel readExcel=new ReadExcel();
							testCaseName="To check sales view of order";
							startExtentReport(market, applicationType, browserName);
							
							//readExcel.excelRead("validatePasswordDetails",capData);
							
							
							//DBConn db=new DBConn(market);
							String userName="11323716";//db.returnSelfAppointedAccountNumbers().get(0);
					
							new RegressionPage(driver, test, market, userName, capData)
							.login(userName, "123abc")
							.createNewOrderNavigation(readExcel)
							.enterSingleProductTabularViewWithQTY("1")
							.updateOrder_allValidProducts()
							.orderSplit_salesView();
							
						}
						catch(Exception e)
						{
							java.io.Writer writer=new StringWriter();
							e.printStackTrace(new PrintWriter(writer));
							String rep=writer.toString();
							verifyStep(rep, "FAIL");
							Assert.assertTrue(false);
						}

				}
					
					@Test(priority=1,enabled=false)
					public void verifySalesTools() throws Exception
					{
						try
						{
							ReadExcel readExcel=new ReadExcel();
							testCaseName="To verify product addition on  sales tools page";
							startExtentReport(market, applicationType, browserName);
							
							//readExcel.excelRead("validatePasswordDetails",capData);
							
							
							//DBConn db=new DBConn(market);
							String userName="11323716";//db.returnSelfAppointedAccountNumbers().get(0);
					
							new RegressionPage(driver, test, market, userName, capData)
							.login(userName, "123abc")
							.createNewOrderNavigation(readExcel)
							.enterSingleProductTabularViewWithQTY("1")
							.updateOrder_allValidProducts()
							.navigateToSalesTools()
							.addFromSalesTools(2);
							
						}
						catch(Exception e)
						{
							java.io.Writer writer=new StringWriter();
							e.printStackTrace(new PrintWriter(writer));
							String rep=writer.toString();
							verifyStep(rep, "FAIL");
							Assert.assertTrue(false);
						}

				}
					@Test(priority=1,enabled=false)
					public void verifySalesToolsIncentiveMeter() throws Exception
					{
						try
						{
							ReadExcel readExcel=new ReadExcel();
							testCaseName="To verify Incnetive meter on  sales tools page";
							startExtentReport(market, applicationType, browserName);
							
							//readExcel.excelRead("validatePasswordDetails",capData);
							
							
							//DBConn db=new DBConn(market);
							String userName="11323716";//db.returnSelfAppointedAccountNumbers().get(0);
					
							new RegressionPage(driver, test, market, userName, capData)
							.login(userName, "123abc")
							.createNewOrderNavigation(readExcel)
							.enterSingleProductTabularViewWithQTY("1")
							.updateOrder_allValidProducts()
							.navigateToSalesTools()
							.SalesToolsIncentivemeter();
							
						}
						catch(Exception e)
						{
							java.io.Writer writer=new StringWriter();
							e.printStackTrace(new PrintWriter(writer));
							String rep=writer.toString();
							verifyStep(rep, "FAIL");
							Assert.assertTrue(false);
						}

				}
					
					@Test(priority=1,enabled=false)
					public void verifyProfileDetails() throws Exception
					{
						try
						{
							
							testCaseName="To verify user's profile details";
							startExtentReport(market, applicationType, browserName);
							
							
							
							
							//DBConn db=new DBConn(market);
							String userName="11323716";//db.returnSelfAppointedAccountNumbers().get(0);
					
							new RegressionPage(driver, test, market, userName, capData)
							.login(userName, "123abc")
							.navigateToProfile(market)
							.accountOverviewTabValdn();
							
						}
						catch(Exception e)
						{
							java.io.Writer writer=new StringWriter();
							e.printStackTrace(new PrintWriter(writer));
							String rep=writer.toString();
							verifyStep(rep, "FAIL");
							Assert.assertTrue(false);
						}

				}*/
}