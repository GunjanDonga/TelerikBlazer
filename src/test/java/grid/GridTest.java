package grid;
import base.BaseTest;
import com.google.common.collect.ImmutableMap;
import datafactory.grid.addEditProduct.AddEditProductData;
import datafactory.grid.columnFilter.CategoryColumnFilterData;
import datafactory.grid.columnFilter.ColumnFilterData;
import datafactory.grid.columnFilter.DiscontinuedColumnFilterData;
import dataobjects.grid.addEditProduct.AddEditProductDetails;
import dataobjects.grid.columnFilter.CategoryColumnFilterDetails;
import dataobjects.grid.columnFilter.ColumnFilterDetails;
import dataobjects.grid.columnFilter.DiscontinuedColumnFilterDetails;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pageobjects.grid.addEditProduct.AddEditProductPO;
import pageobjects.grid.columnFilter.*;
import pageobjects.grid.deleteProduct.DeleteProductPO;
import pageobjects.grid.gridOverview.GridOverviewPO;
import utilities.Constants;
import java.util.*;

public class GridTest extends BaseTest {

    String expectedTitle= "Blazor Data Grid - Overview";
    String expectedAddProductPopupHeaderText= "Add new record";
    ImmutableMap<Integer, String> immutableMap = ImmutableMap.of(
            1, "Product Name",
            2, "Cost",
            3, "Discontinued",
            4, "Category",
            5, "In Stock",
            6, "Quantity Per Unit");

    @Test
    public void verifyThatTheUserIsAbleToChangeTheThemeSuccessfully() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");

        Reporter.log("Step 3: Select option from change theme drop down");
        String selectedThemeColor = gridOverviewPO.selectAndGetThemeColor("Material","Main Dark");

