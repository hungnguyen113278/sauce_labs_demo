package Search;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.AbstractTest;
import common.DriverManager;
import page.PageFactory;
import page.SearchPage;
import common.Constant;

public class Search_001_SearchOnGoogle extends AbstractTest {
	
	@Parameters({ "browser", "version", "platform"})
	@BeforeClass(alwaysRun = true)
	public void setup(String browser, String version, String platform) {
		openBrowserSauceLaps(browser,version, platform);
//		searchPage = PageFactory.getSearchPage(DriverManager.getDriver(), ipClient);
		
		invalidKeyword = getDataSet("invalidKeyword");
		validKeyword = getDataSet("validKeyword");
	}

	@Test(groups = { "regression" }, description = "Search an invalid text on Google")
	public void GoogleSearch_01_0_GoogleSearchInvalidText() {
		
		searchPage = PageFactory.getSearchPage(DriverManager.getDriver(), ipClient);
		
	}
//		log.info("Step GoogleSearch_01 - 1: Search invalid text on Google");
//		searchPage.searchGoogleWithKeyword(invalidKeyword);
//		
//		log.info("VP GoogleSearch_01: Cannot find any result for the key word");
//		verifyTrue(searchPage.isNotResultMessageContainKeyWord(Constant.CommonData.USERNAME));
//	}
	
//	@Test(groups = { "regression" }, description = "Search an valid text on Google")
	public void GoogleSearch_02_0_GoogleSearchValidText() {
		log.info("Step GoogleSearch_02 - 1: Search valid text on Google");
		searchPage.searchGoogleWithKeyword(validKeyword);
		
		log.info("VP GoogleSearch_02: Results display for the key word");
		verifyTrue(searchPage.isFirstResultDisplays());
	}
	
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		closeBrowser(DriverManager.getDriver());
	}
	
	private SearchPage searchPage;
	private String invalidKeyword, validKeyword;
}
