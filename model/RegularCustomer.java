package model;

public class RegularCustomer extends Customer {
    private String joinDate;

    public RegularCustomer(int customerId, String name, int age, String email, String preferredSize, int points, String joinDate) {
        super(customerId, name, age, email, preferredSize, points);
        setJoinDate(joinDate); // use setter validation
    }

    public String getJoinDate() { return joinDate; }

    public void setJoinDate(String joinDate) {
        if (joinDate == null || joinDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Join date cannot be empty: " + joinDate);
        }
        // Minimal format check for YYYY-MM-DD (достаточно для задания Week 6)
        String d = joinDate.trim();
        if (!d.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Join date must be in format YYYY-MM-DD: " + joinDate);
        }
        this.joinDate = d;
    }

    @Override
    public double getDiscount() {
        return points / 200.0;
    }

    @Override
    public boolean isVIP() { return false; }

    @Override
    public String getCustomerType() { return "Regular"; }

    public boolean isLongTerm() { return joinDate.startsWith("202"); }

    public void renewMembership() { System.out.println("Membership renewed for " + name); }

    @Override
    public String toString() {
        return "[RegularCustomer] " + name + " (ID: " + customerId + ", Age: " + age + ", Email: " + email +
                ", Preferred Size: " + preferredSize + ", Points: " + points + ", Join Date: " + joinDate + ")";
    }
}
