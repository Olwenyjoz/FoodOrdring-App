package com.example.foodorderingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etCustomerName;
    private EditText etPhone;
    private EditText etAddress;

    private LinearLayout orderSummaryContainer;

    private TextView tvTotalItems;
    private TextView tvGrandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(
                getWindow(),
                false
        );

        setContentView(
                R.layout.activity_checkout
        );

        // Views
        etCustomerName =
                findViewById(
                        R.id.etCustomerName
                );

        etPhone =
                findViewById(
                        R.id.etPhone
                );

        etAddress =
                findViewById(
                        R.id.etAddress
                );

        orderSummaryContainer =
                findViewById(
                        R.id.orderSummaryContainer
                );

        tvTotalItems =
                findViewById(
                        R.id.tvCheckoutTotalItems
                );

        tvGrandTotal =
                findViewById(
                        R.id.tvCheckoutGrandTotal
                );

        Button btnConfirmOrder =
                findViewById(
                        R.id.btnConfirmOrder
                );

        Button tvBack =
                findViewById(
                        R.id.tvBack
                );

        View checkoutHeader =
                findViewById(
                        R.id.checkoutHeader
                );

        View checkoutScrollView =
                findViewById(
                        R.id.checkoutScrollView
                );

        // System insets
        ViewCompat.setOnApplyWindowInsetsListener(
                checkoutHeader,
                (view, windowInsets) -> {

                    Insets systemBars =
                            windowInsets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    view.setPadding(
                            view.getPaddingLeft(),
                            systemBars.top,
                            view.getPaddingRight(),
                            view.getPaddingBottom()
                    );

                    return windowInsets;
                }
        );

        ViewCompat.setOnApplyWindowInsetsListener(
                checkoutScrollView,
                (view, windowInsets) -> {

                    Insets systemBars =
                            windowInsets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    view.setPadding(
                            view.getPaddingLeft(),
                            view.getPaddingTop(),
                            view.getPaddingRight(),
                            systemBars.bottom
                    );

                    return windowInsets;
                }
        );

        // Order summary
        displayOrderSummary();

        // Navigation
        tvBack.setOnClickListener(
                v -> finish()
        );

        // Confirm order
        btnConfirmOrder.setOnClickListener(
                v -> confirmOrder()
        );
    }

    // Order summary
    private void displayOrderSummary() {

        CartManager cartManager =
                CartManager.getInstance();

        orderSummaryContainer.removeAllViews();

        for (
                CartItem item :
                cartManager.getItems()
        ) {

            LinearLayout row =
                    createOrderSummaryRow(
                            item
                    );

            orderSummaryContainer.addView(
                    row
            );

            View divider =
                    new View(this);

            divider.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            dpToPx(1)
                    )
            );

            divider.setBackgroundColor(
                    Color.parseColor(
                            "#E2DDEE"
                    )
            );

            divider.setImportantForAccessibility(
                    View.IMPORTANT_FOR_ACCESSIBILITY_NO
            );

            orderSummaryContainer.addView(
                    divider
            );
        }

        tvTotalItems.setText(
                String.valueOf(
                        cartManager.getItemCount()
                )
        );

        tvGrandTotal.setText(
                String.format(
                        Locale.getDefault(),
                        "KSh %.0f",
                        cartManager.getGrandTotal()
                )
        );
    }

    // Summary row
    private LinearLayout createOrderSummaryRow(
            CartItem item
    ) {

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
                LinearLayout.HORIZONTAL
        );

        row.setGravity(
                Gravity.CENTER_VERTICAL
        );

        row.setPadding(
                0,
                dpToPx(10),
                0,
                dpToPx(10)
        );

        row.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );

        // Item name
        TextView tvItemName =
                new TextView(this);

        LinearLayout.LayoutParams nameParams =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f
                );

        tvItemName.setLayoutParams(
                nameParams
        );

        tvItemName.setText(
                item.getFoodItem().getName()
        );

        tvItemName.setTextColor(
                Color.parseColor(
                        "#29243A"
                )
        );

        tvItemName.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                13
        );

        tvItemName.setGravity(
                Gravity.CENTER_VERTICAL
        );

        // Quantity
        TextView tvQuantity =
                new TextView(this);

        LinearLayout.LayoutParams quantityParams =
                new LinearLayout.LayoutParams(
                        dpToPx(60),
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        tvQuantity.setLayoutParams(
                quantityParams
        );

        tvQuantity.setText(
                String.valueOf(
                        item.getQuantity()
                )
        );

        tvQuantity.setTextColor(
                Color.parseColor(
                        "#29243A"
                )
        );

        tvQuantity.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                13
        );

        tvQuantity.setGravity(
                Gravity.CENTER
        );

        // Amount
        TextView tvAmount =
                new TextView(this);

        LinearLayout.LayoutParams amountParams =
                new LinearLayout.LayoutParams(
                        dpToPx(95),
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        tvAmount.setLayoutParams(
                amountParams
        );

        tvAmount.setText(
                String.format(
                        Locale.getDefault(),
                        "KSh %.0f",
                        item.getTotalPrice()
                )
        );

        tvAmount.setTextColor(
                Color.parseColor(
                        "#6844B5"
                )
        );

        tvAmount.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                13
        );

        tvAmount.setGravity(
                Gravity.END
        );

        // Add columns
        row.addView(
                tvItemName
        );

        row.addView(
                tvQuantity
        );

        row.addView(
                tvAmount
        );

        return row;
    }

    // Convert dp to pixels
    private int dpToPx(
            int dp
    ) {

        return Math.round(
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dp,
                        getResources()
                                .getDisplayMetrics()
                )
        );
    }

    // Confirm order
    private void confirmOrder() {

        String customerName =
                etCustomerName
                        .getText()
                        .toString()
                        .trim();

        String phone =
                etPhone
                        .getText()
                        .toString()
                        .trim();

        String address =
                etAddress
                        .getText()
                        .toString()
                        .trim();

        if (customerName.isEmpty()) {

            etCustomerName.setError(
                    getString(
                            R.string.enter_name_error
                    )
            );

            etCustomerName.requestFocus();

            return;
        }

        if (phone.isEmpty()) {

            etPhone.setError(
                    getString(
                            R.string.enter_phone_error
                    )
            );

            etPhone.requestFocus();

            return;
        }

        if (address.isEmpty()) {

            etAddress.setError(
                    getString(
                            R.string.enter_address_error
                    )
            );

            etAddress.requestFocus();

            return;
        }

        CartManager cartManager =
                CartManager.getInstance();

        if (cartManager.isEmpty()) {
            return;
        }

        ArrayList<String> orderItems =
                new ArrayList<>();

        for (
                CartItem item :
                cartManager.getItems()
        ) {

            String itemText =
                    item.getFoodItem().getName()
                            + " x "
                            + item.getQuantity()
                            + "     KSh "
                            + String.format(
                            Locale.getDefault(),
                            "%.0f",
                            item.getTotalPrice()
                    );

            orderItems.add(
                    itemText
            );
        }

        double total =
                cartManager.getGrandTotal();

        int totalItems =
                cartManager.getItemCount();

        OrderHistoryManager
                .getInstance(this)
                .saveOrder(
                        customerName,
                        phone,
                        address,
                        orderItems,
                        total
                );

        Intent intent =
                new Intent(
                        CheckoutActivity.this,
                        OrderConfirmationActivity.class
                );

        intent.putExtra(
                "customer_name",
                customerName
        );

        intent.putExtra(
                "customer_phone",
                phone
        );

        intent.putExtra(
                "customer_address",
                address
        );

        intent.putStringArrayListExtra(
                "order_items",
                orderItems
        );

        intent.putExtra(
                "order_total",
                total
        );

        intent.putExtra(
                "order_total_items",
                totalItems
        );

        cartManager.clear();

        startActivity(
                intent
        );

        finish();
    }
}