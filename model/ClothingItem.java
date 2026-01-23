package model;

public class ClothingItem implements Discountable {
    private int itemId;
    private String name;
    private String size;
    private double price;
    private String brand;

    public ClothingItem(int itemId, String name, String size, double price, String brand) {
        setItemId(itemId);
        setName(name);
        setSize(size);
        setPrice(price);
        setBrand(brand);
    }

    public ClothingItem(String name, String size, double price, String brand) {
        this(0, name, size, price, brand);
    }

    public ClothingItem() {
        this.itemId = 0;
        this.name = "Unknown Item";
        this.size = "M";
        this.price = 0.0;
        this.brand = "No brand";
    }

    public int getItemId() { return itemId; }
    public String getFormattedPrice() { return String.format("%.2f KZT", price); }
    public String getName() { return name; }
    public String getSize() { return size; }
    public double getPrice() { return price; }
    public String getBrand() { return brand; }

    public void setItemId(int itemId) {
        if (itemId < 0) {
            throw new IllegalArgumentException("Item ID must be positive: " + itemId);
        }
        this.itemId = itemId;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty: " + name);
        }
        this.name = name;
    }

    public void setSize(String size) {
        if (size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("Size cannot be empty: " + size);
        }
        this.size = size;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative: " + price);
        }
        this.price = price;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be empty: " + brand);
        }
        this.brand = brand;
    }

    @Override
    public void applyDiscount(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Discount must be between 0 and 100: " + percentage);
        }
        this.price = this.price * (1 - percentage / 100.0);
    }

    @Override
    public boolean isPremium() {
        return this.price > 5000.0;
    }

    @Override
    public String toString() {
        return "ClothingItem{itemId=" + itemId + ", name='" + name + "', size='" + size +
                "', price=" + price + ", brand='" + brand + "'}";
    }
}
