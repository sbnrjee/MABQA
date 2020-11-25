package pageobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import utils.ReadExcel;
import wrappers.AvonWrapper;

public class DashboardPage extends AvonWrapper{
	private static Properties prop;
	public Map<String,String> capData1 = new HashMap<String,String>();
	public DashboardPage(RemoteWebDriver driver, ExtentTest test,Map<String,String> capData1)
	{
		this.driver = driver;
		this.test = test;
		this.capData1=capData1;
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/dashboardpage.properties")));
			//prop.load(new FileInputStream(new File("./src/main/resources/angular/Avon MAB/poland/login.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 public DashboardPage navigateToHelp(String  market) {
		 
		 try {
				
				switch (market) {
				case "poland":
					
					driver.get("https://qafmab.pl.avon.com/pl-home/help-center.html"); //need to add the order builder link
					break;

				case "hungary":

					driver.get("https://qafmab.hu.avon.com/hu-home/help-center.html");

					break;
				case "czech":

					driver.get("https://qafmab.cz.avon.com/cz-home/help-center.html");

					break;
				case "bulgaria":

					driver.get("https://qafmab.bg.avon.com/bg-home/help-center.html");

					break;
				case "romania":

					driver.get("https://qafmab.ro.avon.com/ro-home/help-center.html");

					break;

				default:
					break;
				}

			}catch(Exception e) {
				e.printStackTrace();
			}
		 return this;
	 }
 
	
	public DashboardPage closeOverlays() {
     // TODO Auto-generated constructor stub
    
     if(verifyElementIsDisplayed(prop.getProperty("dialog1"))){
            click(prop.getProperty("dialog1"));
     }   
     if(verifyElementIsDisplayed(prop.getProperty("simModal"))){
         click(prop.getProperty("simModal"));
     }
     pageWaitMin();
         if(verifyElementIsDisplayed(prop.getProperty("contactInfoPopup"))){
        	 click(prop.getProperty("contactInfoPopup"));
         }
  
     return this;
     }
 public DashboardPage navigateToMyConn(String market) {
	 try {
			
			switch (market) {
			case "poland":
				
				driver.get("https://qafmab.pl.avon.com/pl-home/my-connection.html");
						 		break;

			case "hungary":

				driver.get("https://qafmab.hu.avon.com/hu-home/my-connection.html");

				break;
			case "czech":

				driver.get("https://qafmab.cz.avon.com/cz-home/my-connection.html");

				break;
			case "bulgaria":

				driver.get("https://qafmab.bg.avon.com/bg-home/my-connection.html");

				break;
			case "romania":

				driver.get("https://qafmab.ro.avon.com/ro-home/my-connection.html");

				break;

			default:
				break;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
	 return this;
	
}
 public DashboardPage navigateToProdEntry(String market) {
	 try {
			
			switch (market) {
			case "poland":
				
				driver.get("https://qafmab.pl.avon.com/pl-home/orders/product-entry");
						 		break;

			case "hungary":

				driver.get("https://qafmab.hu.avon.com/hu-home/orders/product-entry");

				break;
			case "czech":

				driver.get("https://qafmab.cz.avon.com/cz-home/orders/product-entry");

				break;
			case "bulgaria":

				driver.get("https://qafmab.bg.avon.com/bg-home/orders/product-entry");

				break;
			case "romania":

				driver.get("https://qafmab.ro.avon.com/ro-home/orders/product-entry");

				break;

			default:
				break;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
	 return this;
	
}
 public DashboardPage navigateToVoucherRegistrationPage(String market) {

     try {



     switch (market) {

     case "poland":


     driver.get("https://qafmab.pl.avon.com/pl-home/voucher-registration.html");

     break;


     case "hungary":


     driver.get("https://qafmab.hu.avon.com/hu-home/voucher-registration.html");


     break;

     case "czech":


     driver.get("https://qafmab.cz.avon.com/cz-home/voucher-registration.html");


     break;

     case "bulgaria":


     driver.get("https://qafmab.bg.avon.com/bg-home/voucher-registration.html");


     break;

     case "romania":


     driver.get("https://qafmab.ro.avon.com/ro-home/voucher-registration.html");


     break;


     default:

     break;

     }

     }catch(Exception e) {

     e.printStackTrace();

     }


     return this;

     }

	/*Epic Name: Comm Centre
	 *Description:To Navifate To Comm Centre Page
	 * */

	public DashboardPage navigateToCommunicationCenterPage(String market) {

		try {


			switch (market) {

			case "poland":

				System.out.println("   entered poland ");

				driver.get("https://qafmab.pl.avon.com/pl-home/communication-center.html");


				break;


			case "hungary":


				driver.get("https://qafmab.hu.avon.com/hu-home/communication-center.html");


				break;

			case "czech":


				driver.get("https://qafmab.cz.avon.com/cz-home/communication-center.html");


				break;

			case "bulgaria":


				driver.get("https://qafmab.bg.avon.com/bg-home/communication-center.html");


				break;

			case "romania":


				driver.get("https://qafmab.ro.avon.com/ro-home/communication-center.html");


				break;


			default:

				break;

			}


		}catch(Exception e) {

			e.printStackTrace();

		}


		return this;

	}
	public DashboardPage navigateToOSAPage(String market) {

		try {

			switch(market){
			case "poland":
			driver.get("https://qafmab.pl.avon.com/pl-home/osa-single-column.html");
			break;
			
			case "romania":
				driver.get("https://qafmab.ro.avon.com/ro-home/osa-single-column.html");
				break;
			case "hungary":
				driver.get("https://qafmab.hu.avon.com/hu-home/osa-single-column.html");
				break;
			case "bulgaria":
				driver.get("https://qafmab.bg.avon.com/bg-home/osa-single-column.html");
				break;
			case "czech":
				driver.get("https://qafmab.cz.avon.com/cz-home/osa-single-column.html");
				break;

			}

		}catch(Exception e) {

			e.printStackTrace();

		}


		return this;

	}
	public DashboardPage navigateToPreferencesPage(String market) {
		try {
		
			Thread.sleep(9000);
			switch (market) {
			case "poland":
				
				driver.get("https://qafmab.pl.avon.com/pl-home/my-account-overview/personal-preferences.html?shwPge=true");
				break;
				
			case "hungary":
				
				driver.get("https://qafmab.hu.avon.com/hu-home/my-account-overview/personal-preferences.html?shwPge=true");
				
				break;
			case "czech":
				
				driver.get("https://qafmab.cz.avon.com/cz-home/my-account-overview/personal-preferences.html?shwPge=true");
				
				break;
			case "bulgaria":
				
				driver.get("https://qafmab.bg.avon.com/bg-home/my-account-overview/personal-preferences.html?shwPge=true");
				
				break;
			case "romania":
				
				driver.get("https://qafmab.ro.avon.com/ro-home/my-account-overview/personal-preferences.html?shwPge=true");
				
				break;

			default:
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	
	public DashboardPage navigateToAddConnections(String market) {

		try {



		switch (market) {

		case "poland":


		driver.get("https://qafmab.pl.avon.com/pl-home/my-connection/add-new-connection");

		break;


		case "hungary":


		driver.get("https://qafmab.hu.avon.com/hu-home/my-connection/add-new-connection");


		break;

		case "czech":


		driver.get("https://qafmab.cz.avon.com/cz-home/my-connection/add-new-connection");


		break;

		case "bulgaria":


		driver.get("https://qafmab.bg.avon.com/bg-home/my-connection/add-new-connection");


		break;

		case "romania":


		driver.get("https://qafmab.ro.avon.com/ro-home/my-connection/add-new-connection");


		break;


		default:

		break;

		}

		}catch(Exception e) {

		e.printStackTrace();

		}


		return this;

		}
	
	
	/*Epic Name: Referral
	 *Description:To Navigate To Referral Page
	 * */


	public DashboardPage navigateToReferral(String market) {

		try {


			switch (market) {

			case "poland":


				driver.get("https://qafmab.pl.avon.com/pl-home/referral.html");

				break;


			case "hungary":


				driver.get("https://qafmab.hu.avon.com/hu-home/referral.html");


				break;

			case "czech":


				driver.get("https://qafmab.cz.avon.com/cz-home/referral.html");


				break;

			case "bulgaria":


				driver.get("https://qafmab.bg.avon.com/bg-home/referral.html");


				break;

			case "romania":


				driver.get("https://qafmab.ro.avon.com/ro-home/referral.html");


				break;


			default:

				break;

			}


		}catch(Exception e) {

			e.printStackTrace();

		}


		return this;

	} 


	/*Epic Name: ContactUS
	 *Description:To Navifate To Contact Us Page
	 * */


	public DashboardPage navigateToContactus(String market) {
		try {

			switch (market) {
			case "poland":

				driver.get("https://qafmab.pl.avon.com/pl-home/contactus-landing");
				break;

			case "hungary":

				driver.get("https://qafmab.hu.avon.com/hu-home/contactus-landing");

				break;
			case "czech":

				driver.get("https://qafmab.cz.avon.com/cz-home/contactus-landing");

				break;
			case "bulgaria":

				driver.get("https://qafmab.bg.avon.com/bg-home/contactus-landing");

				break;
			case "romania":

				driver.get("https://qafmab.ro.avon.com/ro-home/contactus-landing");

				break;

			default:
				break;
			}



			verifyElementIsPresent(prop.getProperty("contactcentre_text"));

		}catch(Exception e) {
			e.printStackTrace();
		}

		if(getAllLinks(prop.getProperty("closeLink")).size()>0)
		{
			click(prop.getProperty("closeLink"));
		}




		return this;
	}

	/*Epic Name: Common Function
	 *Description:To Verify The Hamburger Menu
	 * */


	public DashboardPage verifyDashboardPage() {


		verifyElementIsPresent(prop.getProperty("menu_hamburger"), 60);


		return this;

	}

	/*Epic Name: E-Brouchre
	 *Description:To navigate to E-Brouchre Page
	 * */



	public DashboardPage navigateToeBrochure(String market) {

		try {


			switch (market) {

			case "poland":


				driver.get("https://qafmab.pl.avon.com/pl-home/e-brochure-landing.html");

				break;


			case "hungary":


				driver.get("https://qafmab.hu.avon.com/hu-home/e-brochure-landing.html");


				break;

			case "czech":


				driver.get("https://qafmab.cz.avon.com/cz-home/e-brochure-landing.html");


				break;

			case "bulgaria":


				driver.get("https://qafmab.bg.avon.com/bg-home/e-brochure-landing.html");


				break;

			case "romania":


				driver.get("https://qafmab.ro.avon.com/ro-home/e-brochure-landing.html");


				break;


			default:

				break;

			}

		}catch(Exception e) {

			e.printStackTrace();

		}


		return this;

	}


	/*Epic Name: Order Tracking
	 *Description:To Navigate to Order Tracking Page
	 * */
	

	public DashboardPage navigateToOrderTracking(String market) {
		try {
			
			switch (market) {
			case "poland":

				driver.get("https://qafmab.pl.avon.com/pl-home/orders.html");
				break;

			case "hungary":

				driver.get("https://qafmab.hu.avon.com/hu-home/orders.html");

				break;
			case "czech":

				driver.get("https://qafmab.cz.avon.com/cz-home/orders.html");

				break;
			case "bulgaria":

				driver.get("https://qafmab.bg.avon.com/bg-home/orders.html");

				break;
			case "romania":

				driver.get("https://qafmab.ro.avon.com/ro-home/orders.html");

				break;

			default:
				break;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		return this;
	}
	
	/*Epic Name: PAO Order Builder
	 *Description:To Navigate To Order Builder Page
	 * */
	

	public DashboardPage navigateToOrderBuilder(String market) {
		try {
			
			switch (market) {
			case "poland":

				driver.get("https://qafmab.pl.avon.com/pl-home/order-tracking.html"); //need to add the order builder link
				break;

			case "hungary":

				driver.get("https://qafmab.hu.avon.com/hu-home/order-tracking.html");

				break;
			case "czech":

				driver.get("https://qafmab.cz.avon.com/cz-home/order-tracking.html");

				break;
			case "bulgaria":

				driver.get("https://qafmab.bg.avon.com/bg-home/order-tracking.html");

				break;
			case "romania":

				driver.get("https://qafmab.ro.avon.com/ro-home/order-tracking.html");

				break;

			default:
				break;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		return this;
	}


	/*Epic Name: PAO
	 *Description:To Navigate to Order tracking Page
	 * */
	

	
	public DashboardPage navigateToPAOValidation(String market) {
		try {
			
			switch (market) {
			case "poland":

				driver.get("https://qafmab.pl.avon.com/pl-home/order-tracking.html");
				break;

			case "hungary":

				driver.get("https://qafmab.hu.avon.com/hu-home/order-tracking.html");

				break;
			case "czech":

				driver.get("https://qafmab.cz.avon.com/cz-home/order-tracking.html");

				break;
			case "bulgaria":

				driver.get("https://qafmab.bg.avon.com/bg-home/order-tracking.html");

				break;
			case "romania":

				driver.get("https://qafmab.ro.avon.com/ro-home/order-tracking.html");

				break;

			default:
				break;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		return this;
	}
	
	
	public DashboardPage navigateToProductCatalogSearch(String market) {
		try {
			
			switch (market) {
			case "poland":

				driver.get("https://qafmab.pl.avon.com/pl-home/order-tracking.html");
				break;

			case "hungary":

				driver.get("https://qafmab.hu.avon.com/hu-home/order-tracking.html");

				break;
			case "czech":

				driver.get("https://qafmab.cz.avon.com/cz-home/order-tracking.html");

				break;
			case "bulgaria":

				driver.get("https://qafmab.bg.avon.com/bg-home/order-tracking.html");

				break;
			case "romania":

				driver.get("https://qafmab.ro.avon.com/ro-home/order-tracking.html");

				break;

			default:
				break;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		return this;
	}
	public DashboardPage navigateToReferrals(String market) {

		try {

		//click(prop.getProperty("menu_hamburger"));

		//click(prop.getProperty("accountBalanceLink"));

		//System.out.println(market);

		switch (market) {

		case "poland":


		driver.get("https://qafmab.pl.avon.com/pl-home/referral.html");

		break;


		case "hungary":


		driver.get("https://qafmab.hu.avon.com/hu-home/referral.html");


		break;

		case "czech":


		driver.get("https://qafmab.cz.avon.com/cz-home/referral.html");


		break;

		case "bulgaria":


		driver.get("https://qafmab.bg.avon.com/bg-home/referral.html");


		break;

		case "romania":


		driver.get("https://qafmab.ro.avon.com/ro-home/referral.html");


		break;


		default:

		break;

		}


		}catch(Exception e) {

		e.printStackTrace();

		}


		return this;

		} 


	
	/*Epic Name: Account Balance
	 *Description:To Navigate to Account Balance Page
	 * */
	

	public DashboardPage navigateToAccountBalance(String market) {
		try {
			
			switch (market) {
			case "poland":

				driver.get("https://qafmab.pl.avon.com/pl-home/account-balance.html");
				break;

			case "hungary":

				driver.get("https://qafmab.hu.avon.com/hu-home/account-balance.html");

				break;
			case "czech":

				driver.get("https://qafmab.cz.avon.com/cz-home/account-balance.html");

				break;
			case "bulgaria":

				driver.get("https://qafmab.bg.avon.com/bg-home/account-balance.html");

				break;
			case "romania":

				driver.get("https://qafmab.ro.avon.com/ro-home/account-balance.html");

				break;

			default:
				break;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		return this;
	}

	/*Epic Name: Profile
	 *Description:To Navigate To Profile Page
	 * */
	

	
	public void navigateToProfile(String market) {
		
		switch (market) {
		case "poland":

			driver.get("https://qafmab.pl.avon.com/pl-home/my-account-overview.html");
			break;
		case "hungary":

			driver.get("https://qafmab.hu.avon.com/hu-home/my-account-overview.html");

			break;
		case "czech":

			driver.get("https://qafmab.cz.avon.com/cz-home/my-account-overview.html");

			break;
		case "bulgaria":

			driver.get("https://qafmab.bg.avon.com/bg-home/my-account-overview.html");

			break;
		case "romania":

			driver.get("https://qafmab.ro.avon.com/ro-home/my-account-overview.html");

			break;

		default:
			break;
		}
	
	}

	


	/*Epic Name: My Connection
	 *Description:To Navigate to My Connection Page
	 * */
	


	public DashboardPage navigateToMyConnection(String market) {
		
		try {
			
			Thread.sleep(7000);
			switch (market) {
			case "poland":

				driver.get("https://qafmab.pl.avon.com/pl-home/my-connection.html");
				break;

			case "hungary":

				driver.get("https://qafmab.hu.avon.com/hu-home/my-connection.html");

				break;
			case "czech":

				driver.get("https://qafmab.cz.avon.com/cz-home/my-connection.html");

				break;
			case "bulgaria":

				driver.get("https://qafmab.bg.avon.com/bg-home/my-connection.html");

				break;
			case "romania":

				driver.get("https://qafmab.ro.avon.com/ro-home/my-connection.html");

				break;

			default:
				break;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	
	public void searchProduct(ReadExcel readExcel)
	{	
		
		if(verifyElementIsDisplayed(prop.getProperty("skipLink")))
			click(prop.getProperty("skipLink"));
		
		click(prop.getProperty("searchIcon"));
		enterText(prop.getProperty("searchInput"), readExcel.getValue("searchProduct"));
		click(prop.getProperty("firstSearchLink"));
	}

public DashboardPage navigateToDashBoard(String market) {
		
		try {
			
			Thread.sleep(7000);
			switch (market) {
			case "poland":

				driver.get("https://qafmab.pl.avon.com/pl-home/dashboard-landing");
				break;

			case "hungary":

				driver.get("https://qafmab.hu.avon.com/hu-home/dashboard-landing");

				break;
			case "czech":

				driver.get("https://qafmab.cz.avon.com/cz-home/dashboard-landing");

				break;
			case "bulgaria":

				driver.get("https://qafmab.bg.avon.com/bg-home/dashboard-landing");

				break;
			case "romania":

				driver.get("https://qafmab.ro.avon.com/ro-home/dashboard-landing");

				break;

			default:
				break;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
public void navigateToProductCatalogue(String market)
{

	try {
		Thread.sleep(7000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	switch (market) {
	case "poland":

		driver.get("https://qafmab.pl.avon.com/pl-home/product-catalog.html");
		break;

	case "hungary":

		driver.get("https://qafmab.hu.avon.com/hu-home/product-catalog.html");

		break;
	case "czech":

		driver.get("https://qafmab.cz.avon.com/cz-home/product-catalog.html");

		break;
	case "bulgaria":

		driver.get("https://qafmab.bg.avon.com/bg-home/product-catalog.html");

		break;
	case "romania":

		driver.get("https://qafmab.ro.avon.com/ro-home/product-catalog.html");

		break;

	default:
		break;
	}
}

public void logout()
{
	click(prop.getProperty("hamburgerIcon"));
	click(prop.getProperty("logout"));
}
public void navigateToIncentivesAndLoyalty(String market) {
	// TODO Auto-generated method stub
	pageWaitMax();
	switch (market) {
	case "poland":

		driver.get("https://qafmab.pl.avon.com/pl-home/incentive-landing.html");
		break;

	case "hungary":

		driver.get("https://qafmab.hu.avon.com/hu-home/incentive-landing.html");

		break;
	case "czech":

		driver.get("https://qafmab.cz.avon.com/cz-home/incentive-landing.html");

		break;
	case "bulgaria":

		driver.get("https://qafmab.bg.avon.com/bg-home/incentive-landing.html");

		break;
	case "romania":

		driver.get("https://qafmab.ro.avon.com/ro-home/incentive-landing.html");

		break;

	default:
		break;
	}
	
}
	
}
