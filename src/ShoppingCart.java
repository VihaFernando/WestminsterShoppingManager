import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;
    private User user;
    private WestminsterShoppingManager shoppingManager;

    public ShoppingCart(User user, WestminsterShoppingManager shoppingManager) {
        this.user = user;
        this.products = new ArrayList<>();
        this.shoppingManager = shoppingManager;
    }

    public void addProduct(Product product) {
        if (product != null && product.getNumAvailable() > 0) {
            products.add(product);
            System.out.println("Product added to the cart.");
            product.setNumAvailable(product.getNumAvailable() - 1); // Decrement available quantity
        } else {
            System.out.println("Product not available or invalid. Cannot add to the cart.");
        }
    }

    public void removeProduct(String productId) {
        Product product = findProductById(productId);

        if (product != null) {
            products.remove(product);
            System.out.println("Product removed from the cart.");
            product.setNumAvailable(product.getNumAvailable() + 1); // Increment available quantity
        } else {
            System.out.println("Product with ID " + productId + " not found in the cart.");
        }
    }

    public void displayCart() {
        if (products.isEmpty()) {
            System.out.println("Your shopping cart is empty.");
        } else {
            System.out.println("\n==== Shopping Cart ====");
            double totalCost = 0;

            for (Product product : products) {
                product.displayInfo();
                System.out.println("------------------------------");
                totalCost += product.getPrice();
            }

            System.out.println("Total Cost: $" + totalCost);
        }
    }

    public void clearCart() {
        for (Product product : products) {
            product.setNumAvailable(product.getNumAvailable() + 1); // Increment available quantity
        }
        products.clear();
        System.out.println("Shopping cart cleared.");
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products); // Return a copy to prevent direct manipulation
    }


    private Product findProductById(String productId) {
        return shoppingManager.findProductById(productId);
    }
}
