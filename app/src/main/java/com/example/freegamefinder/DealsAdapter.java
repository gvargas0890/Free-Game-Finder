package com.example.freegamefinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.DealsHolder> {
    List<Deal> dealsList;
    Context context;

    public DealsAdapter(List<Deal> dealsList, Context context) {
        this.dealsList = dealsList;
        this.context = context;
    }

    @NonNull
    @Override
    public DealsAdapter.DealsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deals_layout, parent, false);
        return new DealsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealsAdapter.DealsHolder holder, int position) {
        holder.tvTitle.setText(dealsList.get(position).getTitle());
        holder.tvSalePrice.setText("Sale Price: $" + dealsList.get(position).getSalePrice());
        holder.tvNormalPrice.setText("Was: $" + dealsList.get(position).getNormalPrice());

        Glide.with(context)
                .load(dealsList.get(position).getThumb())
                .into(holder.ivGameCover);

    }

    @Override
    public int getItemCount() {
        return dealsList.size();
    }

    public class DealsHolder extends RecyclerView.ViewHolder{
        ImageView ivGameCover;
        TextView tvTitle;
        TextView tvSalePrice;
        TextView tvNormalPrice;

        public DealsHolder(@NonNull View itemView) {
            super(itemView);

            ivGameCover = itemView.findViewById(R.id.ivGameCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSalePrice = itemView.findViewById(R.id.tvSalePrice);
            tvNormalPrice = itemView.findViewById(R.id.tvNormalPrice);

        }
    }
}
