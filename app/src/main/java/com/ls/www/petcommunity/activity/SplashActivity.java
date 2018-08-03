package com.ls.www.petcommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.model.table._User;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class SplashActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        // 默认初始化Bmob
        Bmob.initialize(this, "9f33544308066691f4bb046d685484b3");

        // 判断是否存在当前用户，如果存在，则直接进入主界面，不用再次登录
        _User current_user = BmobUser.getCurrentUser(_User.class);
        if (current_user != null) {
            intent = new Intent(this, MainActivity.class);
        } else {
            // 不存在当前用户，转向登录界面
            intent = new Intent(this, LoginActivity.class);
//            intent = new Intent(this, MainActivity.class);
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent); //执行意图
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        };
        timer.schedule(task, 1000 * 2); //2秒后跳转

    }
}
