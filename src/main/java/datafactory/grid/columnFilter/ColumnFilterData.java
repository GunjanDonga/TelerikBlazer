package datafactory.grid.columnFilter;
import dataobjects.grid.columnFilter.ColumnFilterDetails;

public class ColumnFilterData {

    ColumnFilterDetails columnFilterDetails = new ColumnFilterDetails();

    public ColumnFilterDetails getProductNameColumnDataContainsAndEndsWithGivenName(){
        columnFilterDetails.setFirstContainsDropdown("Ends with");
        columnFilterDetails.setFirstTextBox("a");
        columnFilterDetails.setAndOrDropdown("And");
        columnFilterDetails.setSecondContainsDropdown("Contains");
        columnFilterDetails.setSecondTextBox("e");
        return columnFilterDetails;
    }
    public ColumnFilterDetails getProductNameColumnDataDoesNotContainsGivenName(){
        columnFilterDetails.setFirstContainsDropdown("Does not contain");
        columnFilterDetails.setFirstTextBox("a");
        return columnFilterDetails;
    }
    public ColumnFilterDetails getCostColumnDataIsNotEqualToGivenCost(){
        columnFilterDetails.setFirstContainsDropdown("Is not equal to");
        columnFilterDetails.setFirstTextBox("10.00");
        columnFilterDetails.setAndOrDropdown("And");
        columnFilterDetails.setSecondContainsDropdown("Is not equal to");
        columnFilterDetails.setSecondTextBox("31.23");
        return columnFilterDetails;
    }

    public ColumnFilterDetails getCostColumnDataIsLessThanOrEqualToGivenCost(){
        columnFilterDetails.setFirstContainsDropdown("Is less than or equal to");
        columnFilterDetails.setFirstTextBox("20.00");
        return columnFilterDetails;
    }

    public ColumnFilterDetails getCostColumnDataIsGreaterThanAndLessThanGivenCost(){
        columnFilterDetails.setFirstContainsDropdown("Is greater than");
        columnFilterDetails.setFirstTextBox("10.00");
        columnFilterDetails.setAndOrDropdown("And");
        columnFilterDetails.setSecondContainsDropdown("Is less than");
        columnFilterDetails.setSecondTextBox("50.23");
        return columnFilterDetails;
    }

    public ColumnFilterDetails getInStockColumnDataIsGreaterThanAndLessThanOrEqualToGivenStockData(){
        columnFilterDetails.setFirstContainsDropdown("Is greater than or equal to");
        columnFilterDetails.setFirstTextBox("10");
        columnFilterDetails.setAndOrDropdown("And");
        columnFilterDetails.setSecondContainsDropdown("Is less than or equal to");
        columnFilterDetails.setSecondTextBox("20");
        return columnFilterDetails;
    }

    public ColumnFilterDetails getInStockColumnDataIsEqualToGivenStockData(){
        columnFilterDetails.setFirstContainsDropdown("Is equal to");
        columnFilterDetails.setFirstTextBox("15");
        columnFilterDetails.setAndOrDropdown("Or");
        columnFilterDetails.setSecondContainsDropdown("Is equal to");
        columnFilterDetails.setSecondTextBox("20");
        return columnFilterDetails;
    }
}
