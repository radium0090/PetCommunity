package com.ls.www.petcommunity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.adapter.HotUserAdapter;
import com.ls.www.petcommunity.model.CollectionModel;
import com.ls.www.petcommunity.model.table.tb_user_followers;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HotUsersActivity extends AppCompatActivity {

    private ImageView back;
    private List<CollectionModel> data = new ArrayList<>();//必须初始化
    private RecyclerView recyclerView;
    private HotUserAdapter adapter;
    private String[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_user);

        findView();
        initialization();
        clickEvents();

    }

    public void findView() {
        back = (ImageView) findViewById(R.id.back);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }

    public void initialization() {

        //查询粉丝量靠前的用户，先查询他们的user_followers
        BmobQuery<tb_user_followers> query = new BmobQuery("tb_user_followers");
        query.include("user");
        query.order("-follower_sum");
        query.findObjects(new FindListener<tb_user_followers>() {
            @Override
            public void done(List<tb_user_followers> list, BmobException e) {
                if (e == null) {
                    ids = new String[list.size()];
                    if (list.size() != 0) {
                        for (int i = 0; i < list.size(); i++) {
                            CollectionModel flag = new CollectionModel(list.get(i).getUser().getObjectId().toString(), list.get(i).getUser().getNickName().toString(), list.get(i).getUser().getHeadPortrait().getFileUrl());
                            data.add(flag);
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new HotUserAdapter(data);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "用户查询失败", Toast.LENGTH_SHORT).show();
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
