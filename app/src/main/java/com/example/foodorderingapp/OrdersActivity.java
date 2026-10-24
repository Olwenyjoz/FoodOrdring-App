package com.example.foodorderingapp;

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

public class OrdersActivity extends AppCompatActivity {

    private LinearLayout ordersSummaryContainer;

    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {

        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(
                getWindow(),
                false
        );

        setContentView(
                R.layout.activity_orders
        );

        // Views
        LinearLayout noOrdersLayout =
                findViewById(
                        R.id.noOrdersLayout
                );

        LinearLayout orderDetailsLayout =
                findViewById(
                        R.id.orderDetailsLayout
                );

        TextView tvOrderCustomer =
                findViewById(
                        R.id.tvOrderCustomer
                );

        TextView tvOrderPhone =
                findViewById(
                        R.id.tvOrderPhone
                );

        TextView tvOrderAddress =
                findViewById(
                        R.id.tvOrderAddress
                );

        TextView tvOrderTotal =
                findViewById(
                        R.id.tvOrderTotal
                );

        Button tvBack =
                findViewById(
                        R.id.tvBack
                );

        ordersSummaryContainer =
                findViewById(
                        R.id.ordersSummaryContainer
                );

        View ordersHeader =
                findViewById(
                        R.id.ordersHeader
                );

        View ordersScrollView =
                findViewById(
                        R.id.ordersScrollView
                );

        // System insets
        ViewCompat.setOnApplyWindowInsetsListener(
                ordersHeader,
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
                ordersScrollView,
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

        // Back navigation
        tvBack.setOnClickListener(
                v -> finish()
        );

        // Get saved order
        OrderHistoryManager historyManager =
                OrderHistoryManager.getInstance(this);

        if (
                !historyManager.hasOrder()
        ) {

            noOrdersLayout.setVisibility(
                    View.VISIBLE
            );

            orderDetailsLayout.setVisibility(
                    View.GONE
            );

            return;
        }

        // Show saved order
        noOrdersLayout.setVisibility(
                View.GONE
        );

        orderDetailsLayout.setVisibility(
                View.VISIBLE
        );

        tvOrderCustomer.setText(
                historyManager.getCustomerName()
        );

        tvOrderPhone.setText(
                historyManager.getPhone()
        );

        tvOrderAddress.setText(
                historyManager.getAddress()
        );

        tvOrderTotal.setText(
                String.format(
                        java.util.Locale.getDefault(),
                        "KSh %.0f",
                        historyManager.getTotal()
                )
        );

        displayOrderSummary(
                historyManager.getItems()
        );
    }

    // Order summary
    private void displayOrderSummary(
            ArrayList<String> items
    ) {

        ordersSummaryContainer.removeAllViews();

        if (
                items == null ||
                        items.isEmpty()
        ) {
            return;
        }

        for (
                String item :
                items
        ) {

            OrderRowData rowData =
                    parseOrderItem(
                            item
                    );

            LinearLayout row =
                    createOrderSummaryRow(
                            rowData
                    );

            ordersSummaryContainer.addView(
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

            ordersSummaryContainer.addView(
                    divider
            );
        }
    }

    // Parse saved item
    private OrderRowData parseOrderItem(
            String item
    ) {

        String itemName =
                item;

        String quantity =
                "1";

        String amount =
                "KSh 0";

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

        // Item
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

    // Convert dp
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