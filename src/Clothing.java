public class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(String productId,String productName, String productCategory, int numAvailable, double price, String color, String size) {
        super(productId,productName, productCategory,  numAvailable, price);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    @Override
    public void displayInfo() {
        System.out.println("Clothing - " + getProductName() + " (ID: " + getProductId() + ")");
        System.out.println("Size: " + size);
        System.out.println("Color: " + color);
        System.out.println("Price: $" + getPrice());
        System.out.println("Available: " + getNumAvailable() + " units");
    }
}
