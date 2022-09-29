package com.example.freegamefinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class GameInfoFragment extends Fragment {
    View view;

    SaveUtil saving = new SaveUtil();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_info, container, false);

        ivGameImage = view.findViewById(R.id.ivGameImage);
        tvGameName = view.findViewById(R.id.tvGameName);
        tvFullDescription = view.findViewById(R.id.tvFullDescription);
        tvDeveloper = view.findViewById(R.id.tvDeveloper);
        tvPublisher = view.findViewById(R.id.tvPublisher);
        tvReleaseDate = view.findViewById(R.id.tvReleaseDate);
        tvGameGenre = view.findViewById(R.id.tvGameGenre);
        tvGamePlatform = view.findViewById(R.id.tvGamePlatform);
        progressBar = view.findViewById(R.id.progressB);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnAdd = view.findViewById(R.id.btnAdd);

        Bundle index = getArguments();
        int gameIndex = index.getInt("game_index", 0);

        gamesApi.requestGames(new GamesApi.OnGamesListener() {
            @Override
            public void onSuccess(GamesResponse gamesResponse) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        populateGame(gamesResponse, gameIndex);

                    }
                });
                btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = gamesResponse.getGamesList().get(gameIndex).getGameUrl();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
            }

            @Override
            public void onFailure() {

            }
        });

        return view;
    }

    private void populateGame(GamesResponse gamesResponse, int gameIndex) {
        Glide.with(view.getContext())
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