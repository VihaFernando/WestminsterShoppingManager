import java.util.List;

public interface ShoppingManager {
    void addProduct(Product product);

    void addProductList(List<Product> products);

    void deleteProduct(String productId);

    void printProductList();

    void saveToFile();

    void loadFromFile();

    Product findProductById(String productId);
}
