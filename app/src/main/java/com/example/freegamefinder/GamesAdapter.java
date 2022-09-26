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

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GamesHolder> {
    List<Games> gamesList;
    Context context;
    OnGameClickListener listener;

    public GamesAdapter(List<Games> gamesList, Context context, OnGameClickListener listener) {
        this.gamesList = gamesList;
        this.context =  context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GamesAdapter.GamesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_layout, parent, false);
        return new GamesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesAdapter.GamesHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTitle.setText(gamesList.get(position).getTitle());
        holder.tvDescription.setText(gamesList.get(position).getShortDescription());
        holder.tvGenre.setText(gamesList.get(position).getGenre());
        holder.tvPlatform.setText(gamesList.get(position).getPlatform());
        holder.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickSave(gamesList.get(position), position);
            }
        });

        Glide.with(context)
                .load(gamesList.get(position).getThumbnail())
                .into(holder.ivGameCover);

        holder.rlGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onGameClicked(gamesList.get(position), position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public static class GamesHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvDescription;
        ImageView ivGameCover;
        TextView tvGenre;
        TextView tvPlatform;
        RelativeLayout rlGame;
        Button saveBtn;

        public GamesHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivGameCover = itemView.findViewById(R.id.ivGameCover);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            tvPlatform = itemView.findViewById(R.id.tvPlatform);
            rlGame = itemView.findViewById(R.id.rlGame);
            saveBtn = itemView.findViewById(R.id.saveBtn);
        }
    }
    interface OnGameClickListener{
        void onGameClicked(Games games, int position);
        void onClickSave(Games games, int position);
    }
}
