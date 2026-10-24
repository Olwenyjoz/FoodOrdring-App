package com.example.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.adapter.FoodAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity
        extends AppCompatActivity
        implements CartManager.OnCartChangedListener {

    private List<FoodItem> foodItems;

    private FoodAdapter adapter;

    private TextView tvCartBadge;

    private CartManager cartManager;

    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_main
        );

        // Cart manager
        cartManager =
                CartManager.getInstance();

        // Cart badge
        tvCartBadge =
                findViewById(
                        R.id.tvCartBadge
                );

        // Food list
        foodItems =
                new ArrayList<>();

        foodItems.add(
                new FoodItem(
                        "Cheese Burger",
                        "KSh 650",
                        R.drawable.burger
                )
        );

        foodItems.add(
                new FoodItem(
                        "Chicken Pizza",
                        "KSh 850",
                        R.drawable.chickenpizza
                )
        );

        foodItems.add(
                new FoodItem(
                        "Fried Chicken",
                        "KSh 700",
                        R.drawable.friedchicken
                )
        );

        foodItems.add(
                new FoodItem(
                        "Beef Tacos",
                        "KSh 600",
                        R.drawable.beeftacos
                )
        );

        foodItems.add(
                new FoodItem(
                        "Fresh Juice",
                        "KSh 250",
                        R.drawable.fresh_juice
                )
        );

        foodItems.add(
                new FoodItem(
                        "Chocolate Cake",
                        "KSh 450",
                        R.drawable.chocholate_cake
                )
        );

        // Food recycler view
        RecyclerView recyclerViewFood =
                findViewById(
                        R.id.recyclerViewFood
                );

        recyclerViewFood.setLayoutManager(
                new GridLayoutManager(
                        this,
                        2
                )
        );

        adapter =
                new FoodAdapter(
                        new ArrayList<>(
                                foodItems
                        )
                );

        recyclerViewFood.setAdapter(
                adapter
        );

        // Search
        EditText etSearchFood =
                findViewById(
                        R.id.etSearchFood
                );

        etSearchFood.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after
                    ) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count
                    ) {

                        String searchText =
                                s.toString()
                                        .toLowerCase(
                                                Locale.getDefault()
                                        )
                                        .trim();

                        if (
                                searchText.isEmpty()
                        ) {

                            showAllFoods();

                            return;
                        }

                        List<FoodItem> filteredList =
                                new ArrayList<>();

                        for (
                                FoodItem foodItem :
                                foodItems
                        ) {

                            String foodName =
                                    foodItem
                                            .getName()
                                            .toLowerCase(
                                                    Locale.getDefault()
                                            );

                            if (
                                    foodName.contains(
                                            searchText
                                    )
                            ) {

                                filteredList.add(
                                        foodItem
                                );
                            }
                        }

                        adapter.updateList(
                                filteredList
                        );
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s
                    ) {
                    }
                }
        );

        // Burger category
        findViewById(
                R.id.categoryBurger
        ).setOnClickListener(
                v -> filterByKeyword(
                        "burger"
                )
        );

        // Pizza category
        findViewById(
                R.id.categoryPizza
        ).setOnClickListener(
                v -> filterByKeyword(
                        "pizza"
                )
        );

        // Chicken category
        findViewById(
                R.id.categoryChicken
        ).setOnClickListener(
                v -> filterByKeyword(
                        "chicken"
                )
        );

        // Tacos category
        findViewById(
                R.id.categoryTacos
        ).setOnClickListener(
                v -> filterByKeyword(
                        "tacos"
                )
        );

        // See all
        findViewById(
                R.id.tvSeeAll
        ).setOnClickListener(
                v -> {

                    etSearchFood.setText("");

                    showAllFoods();
                }
        );

        // Home navigation
        findViewById(
                R.id.navHome
        ).setOnClickListener(
                v -> {

                    etSearchFood.setText("");

                    showAllFoods();
                }
        );

        // Cart navigation
        findViewById(
                R.id.navCart
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    MainActivity.this,
                                    CartActivity.class
                            );

                    startActivity(intent);
                }
        );

        // Orders navigation
        findViewById(
                R.id.navOrders
        ).setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    MainActivity.this,
                                    OrdersActivity.class
                            );

                    startActivity(intent);
                }
        );

        // Profile navigation
        findViewById(
                R.id.navProfile
        ).setOnClickListener(
                v -> {

                    // Profile screen will be implemented later.
                }
        );

        // Initial badge
        updateCartBadge();
    }

    // Register cart listener
    @Override
    protected void onStart() {

        super.onStart();

        cartManager.addCartChangedListener(
                this
        );

        updateCartBadge();
    }

    // Remove cart listener
    @Override
    protected void onStop() {

        cartManager.removeCartChangedListener(
                this
        );

        super.onStop();
    }

    // Cart changed
    @Override
    public void onCartChanged() {

        runOnUiThread(
                this::updateCartBadge
        );
    }

    // Filter foods
    private void filterByKeyword(
            String keyword
    ) {

        List<FoodItem> filteredList =
                new ArrayList<>();

        for (
                FoodItem foodItem :
                foodItems
        ) {

            String foodName =
                    foodItem
                            .getName()
                            .toLowerCase(
                                    Locale.getDefault()
                            );

            if (
                    foodName.contains(
                            keyword.toLowerCase(
                                    Locale.getDefault()
                            )
                    )
            ) {

                filteredList.add(
                        foodItem
                );
            }
        }

        adapter.updateList(
                filteredList
        );
    }

    // Show all foods
    private void showAllFoods() {

        adapter.updateList(
                new ArrayList<>(
                        foodItems
                )
        );
    }

    // Update cart badge
    private void updateCartBadge() {

        int itemCount =
                cartManager.getItemCount();

        if (itemCount > 0) {

            tvCartBadge.setVisibility(
                    View.VISIBLE
            );

            tvCartBadge.setText(
                    String.valueOf(
                            itemCount
                    )
            );

        } else {

            tvCartBadge.setVisibility(
                    View.GONE
            );
        }
    }

    // Refresh badge
    @Override
    protected void onResume() {

        super.onResume();

        updateCartBadge();
    }
}