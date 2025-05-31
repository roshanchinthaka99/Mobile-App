package com.example.citycyclerentals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        private List<Item> itemList;
        private List<Item> fullList; // for search
        private Context context;
        private CartDatabaseHelper dbHelper;



        public ItemAdapter(Context context, List<Item> itemList) {
            this.context = context;
            this.itemList = new ArrayList<>(itemList);
            this.fullList = itemList;
            this.dbHelper = new CartDatabaseHelper(context);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemName, itemLocation, itemHourly, itemDaily, itemMonthly;
            Button addToCartBtn;

            public ViewHolder(View view) {
                super(view);
                itemName = view.findViewById(R.id.item_name);
                itemLocation = view.findViewById(R.id.item_location);
                itemHourly = view.findViewById(R.id.item_hourly);
                itemDaily = view.findViewById(R.id.item_daily);
                itemMonthly = view.findViewById(R.id.item_monthly);
                addToCartBtn = view.findViewById(R.id.add_to_cart_button);
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Item item = itemList.get(position);
            holder.itemName.setText(item.getName());
            holder.itemLocation.setText(item.getLocation());
            holder.itemHourly.setText("Hourly: Rs " + item.getHourlyPrice());
            holder.itemDaily.setText("Daily: Rs " + item.getDailyPrice());
            holder.itemMonthly.setText("Monthly: Rs " + item.getMonthlyPrice());

            holder.addToCartBtn.setText(item.isInCart() ? "In Cart" : "Add to Cart");

            holder.addToCartBtn.setOnClickListener(v -> {
                if (!item.isInCart()) {
                    dbHelper.addItemToCart(item);
                } else {
                    dbHelper.removeItemFromCart(item.getName());
                }

                item.setInCart(!item.isInCart());
                notifyItemChanged(position);

                Toast.makeText(context, item.getName() + (item.isInCart() ? " added to" : " removed from") + " cart", Toast.LENGTH_SHORT).show();
            });

        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public void filter(String text) {
            itemList = new ArrayList<>();
            if (text.isEmpty()) {
                itemList.addAll(fullList);
            } else {
                for (Item item : fullList) {
                    if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                        itemList.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

