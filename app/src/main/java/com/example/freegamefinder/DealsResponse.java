package com.example.freegamefinder;

import java.util.List;

public class DealsResponse {
    List<Deal> dealsList;

    public List<Deal> getDealsList() {
        return dealsList;
    }

    public void setDealsList(List<Deal> dealsList) {
        this.dealsList = dealsList;
    }
}
