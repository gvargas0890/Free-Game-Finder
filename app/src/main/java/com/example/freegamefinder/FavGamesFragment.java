package com.example.freegamefinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FavGamesFragment extends Fragment {
    SaveUtil saving = new SaveUtil();
    FavoritesAdapter adapter;
    RecyclerView rvFavGames;
    Button btnPlay;
    Button btnAdd;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fav_games, container, false);

        rvFavGames = view.findViewById(R.id.rvFavGames);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnPlay = view.findViewById(R.id.btnPlay);

        SharedPreferences prefs = getActivity().getSharedPreferences("", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String gameArray = prefs.getString("game", "");

        List<Game> gamesList = new ArrayList<>();

        saving.populateGameArray(gameArray, gamesList);

//        btnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        rvFavGames.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new FavoritesAdapter(gamesList, view.getContext(), new FavoritesAdapter.OnGameLongClickListener() {
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

        return view;
    }
}