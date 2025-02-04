package pageobjects.grid.common;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;

public class CommonPO extends BasePO {
    public CommonPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.k-filter-menu-container span.k-dropdownlist:nth-child(3)")
    private WebElement andOrOption;

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement cookieButton;

    /**
     * Click On Button
     *
     * @param buttonName button name
     * @throws InterruptedException Exception
     */
    public void clickOnButton(String buttonName) throws InterruptedException {
        selenium.clickOn(By.xpath("//span[text()='"+buttonName+"']"));

    }

    /**
     * Click On Option
     *
     * @param option option
     * @throws InterruptedException Exception
     */
    public void clickOnOption(String option) throws InterruptedException {
        selenium.clickOn(By.xpath("//div[@class='k-list-content']//li/span[text()='"+option+"']"));
    }

    /**
     * Select And Or Option
     *
     * @param option option
     * @throws InterruptedException Exception
     */
    public void selectAndOrOption(String option) throws InterruptedException {
        selenium.clickOn(andOrOption);
        clickOnOption(option);
    }

    /**
     * Click On Cookie Button
     *
     * @throws InterruptedException Exception
     */

    public void clickOnAcceptCookieButton() throws InterruptedException {
        selenium.hardWait(5);
        selenium.clickOn(cookieButton);
    }
}
