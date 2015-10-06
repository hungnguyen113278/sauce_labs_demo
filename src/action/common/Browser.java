package common;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.uncommons.reportng.ReportMetadata;
import config.ProviderConfiguration;
import common.Constant;

public class Browser {

	public WebDriver getDriver() {
		return driver;
	}
	public static final String USERNAME = "hungnguyen113278";
	public static final String AUTOMATE_KEY = "306d7aab-eaad-4973-8bd3-27c7090caf7e";
	public static final String URL_BS = "http://" + USERNAME + ":" + AUTOMATE_KEY + "@ondemand.saucelabs.com:80/wd/hub";
	
	/*
	 * Using with Grid Open browser and navigate to url
	 */
	public WebDriver launch(String browser, String port, String ipClient)  {
		try {
			provider.loadInstance("TrainingTest");
			
			final String url = ProviderConfiguration.getProvider().getInstance().getUrl();
			final String grid = ProviderConfiguration.getProvider().getInstance().getGrid();
			ReportMetadata.link = "AUT url: "+url;
			Constant.PathConfig.GRID = grid;
			Constant.PathConfig.HOME_URL = url;
			
			if (grid.toLowerCase().equals("no")) {
//				final String driverClass = ProviderConfiguration.getProvider()
//						.getSelenium().getDriver();
	
				if (browser.equals("firefox")) {
					driver = new FirefoxDriver();
				} else if (browser.equals("internetexplorer")) {
					startIEDriver();
					driver = new InternetExplorerDriver();
				} else if(browser.equals("chrome")){
					startChromeDriver();
					driver = new ChromeDriver();
				} else if(browser.equals("Ipad")) {
					DesiredCapabilities capability = DesiredCapabilities.ipad();
					capability.setJavascriptEnabled(true);
					driver = new RemoteWebDriver(new URL("http://localhost:3001/wd/hub"), capability);
				} else {
					DesiredCapabilities capability = DesiredCapabilities.safari();
					capability.setJavascriptEnabled(true);
					driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
				}						
			} else {
				DesiredCapabilities capability = null;
				if (browser.equalsIgnoreCase("firefox")) {
					capability = DesiredCapabilities.firefox();
					capability.setBrowserName("firefox");
					capability.setPlatform(org.openqa.selenium.Platform.ANY);
					capability.setJavascriptEnabled(true);
				} else if (browser.equalsIgnoreCase("chrome")) {
					startChromeDriver();
					capability = DesiredCapabilities.chrome();
					capability.setBrowserName("chrome");
					capability.setPlatform(org.openqa.selenium.Platform.ANY);
					capability.setJavascriptEnabled(true);
				} else if (browser.equalsIgnoreCase("internetexplorer")) {
					startIEDriver();
					capability = DesiredCapabilities.internetExplorer();
					capability.setBrowserName("internetexplorer");
					capability.setPlatform(org.openqa.selenium.Platform.ANY);
					capability.setJavascriptEnabled(true);
				} else if (browser.equalsIgnoreCase("safari")) {					
					capability = DesiredCapabilities.safari();
					capability.setBrowserName("safari");
					capability.setJavascriptEnabled(true);
					capability.setPlatform(org.openqa.selenium.Platform.MAC);
				}
				try {
					driver = new RemoteWebDriver(
							new URL(String.format("http://%s:%s/wd/hub",ipClient,port)), capability);
				} catch (MalformedURLException e) {
					System.out.print(e.getMessage());
				}
			}
			driver.get(url);
			if(System.getProperty("os.name").toLowerCase().contains("mac")) {
				driver.manage().window().setPosition(new Point(0,0));
				java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
				driver.manage().window().setSize(dim);
				driver.get(url);
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return driver;
	}
	
	/*
	 * Using with BrowserStact Open browser and navigate to url
	 */
	public WebDriver launchSauceLabs(String browser,String version, String platform)  {
		try {
			provider.loadInstance("TrainingTest");
			
			final String url = ProviderConfiguration.getProvider().getInstance().getUrl();
			final String grid = ProviderConfiguration.getProvider().getInstance().getGrid();
			ReportMetadata.link = "AUT url: "+url;
			Constant.PathConfig.GRID = grid;
			Constant.PathConfig.HOME_URL = url;
			
			DesiredCapabilities caps = new DesiredCapabilities();
			
			caps.setCapability(CapabilityType.BROWSER_NAME, browser);
		    caps.setCapability(CapabilityType.VERSION, version);
		    caps.setCapability(CapabilityType.PLATFORM,platform);
//			
//			if (grid.toLowerCase().equals("no")) {
////				final String driverClass = ProviderConfiguration.getProvider()
////						.getSelenium().getDriver();
//	
//				if (browser.equals("firefox")) {
//				    caps.setCapability(CapabilityType.BROWSER_NAME, browser);
//				    caps.setCapability(CapabilityType.VERSION, version);
//				    caps.setCapability(CapabilityType.PLATFORM,platform);
//				    
//				} else if (browser.equals("internetexplorer")) {
//					caps.setCapability("browser", browser);
//				    caps.setCapability("browser_version", "11");
//				    caps.setCapability("browserstack.debug", "true");
//				} else if(browser.equals("chrome")){
//					caps.setCapability(CapabilityType.BROWSER_NAME, browser);
//				    caps.setCapability(CapabilityType.VERSION, version);
//				    caps.setCapability(CapabilityType.PLATFORM,platform);
//				}			
//			} else {
//				caps = null;
//				if (browser.equalsIgnoreCase("firefox")) {
//					caps = DesiredCapabilities.firefox();
//					caps.setBrowserName("firefox");
//					caps.setPlatform(org.openqa.selenium.Platform.ANY);
//					caps.setJavascriptEnabled(true);
//				} else if (browser.equalsIgnoreCase("chrome")) {
//					startChromeDriver();
//					caps = DesiredCapabilities.chrome();
//					caps.setBrowserName("chrome");
//					caps.setPlatform(org.openqa.selenium.Platform.ANY);
//					caps.setJavascriptEnabled(true);
//				} else if (browser.equalsIgnoreCase("internetexplorer")) {
//					startIEDriver();
//					caps = DesiredCapabilities.internetExplorer();
//					caps.setBrowserName("internetexplorer");
//					caps.setPlatform(org.openqa.selenium.Platform.ANY);
//					caps.setJavascriptEnabled(true);
//				} else if (browser.equalsIgnoreCase("safari")) {					
//					caps = DesiredCapabilities.safari();
//					caps.setBrowserName("safari");
//					caps.setJavascriptEnabled(true);
//					caps.setPlatform(org.openqa.selenium.Platform.MAC);
//				}
//			}
			try {
				driver = new RemoteWebDriver(new URL(URL_BS), caps);
			} catch (MalformedURLException e) {
				System.out.print(e.getMessage());
			}
			
			driver.get(url);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return driver;
	}
	
	public void takeScreenshot(String name) {
		TakesScreenshot view = TakesScreenshot.class.cast(driver);
		File screenshot = view.getScreenshotAs(OutputType.FILE);
		File destination = new File(name + ".png");
		try {
			FileUtils.copyFile(screenshot, destination);
			log.info("Screenshot saved to " + destination.getAbsolutePath());
		} catch (IOException e) {
			log.error(
					"Failed to write screenshot to "
							+ destination.getAbsolutePath(), e);
		}
	}

	public void goHome() {
		open(homeUrl);

	}

	public void open(String url) {
		driver.get(url);
	}

	public void rememberLocation() {
		rememberedUrl = driver.getCurrentUrl();
	}

	public void recallLocation() {
		if (rememberedUrl != null) {
			driver.get(rememberedUrl);
		}
	}

	/**
	 * Close browser
	 */
	public void shutdown() {
		driver.quit();
		// instance=null;
	}

	/**
	 * Start chrome driver
	 */
	public void startChromeDriver() {
		System.setProperty("webdriver.chrome.driver",
				"..\\TrainingTest\\src\\resource\\chromedriver.exe");		
	}
	
	public void startIEDriver() {
		System.setProperty("webdriver.ie.driver",
				"..\\TrainingTest\\src\\resource\\IEDriverServer.exe");
	}

	public void closeNewWindow() {
		if (openWindowHandles.size() < 1) {
			log.error("Called close new window when only the main browser was open.");
		} else {
			driver.close();
			driver.switchTo().window(openWindowHandles.pop());
		}
	}
	
	
	private WebDriver driver = null;
	private String homeUrl = null;
	private String rememberedUrl = null;
	private final Stack<String> openWindowHandles = new Stack<String>();
	private static final Log log = LogFactory.getLog(Browser.class);
	private final ProviderConfiguration provider = ProviderConfiguration.getProvider();
}	
