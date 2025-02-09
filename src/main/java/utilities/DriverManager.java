package utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private WebDriver driver;

    public WebDriver setUp(String browserName) {
        switch (browserName.toLowerCase()) {
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                options.setAcceptInsecureCerts(true);
                options.addPreference("dom.webnotifications.enabled", false);
                options.addPreference("dom.push.enabled", false);
                driver = new FirefoxDriver(options);
            }
            case "safari" -> {
                driver = new SafariDriver(); // No additional setup required for Safari
            }
            case "chrome" -> {
                //Create a map to store  preferences
                Map<String, Object> prefs = new HashMap<>();
                //add key and value to map as follow to switch off browser notification
                //Pass the argument 1 to allow and 2 to block
                prefs.put("profile.default_content_setting_values.notifications", 2);
                //Create an instance of ChromeOptions
                ChromeOptions cOptions = new ChromeOptions();
                // set ExperimentalOption - prefs
                cOptions.setExperimentalOption("prefs", prefs);
                cOptions.addArguments("--incognito");

                //Now Pass ChromeOptions instance to ChromeDriver Constructor to initialize chrome driver which will switch off this browser notification on the chrome browser
                driver = new ChromeDriver(cOptions);
            }
            case "chrome-headless" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.addArguments("headless");
//                chromeOptions.addArguments("window-size=1920x1080");
//                //chromeOptions.addArguments("window-size=1440x900");
//                chromeOptions.addArguments("--proxy-server='direct://'");
//                chromeOptions.addArguments("--proxy-bypass-list=*");

                //added for github
                chromeOptions.addArguments("--headless=new");  // Use the latest headless mode
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-popup-blocking");

                driver = new ChromeDriver(chromeOptions);
            }
            case "ie" -> {
                driver = new InternetExplorerDriver();
            }
            case "edge" -> {
                driver = new EdgeDriver();
            }
            default ->
                    throw new IllegalArgumentException("Please specify valid browser name. Valid browser names are: firefox, chrome,chrome-headless, ie ,edge");
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(60));
        driver.manage().window().maximize();
        return driver;
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
