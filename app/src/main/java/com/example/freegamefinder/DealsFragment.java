package com.example.freegamefinder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class DealsFragment extends Fragment {
    View view;

    RecyclerView rvGameDeals;
    DealsAdapter adapter;
    DealsApi dealsApi = new DealsApi();

    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_deals, container, false);

        rvGameDeals = view.findViewById(R.id.rvGameDeals);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        dealsApi.requestDealsApi(new DealsApi.OnDealsListener() {
            @Override
            public void onSuccess(DealsResponse dealsResponse) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        rvGameDeals.setLayoutManager(new LinearLayoutManager(view.getContext()));
                        adapter = new DealsAdapter(dealsResponse.getDealsList(), view.getContext());
                        rvGameDeals.setAdapter(adapter);

                        progressBar.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onFailure() {

            }
        });
        return view;
    }
}