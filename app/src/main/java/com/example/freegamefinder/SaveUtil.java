package com.example.freegamefinder;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SaveUtil {

    public void saveFavGame(String gameArray, GamesResponse gamesResponse, int gameIndex, android.content.SharedPreferences.Editor editor) {
        if(!gameArray.isEmpty()) {
            try {
                JSONArray jsonArray = new JSONArray(gameArray);
                JSONObject favoritesObject = new JSONObject();

                putJsonObjects(gamesResponse, gameIndex, favoritesObject);
                jsonArray.put(favoritesObject);

                editor.putString("game", jsonArray.toString());
                editor.apply();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

            JSONObject favoritesObject =  new JSONObject();
            try {
                putJsonObjects(gamesResponse, gameIndex, favoritesObject);

                JSONArray favArray = new JSONArray();
                favArray.put(favoritesObject);

                editor.putString("game", favArray.toString());
                editor.apply();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void populateGameArray(String gameArray, List<Game> gamesList) {
        try {
            JSONArray favGamesArray = new JSONArray(gameArray);
            for(int i = 0; i < favGamesArray.length(); i++) {
                JSONObject gamesObjects = favGamesArray.getJSONObject(i);

                Game games = new Game();

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

                gamesList.add(games);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void putJsonObjects(GamesResponse gamesResponse, int gameIndex, JSONObject favoritesObject) throws JSONException {
        favoritesObject.put("tittle", gamesResponse.getGamesList().get(gameIndex).getTitle());
        favoritesObject.put("short_description", gamesResponse.getGamesList().get(gameIndex).getShortDescription());
        favoritesObject.put("genre", gamesResponse.getGamesList().get(gameIndex).getGenre());
        favoritesObject.put("platform", gamesResponse.getGamesList().get(gameIndex).getPlatform());
        favoritesObject.put("developer", gamesResponse.getGamesList().get(gameIndex).getDeveloper());
        favoritesObject.put("publisher", gamesResponse.getGamesList().get(gameIndex).getPublisher());
        favoritesObject.put("release_date", gamesResponse.getGamesList().get(gameIndex).getReleaseDate());
        favoritesObject.put("game_url", gamesResponse.getGamesList().get(gameIndex).getGameUrl());
        favoritesObject.put("thumbnail", gamesResponse.getGamesList().get(gameIndex).getThumbnail());

    }
}
