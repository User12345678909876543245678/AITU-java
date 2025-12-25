public class Customer {
    private int customerId;
    private String name;
    private String preferredSize;
    private int points;

    public Customer(int customerId, String name, String preferredSize, int points) {
        this.customerId = customerId;
        this.name = name;
        this.preferredSize = preferredSize;
        this.points = points;
    }

    public Customer() {
        this.customerId = 0;
        this.name = "Unknown Customer";
        this.preferredSize = "M";
        this.points = 0;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreferredSize() {
        return preferredSize;
    }

    public void setPreferredSize(String preferredSize) {
        this.preferredSize = preferredSize;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }

    public boolean isVIP() {
        return this.points > 1000;
    }

    @Override
    public String toString() {
        return "Customer{customerId=" + customerId + ", name='" + name + "', preferredSize='" + preferredSize + "', points=" + points + "}";
    }
}