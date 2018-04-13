package com.example.user.portfolio.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.user.portfolio.Adapters.MyRecyclerAdapter;
import com.example.user.portfolio.Adapters.RecyclerItemClickListener;
import com.example.user.portfolio.DataBase.DbHelper;
import com.example.user.portfolio.DataBase.HeaderPhotoDao;
import com.example.user.portfolio.Entity.HeaderPhoto;
import com.example.user.portfolio.Entity.ItemPhoto;
import com.example.user.portfolio.R;
import com.example.user.portfolio.util.Logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.user.portfolio.util.CONSTANTS.PHOTO_PATH_SHOW;
import static com.example.user.portfolio.util.CONSTANTS.PHOTO_POSITION_SHOW;

public class ListPhotoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyRecyclerAdapter adapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photo);

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
        navigationView.setCheckedItem(R.id.nav_camera);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_camera:
                                break;
                            case R.id.nav_wiki:
                                if (Logic.isNetworkAvailable(ListPhotoActivity.this)) {
                                    intent = new Intent(ListPhotoActivity.this, WikiActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(ListPhotoActivity.this, "Internet connection is absent!", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case R.id.nav_home:
                                intent = new Intent(ListPhotoActivity.this, MainActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_notice:
                                intent = new Intent(ListPhotoActivity.this, NoticeActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_photo);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        HeaderPhotoDao dao = DbHelper.getDb().getHeaderPhotoDao();
        dao.getAll();
        List<HeaderPhoto> headerPhotos = dao.getAll();
        Collections.reverse(headerPhotos);
        final List<ItemPhoto> itemPhotos = new ArrayList<ItemPhoto>();
        for (HeaderPhoto photo : headerPhotos) {
            itemPhotos.add(ItemPhoto.getItemPhoto(this, photo));
        }
        adapter = new MyRecyclerAdapter(itemPhotos);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ListPhotoActivity.this, ShowPhotoActivity.class);
                intent.putExtra(PHOTO_PATH_SHOW, itemPhotos.get(position).path);
                intent.putExtra(PHOTO_POSITION_SHOW, position);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setCheckedItem(R.id.nav_camera);
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
}
