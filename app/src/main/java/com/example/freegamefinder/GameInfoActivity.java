package com.example.freegamefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameInfoActivity extends AppCompatActivity {
    Saving saving = new Saving();
    private final GamesApi gamesApi = new GamesApi();
    private ImageView ivGameImage;
    private TextView tvGameName;
    private TextView tvFullDescription;
    private TextView tvDeveloper;
    private TextView tvPublisher;
    private TextView tvReleaseDate;
    private TextView tvGameGenre;
    private TextView tvGamePlatform;
    private ProgressBar progressBar;
    private Button btnPlay;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        ivGameImage = findViewById(R.id.ivGameImage);
        tvGameName = findViewById(R.id.tvGameName);
        tvFullDescription = findViewById(R.id.tvFullDescription);
        tvDeveloper = findViewById(R.id.tvDeveloper);
        tvPublisher = findViewById(R.id.tvPublisher);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvGameGenre = findViewById(R.id.tvGameGenre);
        tvGamePlatform = findViewById(R.id.tvGamePlatform);
        progressBar = findViewById(R.id.progressB);
        btnPlay = findViewById(R.id.btnPlay);
        btnAdd = findViewById(R.id.btnAdd);

        Intent intent = getIntent();
        int gameIndex = intent.getIntExtra("game_index", 0);

        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences prefs = getSharedPreferences("", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String gameArray = prefs.getString("game", "");

        gamesApi.requestGames(new GamesApi.OnGamesListener() {
            @Override
            public void onSuccess(GamesResponse gamesResponse) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        setTextViews(gamesResponse, gameIndex);
                        progressBar.setVisibility(View.GONE);

                    }
                });

                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = gamesResponse.getGamesList().get(gameIndex).getGameUrl();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        saving.saveFavGame(gameArray, gamesResponse, gameIndex, editor);

                        Toast.makeText(GameInfoActivity.this, gamesResponse.getGamesList().get(gameIndex).getTitle() + " has been added to you list.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
            }
            @Override
            public void onFailure() {

            }
        });
    }

    private void setTextViews(GamesResponse gamesResponse, int gameIndex) {
        Glide.with(GameInfoActivity.this)
                .load(gamesResponse.gamesList.get(gameIndex).getThumbnail())
                .into(ivGameImage);

        tvGameName.setText(gamesResponse.getGamesList().get(gameIndex).getTitle());
        tvFullDescription.setText(gamesResponse.getGamesList().get(gameIndex).getShortDescription());
        tvDeveloper.setText("Developer: " + gamesResponse.getGamesList().get(gameIndex).getDeveloper());
        tvPublisher.setText("Publisher: " + gamesResponse.getGamesList().get(gameIndex).getPublisher());
        tvGameGenre.setText("Genre: " + gamesResponse.getGamesList().get(gameIndex).getGenre());
        tvReleaseDate.setText("Release Date: " + gamesResponse.getGamesList().get(gameIndex).getReleaseDate());
        tvGamePlatform.setText("Platform: " + gamesResponse.getGamesList().get(gameIndex).getPlatform());
    }
}