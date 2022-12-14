package com.example.freegamefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FavGamesActivity extends AppCompatActivity {
    SaveUtil saving = new SaveUtil();
    FavoritesAdapter adapter;
    RecyclerView rvFavGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        SharedPreferences prefs = getSharedPreferences("", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String gameArray = prefs.getString("game", "");

        rvFavGames = findViewById(R.id.rvFavGames);
        List<Game> gamesList = new ArrayList<>();

        saving.populateGameArray(gameArray, gamesList);

        rvFavGames.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoritesAdapter(gamesList, this, new FavoritesAdapter.OnGameLongClickListener() {
            @Override
            public void onGameLongClick(int position) {
                gamesList.remove(position);
                adapter.notifyItemRemoved(position);

                try {
                    JSONArray jsonArray = new JSONArray(gameArray);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        jsonArray.remove(position);
                    }

                    editor.putString("game", jsonArray.toString());
                    editor.apply();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rvFavGames.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FavGamesActivity.this, MainActivity.class));
    }
}