package com.example.smartwastebinmanagementsystem.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwastebinmanagementsystem.BinDetailsActivity;
import com.example.smartwastebinmanagementsystem.R;
import com.example.smartwastebinmanagementsystem.models.Bin;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Adapter class for displaying a list of bins in a RecyclerView.
 */
public class BinAdapter extends RecyclerView.Adapter<BinAdapter.BinViewHolder> {

    private Context context;
    private List<Bin> binList;

    public BinAdapter(Context context, List<Bin> binList) {
        this.context = context;
        this.binList = binList;
    }

    @NonNull
    @Override
    public BinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bin, parent, false);
        return new BinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BinViewHolder holder, int position) {
        Bin bin = binList.get(position);
        holder.binNameTextView.setText(bin.getName());
        holder.binStatusTextView.setText(bin.getStatus());

        // Set color based on bin status
        int color;
        switch (bin.getStatus().toLowerCase()) {
            case "empty":
                color = Color.GREEN;
                break;
            case "half-full":
                color = Color.YELLOW;
                break;
            case "full":
                color = Color.RED;
                break;
            default:
                color = Color.GRAY;
                break;
        }
        holder.statusColorView.setBackgroundColor(color);

        // On click, open BinDetailsActivity with bin details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BinDetailsActivity.class);
            intent.putExtra("binId", bin.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return binList.size();
    }

    public static class BinViewHolder extends RecyclerView.ViewHolder {
        View statusColorView;
        TextView binNameTextView, binStatusTextView;

        public BinViewHolder(@NonNull View itemView) {
            super(itemView);
            statusColorView = itemView.findViewById(R.id.statusColorView);
            binNameTextView = itemView.findViewById(R.id.binNameTextView);
            binStatusTextView = itemView.findViewById(R.id.binStatusTextView);
        }
    }
}
