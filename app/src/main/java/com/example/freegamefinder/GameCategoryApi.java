package com.example.freegamefinder;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GameCategoryApi {

    //get end point plus text view value to get the filter listener.

    public void requestCategoryApi() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://www.freetogame.com/api/games?category=shooter";

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

                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
