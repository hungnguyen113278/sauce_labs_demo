package page;

import org.openqa.selenium.WebDriver;


public class PageFactory {
	
	/**
	 * Get login page
	 * @param driver
	 * @return Login page
	 */
	public static LoginPage getLoginPage(WebDriver driver)
	{
		return new LoginPage(driver);
	}
	
	/**
	 * Get login page
	 * @param driver, ipclient
	 * @return Login page
	 */
	public static LoginPage getLoginPage(WebDriver driver, String ipClient)
	{
		return new LoginPage(driver, ipClient);
	}
	
	/**
	 * Get home page
	 * @param driver
	 * @return Home page
	 */
	public static HomePage getHomePage(WebDriver driver)
	{
		return new HomePage(driver);
	}

	/**
	 * Get home page
	 * @param driver, ipclient
	 * @return Home page
	 */
	public static HomePage getHomePage(WebDriver driver, String ipClient)
	{
		return new HomePage(driver, ipClient);
	}
	
	/**
	 * Get search page
	 * @param driver
	 * @return Search page
	 */
	public static SearchPage getSearchPage(WebDriver driver)
	{
		return new SearchPage(driver);
	}

	/**
	 * Get search page
	 * @param driver, ipclient
	 * @return Search page
	 */
	public static SearchPage getSearchPage(WebDriver driver, String ipClient)
	{
		return new SearchPage(driver, ipClient);
	}
}
