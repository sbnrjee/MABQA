package wrappers;


public interface Wrappers {

		
		/**
		 * This method will launch the given browser and maximize the browser and set the
		 * wait for 30 seconds and load the url
		 */
		public void invokeApp(String browser);


		/**
		 * This method will verify the title of the browser 
		 * @param title - The expected title of the browser
		 */
		public boolean verifyTitle(String title);
		
		/**
		 * This method will verify the given text
		 * @param id - The locator of the object in id
		 * @param text  - The text to be verified
		 * @return 
		 * @throws Exception 
		 */
		public boolean verifyText(String property, String text) throws Exception;
		
		
		/**
		 * This method will verify the given text
		 * @param xpath - The locator of the object in xpath
		 * @param text  - The text to be verified
		 * @return 
		 * @throws Exception 
		 */
		public boolean verifyTextContains(String property, String text) throws Exception;



		/**
		 * This method will click the element using id as locator
		 * @param id  The id (locator) of the element to be clicked
		 * @throws Exception 
		 */
		public void click(String property) throws Exception;

		/**
		 * This method will get the text of the element using id as locator
		 * @param xpathVal  The id (locator) of the element 
		 */
		public String getText(String property);


		/**
		 * This method will select the drop down visible text using id as locator
		 * @param id The id (locator) of the drop down element
		 * @param value The value to be selected (visibletext) from the dropdown 
		 * @throws Exception 
		 */
		public void selectVisibileText(String property, String value) throws Exception;
		
		/**
		 * This method will switch to the parent Window
		 * @author Prince
		 */
		public void switchToParentWindow();
		
		/**
		 * This method will move the control to the last window
		 * @author Prince
		 */
		public void switchToLastWindow();
		
		/**
		 * This method will accept the alert opened
		 * @author Prince
		 */
		public void acceptAlert();
		
			/**
		 * This method will close all the browsers
		 * @author Prince
		 */
		
		public boolean isAlertPresent();
		
		public void quitBrowser();
		
		public void pageWaitMin();
		
		public void pageWaitMid();
		
		public void pageWaitMax();
		
		public String getAttributeValue(String xpath, String attribute) throws Exception;
		
		

}
