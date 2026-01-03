import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Order> orders = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>(); // Polymorphic list
    private static ArrayList<ClothingItem> items = new ArrayList<>();

    public static void main(String[] args) {
        items.add(new ClothingItem(1001, "T-Shirt", "L", 3500.0, "Nike"));
        items.add(new ClothingItem(1002, "Jeans", "M", 5000.0, "Levi's"));
        items.add(new ClothingItem(1003, "Jacket", "L", 4500.0, "Adidas"));

        // Polymorphic addition: mix types
        customers.add(new Customer(3000, "General User", 30, "general@mail.com", "M", 0)); // Parent instance
        customers.add(new RegularCustomer(3001, "Alice", 24, "alice@mail.com", "S", 50, "2023-01-01"));
        customers.add(new VIPCustomer(3002, "Bob", 29, "bobsuper@mail.com", "XL", 150, "Gold"));

        orders.add(new Order(2001, "John", 8000.0, "Pending"));
        orders.add(new Order(2002, "Peter Parker", 0.0, "Pending"));

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addClothingItem();
                    break;
                case 2:
                    viewAllClothingItems();
                    break;
                case 3:
                    addCustomer(); // Add parent type
                    break;
                case 4:
                    viewAllCustomers(); // Polymorphic view
                    break;
                case 5:
                    addOrder();
                    break;
                case 6:
                    viewAllOrders();
                    break;
                case 7:
                    addRegularCustomer();
                    break;
                case 8:
                    addVIPCustomer();
                    break;
                case 9:
                    demonstratePolymorphism();
                    break;
                case 10:
                    viewVIPCustomersOnly();
                    break;
                case 0:
                    System.out.println("\nGoodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice!");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n========================================");
        System.out.println("  CLOTHING STORE MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add Clothing Item");//
        System.out.println("2. View All Clothing Items");//
        System.out.println("3. Add Customer");//
        System.out.println("4. View All Customers (Polymorphic)");//
        System.out.println("5. Add Order");//
        System.out.println("6. View All Orders");//
        System.out.println("7. Add Regular Customer");//
        System.out.println("8. Add VIP Customer");//
        System.out.println("9. Demonstrate Polymorphism");//
        System.out.println("10. View VIP Customers Only");//
        System.out.println("0. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
    }

    // ... (keep addClothingItem, viewAllClothingItems, addOrder, viewAllOrders as is)

    private static void addClothingItem() {
        System.out.println("\n--- ADD CLOTHING ITEM ---");
        System.out.print("Enter item ID: ");
        int itemId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter size: ");
        String size = scanner.nextLine();
        System.out.print("Enter price (KZT): ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter brand: ");
        String brand = scanner.nextLine();
        ClothingItem item = new ClothingItem(itemId, name, size, price, brand);
        items.add(item);
        System.out.println("\n✅ Clothing item added successfully!");
    }
    private static void addCustomer() {
        System.out.println("\n--- ADD CUSTOMER ---");
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter preferred size: ");
        String preferredSize = scanner.nextLine();
        System.out.print("Enter points: ");
        int points = scanner.nextInt();
        scanner.nextLine();
        Customer customer = new Customer(customerId, name, age, email, preferredSize, points);
        customers.add(customer);
        System.out.println("\n✅ Customer added successfully!");
    }

    private static void addRegularCustomer() {
        System.out.println("\n--- ADD REGULAR CUSTOMER ---");
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter preferred size: ");
        String preferredSize = scanner.nextLine();
        System.out.print("Enter points: ");
        int points = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter join date (YYYY-MM-DD): ");
        String joinDate = scanner.nextLine();
        Customer customer = new RegularCustomer(customerId, name, age, email, preferredSize, points, joinDate);
        customers.add(customer);
        System.out.println("\n✅ Regular Customer added successfully!");
    }
    private static void viewAllClothingItems() {
        System.out.println("\n========================================");
        System.out.println("         ALL CLOTHING ITEMS");
        System.out.println("========================================");
        if (items.isEmpty()) {
            System.out.println("No clothing items found.");
            return;
        }
        System.out.println("Total items: " + items.size());
        System.out.println();
        for (int i = 0; i < items.size(); i++) {
            ClothingItem item = items.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " - " + item.getFormattedPrice());
            System.out.println("   Size: " + item.getSize());
            System.out.println("   Brand: " + item.getBrand());
            System.out.println("   Available: ✅ Yes");  // Assuming always available; adjust if needed
            if (item.isPremium()) {
                System.out.println("   💎 Premium Item");
            }
            System.out.println();
        }
    }

    private static void addOrder() {
        System.out.println("\n--- ADD ORDER ---");
        System.out.print("Enter item ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        System.out.print("Enter total (KZT): ");
        double total = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter status (Pending/Completed/Cancelled): ");
        String status = scanner.nextLine();

        Order order = new Order(orderId, customerName, total, status);
        orders.add(order);

        System.out.println("\n✅ Order added successfully!");
    }

    private static void viewAllOrders() {
        System.out.println("\n========================================");
        System.out.println("         ALL ORDERS");
        System.out.println("========================================");

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("Total orders: " + orders.size());
        System.out.println();

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);

            System.out.println((i + 1) + ". Order ID: " + order.getOrderId() + " - " + order.getFormattedTotal());
            System.out.println("   Customer: " + order.getCustomerName());
            System.out.println("   Status: " + order.getStatus());

            System.out.println();
        }
    }
    private static void addVIPCustomer() {
        System.out.println("\n--- ADD VIP CUSTOMER ---");
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter preferred size: ");
        String preferredSize = scanner.nextLine();
        System.out.print("Enter points: ");
        int points = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter VIP level (Gold/Silver): ");
        String vipLevel = scanner.nextLine();
        Customer customer = new VIPCustomer(customerId, name, age, email, preferredSize, points, vipLevel);
        customers.add(customer);
        System.out.println("\n✅ VIP Customer added successfully!");
    }

    private static void viewAllCustomers() {
        System.out.println("\n========================================");
        System.out.println("         ALL CUSTOMERS (POLYMORPHIC)");
        System.out.println("========================================");

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        System.out.println("Total customers: " + customers.size());
        System.out.println();

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.println((i + 1) + ". " + customer); // Overridden toString
            System.out.println("   Discount: " + customer.getDiscount() + "%"); // Polymorphic call
            if (customer.isVIP()) {
                System.out.println("   🌟 VIP Customer");
            }
            System.out.println();
        }
    }
    
    private static void demonstratePolymorphism() {
        System.out.println("\n========================================");
        System.out.println("   POLYMORPHISM DEMONSTRATION");
        System.out.println("========================================");
        System.out.println("Calling getDiscount() on all customers:");

        for (Customer c : customers) {
            System.out.println(c.getName() + ": " + c.getDiscount() + "%"); // Different behavior
        }

        System.out.println("\nNotice: Same method, different discounts based on type!");
    }

    private static void viewVIPCustomersOnly() {
        System.out.println("\n========================================");
        System.out.println("         VIP CUSTOMERS ONLY");
        System.out.println("========================================");

        int vipCount = 0;
        for (Customer c : customers) {
            if (c instanceof VIPCustomer) {
                vipCount++;
                VIPCustomer vip = (VIPCustomer) c; // Downcast
                System.out.println(vipCount + ". " + vip.getName() + " (ID: " + vip.getCustomerId() + ")");
                System.out.println("   VIP Level: " + vip.getVipLevel());
                System.out.println("   Points: " + vip.getPoints());
                System.out.println("   Extra Perk: " + vip.getExtraPerk());
                System.out.println();
            }
        }

        if (vipCount == 0) {
            System.out.println("No VIP customers found.");
        }
    }
}