import java.io.Serializable;

public abstract class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String productId;
    private String productName;
    private int numAvailable;
    private double price;
    private String category;

    public Product(String productId, String productCategory, String productName, int numAvailable, double price) {
        this.productId = productId;
        this.productName = productName;
        this.numAvailable = numAvailable;
        this.price = price;
        this.category = productCategory;
    }

    public String getCategory() {
        return category;
    }
    public void setNumAvailable(int numAvailable) {
        this.numAvailable = numAvailable;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getNumAvailable() {
        return numAvailable;
    }

    public double getPrice() {
        return price;
    }


    public abstract void displayInfo();
}
