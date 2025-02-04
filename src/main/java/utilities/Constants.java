package utilities;

public class Constants {
    public static final String PROPERTY_FILE = "src/main/resources/constants.properties";

    //Environment i.e. Dev,QA,Prod etc
    public static final String ENV = JavaHelpers.setSystemVariable(PROPERTY_FILE, "Env");

    //Setting up the URLs
    public static final String GridURL = JavaHelpers.getPropertyValue(PROPERTY_FILE, "url_BlazorGrid");
    public static final String CardURL = JavaHelpers.getPropertyValue(PROPERTY_FILE, "url_BlazorCard");

    //Selenium constants
    public static final int WEBDRIVER_WAIT_DURATION = Integer.parseInt(JavaHelpers.getPropertyValue(PROPERTY_FILE, "WebDriverWaitDuration"));
    public static final int MINIMUM_WEBDRIVER_WAIT_DURATION = Integer.parseInt(JavaHelpers.getPropertyValue(PROPERTY_FILE, "MinimumWebDriverWaitDuration"));
    public static final int PAGEFACTORY_WAIT_DURATION = Integer.parseInt(JavaHelpers.getPropertyValue(PROPERTY_FILE, "PageFactoryWaitDuration"));
    public static final String SCREENSHOT_LOCATION = JavaHelpers.getPropertyValue(PROPERTY_FILE, "ScreenshotLocation");
}
