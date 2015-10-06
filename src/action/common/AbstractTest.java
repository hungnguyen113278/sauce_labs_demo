package common;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;

import Interface.*;

import page.LoginPage;
import page.PageFactory;

import common.AutomationControl;
import common.Browser;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public abstract class AbstractTest {
	protected boolean verifyTrue(boolean condition, boolean halt) {
		boolean pass = true;
		if (halt == false) {
			try {
				Assert.assertTrue(condition);
			} catch (Throwable e) {
				pass = false;
				VerificationFailures.getFailures().addFailureForTest(
						Reporter.getCurrentTestResult(), e);
			}
		} else {
			Assert.assertTrue(condition);
		}
		return pass;
	}

	protected boolean verifyTrue(boolean condition) {
		return verifyTrue(condition, false);
	}

	protected boolean verifyFalse(boolean condition, boolean halt) {
		boolean pass = true;
		if (halt == false) {
			try {
				Assert.assertFalse(condition);
			} catch (Throwable e) {
				pass = false;
				VerificationFailures.getFailures().addFailureForTest(
						Reporter.getCurrentTestResult(), e);
			}
		} else {
			Assert.assertFalse(condition);
		}
		return pass;

	}

	protected boolean verifyFalse(boolean condition) {
		return verifyFalse(condition, false);
	}

	protected boolean verifyEquals(Object actual, Object expected, boolean halt) {
		boolean pass = true;
		if (halt == false) {
			try {
				Assert.assertEquals(actual, expected);
			} catch (Throwable e) {
				pass = false;
				VerificationFailures.getFailures().addFailureForTest(
						Reporter.getCurrentTestResult(), e);
			}
		} else {
			Assert.assertEquals(actual, expected);
		}
		return pass;
	}

	protected boolean verifyEquals(Object actual, Object expected) {
		return verifyEquals(actual, expected, false);
	}

	protected void refreshBrowser(WebDriver driver) {
		driver.navigate().refresh();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * open browser and navigate to url
	 * 
	 * @param browser
	 * @throws Exception
	 */
	protected WebDriver openBrowser(String browser, String port, String ipClient) {
		Browser br = new Browser();
			
		if (driver.toString().toLowerCase().contains("chrome")
				|| driver.toString().toLowerCase().contains("firefox")
				|| driver.toString().toLowerCase().contains("internetexplorer")) {
			driver.manage().window().maximize();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.focus();");			
		}
		driver.manage().timeouts();
		DriverManager.setWebDriver(driver);
		return driver;
	}
	
	/**
	 * open browser and navigate to url
	 * 
	 * @param browser
	 * @throws Exception
	 */
	protected WebDriver openBrowserSauceLaps(String browser, String version,String platform) {
		Browser br = new Browser();
		WebDriver driver = br.launchSauceLabs(browser, version, platform);	
		
		if (driver.toString().toLowerCase().contains("chrome")
				|| driver.toString().toLowerCase().contains("firefox")
				|| driver.toString().toLowerCase().contains("internetexplorer")) {
			driver.manage().window().maximize();
//			JavascriptExecutor js = (JavascriptExecutor) driver;
//			js.executeScript("window.focus();");			
		}
		driver.manage().timeouts();
		DriverManager.setWebDriver(driver);
		
		return driver;
	}
	
	/**
	 * Close browser and kill chromedriver.exe process
	 * 
	 * @param driver
	 */
	protected void closeBrowser(WebDriver driver) {
		try {
			driver.quit();
			System.gc();
			if (driver.toString().toLowerCase().contains("chrome")) {
				String cmd = "taskkill /IM chromedriver.exe /F";
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Return a random unique string
	 * 
	 * @return unique string
	 */
	protected String getUniqueName() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * Return a random unique number
	 * 
	 * @return unique number
	 */
	protected String getUniqueNumber() {
		Random rand = new Random();

		int number = rand.nextInt(9000) + 1;
		String numberString = Integer.toString(number);
		return numberString;
	}
	
	/**
	 * Get data from data.xml file
	 * 
	 * @param tagName
	 * @return
	 */
	protected String getDataSet(String tagName) {
		String result = "";
		String testModuleName = new Throwable().getStackTrace()[1]
				.getClassName().replace(".", "/").split("/")[1];
		result = Common.getCommon().getDataSet(testModuleName, tagName);
		return result;
	}
	
	/**
	 * Logout page at any current page luong.mai
	 */
	protected LoginPage logout(WebDriver driver, String ipClient) {
		try {
			String url = "";
			if (Common.getCommon().getLogoutLink().equals("")) {
				Thread.sleep(2000);
				control.setPage("AbstractPage");
				url = control.findElement(driver,
						Interface.AbstractPage.LOGOUT_LINK).getAttribute(
						"href");
				Common.getCommon().setLogoutLink(url);
			} else {
				url = Common.getCommon().getLogoutLink();
			}
			driver.get(url);
			Thread.sleep(4000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return PageFactory.getLoginPage(driver, ipClient);
	}

	/**
	 * clear cookie
	 */
	public void clearCookie(WebDriver driver) {
		driver.manage().deleteAllCookies();
	}

	/**
	 * Print comment on console to NGreport
	 */
	protected AbstractTest() {
		log = LogFactory.getLog(getClass());
	}	
	
	/**
	 * switch Other Window
	 * @param driver
	 * @return
	 */
	public WebDriver switchOtherWindow(WebDriver driver) {
		WebDriver tmp = driver;
		for(String winHandle : driver.getWindowHandles()){
			tmp =  driver.switchTo().window(winHandle);
		}
		return tmp;
	}

	/**
	 * get Current Driver
	 * @param driver
	 * @return
	 */
	public String getCurrentDriver(WebDriver driver)
	{
		return driver.getWindowHandle();
	}
	
	/**
	 * switch Current Driver
	 * @param driver
	 * @param currentHandle
	 * @return
	 */
	public WebDriver switchCurrentDriver(WebDriver driver, String currentHandle){
		try{
			driver.close();
			return driver.switchTo().window(currentHandle);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		return driver;
	}

	/**
	 * start Capture Video
	 * @param driver
	 * @param currentHandle
	 * @return
	 */
	public void startCaptureVideo() throws IOException, AWTException {
		GraphicsConfiguration gconfig = GraphicsEnvironment
		.getLocalGraphicsEnvironment()
		.getDefaultScreenDevice()
		.getDefaultConfiguration();
		
		screenRecorder = new ScreenRecorder(gconfig,
		new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey,
		MIME_AVI),
		new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
		ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
		CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
		DepthKey, (int)24, FrameRateKey, Rational.valueOf(15),
		QualityKey, 1.0f,
		KeyFrameIntervalKey, (int) (15 * 60)),
		new Format(MediaTypeKey, MediaType.VIDEO,
		EncodingKey,"black",
		FrameRateKey, Rational.valueOf(30)), null);
		screenRecorder.start();
	}
	
	/**
	 * stop Capture Video
	 * @param driver
	 * @param currentHandle
	 * @return
	 */
	public void stopCaptureVideo() throws IOException, AWTException {
		screenRecorder.stop();
	}
	
	private static ScreenRecorder screenRecorder;
	protected final Log log;
	protected WebDriver driver;
	protected String ipClient;
	protected AutomationControl control = new AutomationControl();
	public static final String USERNAME = "hungnguyen19";
	public static final String AUTOMATE_KEY = "Py6jyXj21Jmhybjo6RjL";
	public static final String URL = "http://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";
}
