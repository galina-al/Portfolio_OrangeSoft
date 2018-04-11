package com.example.user.portfolio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListPhotoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyRecyclerAdapter adapter;

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
        List<ItemPhoto> itemPhotos = new ArrayList<ItemPhoto>();
        for (HeaderPhoto photo : headerPhotos){
            itemPhotos.add(ItemPhoto.getItemPhoto(this,photo));
        }
        adapter = new MyRecyclerAdapter(itemPhotos);
        recyclerView.setAdapter(adapter);
    }


}
