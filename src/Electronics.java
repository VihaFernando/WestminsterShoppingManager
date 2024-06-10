public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId,String productName, String productCategory,  int numAvailable, double price, String brand, int warrantyPeriod) {
        super(productId,productName, productCategory, numAvailable, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    @Override
    public void displayInfo() {
        System.out.println("Electronics - " + getProductName() + " (ID: " + getProductId() + ")");
        System.out.println("Brand: " + brand);
        System.out.println("Warranty Period: " + warrantyPeriod + " months");
        System.out.println("Price: $" + getPrice());
        System.out.println("Available: " + getNumAvailable() + " units");
    }

}
