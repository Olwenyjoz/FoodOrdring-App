package com.example.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    private TextView tvQuantity;
    private TextView tvOrderTotal;

    private FoodItem foodItem;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);

        ImageView ivOrderFood =
                findViewById(R.id.ivOrderFood);

        TextView tvOrderFoodName =
                findViewById(R.id.tvOrderFoodName);

        TextView tvOrderFoodPrice =
                findViewById(R.id.tvOrderFoodPrice);

        tvQuantity =
                findViewById(R.id.tvQuantity);

        tvOrderTotal =
                findViewById(R.id.tvOrderTotal);

        Button btnIncrease =
                findViewById(R.id.btnIncrease);

        Button btnDecrease =
                findViewById(R.id.btnDecrease);

        Button btnAddToCart =
                findViewById(R.id.btnAddToCart);

        Button btnPlaceOrderNow =
                findViewById(R.id.btnPlaceOrderNow);

        Button tvBack =
                findViewById(R.id.tvBack);

        String foodName =
                getIntent().getStringExtra("food_name");

        String foodPrice =
                getIntent().getStringExtra("food_price");

        int foodImage =
                getIntent().getIntExtra(
                        "food_image",
                        R.drawable.background
                );

        foodItem =
                new FoodItem(
                        foodName != null
                                ? foodName
                                : getString(
                                R.string.chicken_pizza
                        ),
                        foodPrice != null
                                ? foodPrice
                                : getString(
                                R.string.chicken_pizza_price
                        ),
                        foodImage
                );

        ivOrderFood.setImageResource(
                foodItem.getImageResource()
        );

        tvOrderFoodName.setText(
                foodItem.getName()
        );

        tvOrderFoodPrice.setText(
                foodItem.getPrice()
        );

        updateQuantityDisplay();

        tvBack.setOnClickListener(
                v -> finish()
        );

        btnIncrease.setOnClickListener(
                v -> {
                    quantity++;
                    updateQuantityDisplay();
                }
        );

        btnDecrease.setOnClickListener(
                v -> {
                    if (quantity > 1) {
                        quantity--;
                        updateQuantityDisplay();
                    }
                }
        );

        btnAddToCart.setOnClickListener(
                v -> {

                    CartManager
                            .getInstance()
                            .addItem(
                                    foodItem,
                                    quantity
                            );

                    Toast.makeText(
                            OrderActivity.this,
                            getString(
                                    R.string.added_to_cart,
                                    foodItem.getName()
                            ),
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        btnPlaceOrderNow.setOnClickListener(
                v -> {

                    CartManager
                            .getInstance()
                            .addItem(
                                    foodItem,
                                    quantity
                            );

                    Intent intent =
                            new Intent(
                                    OrderActivity.this,
                                    CheckoutActivity.class
                            );

                    startActivity(intent);
                }
        );
    }

    private void updateQuantityDisplay() {

        tvQuantity.setText(
                String.valueOf(quantity)
        );

        double total =
                foodItem.getNumericPrice()
                        * quantity;

        tvOrderTotal.setText(
                String.format(
                        Locale.getDefault(),
                        "KSh %.0f",
                        total
                )
        );
    }
}