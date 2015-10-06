package Interface;

public class SearchPage {

	public static final String SEARCH_TEXTBOX = "//div[@id='gs_lc0']";
	public static final String SEARCH_BUTTON = "//form[@id='tsf']//input[contains(@aria-label,'Google')]";
	public static final String DYNAMIC_NO_RESULT_MESSAGE = "//*[@id='topstuff']//p/em[text()='%s']";
	public static final String FIRST_SEARCH_RESULT = "//ol[@id='rso']/div[2]/li[1]/div/h3/a";
}
