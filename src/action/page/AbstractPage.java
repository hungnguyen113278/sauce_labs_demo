package page;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import common.AutomationControl;

public class AbstractPage {

	/**
	 * Refresh a page
	 */
	public void refresh(WebDriver driver) {
		driver.navigate().refresh();
		sleep();
	}

	public void back(WebDriver driver) {
		driver.navigate().back();
	}

	/**
	 * get title of page
	 * 
	 * @return
	 */
	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	/**
	 * Check control displayed
	 * 
	 * @param by
	 *            : By: id, name, xpath...
	 * @return
	 */
	public boolean isControlDisplayed(WebDriver driver, String controlName) {
		try {
			element = control.findElement(driver, controlName);
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * check control is displayed
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return
	 */
	public boolean isControlDisplayed(WebDriver driver, String controlName,
			String value) {
		try {
			element = control.findElement(driver, controlName, value);
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * check control is displayed
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return
	 */
	public boolean isControlDisplayed(WebDriver driver, String controlName,
			String value1, String value2) {
		try {
			element = control.findElement(driver, controlName, value1, value2);
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * check control is selected
	 * 
	 * @param driver
	 * @param controlName
	 * @return
	 */
	public boolean isControlSelected(WebDriver driver, String controlName) {
		try {
			element = control.findElement(driver, controlName);
			return element.isSelected();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * check control is selected
	 * 
	 * @param driver
	 * @param controlName
	 * @return
	 */
	public boolean isControlSelected(WebDriver driver, String controlName,
			String value) {
		try {
			element = control.findElement(driver, controlName, value);
			return element.isSelected();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Check control enabled
	 * 
	 * @param controlName
	 * @return
	 */
	public boolean isControlEnabled(WebDriver driver, String controlName) {
		try {
			element = control.findElement(driver, controlName);
			return element.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Wait for control is displayed
	 * 
	 * @param by
	 *            : By:id,name,xpath...
	 * @param timeout
	 */
	public void waitForControl(WebDriver driver, final String controlName,
			long timeout) {
		try {
			By by = control.getBy(driver, controlName);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			log.debug("Element doesn't exist");
		}
	}

	/**
	 * Wait for control is displayed
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @param timeout
	 */
	public void waitForControl(WebDriver driver, final String controlName,
			final String value, long timeout) {
		try {
			By by = control.getBy(driver, controlName, value);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			log.debug("Element doesn't exist");
		}
	}

	/**
	 * Wait for control is displayed
	 * 
	 * @param driver
	 * @param controlName
	 * @param value1
	 * @param value2
	 * @param timeout
	 */
	public void waitForControl(WebDriver driver, final String controlName,
			final String value1, final String value2, long timeout) {
		try {
			By by = control.getBy(driver, controlName, value1, value2);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			log.debug("Element doesn't exist");
		}
	}

	/**
	 * Wait for control is closed
	 * 
	 * @param by
	 * @param timeout
	 */
	public void waitForControlNotDisplayed(WebDriver driver,
			final String controlName, long timeout) {
		try {
			By by = control.getBy(driver, controlName);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception e) {
			log.debug("Control does not close");
		}
	}

	/**
	 * Wait for control is closed
	 * 
	 * @param by
	 * @param timeout
	 */
	public void waitForControlNotDisplayed(WebDriver driver,
			final String controlName, final String value, long timeout) {
		try {
			By by = control.getBy(driver, controlName, value);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (Exception e) {
			log.debug("Control does not close");
		}
	}

	/**
	 * Close dialog download because web driver does not capture or use hot key.
	 * 
	 * @param keyEvent
	 */
	public void keyPressing(String keyEvent) {
		try {
			Robot robot = new Robot();
			if (keyEvent == "esc") {
				robot.keyPress(KeyEvent.VK_ESCAPE);
			}
			if (keyEvent == "enter") {
				robot.keyPress(KeyEvent.VK_ENTER);
			}
			if (keyEvent == "tab") {
				robot.keyPress(KeyEvent.VK_TAB);
			}
			if (keyEvent == "window_q") {
				robot.keyPress(KeyEvent.VK_WINDOWS);
				robot.keyPress(KeyEvent.VK_Q);
				robot.keyRelease(KeyEvent.VK_WINDOWS);
				robot.keyRelease(KeyEvent.VK_Q);
			}
		} catch (AWTException e) {
			log.debug("Can not use Key");
		}

	}

	/**
	 * get attribute of element
	 * 
	 * @param controlName
	 * @param attribute
	 * @return
	 */
	public String getAttributeValue(WebDriver driver, String controlName,
			String attribute) {
		waitForControl(driver, controlName, timeWait);
		element = control.findElement(driver, controlName);
		return element.getAttribute(attribute);
	}

	/**
	 * get attribute of element
	 * 
	 * @param controlName
	 * @param attribute
	 * @return
	 */
	public String getAttributeValue(WebDriver driver, String controlName,
			String value, String attribute) {
		waitForControl(driver, controlName, value, timeWait);
		element = control.findElement(driver, controlName, value);
		return element.getAttribute(attribute);
	}

	public String getAttributeValue(WebDriver driver, String controlName,	String value1, String value2, String attribute) {
		waitForControl(driver, controlName, value1, value2, timeWait);
		element = control.findElement(driver, controlName, value1, value2);
		return element.getAttribute(attribute);
	}

	/**
	 * click on element
	 * 
	 * @param driver
	 * @param controlName
	 */
	public void click(WebDriver driver, String controlName) {
		waitForControl(driver, controlName, timeWait);
		element = control.findElement(driver, controlName);
		element.click();
	}

	/**
	 * click on element
	 * 
	 * @param driver
	 * @param controlName
	 */
	public void click(WebDriver driver, String controlName, String value) {
		waitForControl(driver, controlName, value, timeWait);
		element = control.findElement(driver, controlName, value);
		element.click();
	}

	/**
	 * clear text of control
	 * 
	 * @param driver
	 * @param controlName
	 */
	public void clear(WebDriver driver, String controlName) {
		waitForControl(driver, controlName, timeWait);
		element = control.findElement(driver, controlName);
		element.clear();
	}

	/**
	 * click on element
	 * 
	 * @param driver
	 * @param controlName
	 */
	public void click(WebDriver driver, String controlName, String value1,
			String value2) {
		waitForControl(driver, controlName, value1, value2, timeWait);
		element = control.findElement(driver, controlName, value1, value2);
		element.click();
	}

	/**
	 * enter value for element
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 */
	public void type(WebDriver driver, String controlName, String value) {
		waitForControl(driver, controlName, timeWait);
		element = control.findElement(driver, controlName);
		element.clear();
		element.sendKeys(value);
	}

	/**
	 * enter value for element
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 */
	public void type(WebDriver driver, String controlName, String value,
			String valueinput) {
		waitForControl(driver, controlName, value, timeWait);
		element = control.findElement(driver, controlName, value);
		sleep(1);
		element.clear();
		sleep(1);
		element.sendKeys(valueinput);
	}

	/**
	 * get element
	 * 
	 * @param driver
	 * @param controlName
	 */
	public WebElement getElement(WebDriver driver, String controlName) {
		element = control.findElement(driver, controlName);
		return element;
	}

	/**
	 * get a list element
	 * 
	 * @param driver
	 * @param controlName
	 */
	public List<WebElement> getElements(WebDriver driver, String controlName) {
		return control.findElements(driver, controlName);
	}

	/**
	 * get a list elements
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 */
	public List<WebElement> getElements(WebDriver driver, String controlName,
			String value) {
		return control.findElements(driver, controlName, value);
	}
	
	/**
	 * get a list elements
	 * 
	 * @param driver
	 * @param controlName
	 * @param value1
	 * @param value2
	 */
	public List<WebElement> getElements(WebDriver driver, String controlName,
			String value1, String value2) {
		return control.findElements(driver, controlName, value1, value2);
	}

	/**
	 * get element
	 * 
	 * @param driver
	 * @param controlName
	 */
	public WebElement getElement(WebDriver driver, String controlName,
			String value) {
		element = control.findElement(driver, controlName, value);
		return element;
	}

	/**
	 * get element
	 * 
	 * @param driver
	 * @param controlName
	 */
	public WebElement getElement(WebDriver driver, String controlName,
			String value1, String value2) {
		element = control.findElement(driver, controlName, value1, value2);
		return element;
	}

	/**
	 * press enter
	 * 
	 * @param driver
	 * @param controlName
	 */
	public void pressEnter(WebDriver driver, String controlName, String value) {
		WebElement element = control.findElement(driver, controlName, value);
		element.sendKeys(Keys.ENTER);
	}

	/**
	 * get text of element
	 * 
	 * @param driver
	 * @param controlName
	 */
	public String getText(WebDriver driver, String controlName) {
		try {
			waitForControl(driver, controlName, timeWait);
			WebElement element = control.findElement(driver, controlName);
			return element.getText();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get text of element
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return text
	 */
	public String getText(WebDriver driver, String controlName, String value) {
		try {
			waitForControl(driver, controlName, value, timeWait);
			WebElement element = control
					.findElement(driver, controlName, value);
			return element.getText();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * open link
	 * 
	 * @param driver
	 * @param url
	 */
	public void openLink(WebDriver driver, String url) {
		driver.get(url);
	}

	/**
	 * get control href
	 * 
	 * @param driver
	 * @param controlName
	 * @return href
	 */
	public String getControlHref(WebDriver driver, String controlName) {
		return getAttributeValue(driver, controlName, "href");
	}

	/**
	 * get control href
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return href
	 */
	public String getControlHref(WebDriver driver, String controlName,
			String value) {
		waitForControl(driver, controlName, value, timeWait);
		element = control.findElement(driver, controlName, value);
		return element.getAttribute("href");
	}

	/**
	 * count element
	 * 
	 * @param driver
	 * @param controlName
	 * @return number of element
	 */
	public int countElement(WebDriver driver, String controlName) {
		 waitForControl(driver, controlName, timeWait);
		return control.findElements(driver, controlName).size();
	}

	/**
	 * count element
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return number of element
	 */
	public int countElement(WebDriver driver, String controlName, String value) {
		waitForControl(driver, controlName, timeWait);
		return control.findElements(driver, controlName, value).size();
	}

	/**
	 * get CSS value of element
	 * 
	 * @param driver
	 * @param controlName
	 * @param css
	 * @return css value
	 */
	public String getCssValue(WebDriver driver, String controlName, String css) {
		element = control.findElement(driver, controlName);
		return element.getCssValue(css);
	}

	/**
	 * get CSS value of element
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @param css
	 * @return css value
	 */
	public String getCssValue(WebDriver driver, String controlName,
			String value, String css) {
		waitForControl(driver, controlName, value, timeWait);
		element = control.findElement(driver, controlName, value);
		return element.getCssValue(css);
	}

	
	protected AbstractPage() {
		log = LogFactory.getLog(getClass());
		log.debug("Created page abstraction for " + getClass().getName());
	}

	/**
	 * sleep
	 * 
	 * @param timeout
	 */
	public void sleep(long timeout) {
		try {
			Thread.sleep(timeout * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sleep
	 */
	public void sleep() {
		sleep(timeSleep);
	}

	/**
	 * move mouse to element
	 * 
	 * @param driver
	 * @param controlName
	 */
	public void moveMouseToElement(WebDriver driver, String controlName) {
		waitForControl(driver, controlName, timeWait);
		Actions action = new Actions(driver);
		action.moveToElement(getElement(driver, controlName), 156, 20);
		action.build().perform();
	}

	/**
	 * move mouse to element
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 */
	public void moveMouseToElement(WebDriver driver, String controlName,
			String value) {
		waitForControl(driver, controlName, value, timeWait);
		Actions action = new Actions(driver);
		action.moveToElement(getElement(driver, controlName, value));
		action.perform();
	}

	/**
	 * move mouse to element
	 * 
	 * @param driver
	 * @param controlName
	 * @param value1
	 * @param value2
	 */
	public void moveMouseToElement(WebDriver driver, String controlName,
			String value1, String value2) {
		waitForControl(driver, controlName, value1, value2, timeWait);
		Actions action = new Actions(driver);
		action.moveToElement(getElement(driver, controlName, value1, value2));
		action.perform();
	}

	/**
	 * close browser
	 * 
	 * @param driver
	 */
	public void closeBrowser(WebDriver driver) {
		driver.quit();
	}
	
	/**
	 * execute javascript
	 * 
	 * @param driver
	 * @param javaSript
	 */
	public Object executeJavaScript(WebDriver driver, String javaSript) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript(javaSript);
		} catch (Exception e) {
			log.debug(e.getMessage());
			return null;
		}
	}

	/**
	 * get current url of page
	 * 
	 * @param driver
	 * @return
	 */
	public String getCurrentUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	/**
	 * scroll page down
	 * 
	 * @param driver
	 * @author test
	 */
	public void scrollPage(WebDriver driver) {
		executeJavaScript(
				driver,
				"window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
	}

	/**
	 * scroll page down
	 * 
	 * @param driver
	 * @param x
	 * @param y
	 */
	public void scrollPage(WebDriver driver, int x, int y) {
		executeJavaScript(driver, "window.scrollTo(" + x + "," + y + ");");
		sleep();
	}

	/**
	 * select item in Combobox
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @param item
	 */
	public void selectItemComboxbox(WebDriver driver, String controlName,
			String value, String item) {
		element = control.findElement(driver, controlName, value);
		Select select = new Select(element);
		select.selectByVisibleText(item);
	}
	/**
	 * select item in Combobox
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @param item
	 */
	public void selectItemComboxbox(WebDriver driver, String controlName, String item) {
		element = control.findElement(driver, controlName);
		Select select = new Select(element);
		select.selectByVisibleText(item);
	}
	/**
	 * get item selected in combobox
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return item
	 */
	public String getItemSelectedCombobox(WebDriver driver, String controlName,
			String value) {
		element = control.findElement(driver, controlName, value);
		Select select = new Select(element);
		String itemSelected = select.getFirstSelectedOption().getText();
		return itemSelected;
	}
	/**
	 * get item selected in combobox
	 * 
	 * @param driver
	 * @param controlName
	 * @param value
	 * @return item
	 */
	public String getItemSelectedCombobox(WebDriver driver, String controlName) {
		element = control.findElement(driver, controlName);
		Select select = new Select(element);
		String itemSelected = select.getFirstSelectedOption().getText();
		return itemSelected;
	}
	
	protected int timeWait = 30;
	protected int timeSleep = 2;
	protected WebElement element;
	protected AutomationControl control = new AutomationControl();
	protected final Log log;
}
