package com.example.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.adapter.CartAdapter;

import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private CartAdapter cartAdapter;

    private TextView tvCartTotal;
    private TextView tvTotalItems;

    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_cart
        );

        RecyclerView recyclerViewCart =
                findViewById(
                        R.id.recyclerViewCart
                );

        tvCartTotal =
                findViewById(
                        R.id.tvCartTotal
                );

        tvTotalItems =
                findViewById(
                        R.id.tvTotalItems
                );

        Button btnClearCart =
                findViewById(
                        R.id.btnClearCart
                );

        Button btnPlaceOrder =
                findViewById(
                        R.id.btnPlaceOrder
                );

        TextView tvBack =
                findViewById(
                        R.id.tvBack
                );

        recyclerViewCart.setLayoutManager(
                new LinearLayoutManager(this)
        );

        cartAdapter =
                new CartAdapter(
                        CartManager
                                .getInstance()
                                .getItems(),
                        this::updateCartSummary
                );

        recyclerViewCart.setAdapter(
                cartAdapter
        );

        updateCartSummary();

        // Back
        tvBack.setOnClickListener(
                v -> finish()
        );

        // Clear Cart
        btnClearCart.setOnClickListener(
                v -> {

                    CartManager
                            .getInstance()
                            .clear();

                    cartAdapter.notifyDataSetChanged();

                    updateCartSummary();

                    Toast.makeText(
                            CartActivity.this,
                            "Cart cleared.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );

        // Place Order
        btnPlaceOrder.setOnClickListener(
                v -> {

                    if (
                            CartManager
                                    .getInstance()
                                    .isEmpty()
                    ) {

                        Toast.makeText(
                                CartActivity.this,
                                "Your cart is empty.",
                                Toast.LENGTH_SHORT
                        ).show();

                        return;
                    }

                    Intent intent =
                            new Intent(
                                    CartActivity.this,
                                    CheckoutActivity.class
                            );

                    startActivity(intent);
                }
        );
    }

    private void updateCartSummary() {

        CartManager cartManager =
                CartManager.getInstance();

        int totalItems =
                cartManager.getItemCount();

        double total =
                cartManager.getGrandTotal();

        tvTotalItems.setText(
                String.valueOf(
                        totalItems
                )
        );

        tvCartTotal.setText(
                String.format(
                        Locale.getDefault(),
                        "KSh %.0f",
                        total
                )
        );
    }
}