package com.example.simpletodo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Responsible for displaying data from the model into a row in the RecyclerView
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    String[] colors = {"#ffbe0b", "#fb5607", "#ff006e", "#8338ec", "#3a86ff"};
    int colorsPtr = 0;

    public interface OnClickListener {
        void onItemClicked(int position);
    }

    public interface OnLongClickListener {
        // The class that implements OnLongClickListener needs to know where the long press occured
        // so it can notify the Adapter that that's the position where it should delete
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnClickListener clickListener;
    OnLongClickListener longClickListener;

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    // Responsible for creating each view (create a new View and wrap it inside of a ViewHolder)
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // Wrap it inside a ViewHolder and return it
        return new ViewHolder(todoView);
    }

    @Override
    // Responsible for binding data to a particular ViewHolder
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        // Grab the item at the position
        String item = items.get(position);
        // Bind the item into the specified ViewHolder
        holder.bind(item);
    }

    @Override
    // Tells the RecyclerView how many items are in the list
    public int getItemCount() {
        return items.size();
    }

    // Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        // Responsible for updating the View inside of the ViewHolder with this data of item
        public void bind(String item) {
            // Get a reference to the View in bind that we can access
            tvItem.setText(item);
            tvItem.setTextColor(Color.parseColor(colors[colorsPtr % colors.length]));
            colorsPtr += 1;
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                // Notify the listener which position was long pressed
                public boolean onLongClick(View v) {
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });

        }
    }
}
