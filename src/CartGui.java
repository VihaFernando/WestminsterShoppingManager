import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CartGui extends JFrame {
    private JTable cartTable;
    private JLabel totalPriceLabel;
    private JLabel firstDiscounts;
    ArrayList<Product>purchaseList;

    public CartGui(List<Product> cartItems) {
        cartTable = new JTable();
        totalPriceLabel = new JLabel("Total Price: $" + calculateTotalPrice(cartItems));

        setLayout(new BorderLayout());

        add(new JScrollPane(cartTable), BorderLayout.CENTER);
        add(totalPriceLabel, BorderLayout.SOUTH);

        // Set frame properties
        setTitle("Shopping Cart");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);

        // Update the cart table
        updateCartTable(cartItems);
    }
    public double calculateFirstDiscount(List<Product>productList){
        double total = calculateTotalPrice(productList);
        return (total/100)*10;
    }
    public double calculateThreeItemDiscount(List<Product>productList){
        double total = calculateTotalPrice(productList);
        return (total/100)*10;
    }

    private void updateCartTable(List<Product> cartItems) {
        // Sort the cart items based on product name
        Collections.sort(cartItems, Comparator.comparing(Product::getProductName));

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Quantity");
        model.addColumn("Price");

        for (Product item : cartItems) {
            model.addRow(new Object[]{item.getProductId(), item.getProductName(), 1, item.getPrice()});
        }

        cartTable.setModel(model);
    }

    // Method to calculate the total price of items in the cart
    private double calculateTotalPrice(List<Product> cartItems) {
        double total = 0;
        int itemCount = cartItems.size();


        Map<String, Integer> categoryCountMap = new HashMap<>();

        for (Product item : cartItems) {
            total += item.getPrice();


            categoryCountMap.put(item.getCategory(), categoryCountMap.getOrDefault(item.getCategory(), 0) + 1);
        }

        return total;
    }
}
