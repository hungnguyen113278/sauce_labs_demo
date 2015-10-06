package common;

import org.openqa.selenium.WebDriver;

public class DriverManager {

	private static ThreadLocal<WebDriver> _webDriver = new ThreadLocal<WebDriver>();

	public static WebDriver getDriver() {
		return _webDriver.get();
	}


	public static void setWebDriver(WebDriver driver) {
		_webDriver.set(driver);
	}

	public static void removeDriver() {
		_webDriver.remove();
	}
	
	
}
