package com.example.user.portfolio.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.portfolio.R;
import com.example.user.portfolio.util.Logic;

import static com.example.user.portfolio.util.CONSTANTS.ACTION_NOTICE;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
   // private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        //alarmReceiver = new AlarmReceiver(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_notice);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_camera:
                                intent = new Intent(NoticeActivity.this, ListPhotoActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_wiki:
                                if (Logic.isNetworkAvailable(NoticeActivity.this)) {
                                    intent = new Intent(NoticeActivity.this, WikiActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(NoticeActivity.this, "Internet connection is absent!", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case R.id.nav_home:
                                intent = new Intent(NoticeActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_notice:
                                break;
                        }
                        return true;
                    }
                });

        Button add_notice = (Button) findViewById(R.id.add_notice);
        add_notice.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void scheduleAlarm() {
        /* Request the AlarmManager object */
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Create the PendingIntent that will launch the BroadcastReceiver */
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_NOTICE), 0);

        /* Schedule Alarm with and authorize to WakeUp the device during sleep */
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 7 * 1000, pending);
        Log.d("ALARM", "NOTICE");
    }

    private void cancelAlarm() {
        /* Request the AlarmManager object */
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Create the PendingIntent that would have launched the BroadcastReceiver */
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_NOTICE), 0);

        /* Cancel the alarm associated with that PendingIntent */
        manager.cancel(pending);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_notice) {
            scheduleAlarm();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setCheckedItem(R.id.nav_notice);
        //this.registerReceiver(alarmReceiver, new IntentFilter(ACTION_NOTICE));
    }


}
