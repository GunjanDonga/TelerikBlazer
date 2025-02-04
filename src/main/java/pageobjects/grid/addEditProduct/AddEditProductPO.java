package pageobjects.grid.addEditProduct;
import dataobjects.grid.addEditProduct.AddEditProductDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.grid.common.CommonPO;

public class AddEditProductPO extends CommonPO {

    public AddEditProductPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(css="div[class='k-window-title']")
    private WebElement addProductPopupHeaderText;

    @FindBy(css = "div[role='alert'] a")
    private WebElement errorText;

    @FindBy(css = "span.k-dropdownlist")
    private WebElement categoryDropDown;

    /**
     * Get Error Message
     *
     * @return error message
     */
    public String getErrorMessage(){
        return selenium.getText(errorText);
    }

    /**
     * Get Add Product Popup Header Text
     *
     * @return add product popup header text
     */
    public String getAddProductPopupHeaderText(){
        return selenium.getText(addProductPopupHeaderText);
    }

    /**
     * Enter Text In TextBox
     *
     * @param labelName label name
     * @param data data
     */
    public void enterTextInTextBox(String labelName,String data) {
        selenium.enterText(By.xpath("//label[text()='"+labelName+"']/parent::div//input"), data, true);
    }

    /**
     * Select Option From Category DropDown
     *
     * @param optionName option
     * @throws InterruptedException Exception
     */
    public void selectOptionFromCategoryDropDown(String optionName) throws InterruptedException {
        selenium.clickOn(categoryDropDown);
        selenium.clickOn(By.xpath("//div[@class='k-list-content']//span[text()='"+optionName+"']"));
    }

    /**
     * Enter Cost Or Stock
     *
     * @param label label
     * @param count count
     * @throws InterruptedException Exception
     */
    public void enterCostOrStock(String label,Double count) throws InterruptedException {
        while(count>0){
            selenium.clickOn(By.xpath("//label[text()='"+label+"']/parent::div//button[@title='Increase value']"));
            count--;
        }
    }

    /**
     * Enter Product Name
     *
     * @param addEditProductDetails object
     */
    public void enterProductName(AddEditProductDetails addEditProductDetails){
        enterTextInTextBox("Product Name", addEditProductDetails.getProductName());
    }

    /**
     * Clear Cost Or Stock TextBox
     *
     * @param label label
     */
    public void clearCostOrStockTextBox(String label){
        selenium.clearTextBoxUsingKeys(By.cssSelector("input[aria-label='"+label+"']"));
    }

    /**
     * Enter Product Data
     *
     * @param addEditProductDetails object
     * @throws InterruptedException Exception
     */
   public void enterProductData(AddEditProductDetails addEditProductDetails) throws InterruptedException {
       clearCostOrStockTextBox("UnitPrice");
       clearCostOrStockTextBox("UnitsInStock");
       selectOptionFromCategoryDropDown(addEditProductDetails.getCategory());
       enterCostOrStock("Cost",addEditProductDetails.getCost());
       enterCostOrStock("In Stock",Double.parseDouble(addEditProductDetails.getInStock()));
       enterTextInTextBox("Quantity Per Unit", addEditProductDetails.getQuantityPerUnit());
       clickOnButton("Update");
    }
}
