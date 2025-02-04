package dataobjects.grid.addEditProduct;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AddEditProductDetails {
    private String productName;
    private Double Cost;
    private String Category;
    private String inStock;
    private String quantityPerUnit;
}
