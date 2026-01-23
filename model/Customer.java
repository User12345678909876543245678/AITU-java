package model;

public abstract class Customer {
    protected int customerId;
    protected String name;
    protected int age;
    protected String email;
    protected String preferredSize;
    protected int points;

    public Customer(int customerId, String name, int age, String email, String preferredSize, int points) {
        setCustomerId(customerId);
        setName(name);
        setAge(age);
        setEmail(email);
        setPreferredSize(preferredSize);
        setPoints(points);
    }

    public Customer() {
        this.customerId = 0;
        this.name = "Unknown";
        this.age = 0;
        this.email = "unknown@mail.com";
        this.preferredSize = "M";
        this.points = 0;
    }

    public String getEmail() { return email; }
    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPreferredSize() { return preferredSize; }
    public int getPoints() { return points; }
    public int getAge() { return age; }

    public void setPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points cannot be negative: " + points);
        }
        this.points = points;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain '@' symbol: " + email);
        }
        this.email = email;
    }

    public void setPreferredSize(String preferredSize) {
        if (preferredSize == null || preferredSize.trim().isEmpty()) {
            throw new IllegalArgumentException("Preferred size cannot be empty: " + preferredSize);
        }
        this.preferredSize = preferredSize;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty: " + name);
        }
        this.name = name;
    }

    public void setCustomerId(int customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive: " + customerId);
        }
        this.customerId = customerId;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        this.age = age;
    }
    // Methods
    public void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }

    public boolean isVIP() {
        return this.points > 1000;
    }

    public double getDiscount() {
        return 0.0; 
    }

    // New abstract method
    public abstract String getCustomerType();

    @Override
    public String toString() {
        return "[Customer] " + name + " (ID: " + customerId + ", Age: " + age + ", Email: " + email +
               ", Preferred Size: " + preferredSize + ", Points: " + points + ")";
    }
}