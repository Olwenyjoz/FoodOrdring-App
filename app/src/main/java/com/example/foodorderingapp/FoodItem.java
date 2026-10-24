package com.example.foodorderingapp;

public class FoodItem {

    private final String name;
    private final String price;
    private final int imageResource;

    public FoodItem(
            String name,
            String price,
            int imageResource
    ) {
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResource() {
        return imageResource;
    }

    public double getNumericPrice() {

        String numericPrice =
                price.replaceAll("[^0-9.]", "");

        try {
            return Double.parseDouble(numericPrice);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}