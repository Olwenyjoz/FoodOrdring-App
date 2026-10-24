package com.example.foodorderingapp;

public class CartItem {

    private final FoodItem foodItem;
    private int quantity;

    public CartItem(
            FoodItem foodItem,
            int quantity
    ) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(
            int quantity
    ) {
        this.quantity = quantity;
    }

    public void increase() {
        quantity++;
    }

    public void decrease() {

        if (quantity > 1) {
            quantity--;
        }
    }

    public double getUnitPrice() {
        return foodItem.getNumericPrice();
    }

    public double getTotalPrice() {
        return getUnitPrice() * quantity;
    }
}