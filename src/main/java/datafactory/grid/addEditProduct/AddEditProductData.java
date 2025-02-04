package datafactory.grid.addEditProduct;
import dataobjects.grid.addEditProduct.AddEditProductDetails;
import utilities.JavaHelpers;

public class AddEditProductData {
    AddEditProductDetails addEditProductDetails = new AddEditProductDetails();
    JavaHelpers javaHelpers = new JavaHelpers();
    public AddEditProductDetails getDetails(){
        addEditProductDetails.setProductName(javaHelpers.getAlphaString(6));
        addEditProductDetails.setCost(javaHelpers.getRandomDoubleInRange(1.00,5.00));
        addEditProductDetails.setCategory("Produce");
        addEditProductDetails.setInStock(String.valueOf(javaHelpers.getRandomNumber(1,5)));
        addEditProductDetails.setQuantityPerUnit(javaHelpers.getAlphaNumericString(6));
        return addEditProductDetails;
    }
}
