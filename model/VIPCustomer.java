package model;

public class VIPCustomer extends Customer {
    private String vipLevel;

    public VIPCustomer(int customerId, String name, int age, String email, String preferredSize, int points, String vipLevel) {
        super(customerId, name, age, email, preferredSize, points);
        setVipLevel(vipLevel); // use setter validation
    }

    public String getVipLevel() { return vipLevel; }

    public void setVipLevel(String vipLevel) {
        if (vipLevel == null || vipLevel.trim().isEmpty()) {
            throw new IllegalArgumentException("VIP level cannot be empty: " + vipLevel);
        }
        String v = vipLevel.trim();
        if (!v.equals("Gold") && !v.equals("Silver")) {
            throw new IllegalArgumentException("VIP level must be Gold or Silver: " + vipLevel);
        }
        this.vipLevel = v;
    }

    @Override
    public double getDiscount() { return 10.0 + (points / 100.0); }

    @Override
    public boolean isVIP() { return true; }

    @Override
    public String getCustomerType() { return "VIP"; }

    public String getExtraPerk() {
        return "Gold".equals(vipLevel) ? "Free shipping" : "Priority support";
    }

    public void upgradeLevel() {
        if ("Silver".equals(vipLevel)) {
            vipLevel = "Gold";
        }
        System.out.println("Upgraded to " + vipLevel + " for " + name);
    }

    @Override
    public String toString() {
        return "[VIPCustomer] " + name + " (ID: " + customerId + ", Age: " + age + ", Email: " + email +
                ", Preferred Size: " + preferredSize + ", Points: " + points + ", VIP Level: " + vipLevel + ")";
    }
}
