package com.ls.www.petcommunity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.adapter.HomepageAdapter;
import com.ls.www.petcommunity.model.CollectionModel;
import com.ls.www.petcommunity.model.table.tb_collection;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HotNotesActivity extends AppCompatActivity {


    private ImageView back;
    private List<CollectionModel> data = new ArrayList<>();//必须初始化
    private RecyclerView recyclerView;
    private HomepageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_notes);

        findView();
        initialization();
        clickEvents();

    }

    public void findView() {
        back = (ImageView) findViewById(R.id.back);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }

    public void initialization() {

        //查询收藏数排前的笔记本
        BmobQuery<tb_collection> query = new BmobQuery("collection");
        query.order("-like_sum");
        query.findObjects(new FindListener<tb_collection>() {
            @Override
            public void done(List<tb_collection> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        for (tb_collection t : list) {
                            CollectionModel flag = new CollectionModel(t.getObjectId().toString(), t.getName().toString(), t.getImage().getFileUrl());
                            data.add(flag);
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new HomepageAdapter(data);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "笔记本查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void clickEvents() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
