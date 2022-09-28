package com.example.freegamefinder;

import android.net.Uri;

public class Deals {
    String title;
    String salePrice;
    String normalPrice;
    String savings;
    int releaseDate;
    int lastChance;
    Uri thumb;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getLastChance() {
        return lastChance;
    }

    public void setLastChance(int lastChance) {
        this.lastChance = lastChance;
    }

    public Uri getThumb() {
        return thumb;
    }

    public void setThumb(Uri thumb) {
        this.thumb = thumb;
    }
}
