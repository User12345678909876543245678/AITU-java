package model;

public interface Discountable {
    void applyDiscount(double percentage);
    boolean isPremium();
}
