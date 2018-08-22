package com.ls.www.petcommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.model.table._User;
import com.ls.www.petcommunity.model.table.tb_pop_events;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class PopularActivity extends AppCompatActivity {


    private _User user = BmobUser.getCurrentUser(_User.class);
    private String objectId;
    private String activity_title;
    private ImageView back;
    private TextView title;
    private ImageView intro;
    private String intro_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        Intent it = getIntent();
        objectId = it.getStringExtra("objectId");

        findView();
        initialization();
        clickEvents();

    }

    public void findView() {
        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        intro = (ImageView) findViewById(R.id.pop_intro);
    }

    public void initialization() {
        BmobQuery<tb_pop_events> query = new BmobQuery<tb_pop_events>();
        query.getObject(objectId, new QueryListener<tb_pop_events>() {
            @Override
            public void done(tb_pop_events object, BmobException e) {
                if(e==null){
                    activity_title = "#" + object.getName().toString() + "#";
                    intro_url = object.getIntro().getFileUrl();
                    ImageLoader.getInstance().displayImage(intro_url, intro);
                }else{
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

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PopularActivity.this, PopularTopicActivity.class);
                it.putExtra("activity_title", activity_title);
                startActivity(it);
            }
        });

        /*
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PopularActivity.this, PicturePreviewActivity.class);
                it.putExtra("url", intro_url);
                startActivity(it);
            }
        });
        */
    }


}
