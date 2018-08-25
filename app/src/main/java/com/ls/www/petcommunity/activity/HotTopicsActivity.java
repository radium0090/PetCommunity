package com.ls.www.petcommunity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.adapter.HotSayingAdapter;
import com.ls.www.petcommunity.model.HotTopicModel;
import com.ls.www.petcommunity.model.table.tb_topic;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HotTopicsActivity extends AppCompatActivity {

    private ImageView back;
    private List<HotTopicModel> data = new ArrayList<>();//必须初始化
    private RecyclerView recyclerView;
    private HotSayingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_topic);

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
        BmobQuery<tb_topic> query = new BmobQuery("tb_topic");
        query.order("-like_sum");
        query.findObjects(new FindListener<tb_topic>() {
            @Override
            public void done(List<tb_topic> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        for (tb_topic t : list) {
                            String img_url = "no_image";
                            if (t.getImage() != null)
                                img_url = t.getImage().getFileUrl();
                            HotTopicModel flag = new HotTopicModel(t.getObjectId(), t.getContent(), img_url);
                            data.add(flag);
                        }
                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new HotSayingAdapter(data);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
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
