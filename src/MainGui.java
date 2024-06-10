import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainGui extends JFrame {
    private JComboBox<String> categoryDropdown;
    private JTable productTable;
    private JButton addToCartButton;
    private JButton cartButton;
    private JLabel productIdLabel;
    private JLabel categoryLabel;
    private JLabel nameLabel;
    private JLabel add1;
    private JLabel add2;
    private JLabel availableQuantityLabel;

    private WestminsterShoppingManager shoppingManager;
    private ShoppingCart userShoppingCart;
    ArrayList<Product> productList, electronic, clothing;
    ArrayList<Product>purchaseList;
    private User currentUser;

    public MainGui(WestminsterShoppingManager shoppingManager, ShoppingCart userShoppingCart, ArrayList<Product> productsModel, User currentUser) {
        this.shoppingManager = shoppingManager;
        this.userShoppingCart = userShoppingCart;
        this.productList = productsModel;
        this.currentUser = currentUser;

        this.electronic = new ArrayList<>();
        this.clothing = new ArrayList<>();

        for (Product product : productList) {
            if (product.getCategory().equals("Electronics")) {
                electronic.add(product);
            } else {
                clothing.add(product);
            }
        }

        // Initialize
        categoryDropdown = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        productTable = new JTable();
        GuiProductsModel guiProductsModel = new GuiProductsModel(productList);
        productTable.setModel(guiProductsModel);

        addToCartButton = new JButton("Add to Cart");
        cartButton = new JButton("Cart");
        productIdLabel = new JLabel("Product ID: ");
        categoryLabel = new JLabel("Category: ");
        nameLabel = new JLabel("Name: ");
        add1 = new JLabel("Additional Info: ");
        add2 = new JLabel("Additional Info: ");
        availableQuantityLabel = new JLabel("Available Quantity: ");


        setLayout(new BorderLayout());

        // Add to the frame
        add(createPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
        add(createCartButtonPanel(), BorderLayout.NORTH);

        // Set frame properties
        setTitle("Shopping System GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);

        categoryDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryDropdown.getSelectedItem();

                switch (selectedCategory) {
                    case "All":
                        GuiProductsModel guiProductsModel = new GuiProductsModel(productList);
                        productTable.setModel(guiProductsModel);
                        break;
                    case "Electronics":
                        GuiProductsModel guiProductsModel2 = new GuiProductsModel(electronic);
                        productTable.setModel(guiProductsModel2);
                        break;
                    case "Clothes":
                        GuiProductsModel guiProductsModel3 = new GuiProductsModel(clothing);
                        productTable.setModel(guiProductsModel3);
                        break;
                }

                revalidate();
                repaint();
            }
        });

        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    Product selectedProduct = productList.get(selectedRow);
                    updateLabels(selectedProduct);
                }
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    File file = new File(currentUser.getUsername()+".txt");
                    if (file.exists()){
                        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                            purchaseList = (ArrayList<Product>)ois.readObject();
                            String productId = productTable.getValueAt(selectedRow, 0).toString();
                            userShoppingCart.addProduct(shoppingManager.getProductById(productId));
                            purchaseList.add(shoppingManager.getProductById(productId));
                            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                                oos.writeObject(purchaseList);
                            } catch (IOException exception) {
                                System.out.println("Error saving data to file: " + exception.getMessage());
                            }
                            JOptionPane.showMessageDialog(MainGui.this, "Product added to cart successfully!");
                        } catch (IOException | ClassNotFoundException exception) {
                            System.out.println("Error loading data from file: " + exception.getMessage());
                        }
                    }
                    else{
                        String productId = productTable.getValueAt(selectedRow, 0).toString();
                        userShoppingCart.addProduct(shoppingManager.getProductById(productId));
                        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                            oos.writeObject(userShoppingCart.getProducts());
                        } catch (IOException exception) {
                            System.out.println("Error saving data to file: " + exception.getMessage());
                        }
                        JOptionPane.showMessageDialog(MainGui.this, "Product added to cart successfully!");
                    }


                } else {
                    JOptionPane.showMessageDialog(MainGui.this, "Please select a product to add to the cart.");
                }
            }
        });

        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Cart GUI here
                // Assuming CartGui constructor now accepts a ShoppingCart instance
                CartGui cartGui = new CartGui(userShoppingCart.getProducts());
                cartGui.setVisible(true);
            }
        });

    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Category:"));
        topPanel.add(categoryDropdown);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(6, 1));
        infoPanel.add(productIdLabel);
        infoPanel.add(categoryLabel);
        infoPanel.add(nameLabel);
        infoPanel.add(add1);
        infoPanel.add(add2);
        infoPanel.add(availableQuantityLabel);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(infoPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addToCartButton);
        return buttonPanel;
    }

    private JPanel createCartButtonPanel() {
        JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartButtonPanel.add(cartButton);
        return cartButtonPanel;
    }

    private void updateLabels(Product product) {
        productIdLabel.setText("Product ID: " + product.getProductId());
        categoryLabel.setText("Category: " + product.getCategory());
        nameLabel.setText("Name: " + product.getProductName());

        // Check if the product is Electronics or Clothes and update additional info
        if (product.getCategory().equals("Electronics")) {
            Electronics electronicsProduct = (Electronics) product;
            add1.setText("Brand: " + electronicsProduct.getBrand());
            add2.setText("Warranty: " + electronicsProduct.getWarrantyPeriod() + " months");
        } else if (product.getCategory().equals("Clothes")) {
            Clothing clothesProduct = (Clothing) product;
            add1.setText("Size: " + clothesProduct.getSize());
            add2.setText("Color: " + clothesProduct.getColor());
        }

        // Update the available quantity label
        availableQuantityLabel.setText("Available Quantity: " + product.getNumAvailable());
    }
}
