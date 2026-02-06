package menu;

import exception.InvalidCustomerException;
import model.*;
import database.*;
import java.util.Scanner;
import java.util.List;

public class MenuManager implements Menu {
    private Scanner scanner;
    private CustomerDAO customerDAO;

    public MenuManager() {
        this.scanner = new Scanner(System.in);
        this.customerDAO = new CustomerDAO();
    }

    @Override
    public void displayMenu() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║          MAIN MENU - Week 8           ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("┌─ CUSTOMER MANAGEMENT ──────────────────┐");
        System.out.println("│ 1. Add RegularCustomer                 │");
        System.out.println("│ 2. Add VIPCustomer                     │");
        System.out.println("│ 3. View All Customer                   │");
        System.out.println("│ 4. View RegularCustomer Only           │");
        System.out.println("│ 5. View VIPCustomer Only               │");
        System.out.println("│ 6. Update Customer                     │");
        System.out.println("│ 7. Delete Customer                     │");
        System.out.println("├─ SEARCH & FILTER ──────────────────────┤");
        System.out.println("│ 8. Search by Name                      │");
        System.out.println("│ 9. Search by size Range                │");
        System.out.println("│10. Size Customer (size >= X)           │");
        System.out.println("├─ DEMO & OTHER ─────────────────────────┤");
        System.out.println("│11. Polymorphism Demo                   │");
        System.out.println("│ 0. Exit                                │");
        System.out.println("└────────────────────────────────────────┘");
    }
    @Override
    public void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        addRegularCustomer();
                        break;
                    case 2:
                        addVIPCustomer();
                        break;
                    case 3:
                        viewAllCustomer();
                        break;
                    case 4:
                        viewRegularCustomerOnly();
                        break;
                    case 5:
                        viewVIPCustomersOnly();
                        break;
                    case 6:
                        updateCustomer();
                        break;
                    case 7:
                        deleteCustomer();
                        break;
                    case 8:
                        SearchbyName();
                        break;
                    case 9:
                        SearchBySizeRange();
                        break;
                    case 10:
                        SearchByMinSize();
                        break;
                    case 11:
                        demonstratePolymorphism();
                        break;
                    case 0:
                        running = false;
                        System.out.println("\n╔════════════════════════════════════════╗");
                        System.out.println("║  Thank you for using our system!      ║");
                        System.out.println("║  Goodbye! 👋                          ║");
                        System.out.println("╚════════════════════════════════════════╝");
                        break;
                    default:
                        System.out.println("\nInvalid choice!");
                }

            } catch (java.util.InputMismatchException e) {
                System.out.println("❌ Error: Please enter a valid number!");
                pressEnterToContinue();
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
                pressEnterToContinue();
            }
        }
        scanner.close();
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

            RegularCustomer regularCustomer = new RegularCustomer(customerId, name, age, email, preferredSize, points, joinDate);
            customerDAO.insertRegularCustomer(regularCustomer);

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

            VIPCustomer vip = new VIPCustomer(customerId, name, age, email, preferredSize, points, vipLevel);
            customerDAO.insertVIPCustomer(vip);


            System.out.println("\n✅ VIP Customer added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format - " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void viewAllCustomer() {

        customerDAO.displayAllCustomer();
    }
    private void viewRegularCustomerOnly() {
        List<RegularCustomer> regularCustomers = customerDAO.getAllRegularCustomers();

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         Regular ONLY                    ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (regularCustomers.isEmpty()) {
            System.out.println("📭 No regulars in database.");
        } else {
            for (int i = 0; i < regularCustomers.size(); i++) {
                RegularCustomer regular = regularCustomers.get(i);
                System.out.println((i + 1) + ". " + regular.toString());
                System.out.println("   Join Date: " + regular.getJoinDate());
                if (regular.isLongTerm()) {
                    System.out.println("   ⭐ MASTER Regalar (7+ years)");
                }
                System.out.println();
            }
            System.out.println("Total Regulars: " + regularCustomers.size());
        }
    }
    private void viewVIPCustomersOnly() {
        List<VIPCustomer> VIPCustomers = customerDAO.getAllVIPCustomers();

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         VIP ONLY                    ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (VIPCustomers.isEmpty()) {
            System.out.println("📭 No VIP customers in database.");
        } else {
            for (int i = 0; i < VIPCustomers.size(); i++) {
                VIPCustomer VIP = VIPCustomers.get(i);
                System.out.println((i + 1) + ". " + VIP.toString());
                System.out.println("   VIP Level: " + VIP.getVipLevel());
                if (VIP.isVIP()) {
                    System.out.println("This customer is VIP");
                }
                System.out.println();
            }
            System.out.println("Total Regulars: " + VIPCustomers.size());
        }
    }
    private void updateCustomer() {
        System.out.println("\n┌─ UPDATE Customer ─────────────────────────┐");
        System.out.print("│ Enter Customer ID to update: ");

        try {
            int customerid = Integer.parseInt(scanner.nextLine().trim());

            Customer existingCustomer = customerDAO.getCustomerById(customerid);

            if (existingCustomer == null) {
                System.out.println("❌ No Customer found with ID: " + customerid);
                return;
            }

            System.out.println("│ Current Info:");
            System.out.println("│ " + existingCustomer.toString());
            System.out.println("└────────────────────────────────────────┘");

            System.out.println("\n┌─ ENTER NEW VALUES ─────────────────────┐");
            System.out.println("│ (Press Enter to keep current value)   │");

            System.out.print("│ New Name [" + existingCustomer.getName() + "]: ");
            String newName = scanner.nextLine();
            if (newName.trim().isEmpty()) {
                newName = existingCustomer.getName();
            }

            System.out.print("│ New Age [" + existingCustomer.getAge() + "]: ");
            String AgeInput = scanner.nextLine();
            int newAge = AgeInput.trim().isEmpty() ?
                    existingCustomer.getAge() : Integer.parseInt(AgeInput);

            System.out.print("│ New Email [" + existingCustomer.getEmail() + "]: ");
            String newEmail = scanner.nextLine();
            if (newEmail.trim().isEmpty()){
                newEmail = existingCustomer.getEmail();
                }

            System.out.print("│ New Preferred Size [" + existingCustomer.getPreferredSize() + "]: ");
            String newPSize = scanner.nextLine();
            if (newPSize.trim().isEmpty()){
                newPSize = existingCustomer.getPreferredSize();
            }

            System.out.print("│ New Points [" + existingCustomer.getPoints() + "]: ");
            String PointsInput = scanner.nextLine();
            int newPoints = PointsInput.trim().isEmpty() ?
                    existingCustomer.getPoints() : Integer.parseInt(PointsInput);

            if (existingCustomer instanceof RegularCustomer) {
                RegularCustomer regularCustomer = (RegularCustomer) existingCustomer;
                System.out.print("│ New Join Date [" + regularCustomer.getJoinDate() + "]: ");
                String newJoin = scanner.nextLine();
                if (newJoin.trim().isEmpty()) {
                    newJoin = regularCustomer.getJoinDate();
                }

                RegularCustomer updatedRegularCustomer = new RegularCustomer(customerid, newName, newAge, newEmail,newPSize, newPoints, newJoin);
                customerDAO.updateRegularCustomer(updatedRegularCustomer);

            } else if (existingCustomer instanceof VIPCustomer) {
                VIPCustomer VIP = (VIPCustomer) existingCustomer;
                System.out.print("│ New VIP level [" + VIP.getVipLevel() + "]: ");
                String newVIPLevel = scanner.nextLine();
                if (newVIPLevel.trim().isEmpty()) {
                    newVIPLevel = VIP.getVipLevel();
                }
                VIPCustomer updatedVIPCustomer = new VIPCustomer(customerid, newName, newAge, newEmail, newPSize, newPoints, newVIPLevel);
                customerDAO.updateVIPCustomer(updatedVIPCustomer);
            }
            System.out.println("└────────────────────────────────────────┘");
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Invalid number format!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Validation Error: " + e.getMessage());
        }
    }
    private void deleteCustomer() {
        System.out.println("\n┌─ DELETE CUSTOMER ─────────────────────────┐");
        System.out.print("│ Enter Customer ID to delete: ");

        try {
            int customerId = scanner.nextInt();
            scanner.nextLine();

            Customer customer = customerDAO.getCustomerById(customerId);

            if (customer == null) {
                System.out.println("❌ No customer found with ID: " + customerId);
                return;
            }

            System.out.println("│ Customer to delete:");
            System.out.println("│ " + customer.toString());
            System.out.println("└────────────────────────────────────────┘");

            System.out.print("⚠️  Are you sure? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                customerDAO.deleteCustomer(customerId);
            } else {
                System.out.println("❌ Deletion cancelled.");
            }

        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Error: Invalid input!");
        }
    }
    private void SearchbyName() {
        System.out.println("\n┌─ SEARCH BY NAME ───────────────────────┐");
        System.out.print("│ Enter name to search: ");
        String name = scanner.nextLine();
        System.out.println("└────────────────────────────────────────┘");

        List<Customer> results = customerDAO.SearchbyName(name);

        displaySearchResults(results, "Search: '" + name + "'");
    }
    private void SearchBySizeRange() {
        try {
            System.out.println("\n┌─ SEARCH BY Size RANGE ───────────────┐");
            System.out.print("│ Enter minimum size: ");
            String minSize = scanner.nextLine();
            System.out.print("│ Enter maximum size: ");
            String maxSize = scanner.nextLine();
            System.out.println("└────────────────────────────────────────┘");
            List<Customer> results = customerDAO.SearchBySizeRange(minSize, maxSize);
            displaySearchResults(results, "Size between: " + minSize + " - " + maxSize);
        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Error: Invalid size!");
            scanner.nextLine();
        }
    }
    private void SearchByMinSize() {
        try {
            System.out.println("\n┌─  Customer's size ──────────────────────┐");
            System.out.print("│ Enter Customer's size: ");
            String Csize = scanner.nextLine();
            System.out.println("└────────────────────────────────────────┘");
            List<Customer> results = customerDAO.SearchByMinSize(Csize);
            displaySearchResults(results, "Customer's size: " + Csize);

        } catch (java.util.InputMismatchException e) {
            System.out.println("❌ Error: Invalid size!");
            scanner.nextLine();
        }
    }
    private void displaySearchResults(List<Customer> results, String criteria) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         SEARCH RESULTS                ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Criteria: " + criteria);
        System.out.println("─────────────────────────────────────────");

        if (results.isEmpty()) {
            System.out.println("📭 No customer found matching criteria.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                Customer s = results.get(i);
                System.out.print((i + 1) + ". ");
                System.out.print("[" + s.getCustomerType() + "] ");
                System.out.println(s.toString());
            }
            System.out.println("─────────────────────────────────────────");
            System.out.println("Total Results: " + results.size());
        }
    }
    private void demonstratePolymorphism() {
        customerDAO.demonstratePolymorphism();
    }
    private void pressEnterToContinue() {
        System.out.println("\n[Press Enter to continue...]");
        scanner.nextLine();
    }

}
