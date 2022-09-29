package com.example.freegamefinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class HomeFragment extends Fragment {
    View view;
    private final GamesApi gamesApi = new GamesApi();
    private GamesAdapter adapter;
    private RecyclerView rvGames;
    private ProgressBar progressBar;
    private Button btnFav;
    Button btnHome;
    RelativeLayout rlMain;
    SaveUtil saving = new SaveUtil();
    Button btnDeals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        rvGames = view.findViewById(R.id.rvGames);
        progressBar = view.findViewById(R.id.progressBar);
        btnFav = view.findViewById(R.id.btnFav);
        btnHome = view.findViewById(R.id.btnHome);
        rlMain = view.findViewById(R.id.rlMain);
        btnDeals = view.findViewById(R.id.btnDeals);

        SharedPreferences prefs = getActivity().getSharedPreferences("", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String gameArray = prefs.getString("game", "");

        progressBar.setVisibility(View.VISIBLE);

        gamesApi.requestGames(new GamesApi.OnGamesListener() {
            @Override
            public void onSuccess(GamesResponse gamesResponse) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        rvGames.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        adapter = new GamesAdapter(gamesResponse.getGamesList(), view.getContext(), new GamesAdapter.OnGameClickListener() {
                            @Override
                            public void onGameClicked(Game games, int position) {
                                GameInfoFragment gameInfo = new GameInfoFragment();

                                Bundle index = new Bundle();
                                index.putInt("game_index", position);
                                gameInfo.setArguments(index);

                                goGameInfoFragment(gameInfo);

//                                Intent intent = new Intent(MainActivity.this, GameInfoActivity.class);
//                                intent.putExtra("game_index", position);
//                                startActivity(intent);

                            }

                            @Override
                            public void onClickSave(Game games, int position) {
                                saving.saveFavGame(gameArray, gamesResponse, position, editor);

                                Toast.makeText(view.getContext(), gamesResponse.getGamesList().get(position).getTitle() +
                                        " has been saved to your list", Toast.LENGTH_SHORT).show();

                            }
                        });
                        rvGames.setAdapter(adapter);

                    }
                });
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {

            }
        });

        return view;
    }
    private void goGameInfoFragment(GameInfoFragment gameInfo) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rlFragment, gameInfo);
        fragmentTransaction.commit();

    }
}