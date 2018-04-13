package com.example.user.portfolio.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.user.portfolio.util.CONSTANTS.*;

public class MyIntentService extends IntentService {

    //private final String USER_AGENT = "Mozilla/5.0";
    private SharedPreferences preferences;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent.getIntExtra(SERVICE_REQUEST, 0) == 1) {
            try {
                sendGet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendGet() throws Exception {

        String url = WIKI_URL;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        //con.setRequestProperty("User-Agent", );


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        preferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString(SAVED_TEXT, response.toString());
        ed.apply();

        sendBroadcast(new Intent(ACTION_DOWNLOAD_SITE));

    }
}
