import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {
    private ArrayList<Product> productList;
    private List<User> userList;

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
        this.userList = new ArrayList<>();
    }
    public int checkForDuplicateID(String productID){
        for (Product product: productList){
            if (product.getProductId().equals(productID)){
                System.out.println("Product ID is already exists, Please enter a different ID");
                return -1;
            }
        }
        return 0;
    }

    @Override
    public void addProduct(Product product) {
        if (productList.size() < 50) {
            productList.add(product);
            System.out.println("Product added to the system.");
        } else {
            System.out.println("Maximum product limit reached. Cannot add more products.");
        }
    }

    @Override
    public void addProductList(List<Product> products) {
        if (productList.size() + products.size() <= 50) {
            productList.addAll(products);
        } else {
            System.out.println("Adding these products exceeds the maximum limit.");
        }
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = findProductById(productId);

        if (product != null) {
            productList.remove(product);
            System.out.println("Product removed from the system.");
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    @Override
    public void printProductList() {
        Collections.sort(productList, (p1, p2) -> p1.getProductId().compareTo(p2.getProductId()));

        System.out.println("\n==== Product List ====");
        for (Product product : productList) {
            product.displayInfo();
            System.out.println("------------------------------");
        }
    }

    @Override
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.txt"))) {
            oos.writeObject(productList);
            System.out.println("Data saved to file");
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    @Override
    public void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.txt"))) {
            productList = (ArrayList<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }

    @Override
    public Product findProductById(String productId) {
        return productList.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }
    public Product getProductById(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null; // Product not found
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }


}
