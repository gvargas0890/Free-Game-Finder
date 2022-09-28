package com.example.freegamefinder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final GamesApi gamesApi = new GamesApi();
    private GamesAdapter adapter;
    private RecyclerView rvGames;
    private ProgressBar progressBar;
    private Button btnFav;
    Button btnFilter;
    RelativeLayout relativeLayout;
    Saving saving = new Saving();
    Button btnDeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvGames = findViewById(R.id.rvGames);
        progressBar = findViewById(R.id.progressBar);
        btnFav = findViewById(R.id.btnFav);
        btnFilter = findViewById(R.id.btnFilter);
        relativeLayout = findViewById(R.id.relativeLayout);
        btnDeals = findViewById(R.id.btnDeals);

        progressBar.setVisibility(View.VISIBLE);

        List<String> strings = new ArrayList<>();
        strings.add("shooter");
        strings.add("strategy");


        SharedPreferences prefs = getSharedPreferences("", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String gameArray = prefs.getString("game", "");

        gamesApi.requestGames(new GamesApi.OnGamesListener() {
            @Override
            public void onSuccess(GamesResponse gamesResponse) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        rvGames.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        adapter = new GamesAdapter(gamesResponse.getGamesList(), MainActivity.this, new GamesAdapter.OnGameClickListener() {
                            @Override
                            public void onGameClicked(Games games, int position) {
                                Intent intent = new Intent(MainActivity.this, GameInfoActivity.class);
                                intent.putExtra("game_index", position);
                                startActivity(intent);

                            }

                            @Override
                            public void onClickSave(Games games, int position) {
                                saving.saveFavGame(gameArray, gamesResponse, position, editor);

                                Toast.makeText(MainActivity.this, gamesResponse.getGamesList().get(position).getTitle() +
                                        " has been saved to your list", Toast.LENGTH_SHORT).show();

                            }
                        });
                        rvGames.setAdapter(adapter);

                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
            @Override
            public void onFailure() {

            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavGamesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DealsActivity.class));
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            }
        });
    }
}