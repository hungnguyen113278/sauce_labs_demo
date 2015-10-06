package common;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Element;
import config.XmlHelper;

public class AutomationControl {
	/**
	 * Load control information from xml file
	 * @param controlName
	 * @author truong.pham
	 */
	public void loadControlInfo(String controlName) {
		try {
			XmlHelper xml = new XmlHelper();
			xml.parseResource(Constant.PathConfig.INTERFACES_XML);
			for (Element pageElement : xml.getElements("/pages/page")) {
				if (pageElement.getAttribute("name").equals(getPage())) {
					for (Element control : xml.getElements(pageElement, "control")) {
						if (control.getElementsByTagName("name").item(0).getTextContent().equals(controlName)) {
							String value = control.getElementsByTagName("value").item(0).getTextContent();
							String type = control.getElementsByTagName("type").item(0).getTextContent();
							setControlType(type);
							setControlValue(value);
							break;
						}
					}
				}
			}

		} catch (Exception e) {
			log.warn(e);
		}
	}

	/**
	 * make out By for find element control
	 * @param controlname: name of control 
	 */
	public WebElement findElement(WebDriver driver,String controlName) {
		WebElement element = null;
		element = driver.findElement(By.xpath(controlName));
		return element;
	}

	public WebElement findElement(WebDriver driver, String specialControl,String value) {
		WebElement element = null;
		String control = String.format(specialControl, value);
		element = driver.findElement(By.xpath(control));
		return element;
	}
	
	public WebElement findElement(WebDriver driver, String specialControl,String value1, String value2) {
		WebElement element = null;
		String control = String.format(specialControl, value1, value2);
		element = driver.findElement(By.xpath(control));
		return element;
	}
	
	/**
	 * get by for element
	 * @param driver
	 * @param controlName
	 */
	public By getBy(WebDriver driver,String controlName) {
		By by = null;
		by = By.xpath(controlName);
		return by;
	}
	
	public By getBy(WebDriver driver, String specialControl,String value) {
		By by = null;
		String control = String.format(specialControl, value);
		by = By.xpath(control);
		return by;
	}
	
	public By getBy(WebDriver driver, String specialControl,String value1, String value2) {
		By by = null;
		String control = String.format(specialControl, value1, value2);
		by = By.xpath(control);
		return by;
	}
	
	public List<WebElement> findElements(WebDriver driver,String controlName) {
		loadControlInfo(controlName);
		List<WebElement> lstElement = null;
		String controlType = getControlType();
		String controlValue = getControlValue();
		if (controlType.equals("xpath")) {
			lstElement = driver.findElements(By.xpath(controlValue));
		}
		if (controlType.equals("id")) {
			lstElement = driver.findElements(By.id(controlValue));
		}
		if (controlType.equals("name")) {
			lstElement = driver.findElements(By.name(controlValue));
		}
		return lstElement;
	}
	
	public List<WebElement> findElements(WebDriver driver,String controlName,String value) {
		loadControlInfo(controlName);
		List<WebElement> lstElement = null;
		String controlType = getControlType();
		String controlValue = getControlValue();
		String control = String.format(controlValue, value);
		if (controlType.equals("xpath")) {
			lstElement = driver.findElements(By.xpath(control));
		}
		if (controlType.equals("id")) {
			lstElement = driver.findElements(By.id(control));
		}
		if (controlType.equals("name")) {
			lstElement = driver.findElements(By.name(control));
		}
		return lstElement;
	}
	
	public List<WebElement> findElements(WebDriver driver,String controlName,String value1, String value2) {
		loadControlInfo(controlName);
		List<WebElement> lstElement = null;
		String controlType = getControlType();
		String controlValue = getControlValue();
		String control = String.format(controlValue, value1, value2);
		if (controlType.equals("xpath")) {
			lstElement = driver.findElements(By.xpath(control));
		}
		if (controlType.equals("id")) {
			lstElement = driver.findElements(By.id(control));
		}
		if (controlType.equals("name")) {
			lstElement = driver.findElements(By.name(control));
		}
		return lstElement;
	}
	
	public void setControlValue(String controlValue) {
		this.controlValue = controlValue;
	}

	public String getControlValue() {
		return controlValue;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getControlType() {
		return controlType;
	}
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
	/**
	 * Switch driver to iFrame
	 * @return driver
	 * @author thuy.duong
	 */
	public WebDriver switchToFrame(WebDriver driver,String controlName) {
		loadControlInfo(controlName);
		String type = getControlType();
		String value = getControlValue();
		if (type.equals("xpath")) {
			driver = driver.switchTo().frame(driver.findElement(By.xpath(value)));
		}
		if (type.equals("id")) {
			driver = driver.switchTo().frame(driver.findElement(By.id(value)));
		}
		if (type.equals("name")) {
			driver = driver.switchTo().frame(driver.findElement(By.name(value)));
		}
		return driver;
	}
	//private static AutomationControl control = null;
	private String page;
	private String controlValue;
	private String controlType;
	protected final Log log = LogFactory.getLog(AutomationControl.class);
}
