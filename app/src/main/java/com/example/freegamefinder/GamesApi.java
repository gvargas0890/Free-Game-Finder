package com.example.freegamefinder;

import android.net.Uri;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GamesApi {

    private static final String TAG = GamesApi.class.getSimpleName();

    public void requestGames(OnGamesListener listener) {
        OkHttpClient client = new OkHttpClient();

        String url = "https://www.freetogame.com/api/games";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                List<Game> list = new ArrayList<>();
                GamesResponse gamesResponse = new GamesResponse();
                Game games = null;

                try {
                    JSONArray gamesArray = new JSONArray(response.body().string());
                    for(int i = 0; i < gamesArray.length(); i++) {
                        JSONObject gamesObjects = gamesArray.getJSONObject(i);

                        games = new Game();

                        String title = gamesObjects.optString("title");
                        games.setTitle(title);

                        String shortDescription = gamesObjects.optString("short_description");
                        games.setShortDescription(shortDescription);

                        Uri thumbNail = Uri.parse(gamesObjects.optString("thumbnail"));
                        games.setThumbnail(thumbNail);

                        String genre = gamesObjects.optString("genre");
                        games.setGenre(genre);

                        String platform = gamesObjects.optString("platform");
                        games.setPlatform(platform);

                        String developer =  gamesObjects.optString("developer");
                        games.setDeveloper(developer);

                        String publisher = gamesObjects.optString("publisher");
                        games.setPublisher(publisher);

                        String url = gamesObjects.optString("game_url");
                        games.setGameUrl(url);

                        String releaseDate = gamesObjects.optString("release_date");
                        games.setReleaseDate(releaseDate);

                        list.add(games);
                    }
                    gamesResponse.setGamesList(list);
                    listener.onSuccess(gamesResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    interface OnGamesListener{
        void onSuccess(GamesResponse gamesResponse);
        void onFailure();
    }
}
