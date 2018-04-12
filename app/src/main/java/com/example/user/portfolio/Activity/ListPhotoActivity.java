package com.example.user.portfolio.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.user.portfolio.DataBase.DbHelper;
import com.example.user.portfolio.DataBase.HeaderPhotoDao;
import com.example.user.portfolio.Entity.HeaderPhoto;
import com.example.user.portfolio.Entity.ItemPhoto;
import com.example.user.portfolio.Adapters.MyRecyclerAdapter;
import com.example.user.portfolio.R;
import com.example.user.portfolio.Adapters.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListPhotoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyRecyclerAdapter adapter;
    public static final String PHOTO_PATH_SHOW = "photo_path_show";
    public static final String PHOTO_POSITION_SHOW = "photo_position_show";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photo);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_photo);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        HeaderPhotoDao dao = DbHelper.getDb().getHeaderPhotoDao();
        dao.getAll();
        List<HeaderPhoto> headerPhotos = dao.getAll();
        Collections.reverse(headerPhotos);
        final List<ItemPhoto> itemPhotos = new ArrayList<ItemPhoto>();
        for (HeaderPhoto photo : headerPhotos){
            itemPhotos.add(ItemPhoto.getItemPhoto(this,photo));
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


}
