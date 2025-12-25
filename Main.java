public class Main {
    public static void main(String[] args) {
        System.out.println("Clothing Store Management System");

        // Create ClothingItem objects
        ClothingItem item1 = new ClothingItem(1001, "T-Shirt", "L", 3500.0, "Nike");
        ClothingItem item2 = new ClothingItem(1002, "Jeans", "M", 5000.0, "Levi's");
        ClothingItem item3 = new ClothingItem(); // Default 
        
        // Create Order objects
        Order order1 = new Order(2001, "John Doe", 8000.0, "Pending");
        Order order2 = new Order(); // Default 

        // Create Customer objects
        Customer customer1 = new Customer(3001, "Alice Johnson", "S", 50);
        Customer customer2 = new Customer(3002, "Bob Williams", "XL", 150);

        System.out.println("items");
        System.out.println(item1);
        System.out.println(item2);
        System.out.println(item3);

        System.out.println("orders");
        System.out.println(order1);
        System.out.println(order2);

        System.out.println("customers");
        System.out.println(customer1);
        System.out.println(customer2);

        System.out.println("testing getters");
        System.out.println("Item 1 name: " + item1.getName());
        System.out.println("Item 1 price: " + item1.getPrice() + " KZT");
        System.out.println("Order 1 status: " + order1.getStatus());
        System.out.println("Customer 1 points: " + customer1.getPoints());
        System.out.println("Item 2 brand: " + item2.getBrand());
        System.out.println("Customer 2 preferred size: " + customer2.getPreferredSize());

        System.out.println("testing setters");
        System.out.println("Updating item3");
        item3.setItemId(1003);
        item3.setName("Jacket");
        item3.setSize("L");
        item3.setPrice(4500.0);
        item3.setBrand("Adidas");
        System.out.println("Updated: " + item3);

        System.out.println("Changing order2 customer");
        order2.setCustomerName("Peter Parker");
        order2.setTotal(0.0);
        order2.setStatus("Pending");
        order2.setOrderId(2002);
        System.out.println("Updated: " + order2);

        System.out.println("testing clothing item methods");
        System.out.println(item1.getName() + " is premium: " + item1.isPremium());
        System.out.println("Applying 10% discount to " + item1.getName());
        item1.applyDiscount(10);
        System.out.println("New price: " + item1.getPrice() + " KZT");

        System.out.println("testing order methods");
        System.out.println("Order " + order1.getOrderId() + " status: " + order1.getStatus());
        order1.complete();
        System.out.println("Order " + order1.getOrderId() + " status after complete: " + order1.getStatus());
        order2.cancel();
        System.out.println("Order " + order2.getOrderId() + " status after cancel: " + order2.getStatus());

        System.out.println("testing customers methods");
        System.out.println(customer1.getName() + " is VIP: " + customer1.isVIP());
        System.out.println(customer2.getName() + " is VIP: " + customer2.isVIP());
        System.out.println("Adding 60 points to " + customer1.getName());
        customer1.addPoints(60);
        System.out.println(customer1.getName() + " points: " + customer1.getPoints());
        System.out.println(customer1.getName() + " is VIP: " + customer1.isVIP());

        System.out.println("Overall");
        System.out.println("Clothing Items:");
        System.out.println(item1);
        System.out.println(item2);
        System.out.println(item3);

        System.out.println("Orders:");
        System.out.println(order1);
        System.out.println(order2);

        System.out.println("Customers:");
        System.out.println(customer1);
        System.out.println(customer2);

        System.out.println("Program Complete");
    }
}