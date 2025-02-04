package pageobjects.grid.columnFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.grid.common.CommonPO;
import java.util.List;

public class CategoryFilterPO extends CommonPO {
    public CategoryFilterPO(WebDriver driver) {
        super(driver);
    }

    /**
     * Click On Check boxes
     *
     * @param checkboxes checkboxes
     * @throws InterruptedException Exception
     */
    public void clickOnCheckboxes(List<String> checkboxes) throws InterruptedException {
        for (String checkbox : checkboxes) {
            selenium.clickOn(By.xpath("//label[text()='"+checkbox+"']"));
        }
        clickOnButton("Filter");
    }
}
