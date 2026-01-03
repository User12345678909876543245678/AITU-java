public class VIPCustomer extends Customer {
    private String vipLevel; // Additional field

    public VIPCustomer(int customerId, String name, int age, String email, String preferredSize, int points, String vipLevel) {
        super(customerId, name, age, email, preferredSize, points); // Call parent constructor
        this.vipLevel = vipLevel;
    }

    public String getVipLevel() { return vipLevel; }
    public void setVipLevel(String vipLevel) { this.vipLevel = vipLevel; }

    @Override
    public double getDiscount() {return 10.0 + (points / 100.0);}
    @Override
    public boolean isVIP() {return true;}

    public String getExtraPerk() {return vipLevel.equals("Gold") ? "Free shipping" : "Priority support";}
    public void upgradeLevel() {
        if (vipLevel.equals("Silver")) vipLevel = "Gold";
        System.out.println("Upgraded to " + vipLevel + " for " + name);
    }

    @Override
    public String toString() {
        return "[VIPCustomer] " + name + " (ID: " + customerId + ", Age: " + age + ", Email: " + email +
               ", Preferred Size: " + preferredSize + ", Points: " + points + ", VIP Level: " + vipLevel + ")";
    }
}