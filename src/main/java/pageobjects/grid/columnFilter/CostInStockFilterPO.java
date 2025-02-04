package pageobjects.grid.columnFilter;
import dataobjects.grid.columnFilter.ColumnFilterDetails;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.grid.common.CommonPO;

public class CostInStockFilterPO extends CommonPO {
    public CostInStockFilterPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(css ="div.k-filter-menu-container span.k-dropdownlist:nth-child(1)" )
    private WebElement firstDropdown;

    @FindBy(css = "span.k-numerictextbox:nth-child(2) input")
    private WebElement firstTextBox;

    @FindBy(css = "div.k-filter-menu-container span.k-dropdownlist:nth-child(4)")
    private WebElement secondDropdown;

    @FindBy(css = "span.k-numerictextbox:nth-child(5) input")
    private WebElement secondTextBox;

    /**
     * Select Option From First Filter DropDown
     *
     * @param columnFilterDetails object
     * @throws InterruptedException Exception
     */
    public void selectOptionFromFirstFilterDropDown(ColumnFilterDetails columnFilterDetails) throws InterruptedException {
        selenium.clickOn(firstDropdown);
        clickOnOption(columnFilterDetails.getFirstContainsDropdown());
        selenium.enterText(firstTextBox,columnFilterDetails.getFirstTextBox(),true);
    }

    /**
     * Select Option From Both Filter DropDown
     *
     * @param columnFilterDetails object
     * @throws InterruptedException Exception
     */
    public void selectOptionFromBothFilterDropDown(ColumnFilterDetails columnFilterDetails) throws InterruptedException {
        selectOptionFromFirstFilterDropDown(columnFilterDetails);
        selectAndOrOption(columnFilterDetails.getAndOrDropdown());
        selenium.clickOn(secondDropdown);
        clickOnOption(columnFilterDetails.getSecondContainsDropdown());
        selenium.enterText(secondTextBox,columnFilterDetails.getSecondTextBox(),true);
        clickOnButton("Filter");
    }
}
