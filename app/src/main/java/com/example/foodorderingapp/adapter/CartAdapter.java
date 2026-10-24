package com.example.foodorderingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.CartItem;
import com.example.foodorderingapp.CartManager;
import com.example.foodorderingapp.R;

import java.util.List;
import java.util.Locale;

public class CartAdapter
        extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<CartItem> cartItems;
    private final OnCartChangedListener listener;

    public interface OnCartChangedListener {
        void onCartChanged();
    }

    public CartAdapter(
            List<CartItem> cartItems,
            OnCartChangedListener listener
    ) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view =
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(
                        R.layout.item_cart,
                        parent,
                        false
                );

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull CartViewHolder holder,
            int position
    ) {

        CartItem cartItem =
                cartItems.get(position);

        holder.ivCartFood.setImageResource(
                cartItem
                        .getFoodItem()
                        .getImageResource()
        );

        holder.tvCartFoodName.setText(
                cartItem
                        .getFoodItem()
                        .getName()
        );

        holder.tvCartFoodPrice.setText(
                cartItem
                        .getFoodItem()
                        .getPrice()
        );

        holder.tvCartQuantity.setText(
                String.valueOf(
                        cartItem.getQuantity()
                )
        );

        holder.tvCartItemTotal.setText(
                String.format(
                        Locale.getDefault(),
                        "KSh %.0f",
                        cartItem.getTotalPrice()
                )
        );

        holder.btnCartIncrease.setOnClickListener(
                v -> {

                    cartItem.increase();

                    holder.tvCartQuantity.setText(
                            String.valueOf(
                                    cartItem.getQuantity()
                            )
                    );

                    holder.tvCartItemTotal.setText(
                            String.format(
                                    Locale.getDefault(),
                                    "KSh %.0f",
                                    cartItem.getTotalPrice()
                            )
                    );

                    listener.onCartChanged();
                }
        );

        holder.btnCartDecrease.setOnClickListener(
                v -> {

                    cartItem.decrease();

                    holder.tvCartQuantity.setText(
                            String.valueOf(
                                    cartItem.getQuantity()
                            )
                    );

                    holder.tvCartItemTotal.setText(
                            String.format(
                                    Locale.getDefault(),
                                    "KSh %.0f",
                                    cartItem.getTotalPrice()
                            )
                    );

                    listener.onCartChanged();
                }
        );

        holder.btnRemoveCartItem.setOnClickListener(
                v -> {

                    CartManager
                            .getInstance()
                            .removeItem(
                                    cartItem
                            );

                    int currentPosition =
                            holder.getBindingAdapterPosition();

                    if (
                            currentPosition
                                    != RecyclerView.NO_POSITION
                    ) {

                        notifyItemRemoved(
                                currentPosition
                        );
                    }

                    listener.onCartChanged();
                }
        );
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder
            extends RecyclerView.ViewHolder {

        ImageView ivCartFood;

        TextView
                tvCartFoodName,
                tvCartFoodPrice,
                tvCartQuantity,
                tvCartItemTotal;

        Button
                btnCartIncrease,
                btnCartDecrease;

        ImageButton btnRemoveCartItem;

        public CartViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);

            ivCartFood =
                    itemView.findViewById(
                            R.id.ivCartFood
                    );

            tvCartFoodName =
                    itemView.findViewById(
                            R.id.tvCartFoodName
                    );

            tvCartFoodPrice =
                    itemView.findViewById(
                            R.id.tvCartFoodPrice
                    );

            tvCartQuantity =
                    itemView.findViewById(
                            R.id.tvCartQuantity
                    );

            tvCartItemTotal =
                    itemView.findViewById(
                            R.id.tvCartItemTotal
                    );

            btnCartIncrease =
                    itemView.findViewById(
                            R.id.btnCartIncrease
                    );

            btnCartDecrease =
                    itemView.findViewById(
                            R.id.btnCartDecrease
                    );

            btnRemoveCartItem =
                    itemView.findViewById(
                            R.id.btnRemoveCartItem
                    );
        }
    }
}