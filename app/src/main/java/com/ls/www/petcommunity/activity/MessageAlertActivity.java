package com.ls.www.petcommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.model.table._User;
import com.ls.www.petcommunity.model.table.tb_notification;
import com.ls.www.petcommunity.model.table.tb_user_followers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

public class MessageAlertActivity extends AppCompatActivity {

    private ImageView back;
    private ListView listView;
    private List<Map<String,Object>> data = new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    //查询是否有未读消息，没有则图标为">"，有则图标为红点
    private int icon_1 = R.drawable.ic_keyboard_arrow_right;
    private int icon_2 = R.drawable.ic_keyboard_arrow_right;
    private int icon_3 = R.drawable.ic_keyboard_arrow_right;
    private int icon_4 = R.drawable.ic_keyboard_arrow_right;

    private _User current_user = BmobUser.getCurrentUser(_User.class);
    private Integer notification_sum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_alert);
        findView();
        initialization();
        clickEvents();
    }

    private void findView() {
        back = (ImageView) findViewById(R.id.back);
        listView = (ListView) findViewById(R.id.listview);
        listView.addFooterView(new ViewStub(this));//底部分割线

    }

    public void initialization() {
        //查询系统通知数量
        BmobQuery<tb_notification> query_3 = new BmobQuery<tb_notification>();
        query_3.count(tb_notification.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                if(e==null){
                    notification_sum = count;
                    initialization_2();
                }else{
                    //Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void initialization_2() {
        //查询是否有未读消息
        BmobQuery<tb_user_followers> query = new BmobQuery<tb_user_followers>();
        query.addWhereEqualTo("userId", current_user.getObjectId());
        query.findObjects(new FindListener<tb_user_followers>() {
            @Override
            public void done(List<tb_user_followers> list, BmobException e) {
                if (e == null) {
                    if (!list.get(0).getMessageFansSum().equals(list.get(0).getMessageFansRead())) {
                        //Toast.makeText(getApplicationContext(), "“新粉丝”有未读消息", Toast.LENGTH_SHORT).show();
                        icon_1 = R.drawable.ic_message;
                    }
                    if (!list.get(0).getMessageTopicsSum().equals(list.get(0).getMessageTopicsRead())) {
                        //Toast.makeText(getApplicationContext(), "“语录喜欢”有未读消息", Toast.LENGTH_SHORT).show();
                        icon_2 = R.drawable.ic_message;
                    }
                    if (!list.get(0).getMessageNotesSum().equals(list.get(0).getMessageNotesRead())) {
                        //Toast.makeText(getApplicationContext(), "“笔记喜欢”有未读消息", Toast.LENGTH_SHORT).show();
                        icon_3 = R.drawable.ic_message;
                    }
                    if (!list.get(0).getNotificationRead().equals(notification_sum)) {
                        //Toast.makeText(getApplicationContext(), "“系统通知”有未读消息", Toast.LENGTH_SHORT).show();
                        icon_4 = R.drawable.ic_message;
                    }
                } else {
                    //Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }
                init_list();
            }
        });
    }

    public void init_list() {
        data.clear();
        //初始化列表
        Integer[] images = {R.drawable.ic_person_add, R.drawable.ic_fav_border, R.drawable.ic_free_breakfast, R.drawable.ic_volume_up};
        String[] message = {"新的粉丝", "Ta收藏了我的语录", "Ta收藏了我的笔记", "系统通知"};
        Integer[] icons = {icon_1, icon_2, icon_3, icon_4};
        for (int i = 0; i < 4; i++) {
            Map<String,Object> temp = new LinkedHashMap<>();
            temp.put("image", images[i]);
            temp.put("message", message[i]);
            temp.put("icon", icons[i]);
            data.add(temp);
        }
        simpleAdapter = new SimpleAdapter(getApplicationContext(),
                data,
                R.layout.item_message_alert,
                new String[] {"image","message","icon"},
                new int[] {R.id.image, R.id.message, R.id.icon});
        listView.setAdapter(simpleAdapter);
    }

    public void clickEvents() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent it_0 = new Intent(MessageAlertActivity.this, MessageFansActivity.class);
                        startActivityForResult(it_0, 0);
                        break;
                    case 1:
                        Intent it_1 = new Intent(MessageAlertActivity.this, MessageTopicsActivity.class);
                        startActivityForResult(it_1, 1);
                        break;
                    case 2:
                        Intent it_2 = new Intent(MessageAlertActivity.this, MessageNotesActivity.class);
                        startActivityForResult(it_2, 2);
                        break;
                    case 3:
                        Intent it_3 = new Intent(MessageAlertActivity.this, MessageNotificationActivity.class);
                        it_3.putExtra("notification_sum", notification_sum + "");
                        startActivityForResult(it_3, 3);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                icon_1 = R.drawable.ic_keyboard_arrow_right;
                init_list();
                break;
            case 1:
                icon_2 = R.drawable.ic_keyboard_arrow_right;
                init_list();
                break;
            case 2:
                icon_3 = R.drawable.ic_keyboard_arrow_right;
                init_list();
                break;
            case 3:
                icon_4 = R.drawable.ic_keyboard_arrow_right;
                init_list();
                break;
            default:
                break;
        }
    }
}
