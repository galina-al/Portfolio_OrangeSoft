package com.example.user.portfolio.Activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.portfolio.R;
import com.example.user.portfolio.util.Logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.user.portfolio.util.CONSTANTS.ACTION_NOTICE;
import static com.example.user.portfolio.util.CONSTANTS.CONTENT_TEXT;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener {

    private String description;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView selectDate;
    private TextView selectTime;
    private EditText editDescription;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private int selectYear;
    private int selectMonth;
    private int selectDay;
    private int selectHour;
    private int selectMinute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        selectDate = (TextView) findViewById(R.id.selectDate);
        selectTime = (TextView) findViewById(R.id.selectTime);
        editDescription = (EditText) findViewById(R.id.editDescription);

        selectDate.setOnClickListener(this);
        selectTime.setOnClickListener(this);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectYear = year;
                selectMonth = month + 1;
                selectDay = dayOfMonth;
                selectDate.setText(selectDay + "/" + selectMonth + "/" + selectYear);
            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectHour = hourOfDay;
                selectMinute = minute;
                selectTime.setText(selectHour + ":" + selectMinute);
            }
        };

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
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
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
        description = editDescription.getText().toString();
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        PendingIntent pending = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_NOTICE).putExtra(CONTENT_TEXT, description), 0);
        try {
            String str = selectYear + "-" + selectMonth + "-" + selectDay + "-" + selectHour + "- " + selectMinute;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

            Date date1 = format.parse(str);
            long millis = date1.getTime();
            Log.d("DATE", date1.toString() + " desc: " +
                    "" + description);

            manager.setExact(AlarmManager.RTC_WAKEUP, millis, pending);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /*private void cancelAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        PendingIntent pending = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_NOTICE), 0);

        manager.cancel(pending);
    }*/

    @Override
    public void onClick(View v) {

        Calendar calendar = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.add_notice:
                scheduleAlarm();
                break;
            case R.id.selectDate:
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK, dateSetListener, year, month, day);
                datePickerDialog.show();
                break;
            case R.id.selectTime:
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, timeSetListener, hour, minute, true);
                timePickerDialog.show();
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setCheckedItem(R.id.nav_notice);
    }


}
