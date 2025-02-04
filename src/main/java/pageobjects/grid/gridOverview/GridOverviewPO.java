package pageobjects.grid.gridOverview;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageobjects.base.BasePO;
import pageobjects.grid.common.CommonPO;
import java.util.*;

public class GridOverviewPO extends CommonPO {
    public GridOverviewPO(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[@class='theme-name']")
    private WebElement changeThemeDropdown;

    @FindBy(xpath = "//span[@aria-label='Remove']")
    private WebElement removeButton;

    @FindBy(css = ".k-pager-info")
    private WebElement totalProductCount;

    @FindBy(css = "h1[class='kd-title']")
    private WebElement gridOverviewPageTitle;

    @FindBy(css = ".k-button-solid-primary")
    private WebElement editButtonBackgroundColor;

    @FindBy(xpath = "//span[@class='theme-colors']//span[2]")
    private WebElement selectedThemeColor;

    @FindBy(xpath = "//span[text()='Add Product']")
    private WebElement addProductButton;

    @FindBy(xpath = "//div[@class='k-grouping-drop-container']")
    private WebElement dragAndDropColumn;

    @FindBy(css = ".k-pager-numbers>button:last-of-type")
    private WebElement lastPageNumber;

    @FindBy(xpath = "//tbody/tr//p")
    private List<WebElement> groupedColumnName;

    @FindBy(xpath = "//tbody/tr//p[text()='Discontinued: True']/ancestor::tr/following-sibling::tr/td[5]")
    private List<WebElement> trueStatues;

    @FindBy(xpath = "//tbody/tr//p[text()='Discontinued: True']/ancestor::tr/preceding-sibling::tr/td[5]")
    private List<WebElement> falseStatues;

    @FindBy(css = ".k-grid-norecords td")
    private WebElement noRecordText;

    @FindBy(xpath = "//div[@role='progressbar']/span/span/span/span")
    private List<WebElement> inStockList;

    @FindBy(xpath = "//tbody/tr")
    private WebElement columnData;

    /**
     * Click On Column
     *
     * @param columnName column name
     * @throws InterruptedException Exception
     */
    public void clickOnColumn(String columnName) throws InterruptedException {
        selenium.clickOn(By.xpath("//span[text()='" + columnName + "']"));
    }

    /**
     * Click On Edit Button
     *
     * @param productName product name
     * @throws InterruptedException Exception
     */
    public void clickOnEditButton(String productName) throws InterruptedException {
        selenium.clickOn(By.xpath("//tbody/tr/td[3][text()='" + productName + "']/following-sibling::td//button[1]"));
    }

    /**
     * Get Record Text
     *
     * @return text
     */
    public String getRecordText() {
        return selenium.getText(noRecordText);
    }

    /**
     * Click On Delete Button
     *
     * @param productName product name
     * @throws InterruptedException Exception
     */
    public void clickOnDeleteButton(String productName) throws InterruptedException {
        selenium.clickOn(By.xpath("//tbody/tr/td[3][text()='" + productName + "']/following-sibling::td//button[2]"));
    }

    /**
     * Get Total Product Count
     *
     * @return product count
     */
    public String getTotalProductCount() {
        return selenium.getText(totalProductCount);
    }

    /**
     * Get Grid Overview Page Title
     *
     * @return page title
     */
    public String getGridOverviewPageTitle() {
        return selenium.getText(gridOverviewPageTitle);
    }

    /**
     * Get Background Color Of Edit Button
     *
     * @return background color
     */
    public String getBackgroundColorOfEditButton() {
        return editButtonBackgroundColor.getCssValue("background-color");
    }

    /**
     * Click On Add Product Button
     *
     * @throws InterruptedException Exception
     */
    public void clickOnAddProductButton() throws InterruptedException {
        selenium.clickOn(addProductButton);
    }

    /**
     * Click On Remove Button
     *
     * @throws InterruptedException Exception
     */
    public void clickOnCategoryRemoveIcon() throws InterruptedException {
        selenium.hardWait(3);
        selenium.clickOn(removeButton);
    }

    /**
     * Click On Filter
     *
     * @param columnName column name
     * @throws InterruptedException Exception
     */
    public void clickOnFilter(String columnName) throws InterruptedException {
        selenium.hardWait(2);
        selenium.clickOn(By.xpath("//span[text()='" + columnName + "']/parent::span/following-sibling::div/span"));
    }

    /**
     * Select And Get Theme Color
     *
     * @param themeName theme name
     * @param color     color
     * @return theme color
     * @throws InterruptedException Exception
     */
    public String selectAndGetThemeColor(String themeName, String color) throws InterruptedException {
        selenium.hardWait(5);
        selenium.javascriptClickOn(changeThemeDropdown);
        selenium.hardWait(5);
        selenium.javascriptClickOn(By.xpath("//div[text()='" + themeName + "']/following-sibling::ul//span[text()='" + color + "']"));
        return selectedThemeColor.getCssValue("background-color");
    }

    /**
     * Drag And Drop By Column Name
     *
     * @param columnName column name
     * @throws InterruptedException Exception
     */
    public void dragAndDropByColumnName(String columnName) throws InterruptedException {
        selenium.hardWait(3);
        WebElement drag = driver.findElement(By.xpath("//th[@data-field='" + columnName + "']"));
        selenium.dragAndDrop(drag, dragAndDropColumn);
    }


    /**
     * Get InStock List
     *
     * @return list
     */
    public List<Integer> getInStockList() throws InterruptedException {
        int size = Integer.parseInt(lastPageNumber.getText());
        ArrayList<Integer> stockList = new ArrayList<>();
        if (size > 1) {
            for (int i = 1; i <= size; i++) {
                selenium.clickOn(By.xpath("//div[@class='k-pager-numbers']/button[" + i + "]"));
                stockList.addAll(inStockList.stream().map(el -> Integer.parseInt(el.getText())).toList());
            }
            return stockList;
        } else {
            return inStockList.stream().map(el -> Integer.parseInt(el.getText())).toList();
        }
    }

    /**
     * Get List
     *
     * @param index index
     * @return list
     * @throws InterruptedException Exception
     */
    public List<String> getList(int index) throws InterruptedException {
        int size = Integer.parseInt(lastPageNumber.getText());
        List<String> mergedList = new ArrayList<>();
        if (size > 1) {
            for (int i = 1; i <= size; i++) {
                selenium.clickOn(By.xpath("//div[@class='k-pager-numbers']/button[" + i + "]"));
                mergedList.addAll(columnData.findElements(By.xpath("/td["+index+"]")).stream().map(WebElement::getText).toList());
            }
            return mergedList;
        } else {
            return columnData.findElements(By.xpath("/td["+index+"]")).stream().map(WebElement::getText).toList();
        }
    }

    /**
     * Get Combined Lists
     *
     * @return list of list
     * @throws InterruptedException Exception
     */
    public List<List<String>> getGroupedColumnLists() throws InterruptedException {
        int size = Integer.parseInt(lastPageNumber.getText());
        List<String> falselist = new ArrayList<>();
        List<String> truelist = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            selenium.clickOn(By.xpath("//div[@class='k-pager-numbers']/button[" + i + "]"));

            if (groupedColumnName.size() == 1) {
                if (Objects.equals(groupedColumnName.getFirst().getText(), "Discontinued: False")) {
                    falselist.addAll(columnData.findElements(By.xpath("/td[5]")).stream().map(WebElement::getText).toList());
                } else {
                    truelist.addAll(columnData.findElements(By.xpath("/td[5]")).stream().map(WebElement::getText).toList());
                }
            } else {
                if (Objects.equals(groupedColumnName.getFirst().getText(), "Discontinued: False")) {
                    falselist.addAll(falseStatues.stream().map(WebElement::getText).toList());
                } else {
                    truelist.addAll(trueStatues.stream().map(WebElement::getText).toList());
                }
            }
        }
        List<List<String>> combinedLists = new ArrayList<>();
        combinedLists.add(falselist);
        combinedLists.add(truelist);
        return combinedLists;
    }
}