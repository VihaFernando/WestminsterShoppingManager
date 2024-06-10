import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class GuiProductsModel extends AbstractTableModel {

    private ArrayList<Product> products;
    private String[] columns = {"Product ID","Product Name", "Category", "Price","Info"};

    public GuiProductsModel(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object temp = null;
        Product product = products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                temp = products.get(rowIndex).getProductId();
                break;
            case 1:
                temp = products.get(rowIndex).getProductName();
                break;
            case 2:
                temp = products.get(rowIndex).getCategory();
                break;
            case 3:
                temp = products.get(rowIndex).getPrice();
                break;
            case 4:
                if (product instanceof Electronics) {
                    Electronics electronicsProduct = (Electronics) product;
                    temp = "Brand: " + electronicsProduct.getBrand() + " - Warranty: " + electronicsProduct.getWarrantyPeriod() + " months";
                } else if (product instanceof Clothing) {
                    Clothing clothingProduct = (Clothing) product;
                    temp = "Size: " + clothingProduct.getSize() + " - Color: " + clothingProduct.getColor();
                } else {
                    temp = "";
                }
                break;
        }
        return temp;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}
