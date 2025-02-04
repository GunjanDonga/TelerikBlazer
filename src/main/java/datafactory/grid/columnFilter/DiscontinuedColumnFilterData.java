package datafactory.grid.columnFilter;

import dataobjects.grid.columnFilter.DiscontinuedColumnFilterDetails;

public class DiscontinuedColumnFilterData {

    DiscontinuedColumnFilterDetails discontinuedColumnFilterDetails = new DiscontinuedColumnFilterDetails();

    public DiscontinuedColumnFilterDetails getIsTrueOption(){
        discontinuedColumnFilterDetails.setDropdownOption("is true");
        return discontinuedColumnFilterDetails;
    }

    public DiscontinuedColumnFilterDetails getIsFalseOption(){
        discontinuedColumnFilterDetails.setDropdownOption("is false");
        return discontinuedColumnFilterDetails;
    }
}
