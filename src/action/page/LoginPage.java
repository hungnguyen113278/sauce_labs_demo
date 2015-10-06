package page;

import org.openqa.selenium.WebDriver;

import Interface.*;

public class LoginPage extends AbstractPage {

	public LoginPage(WebDriver driver) {
		control.setPage(this.getClass().getSimpleName());
		this.driver = driver;
	}
	
	public LoginPage(WebDriver driver, String ipClient) {
		control.setPage(this.getClass().getSimpleName());
		this.driver = driver;
		this.ipClient = ipClient;
	}
	
	private WebDriver driver;
	private String ipClient;
}
