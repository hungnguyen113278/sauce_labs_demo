package page;

import org.openqa.selenium.WebDriver;


public class HomePage extends AbstractPage{
	public HomePage(WebDriver driver) {
		control.setPage(this.getClass().getSimpleName());
		this.driver = driver;
	}
	
	public HomePage(WebDriver driver, String ipClient) {
		control.setPage(this.getClass().getSimpleName());
		this.driver = driver;
		this.ipClient = ipClient;
	}
	
	
	private WebDriver driver;
	private String ipClient;
}
