package common;


import org.openqa.selenium.WebDriver;

public class Constant {
	
	public static class PathConfig {
		public static final String AUTOMATION_CONFIG_XML = "src/resource/automation.config.xml";
		public static final String DATA_TEST_XML = "src/resource/dataTest.xml";
		public static final String INTERFACES_XML = "src/resource/interfaces.xml";
		public static final String JQUERY_LOAD = "src/resource/jQueryLoad.js";
		public static final String FOLDER_DOWNLOAD_ONE_MAC = "/Users/%s/Downloads/";
		public static final String FOLDER_DOWNLOAD_ONE_WIN = "C:\\Users\\%s\\Downloads\\";
		public static String HOME_URL = "";
		public static String GRID = "";
	}

	public static class CommonData {
		public static final String USERNAME = "125121514110";
		public static final String PASSWORD = "3010";
		
	}
	
	public static class SearchData {
		public static final String SEARCH_VALUE = "125121514110";		
	}
	
	public static WebDriver driver = null;
	
}



