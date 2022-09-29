package com.example.freegamefinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogShower {
    Context context;

    public void dialogWarning(DialogInterface dialogInterface) {
        new AlertDialog.Builder(context)
                .setMessage("Something went wrong, check your internet connection.")
                .show();
    }
}
