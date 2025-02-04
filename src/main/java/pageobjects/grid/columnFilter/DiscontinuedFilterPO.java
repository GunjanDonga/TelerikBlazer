package pageobjects.grid.columnFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.grid.common.CommonPO;

public class DiscontinuedFilterPO extends CommonPO {

    public DiscontinuedFilterPO(WebDriver driver) {
        super(driver);
    }
    @FindBy(css = ".k-input-value-text")
    private WebElement allDropdown;

    /**
     * Click On Option
     *
     * @param optionName option name
     * @throws InterruptedException Exception
     */
    public void clickOnOption(String optionName) throws InterruptedException {
        selenium.clickOn(allDropdown);
        selenium.clickOn(By.xpath("//span[text()='"+optionName+"']"));
        clickOnButton("Filter");
    }
}
