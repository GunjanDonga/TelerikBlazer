package pageobjects.grid.columnFilter;
import dataobjects.grid.columnFilter.ColumnFilterDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.grid.common.CommonPO;
import utilities.JavaHelpers;
import java.util.List;

public class ProductNameAndQtyPerUnitFilterPO extends CommonPO {
    JavaHelpers javaHelpers= new JavaHelpers();
    public ProductNameAndQtyPerUnitFilterPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.k-filter-menu-container span.k-dropdownlist:nth-child(1)")
    private WebElement firstDropdown;

    @FindBy(css = "div.k-filter-menu-container span.k-textbox:nth-child(2) input")
    private WebElement firstTextBox;

    @FindBy(css = "div.k-filter-menu-container span.k-dropdownlist:nth-child(4)")
    private WebElement secondDropdown;

    @FindBy(css = "div.k-filter-menu-container span.k-textbox:nth-child(5) input")
    private WebElement secondTextBox;

    @FindBy(xpath = "//tbody/tr")
    private WebElement columnData;

    /**
     * Get Random Quantity Or Product Name
     *
     * @param index int
     * @return string
     */
    public String getRandomQuantityOrProductName(int index){
        List<String> stringList =  (By.xpath("//tbody/tr/td["+index+"]")).findElements(driver).stream().map(WebElement::getText).toList();
        return javaHelpers.getRandomStringFromList(stringList);
    }

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
