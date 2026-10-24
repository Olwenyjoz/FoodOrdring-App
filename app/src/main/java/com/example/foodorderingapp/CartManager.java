package com.example.foodorderingapp;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static CartManager instance;

    private final List<CartItem> cartItems =
            new ArrayList<>();

    private final List<OnCartChangedListener> listeners =
            new ArrayList<>();

    private CartManager() {
    }

    public static synchronized CartManager getInstance() {

        if (instance == null) {
            instance = new CartManager();
        }

        return instance;
    }

    // Cart listener
    public interface OnCartChangedListener {
        void onCartChanged();
    }

    // Register listener
    public void addCartChangedListener(
            OnCartChangedListener listener
    ) {

        if (
                listener != null &&
                        !listeners.contains(listener)
        ) {
            listeners.add(listener);
        }
    }

    // Remove listener
    public void removeCartChangedListener(
            OnCartChangedListener listener
    ) {

        listeners.remove(listener);
    }

    // Notify listeners
    private void notifyCartChanged() {

        for (
                OnCartChangedListener listener :
                new ArrayList<>(listeners)
        ) {

            listener.onCartChanged();
        }
    }

    // Add item
    public void addItem(
            FoodItem foodItem,
            int quantity
    ) {

        for (CartItem item : cartItems) {

            if (
                    item.getFoodItem()
                            .getName()
                            .equals(
                                    foodItem.getName()
                            )
            ) {

                item.setQuantity(
                        item.getQuantity()
                                + quantity
                );

                notifyCartChanged();

                return;
            }
        }

        cartItems.add(
                new CartItem(
                        foodItem,
                        quantity
                )
        );

        notifyCartChanged();
    }

    // Get cart items
    public List<CartItem> getItems() {

        return cartItems;
    }

    // Get total quantity
    public int getItemCount() {

        int count = 0;

        for (CartItem item : cartItems) {

            count += item.getQuantity();
        }

        return count;
    }

    // Get grand total
    public double getGrandTotal() {

        double total = 0;

        for (CartItem item : cartItems) {

            total += item.getTotalPrice();
        }

        return total;
    }

    // Remove item
    public void removeItem(
            CartItem item
    ) {

        cartItems.remove(item);

        notifyCartChanged();
    }

    // Clear cart
    public void clear() {

        cartItems.clear();

        notifyCartChanged();
    }

    // Check empty
    public boolean isEmpty() {

        return cartItems.isEmpty();
    }
}