        Reporter.log("Step 4: Verify that the theme has been changed");
        Assert.assertEquals(gridOverviewPO.getBackgroundColorOfEditButton(),selectedThemeColor,"Theme color doesn't match");
    }

    @Test
    public void verifyThatProductIsAddedSuccessfully() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        AddEditProductPO addEditProductPO = new AddEditProductPO(driver);
        ProductNameAndQtyPerUnitFilterPO productNameAndQtyPerUnitFilterPO = new ProductNameAndQtyPerUnitFilterPO(driver);

        ColumnFilterDetails columnFilterDetails = new ColumnFilterData().getInStockColumnDataIsEqualToGivenStockData();
        AddEditProductDetails addEditProductDetails= new AddEditProductData().getDetails();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3: Click on 'Add Product' button");
        gridOverviewPO.clickOnAddProductButton();

        Reporter.log("Step 4: Verify that the 'Add Product' popup is open");
        Assert.assertEquals(addEditProductPO.getAddProductPopupHeaderText(),expectedAddProductPopupHeaderText,"Title of popup doesn't match");

        Reporter.log("Step 5: Enter product data and click on update button");
        addEditProductPO.enterProductName(addEditProductDetails);
        addEditProductPO.enterProductData(addEditProductDetails);

        Reporter.log("Step 6: Click on product name filter, enter added product name and click on filer button");
        gridOverviewPO.clickOnFilter(immutableMap.get(1));
        columnFilterDetails.setFirstTextBox(addEditProductDetails.getProductName());
        productNameAndQtyPerUnitFilterPO.selectOptionFromFirstFilterDropDown(columnFilterDetails);
        productNameAndQtyPerUnitFilterPO.clickOnButton("Filter");

        Reporter.log("Step 7: Verify that the product data are successfully added");
        Assert.assertTrue(gridOverviewPO.getList(3).stream().allMatch(el-> el.equals(addEditProductDetails.getProductName())));
        Assert.assertTrue(gridOverviewPO.getList(4).stream().allMatch(el-> Double.compare(Double.parseDouble(el.substring(1)), addEditProductDetails.getCost()) == 0));
        Assert.assertTrue(gridOverviewPO.getList(6).stream().allMatch(el-> el.equals(addEditProductDetails.getCategory())));
        Assert.assertTrue(gridOverviewPO.getInStockList().stream().allMatch(el-> el==(Integer.parseInt(addEditProductDetails.getInStock()))));
        Assert.assertTrue(gridOverviewPO.getList(8).stream().allMatch(el->el.equals(addEditProductDetails.getQuantityPerUnit())));
    }

    @Test
    public void verifyThatErrorMessageDisplayedWhenProductNameFieldEmpty() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        AddEditProductPO addEditProductPO = new AddEditProductPO(driver);

        AddEditProductDetails addEditProductDetails= new AddEditProductData().getDetails();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        addEditProductPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3: Click on 'Add Product' button");
        gridOverviewPO.clickOnAddProductButton();

        Reporter.log("Step 4: Verify that the 'Add Product' popup is open");
        Assert.assertEquals(addEditProductPO.getAddProductPopupHeaderText(),expectedAddProductPopupHeaderText,"Title of popup doesn't match");

        Reporter.log("Step 5: Enter data into all fields except product name field and click on update button");
        addEditProductPO.enterProductData(addEditProductDetails);

        Reporter.log("Step 7: Verify that the error message is displayed");
        Assert.assertEquals(addEditProductPO.getErrorMessage(),"The ProductName field is required.","Error message doesn't match");
    }

    @Test
    public void verifyThatDroppedColumnDataAreGroupedByValue() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(), expectedTitle, "Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3: Drag any column and drop into group by that column");
        gridOverviewPO.clickOnCategoryRemoveIcon();
        gridOverviewPO.dragAndDropByColumnName(immutableMap.get(3));

        Reporter.log("Step 4: Verify the data of grouped column");
        List<List<String>> combinedLists = gridOverviewPO.getGroupedColumnLists();
        Assert.assertTrue(combinedLists.get(0).stream().allMatch(el->el.equals("Available")));
        Assert.assertTrue(combinedLists.get(1).stream().allMatch(el->el.equals("Discontinued")));
    }

    @Test
    public void verifyThatProductDataEditedSuccessfully() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        AddEditProductPO addEditProductPO = new AddEditProductPO(driver);
        ProductNameAndQtyPerUnitFilterPO productNameAndQtyPerUnitFilterPO = new ProductNameAndQtyPerUnitFilterPO(driver);
        ColumnFilterDetails columnFilterDetails = new ColumnFilterData().getInStockColumnDataIsEqualToGivenStockData();

        AddEditProductDetails addEditProductDetails= new AddEditProductData().getDetails();
        String editedProductName= addEditProductDetails.getProductName();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3: Click on 'Edit' button");
        String productName = productNameAndQtyPerUnitFilterPO.getRandomQuantityOrProductName(3);
        gridOverviewPO.clickOnEditButton(productName);

        Reporter.log("Step 4: Verify that the 'Edit' popup is open");
        Assert.assertEquals(addEditProductPO.getAddProductPopupHeaderText(),"Edit","Header text doesn't match");

        Reporter.log("Step 5: Edit product data and click on update button");
        addEditProductPO.enterProductName(addEditProductDetails);
        addEditProductPO.enterProductData(addEditProductDetails);

        Reporter.log("Step 6: Click on product name filter, enter edited product name and click on filer button");
        gridOverviewPO.clickOnFilter(immutableMap.get(1));
        columnFilterDetails.setFirstTextBox(editedProductName);
        productNameAndQtyPerUnitFilterPO.selectOptionFromFirstFilterDropDown(columnFilterDetails);
        productNameAndQtyPerUnitFilterPO.clickOnButton("Filter");

        Reporter.log("Step 7: Verify that the product name is successfully changed");
        Assert.assertTrue(gridOverviewPO.getList(3).stream().allMatch(el-> el.equals(addEditProductDetails.getProductName())));
        Assert.assertTrue(gridOverviewPO.getList(4).stream().allMatch(el-> Double.compare(Double.parseDouble(el.substring(1)), addEditProductDetails.getCost()) == 0));
        Assert.assertTrue(gridOverviewPO.getList(6).stream().allMatch(el-> el.equals(addEditProductDetails.getCategory())));
        Assert.assertTrue(gridOverviewPO.getInStockList().stream().allMatch(el-> el==(Integer.parseInt(addEditProductDetails.getInStock()))));
        Assert.assertTrue(gridOverviewPO.getList(8).stream().allMatch(el->el.equals(addEditProductDetails.getQuantityPerUnit())));
    }

    @Test
    public void verifyThatProductIsSuccessfullyDeleted() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        ProductNameAndQtyPerUnitFilterPO productNameAndQtyPerUnitFilterPO = new ProductNameAndQtyPerUnitFilterPO(driver);
        DeleteProductPO deleteProductPO =  new DeleteProductPO(driver);

        ColumnFilterDetails columnFilterDetails = new ColumnFilterData().getInStockColumnDataIsEqualToGivenStockData();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3: Click on 'Delete' button");
        String productName = productNameAndQtyPerUnitFilterPO.getRandomQuantityOrProductName(3);
        gridOverviewPO.clickOnDeleteButton(productName);

        Reporter.log("Step 4: Verify that the 'Delete' popup is open");
        Assert.assertEquals(deleteProductPO.getHeaderText(),"Delete","Header text doesn't match");

        Reporter.log("Step 5: click Ok button of Delete popup");
        deleteProductPO.clickOnOkButton();

        Reporter.log("Step 6: Click on product name filter,enter deleted product name and click on filter button");
        gridOverviewPO.clickOnFilter(immutableMap.get(1));
        columnFilterDetails.setFirstTextBox(productName);
        productNameAndQtyPerUnitFilterPO.selectOptionFromFirstFilterDropDown(columnFilterDetails);
        productNameAndQtyPerUnitFilterPO.clickOnButton("Filter");

        Reporter.log("Step 7: Verify that the product is successfully deleted");
        Assert.assertEquals(gridOverviewPO.getRecordText(),"No records available.","Record text doesn't match");
    }

    @Test
    public void verifyProductNameColumnDataIsNotEqualToGivenName() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        ProductNameAndQtyPerUnitFilterPO productNameAndQtyPerUnitFilterPO = new ProductNameAndQtyPerUnitFilterPO(driver);

        ColumnFilterDetails columnFilterDetails = new ColumnFilterData().getCostColumnDataIsNotEqualToGivenCost();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3:Click on product name filter button and enter data into it");
        String product1 = productNameAndQtyPerUnitFilterPO.getRandomQuantityOrProductName(3);
        String product2 = productNameAndQtyPerUnitFilterPO.getRandomQuantityOrProductName(3);
        columnFilterDetails.setFirstTextBox(product1);
        columnFilterDetails.setSecondTextBox(product2);
        gridOverviewPO.clickOnFilter(immutableMap.get(1));
        productNameAndQtyPerUnitFilterPO.selectOptionFromBothFilterDropDown(columnFilterDetails);

        Reporter.log("Step 4: Verify that the data in product name column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getList(3).stream().noneMatch(el -> el.equals(product1) && (el.equals(product2))));
    }

    @Test
    public void verifyProductNameColumnDataContainsAndEndsWithGivenName() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        ProductNameAndQtyPerUnitFilterPO productNameAndQtyPerUnitFilterPO = new ProductNameAndQtyPerUnitFilterPO(driver);
        ColumnFilterDetails columnFilterDetails = new ColumnFilterData().getProductNameColumnDataContainsAndEndsWithGivenName();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3:Click on product name filter button and enter data into it");
        gridOverviewPO.clickOnFilter(immutableMap.get(1));
        productNameAndQtyPerUnitFilterPO.selectOptionFromBothFilterDropDown(columnFilterDetails);

        Reporter.log("Step 4: Verify that the data in product name column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getList(3).stream().allMatch(el -> el.endsWith(columnFilterDetails.getFirstTextBox()) && (el.contains(columnFilterDetails.getSecondTextBox()))));
    }

    @Test
    public void verifyProductNameColumnDataDoesNotContainsGivenName() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        ProductNameAndQtyPerUnitFilterPO productNameAndQtyPerUnitFilterPO = new ProductNameAndQtyPerUnitFilterPO(driver);
        ColumnFilterDetails columnFilterDetails= new ColumnFilterData().getProductNameColumnDataDoesNotContainsGivenName();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3:Click on product name filter button and enter data into it");
        gridOverviewPO.clickOnFilter(immutableMap.get(1));
        productNameAndQtyPerUnitFilterPO.selectOptionFromFirstFilterDropDown(columnFilterDetails);
        productNameAndQtyPerUnitFilterPO.clickOnButton("Filter");

        Reporter.log("Step 4: Verify that the data in product name column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getList(3).stream().noneMatch(el -> el.contains(columnFilterDetails.getFirstTextBox())));
    }

    @Test
    public void verifyProductNameColumnDataIsEqualToGivenName() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        ProductNameAndQtyPerUnitFilterPO productNameAndQtyPerUnitFilterPO = new ProductNameAndQtyPerUnitFilterPO(driver);
        ColumnFilterDetails columnFilterDetails = new ColumnFilterData().getInStockColumnDataIsEqualToGivenStockData();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3:Click on product name filter button and enter data into it");
        String product1 = productNameAndQtyPerUnitFilterPO.getRandomQuantityOrProductName(3);
        String product2 = productNameAndQtyPerUnitFilterPO.getRandomQuantityOrProductName(3);
        columnFilterDetails.setFirstTextBox(product1);
        columnFilterDetails.setSecondTextBox(product2);
        gridOverviewPO.clickOnFilter(immutableMap.get(1));
        productNameAndQtyPerUnitFilterPO.selectOptionFromBothFilterDropDown(columnFilterDetails);

        Reporter.log("Step 4: Verify that the data in product name column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getList(3).stream().allMatch(el -> el.equals(product1) || (el.equals(product2))));
    }

    @Test
    public void verifyCostColumnDataIsNotEqualToGivenCost() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        CostInStockFilterPO costInStockFilterPO = new CostInStockFilterPO(driver);
        ColumnFilterDetails columnFilterDetails= new ColumnFilterData().getCostColumnDataIsNotEqualToGivenCost();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3:Click on Cost filter button and enter data into it");
        gridOverviewPO.clickOnFilter(immutableMap.get(2));
        costInStockFilterPO.selectOptionFromBothFilterDropDown(columnFilterDetails);

        Reporter.log("Step 4: Verify that the data in cost column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getList(4).stream().noneMatch(el -> el.equals("$"+columnFilterDetails.getFirstTextBox()) && (el.equals("$"+columnFilterDetails.getSecondTextBox()))));
    }

    @Test
    public void verifyCostColumnDataIsLessThanOrEqualToGivenCost() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        CostInStockFilterPO costInStockFilterPO = new CostInStockFilterPO(driver);
        ColumnFilterDetails columnFilterDetails = new ColumnFilterData().getCostColumnDataIsLessThanOrEqualToGivenCost();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3:Click on Cost filter button and enter data into it");
        gridOverviewPO.clickOnFilter(immutableMap.get(2));
        costInStockFilterPO.selectOptionFromFirstFilterDropDown(columnFilterDetails);
        costInStockFilterPO.clickOnButton("Filter");

        Reporter.log("Step 4: Verify that the data in cost column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getList(4).stream().map(el -> Double.parseDouble(el.substring(1))).allMatch(num -> num <= Double.parseDouble(columnFilterDetails.getFirstTextBox())));
    }

    @Test
    public void verifyCostColumnDataIsGreaterThanAndLessThanGivenCost() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        CostInStockFilterPO costInStockFilterPO = new CostInStockFilterPO(driver);
        ColumnFilterDetails columnFilterDetails = new ColumnFilterData().getCostColumnDataIsGreaterThanAndLessThanGivenCost();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3:Click on Cost filter button and enter data into it");
        gridOverviewPO.clickOnFilter(immutableMap.get(2));
        costInStockFilterPO.selectOptionFromBothFilterDropDown(columnFilterDetails);

        Reporter.log("Step 4: Verify that the data in cost column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getList(4).stream().map(el -> Double.parseDouble(el.substring(1))).allMatch(num -> num > Double.parseDouble(columnFilterDetails.getFirstTextBox()) && num < Double.parseDouble(columnFilterDetails.getSecondTextBox())));
       }

    @Test
    public void verifyTheStatusOfDiscontinuedColumnIsDiscontinued() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        DiscontinuedFilterPO discontinuedFilterPO = new DiscontinuedFilterPO(driver);

        DiscontinuedColumnFilterDetails discontinuedColumnFilterDetails = new DiscontinuedColumnFilterData().getIsTrueOption();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(), expectedTitle, "Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();
        gridOverviewPO.clickOnCategoryRemoveIcon();

        Reporter.log("Step 3:Click on Discontinued filter button and select any option");
        gridOverviewPO.clickOnFilter(immutableMap.get(3));
        discontinuedFilterPO.clickOnOption(discontinuedColumnFilterDetails.getDropdownOption());

        Reporter.log("Step 4: Verify that the data in Discontinued column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getList(4).stream().allMatch(el-> el.equals("Discontinued")));
    }

    @Test
    public void verifyTheStatusOfDiscontinuedColumnIsAvailable() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        DiscontinuedFilterPO discontinuedFilterPO = new DiscontinuedFilterPO(driver);
        DiscontinuedColumnFilterDetails discontinuedColumnFilterDetails = new DiscontinuedColumnFilterData().getIsFalseOption();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(), expectedTitle, "Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();
        gridOverviewPO.clickOnCategoryRemoveIcon();

        Reporter.log("Step 3:Click on Discontinued filter button and select any option");
        gridOverviewPO.clickOnFilter(immutableMap.get(3));
        discontinuedFilterPO.clickOnOption(discontinuedColumnFilterDetails.getDropdownOption());

        Reporter.log("Step 4: Verify that the data in Discontinued column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getList(4).stream().allMatch(el-> el.equals("Available")));
    }

    @Test
    public void verifyCategoryColumnDataIsAsPerSelectedCheckboxes() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        CategoryFilterPO categoryFilterPO = new CategoryFilterPO(driver);
        CategoryColumnFilterDetails categoryColumnFilterDetails = new CategoryColumnFilterData().getCategoryOptionsList();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(), expectedTitle, "Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();
        gridOverviewPO.clickOnCategoryRemoveIcon();

        Reporter.log("Step 3:Click on Category filter button and click on any checkbox");
        gridOverviewPO.clickOnFilter(immutableMap.get(4));
        List<String> checkboxes =categoryColumnFilterDetails.getCategoryOptions();
        categoryFilterPO.clickOnCheckboxes(checkboxes);

        Reporter.log("Step 4: Verify that the data in category column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getList(5).stream().allMatch(el-> el.equals(checkboxes.get(0)) || el.equals(checkboxes.get(1)) || el.equals(checkboxes.get(2))));

        Reporter.log("Step 5: Click on Clear button of filter");
        gridOverviewPO.clickOnFilter(immutableMap.get(4));
        categoryFilterPO.clickOnButton("Clear");

        Reporter.log("Step 6: Verify that all data is displayed in category column");
        Assert.assertEquals(gridOverviewPO.getTotalProductCount(),"1 - 10 of 77 items","Filter doesn't cleared ");
    }

    @Test
    public void verifyInStockColumnDataIsGreaterThanAndLessThanOrEqualToGivenStockData() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        CostInStockFilterPO costInStockFilterPO = new CostInStockFilterPO(driver);
        ColumnFilterDetails columnFilterDetails = new ColumnFilterData().getInStockColumnDataIsGreaterThanAndLessThanOrEqualToGivenStockData();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3:Click on InStock column filter button and enter data into it");
        gridOverviewPO.clickOnFilter(immutableMap.get(5));
        costInStockFilterPO.selectOptionFromBothFilterDropDown(columnFilterDetails);

        Reporter.log("Step 4: Verify that the data in InStock column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getInStockList().stream().allMatch(el-> el >= Integer.parseInt(columnFilterDetails.getFirstTextBox()) && el <= Integer.parseInt(columnFilterDetails.getSecondTextBox())));
    }

    @Test
    public void verifyInStockColumnDataIsEqualToGivenStockData() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        CostInStockFilterPO costInStockFilterPO = new CostInStockFilterPO(driver);
        ColumnFilterDetails columnFilterDetails = new ColumnFilterData().getInStockColumnDataIsEqualToGivenStockData();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3:Click on InStock column filter button and enter data into it");
        gridOverviewPO.clickOnFilter(immutableMap.get(5));
        costInStockFilterPO.selectOptionFromBothFilterDropDown(columnFilterDetails);

        Reporter.log("Step 4: Verify that the data in InStock column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getInStockList().stream().allMatch(el-> el == Integer.parseInt(columnFilterDetails.getFirstTextBox()) || el == Integer.parseInt(columnFilterDetails.getSecondTextBox())));
    }

    @Test
    public void verifyQuantityPerUnitColumnDataIsEqualToGivenQuantity() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);
        ProductNameAndQtyPerUnitFilterPO productNameAndQtyPerUnitFilterPO = new ProductNameAndQtyPerUnitFilterPO(driver);
        ColumnFilterDetails columnFilterDetails = new ColumnFilterData().getInStockColumnDataIsEqualToGivenStockData();

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();

        Reporter.log("Step 3:Click on Quantity Per Unit column filter button and enter data into it");
        String qty1 = productNameAndQtyPerUnitFilterPO.getRandomQuantityOrProductName(8);
        String qty2 = productNameAndQtyPerUnitFilterPO.getRandomQuantityOrProductName(8);
        columnFilterDetails.setFirstTextBox(qty1);
        columnFilterDetails.setSecondTextBox(qty2);
        gridOverviewPO.clickOnFilter(immutableMap.get(6));
        productNameAndQtyPerUnitFilterPO.selectOptionFromBothFilterDropDown(columnFilterDetails);

        Reporter.log("Step 4: Verify that the data in Quantity Per Unit column is as per the applied filter");
        Assert.assertTrue(gridOverviewPO.getList(8).stream().allMatch(el -> el.equals(qty1) || (el.equals(qty2))));
    }

    @Test
    public void verifyCostColumnSortingInAscendingDescendingOrder() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();
        gridOverviewPO.clickOnCategoryRemoveIcon();

        Reporter.log("Step 3: Click on Cost column");
        gridOverviewPO.clickOnColumn(immutableMap.get(2));
        List<Double> originalList  = gridOverviewPO.getList(3).stream().map(el -> Double.parseDouble(el.substring(1))).toList();
        List<Double> sortedList = new ArrayList<>(originalList);
        Collections.sort(sortedList);

        Reporter.log("Step 4: Verify that the 'Cost' column is sorted in ascending order");
        Assert.assertEquals(originalList,sortedList,"Cost column is not sorted in ascending order");

        Reporter.log("Step 5: Click on Cost column again");
        gridOverviewPO.clickOnColumn(immutableMap.get(2));
        List<Double> originalDescendingList  = gridOverviewPO.getList(3).stream().map(el -> Double.parseDouble(el.substring(1))).toList();
        List<Double> descendingList = new ArrayList<>(originalDescendingList);
        descendingList.sort(Collections.reverseOrder());

        Reporter.log("Step 6: Verify that the 'Cost' column is sorted in descending order");
        Assert.assertEquals(originalDescendingList,descendingList,"Cost column is not sorted in descending order");
    }

    @Test
    public void verifyCategoryColumnSortingInAscendingOrder() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();
        gridOverviewPO.clickOnCategoryRemoveIcon();

        Reporter.log("Step 3: Click on Category column");
        gridOverviewPO.clickOnColumn(immutableMap.get(4));
        List<String> originalList  = gridOverviewPO.getList(5);
        List<String> sortedList = new ArrayList<>(originalList);
        Collections.sort(sortedList);

        Reporter.log("Step 4: Verify that the 'Category' column is sorted in ascending order");
        Assert.assertEquals(originalList,sortedList,"Category column is not sorted");
    }

    @Test
    public void verifyInStockColumnSortingInAscendingOrder() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();
        gridOverviewPO.clickOnCategoryRemoveIcon();

        Reporter.log("Step 3: Click on In Stock column");
        gridOverviewPO.clickOnColumn(immutableMap.get(5));
        List<Integer> originalList  = gridOverviewPO.getInStockList();
        List<Integer> sortedList = new ArrayList<>(originalList);
        Collections.sort(sortedList);

        Reporter.log("Step 4: Verify that the 'In Stock' column is sorted in ascending order");
        Assert.assertEquals(originalList,sortedList,"In Stock column is not sorted");
        }

    @Test
    public void verifyQuantityPerUnitColumnSortingInAscendingOrder() throws InterruptedException {

        GridOverviewPO gridOverviewPO = new GridOverviewPO(driver);

        Reporter.log("Step 1: Navigate to url");
        selenium.navigateToPage(Constants.GridURL);

        Reporter.log("Step 2: Verify that the user is successfully navigated to grid overview page");
        Assert.assertEquals(gridOverviewPO.getGridOverviewPageTitle(),expectedTitle,"Title of page doesn't match");
        gridOverviewPO.clickOnAcceptCookieButton();
        gridOverviewPO.clickOnCategoryRemoveIcon();

        Reporter.log("Step 3: Click on Category column");
        gridOverviewPO.clickOnColumn(immutableMap.get(6));
        List<String> originalList  = gridOverviewPO.getList(7);
        List<String> sortedList = new ArrayList<>(originalList);
        Collections.sort(sortedList);

        Reporter.log("Step 4: Verify that the 'Category' column is sorted in ascending order");
        Assert.assertEquals(originalList,sortedList,"Quantity Per Unit column is not sorted");
    }
}
