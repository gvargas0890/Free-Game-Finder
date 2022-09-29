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

public class DealsApi {

    public void requestDealsApi(OnDealsListener listener) {
        OkHttpClient client = new OkHttpClient();

        String url = "https://www.cheapshark.com/api/1.0/deals?storeID=1&upperPrice=15";

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
                List<Deal> dealsList = new ArrayList<>();
                DealsResponse dealsResponse = new DealsResponse();

                try {
                    JSONArray dealsArray = new JSONArray(response.body().string());
                    for(int i = 0; i < dealsArray.length(); i++) {
                        JSONObject dealsObject = dealsArray.getJSONObject(i);

                        Deal deals = new Deal();

                        String title = dealsObject.optString("title");
                        deals.setTitle(title);

                        Uri thumb = Uri.parse(dealsObject.optString("thumb"));
                        deals.setThumb(thumb);

                        String salePrice = dealsObject.optString("salePrice");
                        deals.setSalePrice(salePrice);

                        String normalPrice = dealsObject.optString("normalPrice");
                        deals.setNormalPrice(normalPrice);

                        dealsList.add(deals);

                    }
                    dealsResponse.setDealsList(dealsList);
                    listener.onSuccess(dealsResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    interface OnDealsListener{
        void onSuccess(DealsResponse dealsResponse);
        void onFailure();
    }
}
