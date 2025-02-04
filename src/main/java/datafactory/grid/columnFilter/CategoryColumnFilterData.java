package datafactory.grid.columnFilter;
import dataobjects.grid.columnFilter.CategoryColumnFilterDetails;
import java.util.Arrays;
import java.util.List;

public class CategoryColumnFilterData {

    CategoryColumnFilterDetails categoryColumnFilterDetails = new CategoryColumnFilterDetails();
    List<String> checkboxes = Arrays.asList("Dairy Products","Condiments","Beverages");
    public CategoryColumnFilterDetails getCategoryOptionsList(){
        categoryColumnFilterDetails.setCategoryOptions(checkboxes);
        return categoryColumnFilterDetails;
    }
}
