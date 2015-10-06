package page;

import org.openqa.selenium.WebDriver;

import Interface.*;

public class SearchPage extends AbstractPage{
	public SearchPage(WebDriver driver) {
		control.setPage(this.getClass().getSimpleName());
		this.driver = driver;
	}
	
	public SearchPage(WebDriver driver, String ipClient) {
		control.setPage(this.getClass().getSimpleName());
		this.driver = driver;
		this.ipClient = ipClient;
	}
	
	/**
	 * search Google With key word
	 * @param keyword
	 */
	public void searchGoogleWithKeyword(String keyword) {
		waitForControl(driver, Interface.SearchPage.SEARCH_TEXTBOX, timeWait);
		click(driver, Interface.SearchPage.SEARCH_TEXTBOX);
		type(driver, Interface.SearchPage.SEARCH_TEXTBOX, keyword);
		click(driver, Interface.SearchPage.SEARCH_BUTTON);
	}
	
	/**
	 * check Not Result Message Contain KeyWord
	 * @param keyword
	 */
	public boolean isNotResultMessageContainKeyWord(String keyword) {
		waitForControl(driver, Interface.SearchPage.DYNAMIC_NO_RESULT_MESSAGE, keyword, timeWait);
		return isControlDisplayed(driver, Interface.SearchPage.DYNAMIC_NO_RESULT_MESSAGE, keyword);
	}
	
	/**
	 * check First Result Displays
	 * @param N/A
	 */
	public boolean isFirstResultDisplays() {
		waitForControl(driver, Interface.SearchPage.FIRST_SEARCH_RESULT, timeWait);
		return isControlDisplayed(driver, Interface.SearchPage.FIRST_SEARCH_RESULT);
	}
	
	private WebDriver driver;
	private String ipClient;
}
