package com.ls.www.petcommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.adapter.MessageNotesSimpleAdapter;
import com.ls.www.petcommunity.model.table._User;
import com.ls.www.petcommunity.model.table.tb_message_notes;
import com.ls.www.petcommunity.model.table.tb_user_followers;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MessageNotesActivity extends AppCompatActivity {

    private _User current_user = BmobUser.getCurrentUser(_User.class);
    private ImageView back;
    private ListView listview;
    private List<Map<String,Object>> data = new ArrayList<>();
    private MessageNotesSimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_notes);

        findView();
        initialization();
        clickEvents();

    }

    public void findView() {
        back = (ImageView) findViewById(R.id.back);
        listview = (ListView) findViewById(R.id.listview);
        listview.addFooterView(new ViewStub(getApplicationContext()));
    }

    public void initialization() {

        BmobQuery<tb_message_notes> query = new BmobQuery<tb_message_notes>();
        query.order("-createdAt");
        query.include("focus_book");
        query.addWhereEqualTo("acceptor_id", current_user.getObjectId());
        query.findObjects(new FindListener<tb_message_notes>() {
            @Override
            public void done(List<tb_message_notes> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        Toast.makeText(getApplicationContext(), "当前未有消息提示~", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            Map<String,Object> temp = new LinkedHashMap<>();
                            temp.put("user_name", list.get(i).getUserName());
                            temp.put("user_id", list.get(i).getUserId());
                            temp.put("create_time", "于"+list.get(i).getCreatedAt().toString().split(" ")[0]);
                            temp.put("acceptor_id", list.get(i).getAcceptorId());
                            temp.put("book_id", list.get(i).getFocusNote().getObjectId());
                            temp.put("book_name", "<"+list.get(i).getFocusNote().getName()+">");
                            temp.put("book_cover_image", list.get(i).getFocusNote().getImage().getFileUrl());
                            data.add(temp);
                        }
                        simpleAdapter = new MessageNotesSimpleAdapter(getApplicationContext(), data, R.layout.item_message_notes,
                                new String[] {"user_name","user_id","create_time","acceptor_id","book_id","book_name","book_cover_image"}, new int[] {R.id.user_name, R.id.user_id, R.id.create_time,R.id.acceptor_id,R.id.book_id, R.id.book_name, R.id.book_cover_image});
                        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                            public boolean setViewValue(View view, Object data,
                                                        String textRepresentation) {
                                //判断是否为我们要处理的对象
                                if(view instanceof ImageView && data instanceof String){
                                    ImageView iv = (ImageView) view;
                                    ImageLoader.getInstance().displayImage((String) data, iv);
                                    return true;
                                }else
                                    return false;
                            }
                        });
                        listview.setAdapter(simpleAdapter);
                        //Toast.makeText(getApplicationContext(), "查询成功", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Toast.makeText(getApplicationContext(), "查询失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        BmobQuery<tb_user_followers> query2 = new BmobQuery<tb_user_followers>();
        query2.addWhereEqualTo("user_id", current_user.getObjectId());
        query2.findObjects(new FindListener<tb_user_followers>() {
            @Override
            public void done(List<tb_user_followers> list, BmobException e) {
                if (e == null) {
                    if (list.get(0).getMessageNotesSum() != list.get(0).getMessageNotesRead()) {
                        list.get(0).setMessageNotesRead(list.get(0).getMessageNotesSum());
                        list.get(0).increment("notification_read",0);
                        list.get(0).increment("follower_sum",0);
                        list.get(0).increment("message_fans_sum",0);
                        list.get(0).increment("message_fans_read",0);
                        list.get(0).increment("message_sayings_read",0);
                        list.get(0).increment("message_sayings_sum",0);
                        list.get(0).update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                //if (e == null)
                                //Toast.makeText(getApplicationContext(), "赋值成功", Toast.LENGTH_SHORT).show();
                                //else
                                //Toast.makeText(getApplicationContext(), "赋值失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    //Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void clickEvents() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(2, i);
                finish();
            }
        });

    }

}
