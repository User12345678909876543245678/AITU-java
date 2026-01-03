public class RegularCustomer extends Customer {
    private String joinDate; // Additional field

    public RegularCustomer(int customerId, String name, int age, String email, String preferredSize, int points, String joinDate) {
        super(customerId, name, age, email, preferredSize, points); // Call parent constructor
        this.joinDate = joinDate;
    }

    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }

    // Override 2 methods
    @Override
    public double getDiscount() {
        return points / 200.0; // Different: small discount based on points
    }

    @Override
    public boolean isVIP() {return false;}

    public boolean isLongTerm() {return joinDate.startsWith("202");}
    public void renewMembership() {System.out.println("Membership renewed for " + name);}

    @Override
    public String toString() {
        return "[RegularCustomer] " + name + " (ID: " + customerId + ", Age: " + age + ", Email: " + email +
               ", Preferred Size: " + preferredSize + ", Points: " + points + ", Join Date: " + joinDate + ")";
    }
}