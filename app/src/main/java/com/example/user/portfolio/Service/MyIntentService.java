package com.example.user.portfolio.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.user.portfolio.Receiver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.user.portfolio.Activity.WikiActivity.*;

public class MyIntentService extends IntentService {

    private final String USER_AGENT = "Mozilla/5.0";
    private SharedPreferences preferences;
    private Receiver receiver;

    public static final String SAVED_TEXT = "saved_text";

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

        String url = "https://ru.m.wikipedia.org/wiki/%D0%91%D1%83%D1%84%D1%84%D0%BE%D0%BD,_%D0%94%D0%B6%D0%B0%D0%BD%D0%BB%D1%83%D0%B8%D0%B4%D0%B6%D0%B8";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        preferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString(SAVED_TEXT, response.toString());
        ed.apply();


        //print result
         System.out.println(response.toString());

        sendBroadcast(new Intent("GALINA_BLANKA"));

    }
}
