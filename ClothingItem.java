public class ClothingItem {
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

    public ClothingItem() {
        this.itemId = 0;
        this.name = "Unknown Item";
        this.size = "M";
        this.price = 0.0;
        this.brand = "No brand";
    }
// getters
    public int getItemId() {return itemId;}
    public String getFormattedPrice() {return String.format("%.2f KZT", price);}
    public String getName() {return name;}
    public String getSize() {return size;}
    public double getPrice() {return price;}
    public String getBrand() {return brand;}


// Setters

    public void setItemId(int itemId) {
        if (itemId >=1) {this.itemId = itemId;}
        else{System.out.println("Warning: itemId cannot be negative!");
            this.itemId = 0;
        }
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()){this.name = name;}
        else{ System.out.println("Warning: Name cannot be empty!");
            this.name = "None";
        }
    }

    public void setSize(String size) {
        if (size != null && !size.trim().isEmpty()) { this.size = size;}
        else {
            System.out.println("Warning: size cannot be empty!");
            this.size = "Unknown";
        }
    }

    public void setPrice(double price) {
        if (price >= 0) {this.price = price;}
        else {System.out.println("Warning: price cannot be negotive!");
            this.price = 0;
        }
    }

    public void setBrand(String brand) {
        if (brand != null && !brand.trim().isEmpty()){this.brand = brand;}
        else {System.out.println("Warning: brand is null!");
            this.brand = "No Brand";
        }
    }

    public void applyDiscount(double percentage) {
        if (percentage >= 0 && percentage<=100){
        this.price = this.price * (1 - percentage / 100);}
        else{System.out.println("Invalid discount!");}
    }

    public boolean isPremium() {
        return this.price > 5000.0;
    }

    @Override
    public String toString() {
        return "ClothingItem{itemId=" + itemId + ", name='" + name + "', size='" + size + "', price=" + price + ", brand='" + brand + "'}";
    }
}