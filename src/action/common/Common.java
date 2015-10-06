package common;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Element;

import autoitx4java.AutoItX;

import com.jacob.com.LibraryLoader;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

import config.EmailHandler;
import config.ProviderConfiguration;
import config.XmlHelper;

public class Common {

	
	public static synchronized Common getCommon()
	{
		if ( instance == null ) {
			instance = new Common();
		}
		
		return instance;
	}
		
	/**
	 * get data from data.xml file
	 * @param testModuleName
	 * @param tagName
	 * @return result: data set value
	 */
	public String getDataSet(String testModuleName, String tagName)
	{	
		String result = "";
			try {
	    	XmlHelper xml = new XmlHelper();
	    	xml.parseResource(Constant.PathConfig.DATA_TEST_XML);
	    	for (Element element : xml.getElements("/DataTest/TestModules/TestModule")){
	    		if(element.getAttribute("name").equals(testModuleName)){
	    			result = element.getElementsByTagName(tagName).item(0).getTextContent();
	    			break;
	    		}
	    	}
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}
    	return result;
	}

	/**
	 * Get day of week
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 * @throws ParseException
	 */
	public String getDayOfWeek(int day, int month, int year) {
		try {
			String strDate = day + "/" + month + "/" + year;
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date dt=format.parse(strDate);
			DateFormat dateFormat = new SimpleDateFormat("EEEE");
			return dateFormat.format(dt);
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Convert rgba to hex
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public String toBrowserHexValue(int number) {
		StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
		while (builder.length() < 2) {
			builder.append("0");
		}
		return builder.toString().toUpperCase();
	}

	/**
	 * Convert rgba to hex
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public String toHex(int r, int g, int b) {
		return "#" + toBrowserHexValue(r) + toBrowserHexValue(g)
				+ toBrowserHexValue(b);
	}

	/**
	 * Comverton month
	 * 
	 * @param month
	 * @return
	 */
	public String convertMonth(int month) {
		String m = null;
		switch (month) {
		case 1:
			m = "January";
			break;
		case 2:
			m = "February";
			break;
		case 3:
			m = "March";
			break;
		case 4:
			m = "April";
			break;
		case 5:
			m = "May";
			break;
		case 6:
			m = "June";
			break;
		case 7:
			m = "July";
			break;
		case 8:
			m = "August";
			break;
		case 9:
			m = "September";
			break;
		case 10:
			m = "October";
			break;
		case 11:
			m = "November";
			break;
		case 12:
			m = "December";
			break;
		default:
			break;

		}
		return m;
	}
	
	/**
	 * convert to month
	 * @param monthName
	 * @return month
	 */
	public int convertMonth(String monthName) {
		int m = 0;
		if(monthName.toUpperCase().contains("JAN")) m = 1;
		else if(monthName.toUpperCase().contains("FEB")) m = 2;
		else if(monthName.toUpperCase().contains("MAR")) m = 3;
		else if(monthName.toUpperCase().contains("APR")) m = 4;
		else if(monthName.toUpperCase().contains("MAY")) m = 5;
		else if(monthName.toUpperCase().contains("JUN")) m = 6;
		else if(monthName.toUpperCase().contains("JUL")) m = 7;
		else if(monthName.toUpperCase().contains("AUG")) m = 8;
		else if(monthName.toUpperCase().contains("SEP")) m = 9;
		else if(monthName.toUpperCase().contains("OCT")) m = 10;
		else if(monthName.toUpperCase().contains("NOV")) m = 11;
		else if(monthName.toUpperCase().contains("DEC")) m = 12;
		return m;
	}
	
	/**
	 * get current browser
	 * @return driver
	 */
	public String getCurrentBrowser()
	{
		ProviderConfiguration provider = ProviderConfiguration.getProvider();
		provider.loadInstance("Bloomboard");
		String driverClass = ProviderConfiguration.getProvider()
				.getSelenium().getDriver();
		return driverClass;
	}

	/**
	 * get current day with plus day
	 * @param days
	 * @return day
	 */
	public int getCurrentDayWithPlusDays(int days) {
		DateTime dt = DateTime.now();
		return dt.plusDays(days).getDayOfMonth();
	}
	
	/**
	 * get month when current day plus days
	 * @param days
	 * @return
	 */
	public int getCurrentMonthWithPlusDays(int days) {
		DateTime dt = DateTime.now();
		return dt.plusDays(days).getMonthOfYear();
	}
	/**
	 * get year when current day plus days
	 * @param days
	 * @return
	 */
	public int getCurrentYearWithPlusDays(int days) {
		DateTime dt = DateTime.now();
		return dt.plusDays(days).getYear();
	}
	/**
	 * get current day
	 * @return
	 */
	public int getCurrentDay()
	{
		DateTime now = DateTime.now();
		return now.getDayOfMonth();
	}
	
	/**
	 * get current month
	 * @return
	 */
	public int getCurrentMonth() {
		DateTime now = DateTime.now();
		return now.getMonthOfYear();
	}
	
	/**
	 * get current year
	 * @return
	 */
	public int getCurrentYear() {
		DateTime now = DateTime.now();
		return now.getYear();
	}
	
	/**
	 * get current hour
	 * @return
	 * @author 
	 */
	public int getCurrentHours() {
		DateTime now = DateTime.now();
		return now.getHourOfDay();
	}
	
	/**
	 * get current minute
	 * @return
	 * @author 
	 */
	public int getCurrentMinutes() {
		DateTime now = DateTime.now();
		return now.getMinuteOfHour();
	}
	

	/**
	 * get logout link
	 * @return
	 * @author 
	 */
	public String getLogoutLink() {
		return logOutLink;
	}
	
	/**
	 * set logout link
	 * @param url
	 */
	public void setLogoutLink(String url) {
		this.logOutLink = url;
	}
	
	/**
	 * get en on mac os
	 * @return
	 */
	public String getEnOnMac() {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("ifconfig -u -l");
			InputStream stdout = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
			String line;
			while ((line = reader.readLine()) != null) {
				int index = line.indexOf("en");
				if (index > 0) {
					en = line.substring(index, 7);
					break;
				}
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return en;
	}
	
	/**
	 * disconnect network on mac
	 * @author 
	 */
	public void disconnectNetWorkOnMac(){
		String output = "";
		if(en == "") {
			en = getEnOnMac();
		}
		try {
			String cmd = "sudo ifconfig " + en + " down";
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmd);
			process.waitFor();
			InputStream stdout = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
			String line;
			while ((line = reader.readLine()) != null) {
				output =  output + line;
			}
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/**
	 * re-connect network on mac
	 * @author 
	 */
	public void reconnectNetWorkOnMac(){
		String output = "";
		try {
			String cmd = "sudo ifconfig " + en + " up";
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmd);
			InputStream stdout = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
			String line;
			while ((line = reader.readLine()) != null) {
				output =  output + line;
			}
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/**
	 * close a new window when click on link with target is _blank
	 * @param driver
	 */
	public void closeNewWindow(WebDriver driver) {
		String currentHandle = driver.getWindowHandle();
		openWindowHandles.push(driver.getWindowHandle());
		Set<String> newHandles = driver.getWindowHandles();
		newHandles.removeAll(openWindowHandles);
		String handle = newHandles.iterator().next();
		driver.switchTo().window(handle);
		driver.close();
		driver.switchTo().window(currentHandle);
	}
	
	/**
	 * Define User32
	 * @author 
	 *
	 */
	public interface User32 extends StdCallLibrary {	   
		   User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
		   HWND FindWindow(String lpClassName, String lpWindowName);
		   HWND SetForegroundWindow(HWND hWnd);
		   HWND ShowWindow(HWND hWnd, int nCmdShow);
		   HWND SetFocus(HWND hwnd);
	}
	
	/**
	 * Use User32 to focus window
	 * @param className
	 * @param windowName
	 * @author 
	 */
	public void focusWindow(String className, String windowName) {
	    HWND hWnd = User32.INSTANCE.FindWindow(className, windowName);
	    if(hWnd!=null) {
		    User32.INSTANCE.ShowWindow(hWnd, 10);
		    User32.INSTANCE.SetForegroundWindow(hWnd);
		    User32.INSTANCE.SetFocus(hWnd);
	    }
	    else {
	    	System.out.println("Can not find window with class: "+className+" window name: " + windowName);
	    }
	}


	
	/**
	 * Comverton month
	 * 
	 * @param month
	 * @return short name month
	 * @author 
	 */
	public String convertShortMonth(int month) {
		String m = null;
		switch (month) {
		case 1:
			m = "Jan";
			break;
		case 2:
			m = "Feb";
			break;
		case 3:
			m = "Mar";
			break;
		case 4:
			m = "Apr";
			break;
		case 5:
			m = "May";
			break;
		case 6:
			m = "Jun";
			break;
		case 7:
			m = "Jul";
			break;
		case 8:
			m = "Aug";
			break;
		case 9:
			m = "Sep";
			break;
		case 10:
			m = "Oct";
			break;
		case 11:
			m = "Nov";
			break;
		case 12:
			m = "Dec";
			break;
		default:
			break;

		}
		return m;
	}
	
	/**
	 * get path contain download file
	 * @return path
	 * @author 
	 */
	public String getPathContainDownload()
	{
		String path = "";
		String currentUser = System.getProperty("user.name");
		if(System.getProperty("os.name").toLowerCase().contains("mac")) {
			path = String.format(Constant.PathConfig.FOLDER_DOWNLOAD_ONE_MAC, currentUser);
		}
		else {
			path = String.format(Constant.PathConfig.FOLDER_DOWNLOAD_ONE_WIN, currentUser);
		}
		return path;
	}
	
	/**
	 * check file is existed
	 * @param file
	 * @return true/false
	 * @author 
	 */
	public boolean isFileExists(String file) {
		try {
			File files = new File(pathFolderDownload + file);
			boolean exists = files.exists();
			return exists;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return false;
		}
	}
	
	/**
	 * check file is existed
	 * @param driver
	 * @param file
	 * @param ipClient
	 * @return true/false
	 * @author 
	 */
	public boolean isFileExists(WebDriver driver, String file, String ipClient) 
	{
	
		String currentUser = System.getProperty("user.name");
		String filePath = "\\\\"+ipClient+"\\Users\\"+currentUser+"\\Downloads\\"+file;

		File files = new File(filePath);
		boolean exists = files.exists();
		return exists;
	}
	
	/**
	 * Delete a file from local PC
	 * @param file
	 * @author 
	 */
	public void deleteFile(String file) {
		try {
			if (isFileExists(file)) {
				File files = new File(pathFolderDownload + file);
				files.delete();
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	/**
	 * Delete a file from local PC
	 * @param file
	 * @param ipClient
	 * @author 
	 */
	public void deleteFile(WebDriver driver, String file, String ipClient) {
		Common.getCommon().connectNodeMachine(ipClient);
		String currentUser = System.getProperty("user.name");
		String filePath = "\\\\"+ipClient+"\\Users\\"+currentUser+"\\Downloads\\"+file;
		try {
			if (isFileExists(file)) {
				File files = new File(filePath);
				files.delete();
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		Common.getCommon().disconnectNodeMachine();
	}
	
	/**
	 * Connect to node machine by cmd
	 * @param ip - private ip of node machine
	 * @param password - password of Administrator of node machine
	 */
	public void connectNodeMachine(String ip)
	{
		if (Constant.PathConfig.GRID.toLowerCase().equals("yes")){
			Runtime run = Runtime.getRuntime();
			try {
				run.exec(String.format("net use x: \\\\%s\\C$ /user:Administrator %s", ip, Constant.CommonData.PASSWORD));
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	
	/**
	 * DisConnect to node machine by cmd
	 */
	public void disconnectNodeMachine()
	{
		if (Constant.PathConfig.GRID.toLowerCase().equals("yes")){
			Runtime run = Runtime.getRuntime();
			try {
				run.exec(String.format("net use /delete x:"));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get current time of America
	 * @return time such as 2:20 am
	 */
	public String getCurrentTimeAmerica() {
		DateTimeZone dateTimeZone = DateTimeZone.forID("America/Los_Angeles");
		DateTime dt = new DateTime(dateTimeZone);
		return dt.toString("hh:mm a").toLowerCase();
	}
	
	/**
	 * capture Screenshot
	 * @param filename
	 * @return true/false
	 * @throws IOException 
	 */
	public String captureScreenshot(WebDriver driver,String filename, String filepath)
	{
		String file = "null";
		try{
		//Taking the screen using TakesScreenshot Class
		File objScreenCaptureFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		//Storing the image in the local system.
		file = filepath + "\\" + filename;
		FileUtils.copyFile(objScreenCaptureFile, new File(file));
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * init autoIT
	 * @return autoIT
	 * @author 
	 */
	public AutoItX initAutoIT() {
		File file = new File("lib", "jacob-1.17-M2-x86.dll"); // path to the
																// jacob dll
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());

		AutoItX autoIT = new AutoItX();
		return autoIT;
	}
	
	/**
	 * open file to upload using autoitx lib
	 * @param fileName
	 * @author 
	 */
	public void openFileForUpload(WebDriver driver, String fileName) {
		try {
			AutoItX autoIT = initAutoIT();
			String title;
			String path = getPathFile(fileName);
			if (driver.toString().contains("firefox"))
				title ="File Upload";
			else
				title = "Open";	

	        autoIT.winWaitActive(title,"",5);
	       
	        if(autoIT.winExists(title))
	        {
	        	autoIT.winActivate(title);
	        	autoIT.controlClick(title, "", "[CLASS:Edit; INSTANCE:1]"); 
	 	        Thread.sleep(3000);
	 	        autoIT.controlSend(title, "", "[CLASS:Edit; INSTANCE:1]", path);
				Thread.sleep(2000);
				String tmp = autoIT.controlGetText(title, "",
						"[CLASS:Edit; INSTANCE:1]");
				if (!tmp.equals(path)) {
		 	        autoIT.controlSend(title, "", "[CLASS:Edit; INSTANCE:1]", "^a");
		 	        autoIT.controlSend(title, "", "[CLASS:Edit; INSTANCE:1]", "{BACKSPACE}");
		 	        Thread.sleep(500);
					autoIT.controlSend(title, "", "[CLASS:Edit; INSTANCE:1]",
							path);
					Thread.sleep(2000);
				}
				autoIT.controlClick(title, "", "[CLASS:Button; INSTANCE:1]");
				try {
	 				Thread.sleep(500);
	 			} catch (InterruptedException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	 			if(autoIT.winExists(title))
	 			{
	 				autoIT.controlClick(title, "", "[CLASS:Button; INSTANCE:1]");
	 				autoIT.controlClick(title, "", "[CLASS:Button; INSTANCE:2]");
	 				
	 			}
	        }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * get full path of file
	 * @param fileName
	 */
	public String getPathFile(String fileName)
	{
		if (Constant.PathConfig.GRID.equals("yes"))
		{
				String pathFile =  "C:\\" + fileName;//if running grid, copy and paste src folder to C:\
				return pathFile.replace("/", "\\");
		}
		else
		{
			File file = new File(fileName);
			return file.getAbsolutePath();
		}
	}
	
	/**
	 *Send an email about fail 
	 */
	public void sendEmailFails(WebDriver driver,String sub, String content) {
		String[] lm = { "bloomboardteam@gmail.com" };
		EmailHandler emailHandler = new EmailHandler();
		String tmpurl = driver.getCurrentUrl();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String msg = content + tmpurl + ".\nAt: " + dateFormat.format(date);

		String path = Common.getCommon().captureScreenshot(
				driver, "ErrorScreenShot_"
						+ UUID.randomUUID().toString().replace("-", "")
						+ ".jpg", "C:\\ScreenShot");
		emailHandler.sendEmailAttackFileToList(lm, sub, msg, path);
	}

	public String pathFolderDownload = getPathContainDownload();
	private final Stack<String> openWindowHandles = new Stack<String>();
	private Common() {}
	public static String en ="";
	private String logOutLink = "";
	private static Common instance = null;
	
}
;