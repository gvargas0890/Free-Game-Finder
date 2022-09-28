package com.example.freegamefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

public class DealsActivity extends AppCompatActivity {
    RecyclerView rvGameDeals;
    DealsAdapter adapter;
    DealsApi dealsApi = new DealsApi();

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);

        rvGameDeals = findViewById(R.id.rvGameDeals);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        dealsApi.requestDealsApi(new DealsApi.OnDealsListener() {
            @Override
            public void onSuccess(DealsResponse dealsResponse) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        rvGameDeals.setLayoutManager(new LinearLayoutManager(DealsActivity.this));
                        adapter = new DealsAdapter(dealsResponse.getDealsList(), DealsActivity.this);
                        rvGameDeals.setAdapter(adapter);

                        progressBar.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onFailure() {

            }
        });
    }
}