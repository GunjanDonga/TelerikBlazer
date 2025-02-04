package pageobjects.grid.deleteProduct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;
public class DeleteProductPO extends BasePO {
    public DeleteProductPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(css=".k-dialog-title")
    private WebElement deletePopupHeaderText;

    @FindBy(css=".k-dialog-actions button")
    private WebElement okButton;

    /**
     * Click On Ok Button
     *
     * @throws InterruptedException Exception
     */
    public void clickOnOkButton() throws InterruptedException {
        selenium.click(okButton);
    }

    /**
     * Get Header Text
     *
     * @return header text
     */
    public String getHeaderText(){
        return selenium.getText(deletePopupHeaderText);
    }
}
