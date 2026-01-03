public class Customer {
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

    //GETTERS
    public String getEmail() {return email;}
    public int getCustomerId() {return customerId;}
    public String getName() {return name;}
    public String getPreferredSize() {return preferredSize;}
    public int getPoints() {return points;}
    public int getAge(){return age;}

    //SETTERS
    public void setPoints(int points) {
        if (points >=0) {this.points = points;}
        else {System.out.println("WARNING: points cannot be negative!");
            this.points = 0;
        }
    }
    public void setEmail(String email) {
    if (email != null && email.contains("@")) {this.email = email;}
    else {
        System.out.println("Warning: Email must contain @ symbol!");
        this.email = "unknown@mail.com";
        }
    }
    public void setPreferredSize(String preferredSize) {
        if (preferredSize != null ) {this.preferredSize = preferredSize;}
        else {System.out.println("WARNING: perferred size cannot be empty!");
            this.preferredSize = "Empty";
        }
    }
    public void setName(String name) {
        if (name != null) {this.name = name;}
        else {System.out.println("WARNING: name cannot be empty");
            this.name = "empty";
        }
    }
    public void setCustomerId(int customerId) {
        if (customerId > 0){this.customerId = customerId;}
        else {System.out.println("WARNING: customerId cannot be negative!");
            this.customerId = 0;
        }
    }
    public void setAge(int age) {
        if (age >= 0) {this.age = age;}
        else {System.out.println("WARNING: age cannot be zero or negative!");
        this.age = 0;}
    }

    //METHODS
    public void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }
    public boolean isVIP() {
        return this.points > 1000;
    }
    public double getDiscount() {
        return 0.0; // Base: no discount
    }
    @Override
    public String toString() {
        return "[Customer] " + name + " (ID: " + customerId + ", Age: " + age + ", Email: " + email +
               ", Preferred Size: " + preferredSize + ", Points: " + points + ")";
    }
}