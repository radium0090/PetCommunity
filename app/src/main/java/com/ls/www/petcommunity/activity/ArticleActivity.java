package com.ls.www.petcommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.model.table.tb_article;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class ArticleActivity extends AppCompatActivity {



    private String objectId;
    private ImageView back;
    private TextView title;
    private TextView intro;
    private ImageView image;
    private TextView content;
    private String img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent it = getIntent();
        objectId = it.getStringExtra("objectId");

        findView();
        initialization();
        clickEvents();

    }

    public void findView() {
        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        intro = (TextView) findViewById(R.id.intro);
        image = (ImageView) findViewById(R.id.image);
        content = (TextView) findViewById(R.id.content);
    }

    public void initialization() {

        BmobQuery<tb_article> query = new BmobQuery<tb_article>();
        query.getObject(objectId, new QueryListener<tb_article>() {
            @Override
            public void done(tb_article object, BmobException e) {
                if(e==null){
                    title.setText(object.getTitle());
                    img_url = object.getImage().getFileUrl();
                    ImageLoader.getInstance().displayImage(object.getImage().getFileUrl(), image);
                    intro.setText(object.getIntro());
                    content.setText(object.getContent());
                }else{
                    Toast.makeText(getApplicationContext(), "文章查询失败", Toast.LENGTH_SHORT).show();
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

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ArticleActivity.this, PicturePreviewActivity.class);
                it.putExtra("url", img_url);
                startActivity(it);
            }
        });

    }


}


