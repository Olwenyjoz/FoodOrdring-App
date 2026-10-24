package com.example.foodorderingapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.CartManager;
import com.example.foodorderingapp.FoodItem;
import com.example.foodorderingapp.OrderActivity;
import com.example.foodorderingapp.R;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodItem> foodList;

    public FoodAdapter(List<FoodItem> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull FoodViewHolder holder,
            int position
    ) {
        FoodItem food = foodList.get(position);

        holder.ivFoodImage.setImageResource(
                food.getImageResource()
        );

        holder.tvFoodName.setText(
                food.getName()
        );

        holder.tvFoodPrice.setText(
                food.getPrice()
        );

        // Order Now button
        holder.btnFoodOrder.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    OrderActivity.class
            );

            intent.putExtra(
                    "food_name",
                    food.getName()
            );

            intent.putExtra(
                    "food_price",
                    food.getPrice()
            );

            intent.putExtra(
                    "food_image",
                    food.getImageResource()
            );

            v.getContext().startActivity(intent);
        });

        // Add to Cart button
        holder.btnAddToCart.setOnClickListener(v -> {

            CartManager
                    .getInstance()
                    .addItem(
                            food,
                            1
                    );

            Toast.makeText(
                    v.getContext(),
                    food.getName() + " added to your cart.",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void updateList(List<FoodItem> newList) {

        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(
                        new FoodDiffCallback(
                                foodList,
                                newList
                        )
                );

        foodList = new ArrayList<>(newList);

        diffResult.dispatchUpdatesTo(this);
    }

    public static class FoodViewHolder
            extends RecyclerView.ViewHolder {

        ImageView ivFoodImage;
        TextView tvFoodName;
        TextView tvFoodPrice;
        Button btnFoodOrder;
        Button btnAddToCart;

        public FoodViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            ivFoodImage =
                    itemView.findViewById(
                            R.id.ivFoodImage
                    );

            tvFoodName =
                    itemView.findViewById(
                            R.id.tvFoodName
                    );

            tvFoodPrice =
                    itemView.findViewById(
                            R.id.tvFoodPrice
                    );

            btnFoodOrder =
                    itemView.findViewById(
                            R.id.btnFoodOrder
                    );

            btnAddToCart =
                    itemView.findViewById(
                            R.id.btnAddToCart
                    );
        }
    }

    private static class FoodDiffCallback
            extends DiffUtil.Callback {

        private final List<FoodItem> oldList;
        private final List<FoodItem> newList;

        FoodDiffCallback(
                List<FoodItem> oldList,
                List<FoodItem> newList
        ) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(
                int oldItemPosition,
                int newItemPosition
        ) {
            return oldList
                    .get(oldItemPosition)
                    .getName()
                    .equals(
                            newList
                                    .get(newItemPosition)
                                    .getName()
                    );
        }

        @Override
        public boolean areContentsTheSame(
                int oldItemPosition,
                int newItemPosition
        ) {
            FoodItem oldItem =
                    oldList.get(oldItemPosition);

            FoodItem newItem =
                    newList.get(newItemPosition);

            return oldItem.getName()
                    .equals(newItem.getName())
                    && oldItem.getPrice()
                    .equals(newItem.getPrice())
                    && oldItem.getImageResource()
                    == newItem.getImageResource();
        }
    }
}