package com.example.user.portfolio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.user.portfolio.Activity.WikiActivity;

public class Receiver extends BroadcastReceiver {

    private WikiActivity activity;

    public Receiver(WikiActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        activity.updateContent();
    }
}
