package menu;

import exception.InvalidCustomerException;
import model.ClothingItem;
import model.Customer;
import model.Order;
import model.RegularCustomer;
import model.VIPCustomer;
import database.ClothingItemDAO;


import java.util.ArrayList;
import java.util.Scanner;

public class MenuManager implements Menu {

    private final Scanner scanner = new Scanner(System.in);
    private final ClothingItemDAO itemDAO = new ClothingItemDAO();
    private final ArrayList<Order> orders = new ArrayList<>();
    private final ArrayList<Customer> customers = new ArrayList<>(); // Polymorphic list

    public MenuManager() {

        if (itemDAO.getAll().isEmpty()) {
            itemDAO.insert(new ClothingItem(1, "T-Shirt", "L", 3500.0, "Nike"));
            itemDAO.insert(new ClothingItem(1, "Jeans", "M", 5000.0, "Levi's"));
            itemDAO.insert(new ClothingItem(1, "Jacket", "L", 4500.0, "Adidas"));
        }



        // Customer is ABSTRACT -> so we ONLY add child classes
        customers.add(new RegularCustomer(3001, "Alice", 24, "alice@mail.com", "S", 50, "2023-01-01"));
        customers.add(new VIPCustomer(3002, "Bob", 29, "bobsuper@mail.com", "XL", 150, "Gold"));

        orders.add(new Order(2001, "John", 8000.0, "Pending"));
        orders.add(new Order(2002, "Peter Parker", 0.0, "Pending"));
    }

    @Override
    public void displayMenu() {
        System.out.println("\n========================================");
        System.out.println("  CLOTHING STORE MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add Clothing Item");
        System.out.println("2. View All Clothing Items");
        System.out.println("3. Add Regular Customer");
        System.out.println("4. Add VIP Customer");
        System.out.println("5. View All Customers (Polymorphic)");
        System.out.println("6. Add Order");
        System.out.println("7. View All Orders");
        System.out.println("8. Demonstrate Polymorphism");
        System.out.println("9. View VIP Customers Only");
        System.out.println("0. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
    }

    @Override
    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1: addClothingItem(); break;
                    case 2: viewAllClothingItems(); break;
                    case 3: addRegularCustomer(); break;
                    case 4: addVIPCustomer(); break;
                    case 5: viewAllCustomers(); break;
                    case 6: addOrder(); break;
                    case 7: viewAllOrders(); break;
                    case 8: demonstratePolymorphism(); break;
                    case 9: viewVIPCustomersOnly(); break;
                    case 0:
                        System.out.println("\nGoodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("\nInvalid choice!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number input - " + e.getMessage());
            } catch (IllegalArgumentException | InvalidCustomerException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private void addClothingItem() {
        System.out.println("\n--- ADD CLOTHING ITEM ---");
        try {
            System.out.print("Enter item ID: ");
            int itemId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter size: ");
            String size = scanner.nextLine().trim();

            System.out.print("Enter price (KZT): ");
            double price = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter brand: ");
            String brand = scanner.nextLine().trim();

            ClothingItem item = new ClothingItem(itemId, name, size, price, brand);
            itemDAO.insert(item);

            System.out.println("\n✅ Clothing item added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format - " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllClothingItems() {
        ArrayList<ClothingItem> items = itemDAO.getAll();

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
            System.out.println("   Premium: " + (item.isPremium() ? "✅ Yes" : "❌ No"));
            System.out.println();
        }
    }

    private void addRegularCustomer() throws InvalidCustomerException {
        System.out.println("\n--- ADD REGULAR CUSTOMER ---");
        try {
            System.out.print("Enter customer ID: ");
            int customerId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter preferred size: ");
            String preferredSize = scanner.nextLine().trim();

            System.out.print("Enter points: ");
            int points = Integer.parseInt(scanner.nextLine().trim());

            if (points > 10000) {
                throw new InvalidCustomerException("Points too high for regular customer");
            }

            System.out.print("Enter join date (YYYY-MM-DD): ");
            String joinDate = scanner.nextLine().trim();

            Customer customer = new RegularCustomer(customerId, name, age, email, preferredSize, points, joinDate);
            customers.add(customer);

            System.out.println("\n✅ Regular Customer added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format - " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addVIPCustomer() {
        System.out.println("\n--- ADD VIP CUSTOMER ---");
        try {
            System.out.print("Enter customer ID: ");
            int customerId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter preferred size: ");
            String preferredSize = scanner.nextLine().trim();

            System.out.print("Enter points: ");
            int points = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter VIP level (Gold/Silver): ");
            String vipLevel = scanner.nextLine().trim();

            Customer customer = new VIPCustomer(customerId, name, age, email, preferredSize, points, vipLevel);
            customers.add(customer);

            System.out.println("\n✅ VIP Customer added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format - " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllCustomers() {
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

            System.out.println((i + 1) + ". " + customer);
            System.out.println("   Type: " + customer.getCustomerType());
            System.out.println("   Discount: " + customer.getDiscount() + "%");

            if (customer.isVIP()) {
                System.out.println("   VIP: ✅ Yes");
            }
            System.out.println();
        }
    }

    private void addOrder() {
        System.out.println("\n--- ADD ORDER ---");
        try {
            System.out.print("Enter order ID: ");
            int orderId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter customer name: ");
            String customerName = scanner.nextLine().trim();

            System.out.print("Enter total (KZT): ");
            double total = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter status (Pending/Completed/Cancelled): ");
            String status = scanner.nextLine().trim();

            Order order = new Order(orderId, customerName, total, status);
            orders.add(order);

            System.out.println("\n✅ Order added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format - " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllOrders() {
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

    private void demonstratePolymorphism() {
        System.out.println("\n========================================");
        System.out.println("   POLYMORPHISM DEMONSTRATION");
        System.out.println("========================================");
        System.out.println("Calling getDiscount() on all customers:");

        for (Customer c : customers) {
            System.out.println(c.getName() + " (" + c.getCustomerType() + "): " + c.getDiscount() + "%");
        }

        System.out.println("\nNotice: Same method, different discounts based on type!");
    }

    private void viewVIPCustomersOnly() {
        System.out.println("\n========================================");
        System.out.println("         VIP CUSTOMERS ONLY");
        System.out.println("========================================");

        int vipCount = 0;

        for (Customer c : customers) {
            if (c instanceof VIPCustomer vip) {
                vipCount++;
                System.out.println(vip.getName());

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
