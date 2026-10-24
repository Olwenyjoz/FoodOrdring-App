package com.example.foodorderingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class OrderConfirmationActivity extends AppCompatActivity {

    private LinearLayout confirmationOrderSummaryContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(
                getWindow(),
                false
        );

        setContentView(
                R.layout.activity_order_confirmation
        );

        // Views
        TextView tvCustomer =
                findViewById(
                        R.id.tvConfirmationCustomer
                );

        TextView tvPhone =
                findViewById(
                        R.id.tvConfirmationPhone
                );

        TextView tvAddress =
                findViewById(
                        R.id.tvConfirmationAddress
                );

        TextView tvTotal =
                findViewById(
                        R.id.tvConfirmationTotal
                );

        TextView tvTotalItems =
                findViewById(
                        R.id.tvConfirmationTotalItems
                );

        confirmationOrderSummaryContainer =
                findViewById(
                        R.id.confirmationOrderSummaryContainer
                );

        Button btnBackHome =
                findViewById(
                        R.id.btnBackHome
                );

        View confirmationHeader =
                findViewById(
                        R.id.confirmationHeader
                );

        View confirmationScrollView =
                findViewById(
                        R.id.confirmationScrollView
                );

        // System insets
        ViewCompat.setOnApplyWindowInsetsListener(
                confirmationHeader,
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
                confirmationScrollView,
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

        // Read order data
        String customer =
                getIntent().getStringExtra(
                        "customer_name"
                );

        String phone =
                getIntent().getStringExtra(
                        "customer_phone"
                );

        String address =
                getIntent().getStringExtra(
                        "customer_address"
                );

        ArrayList<String> items =
                getIntent().getStringArrayListExtra(
                        "order_items"
                );

        double total =
                getIntent().getDoubleExtra(
                        "order_total",
                        0
                );

        int totalItems =
                getIntent().getIntExtra(
                        "order_total_items",
                        0
                );

        // Display customer details
        tvCustomer.setText(
                customer
        );

        tvPhone.setText(
                phone
        );

        tvAddress.setText(
                address
        );

        // Display order summary
        displayOrderSummary(
                items
        );

        // Display totals
        tvTotalItems.setText(
                String.valueOf(
                        totalItems
                )
        );

        tvTotal.setText(
                String.format(
                        Locale.getDefault(),
                        "KSh %.0f",
                        total
                )
        );

        // Back home
        btnBackHome.setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    OrderConfirmationActivity.this,
                                    MainActivity.class
                            );

                    intent.addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_SINGLE_TOP
                    );

                    startActivity(
                            intent
                    );

                    finish();
                }
        );
    }

    // Order summary
    private void displayOrderSummary(
            ArrayList<String> items
    ) {

        confirmationOrderSummaryContainer
                .removeAllViews();

        if (items == null) {
            return;
        }

        for (String item : items) {

            OrderRowData rowData =
                    parseOrderItem(
                            item
                    );

            LinearLayout row =
                    createOrderSummaryRow(
                            rowData
                    );

            confirmationOrderSummaryContainer
                    .addView(
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

            confirmationOrderSummaryContainer
                    .addView(
                            divider
                    );
        }
    }

    // Parse saved order
    private OrderRowData parseOrderItem(
            String item
    ) {

        String itemName = item;
        String quantity = "1";
        String amount = "KSh 0";

        int quantitySeparator =
                item.lastIndexOf(
                        " x "
                );

        int amountSeparator =
                item.lastIndexOf(
                        "     KSh "
                );

        if (
                quantitySeparator > 0 &&
                        amountSeparator > quantitySeparator
        ) {

            itemName =
                    item.substring(
                            0,
                            quantitySeparator
                    ).trim();

            quantity =
                    item.substring(
                            quantitySeparator + 3,
                            amountSeparator
                    ).trim();

            String numericAmount =
                    item.substring(
                            amountSeparator + 9
                    ).trim();

            amount =
                    "KSh "
                            + numericAmount;
        }

        return new OrderRowData(
                itemName,
                quantity,
                amount
        );
    }

    // Summary row
    private LinearLayout createOrderSummaryRow(
            OrderRowData data
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

        tvItemName.setLayoutParams(
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f
                )
        );

        tvItemName.setText(
                data.itemName
        );

        tvItemName.setTextColor(
                Color.parseColor(
                        "#29243A"
                )
        );

        tvItemName.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                12
        );

        tvItemName.setGravity(
                Gravity.CENTER_VERTICAL
        );

        // Quantity
        TextView tvQuantity =
                new TextView(this);

        tvQuantity.setLayoutParams(
                new LinearLayout.LayoutParams(
                        dpToPx(60),
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );

        tvQuantity.setText(
                data.quantity
        );

        tvQuantity.setTextColor(
                Color.parseColor(
                        "#29243A"
                )
        );

        tvQuantity.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                12
        );

        tvQuantity.setGravity(
                Gravity.CENTER
        );

        // Amount
        TextView tvAmount =
                new TextView(this);

        tvAmount.setLayoutParams(
                new LinearLayout.LayoutParams(
                        dpToPx(95),
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );

        tvAmount.setText(
                data.amount
        );

        tvAmount.setTextColor(
                Color.parseColor(
                        "#6844B5"
                )
        );

        tvAmount.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                12
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

    // Order row data
    private static class OrderRowData {

        String itemName;
        String quantity;
        String amount;

        OrderRowData(
                String itemName,
                String quantity,
                String amount
        ) {

            this.itemName =
                    itemName;

            this.quantity =
                    quantity;

            this.amount =
                    amount;
        }
    }
}