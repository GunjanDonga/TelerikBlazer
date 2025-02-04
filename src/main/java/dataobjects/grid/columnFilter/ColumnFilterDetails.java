package dataobjects.grid.columnFilter;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ColumnFilterDetails {

    private String firstContainsDropdown;
    private String firstTextBox;
    private String andOrDropdown;
    private String secondContainsDropdown;
    private String secondTextBox;
}
