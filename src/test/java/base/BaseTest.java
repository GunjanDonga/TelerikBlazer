package base;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.DriverManager;
import utilities.JavaHelpers;
import utilities.SeleniumHelpers;
public class BaseTest {
    protected WebDriver driver;
    protected SeleniumHelpers selenium;
    private DriverManager drivermanager;
    JavaHelpers javaHelpers = new JavaHelpers();

    @BeforeSuite
    public void deleteScreenshot(){
        javaHelpers.deleteScreenshots();
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        drivermanager = new DriverManager();
        driver = drivermanager.setUp(browser);
        selenium = new SeleniumHelpers(driver);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            //capturing screenshot if failed
            if (ITestResult.FAILURE == result.getStatus()) {
                selenium.takeScreenshot(result.getName());
            }
        } catch (Exception e) {
            //ignore
        }
        drivermanager.tearDown();
    }
}


