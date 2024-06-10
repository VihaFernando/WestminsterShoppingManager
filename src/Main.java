import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static User currentUser;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // login
        System.out.println("Select login type:");
        System.out.println("1. Regular User");
        System.out.println("2. Manager");
        System.out.print("Enter your choice: ");

        int loginType = scanner.nextInt();
        scanner.nextLine();

        if (loginType == 1) {
            // Regular User
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // Create user and shopping manager
            currentUser = new User(username, password);
            WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
            ShoppingCart userShoppingCart = new ShoppingCart(currentUser, shoppingManager);
            loadFromFile(shoppingManager);

            // Initialize and display the GUI
            openGui(shoppingManager, userShoppingCart, shoppingManager.getProductList());
        } else if (loginType == 2) {
            // Manager
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // Check manager credentials
            WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
            showMenu(shoppingManager, scanner, null);

        } else {
            System.out.println("Invalid choice. Exiting program.");
        }
    }


    private static void showMenu(WestminsterShoppingManager shoppingManager, Scanner scanner, ShoppingCart userShoppingCart) {
        while (true) {
            System.out.println("\n==== Shopping System Menu ====");
            System.out.println("1. Add a new product to the system");
            System.out.println("2. Delete a product from the system");
            System.out.println("3. Print the list of products in the system");
            System.out.println("4. Save the list of products to a file");
            System.out.println("5. Load the list of products from a file");
            System.out.println("6. Load the GUI");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNewProduct(shoppingManager, scanner);
                    break;
                case 2:
                    deleteProduct(shoppingManager, scanner);
                    break;
                case 3:
                    shoppingManager.printProductList();
                    break;
                case 4:
                    saveToFile(shoppingManager);
                    break;
                case 5:
                    loadFromFile(shoppingManager);
                    break;
                case 6:
                    openGui(shoppingManager, userShoppingCart,shoppingManager.getProductList());
                    break;
                case 7:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    private static void addNewProduct(WestminsterShoppingManager shoppingManager, Scanner scanner) {
        System.out.print("Enter product category (E for Electronics / C for Clothing): ");
        String category = scanner.nextLine().toUpperCase();

        // Ensure valid product category
        while (!("E".equals(category) || "C".equals(category))) {
            System.out.print("Invalid product category. Please enter 'E' for Electronics or 'C' for Clothing: ");
            category = scanner.nextLine().toUpperCase();
        }

        System.out.print("Enter product ID: ");
        String productId = scanner.nextLine();

        while (shoppingManager.checkForDuplicateID(productId) == -1) {
            System.out.print("Enter product ID: ");
            productId = scanner.nextLine();
        }

        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();

        System.out.print("Enter number of available items: ");
        int numAvailable = itemNum(scanner);

        System.out.print("Enter price: ");
        double price = priceCheck(scanner);

        if ("E".equals(category)) {
            addElectronics(shoppingManager, productId, "Electronics",productName, numAvailable, price, scanner);
        } else if ("C".equals(category)) {
            addClothing(shoppingManager, productId, "Clothes",productName, numAvailable, price, scanner);
        } else {
            System.out.println("Invalid product category. Please enter either Electronics or Clothing.");
        }
    }
    private static int itemNum(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter the available items: ");
            scanner.next(); // Consume invalid input
        }
        return scanner.nextInt();
    }

    private static double priceCheck(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter the price: ");
            scanner.next(); // Consume invalid input
        }
        return scanner.nextDouble();
    }

    private static void addElectronics(WestminsterShoppingManager shoppingManager, String productId, String productName, String category, int numAvailable, double price, Scanner scanner) {
        System.out.print("Enter brand: ");
        String brand = scanner.next();

        System.out.print("Enter warranty period in months: ");
        int warrantyPeriod = warrantCheck(scanner);

        Electronics electronics = new Electronics(productId, productName, category, numAvailable, price, brand, warrantyPeriod);
        shoppingManager.addProduct(electronics);
    }

    private static int warrantCheck(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter the warranty period in months: ");
            scanner.next(); // Consume invalid input
        }
        return scanner.nextInt();
    }

    private static void addClothing(WestminsterShoppingManager shoppingManager, String productId, String productName, String category, int numAvailable, double price, Scanner scanner) {
        System.out.print("Enter color: ");
        String color = scanner.next(); // Use next() instead of nextLine()

        String size = checkSize(scanner);

        Clothing clothing = new Clothing(productId, productName, category, numAvailable, price, color, size);
        shoppingManager.addProduct(clothing);
    }

    private static String checkSize(Scanner scanner) {
        String size;
        while (true) {
            size = scanner.nextLine().toLowerCase();
            if (size.equals("s") || size.equals("m") || size.equals("l")) {
                break;
            } else {
                System.out.print("Enter size('s', 'm', or 'l'): ");
            }
        }
        return size;
    }


    private static void deleteProduct(WestminsterShoppingManager shoppingManager, Scanner scanner) {
        System.out.print("Enter product ID to delete: ");
        String productId = scanner.nextLine();
        shoppingManager.deleteProduct(productId);
    }

    private static void saveToFile(WestminsterShoppingManager shoppingManager) {
        shoppingManager.saveToFile();
        System.out.println("Data Saved Successfully");    }

    private static void loadFromFile(WestminsterShoppingManager shoppingManager) {
        shoppingManager.loadFromFile();
        System.out.println("Data Loaded Successfully");
    }
    private static void openGui(WestminsterShoppingManager shoppingManager, ShoppingCart userShoppingCart, ArrayList<Product> productsModel) {
        // Create an instance of the MainGui class and pass necessary parameters
        new MainGui(shoppingManager, userShoppingCart, productsModel, currentUser);
    }

}
