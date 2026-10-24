package com.example.foodorderingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryManager {

    private static final String TAG =
            "OrderHistoryManager";

    private static final String PREFS_NAME =
            "foodie_order_history";

    private static final String KEY_HAS_ORDER =
            "has_order";

    private static final String KEY_CUSTOMER_NAME =
            "customer_name";

    private static final String KEY_PHONE =
            "phone";

    private static final String KEY_ADDRESS =
            "address";

    private static final String KEY_ITEMS =
            "items";

    private static final String KEY_TOTAL =
            "total";

    private static OrderHistoryManager instance;

    private final SharedPreferences preferences;

    private OrderHistoryManager(Context context) {

        preferences =
                context.getApplicationContext()
                        .getSharedPreferences(
                                PREFS_NAME,
                                Context.MODE_PRIVATE
                        );
    }

    public static synchronized
    OrderHistoryManager getInstance(
            Context context
    ) {

        if (instance == null) {

            instance =
                    new OrderHistoryManager(
                            context
                    );
        }

        return instance;
    }

    public void saveOrder(
            String customerName,
            String phone,
            String address,
            List<String> items,
            double total
    ) {

        JSONArray jsonItems =
                new JSONArray();

        for (String item : items) {

            jsonItems.put(item);
        }

        preferences.edit()
                .putBoolean(
                        KEY_HAS_ORDER,
                        true
                )
                .putString(
                        KEY_CUSTOMER_NAME,
                        customerName
                )
                .putString(
                        KEY_PHONE,
                        phone
                )
                .putString(
                        KEY_ADDRESS,
                        address
                )
                .putString(
                        KEY_ITEMS,
                        jsonItems.toString()
                )
                .putFloat(
                        KEY_TOTAL,
                        (float) total
                )
                .apply();
    }

    public boolean hasOrder() {

        return preferences.getBoolean(
                KEY_HAS_ORDER,
                false
        );
    }

    public String getCustomerName() {

        return preferences.getString(
                KEY_CUSTOMER_NAME,
                ""
        );
    }

    public String getPhone() {

        return preferences.getString(
                KEY_PHONE,
                ""
        );
    }

    public String getAddress() {

        return preferences.getString(
                KEY_ADDRESS,
                ""
        );
    }

    public ArrayList<String> getItems() {

        ArrayList<String> items =
                new ArrayList<>();

        String savedItems =
                preferences.getString(
                        KEY_ITEMS,
                        "[]"
                );

        try {

            JSONArray jsonItems =
                    new JSONArray(
                            savedItems
                    );

            for (
                    int i = 0;
                    i < jsonItems.length();
                    i++
            ) {

                items.add(
                        jsonItems.getString(i)
                );
            }

        } catch (JSONException e) {

            Log.e(
                    TAG,
                    "Unable to read saved order items",
                    e
            );
        }

        return items;
    }

    public double getTotal() {

        return preferences.getFloat(
                KEY_TOTAL,
                0f
        );
    }
}