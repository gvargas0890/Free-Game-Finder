package com.example.freegamefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private final GamesApi gamesApi = new GamesApi();
    private GamesAdapter adapter;
    private RecyclerView rvGames;
//    private ProgressBar progressBar;
//    GameInfoFragment gameInfo = new GameInfoFragment();
//    SaveUtil saving = new SaveUtil();
    private Button btnFav;
    Button btnHome;
    RelativeLayout rlMain;

    Button btnDeals;

    HomeFragment home = new HomeFragment();
    FavGamesFragment favGamesFragment = new FavGamesFragment();
    DealsFragment dealsFragment = new DealsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        rvGames = findViewById(R.id.rvGames);
        btnFav = findViewById(R.id.btnFav);
        btnHome = findViewById(R.id.btnHome);
        rlMain = findViewById(R.id.rlMain);
        btnDeals = findViewById(R.id.btnDeals);

        goToHomeFragment(home);

//        SharedPreferences prefs = getSharedPreferences("", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        String gameArray = prefs.getString("game", "");




//        gamesApi.requestGames(new GamesApi.OnGamesListener() {
//            @Override
//            public void onSuccess(GamesResponse gamesResponse) {
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        rvGames.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                        adapter = new GamesAdapter(gamesResponse.getGamesList(), MainActivity.this, new GamesAdapter.OnGameClickListener() {
//                            @Override
//                            public void onGameClicked(Games games, int position) {
//                                GameInfo gameInfo = new GameInfo();
//
//                                Bundle index = new Bundle();
//                                index.putInt("game_index", position);
//                                gameInfo.setArguments(index);
//
//                                getGameInfoFragment(gameInfo);
//
////                                Intent intent = new Intent(MainActivity.this, GameInfoActivity.class);
////                                intent.putExtra("game_index", position);
////                                startActivity(intent);
//
//                            }
//
//                            @Override
//                            public void onClickSave(Games games, int position) {
//                                saving.saveFavGame(gameArray, gamesResponse, position, editor);
//
//                                Toast.makeText(MainActivity.this, gamesResponse.getGamesList().get(position).getTitle() +
//                                        " has been saved to your list", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                        rvGames.setAdapter(adapter);
//                        progressBar.setVisibility(View.GONE);
//                    }
//                });
//            }
//            @Override
//            public void onFailure() {
//
//            }
//        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHomeFragment(home);
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rlFragment, favGamesFragment);
                fragmentTransaction.commit();

//                Intent intent = new Intent(MainActivity.this, FavGamesActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        btnDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.rlFragment, dealsFragment);
                fragmentTransaction.commit();
            }
        });
    }
//    private void goGameInfoFragment(GameInfo gameInfo) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.rlFragment, gameInfo);
//        fragmentTransaction.commit();
//
//    }
    private void goToHomeFragment(HomeFragment home) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rlFragment, home);
        fragmentTransaction.commit();
    }
}