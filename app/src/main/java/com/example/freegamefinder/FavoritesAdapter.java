package com.example.freegamefinder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteHolder> {
    List<Games> gamesList;
    Context context;
    OnGameLongClickListener listener;

    public FavoritesAdapter(List<Games> gamesList, Context context, OnGameLongClickListener listener) {
        this.gamesList = gamesList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoritesAdapter.FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_layout, parent, false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.FavoriteHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTitle.setText(gamesList.get(position).getTitle());
        holder.tvDescription.setText(gamesList.get(position).getShortDescription());
        holder.tvGenre.setText(gamesList.get(position).getGenre());
        holder.tvPlatform.setText(gamesList.get(position).getPlatform());

        Glide.with(context)
                .load(gamesList.get(position).getThumbnail())
                .into(holder.ivGameCover);

        holder.rlGame.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onGameLongClick(position);
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        ImageView ivGameCover;
        TextView tvGenre;
        TextView tvPlatform;
        RelativeLayout rlGame;


        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivGameCover = itemView.findViewById(R.id.ivGameCover);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            tvPlatform = itemView.findViewById(R.id.tvPlatform);
            rlGame = itemView.findViewById(R.id.rlGame);
        }
    }
    interface OnGameLongClickListener {
        void onGameLongClick(int position);
    }
}
