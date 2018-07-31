package com.ls.www.petcommunity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.model._User;
import com.ls.www.petcommunity.model.tb_collection;
import com.ls.www.petcommunity.model.tb_message_topics;
import com.ls.www.petcommunity.model.tb_topic;
import com.ls.www.petcommunity.model.tb_user_followers;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class TopicActivity extends AppCompatActivity {

    private _User user = BmobUser.getCurrentUser(_User.class);
    private String objectId;
    private ImageView back;
    private ImageView likes;
    private ImageView add;
    private ImageView head;
    private TextView name;
    private TextView time;
    private ImageView image;
    private TextView content;
    private String userId;
    private TextView likesSum;
    private Integer sum;
    private Integer selectedIndex = 0;
    private String[] arrayNote;//笔记本列表
    private Toolbar toolbar;
    private ImageView boundary;
    private TextView likesSumText;
    private ImageView likeIcon;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Intent it = getIntent();
        objectId = it.getStringExtra("objectId");

        findView();
        initialization();
        clickEvents();

    }

    public void findView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back = (ImageView) findViewById(R.id.back);
        likes = (ImageView) findViewById(R.id.likes);
        add = (ImageView) findViewById(R.id.add);
        head = (ImageView) findViewById(R.id.head);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        image = (ImageView) findViewById(R.id.image);
        content = (TextView) findViewById(R.id.content);
        likesSum = (TextView) findViewById(R.id.likes_sum);
        boundary = (ImageView) findViewById(R.id.boundary);
        likesSumText = (TextView) findViewById(R.id.likes_sum_text);
        likeIcon = (ImageView) findViewById(R.id.like_icon);
    }

    public void initialization() {

        // 查询喜欢的所有语录，多对多关联，因此查询的是saying
        BmobQuery<tb_topic> query = new BmobQuery<tb_topic>();
        // focusSaying是_User表中的字段，用来存储一个用户所关注的语录
        query.addWhereRelatedTo("focusSaying", new BmobPointer(user));
        query.findObjects(new FindListener<tb_topic>() {
            @Override
            public void done(List<tb_topic> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 0) {
                        //Toast.makeText(getApplicationContext(), "你还没收藏任何语录哦", Toast.LENGTH_SHORT).show();
                        likes.setImageResource(R.drawable.ic_fav_border);
                        likes.setTag(0);
                    }
                    else {
                        likes.setImageResource(R.drawable.ic_fav_border);
                        likes.setTag(0);
                        for (int i = 0; i < object.size(); i++) {
                            if (object.get(i).getObjectId().equals(objectId)) {
                                //Toast.makeText(getApplicationContext(), "你已收藏该语录", Toast.LENGTH_SHORT).show();
                                likes.setImageResource(R.drawable.ic_fav);
                                likes.setTag(1);
                                break;
                            }
                        }
                    }
                    add.setImageResource(R.drawable.ic_add);
                } else {
                    Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }
            }

        });

        BmobQuery<tb_topic> query2 = new BmobQuery<tb_topic>();
        query2.include("userId");
        query2.getObject(objectId, new QueryListener<tb_topic>() {

            @Override
            public void done(tb_topic object, BmobException e) {
                if(e==null){
                    userId = object.getUserOnlyId();
                    changeLayout(userId);//判断是否自己发布的语录
                    if (object.getUserId().getHeadPortrait() != null) {
                        String headUrl = object.getUserId().getHeadPortrait().getFileUrl();
                        ImageLoader.getInstance().displayImage(headUrl, head);
                    }
                    if (object.getImage() != null) {
                        imageUrl = object.getImage().getFileUrl();
                        ImageLoader.getInstance().displayImage(imageUrl, image);
                    }
                    sum = object.getLikeSum();
//                    likesSum.setText(Integer.toString(object.getLikeSum()));
                    likesSum.setText(String.valueOf(object.getLikeSum()));
                    likesSumText.setText("该语录已收获" + String.valueOf(object.getLikeSum()) + "个");
                    name.setText(object.getUserId().getNickName());
                    time.setText(object.getCreatedAt());
                    String text = object.getContent() + "\n";
                    if (!object.getAuthor().equals(""))
                        text = text + "\n" + "by " + object.getAuthor();
                    if (!object.getProvenance().equals(""))
                        text = text + "\n" + "『" + object.getProvenance() + "』";
                    content.setText(text);
                }else{
                    Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }
            }

        });

        // 查询创建的所有笔记本
        BmobQuery<tb_collection> query3 = new BmobQuery<tb_collection>();
        // myCollectio是_User表中的字段，用来存储一个用户所创建的笔记本
        query3.addWhereRelatedTo("myCollection", new BmobPointer(user));
        query3.order("createdAt");
        query3.findObjects(new FindListener<tb_collection>() {
            @Override
            public void done(List<tb_collection> object3, BmobException e) {
                if (e == null) {
                    if (object3.size() == 0) {
                        //Toast.makeText(getApplicationContext(), "你还没创建任何笔记本哦", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        arrayNote = new String[object3.size()];
                        for (int i = 0; i < object3.size(); i++) {
                            arrayNote[i] = object3.get(i).getName().toString();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "笔记本列表查询失败", Toast.LENGTH_SHORT).show();
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

        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _User current_user = BmobUser.getCurrentUser(_User.class);
                tb_topic this_saying = new tb_topic();
                this_saying.setObjectId(objectId);
                BmobRelation relation = new BmobRelation();

                if (likes.getTag().equals(0)) {
                    sum = sum + 1;
                    likesSum.setText(Integer.toString(sum));
                    likes.setImageResource(R.drawable.ic_fav);
                    likes.setTag(1);
                    Toast.makeText(getApplicationContext(), "已表示喜欢该语录~", Toast.LENGTH_SHORT).show();
                    // 将语录列入收藏列表里
                    // 添加到多对多关联中
                    relation.add(this_saying);
                    current_user.setFocusTopic(relation);
                    current_user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                //Toast.makeText(getApplication(), "关联成功", Toast.LENGTH_SHORT).show();
                            }else{
                                //Toast.makeText(getApplication(), "关联失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // 该语录的收藏数量加1
                    tb_topic s = new tb_topic();
                    s.increment("like_sum");
                    s.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //Toast.makeText(getApplication(), "该语录收藏量增加成功", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(getApplication(), "该语录收藏量增加失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // 新增消息提示
                    BmobQuery<tb_user_followers> query = new BmobQuery<tb_user_followers>();
                    query.addWhereEqualTo("user_id", userId);
                    query.findObjects(new FindListener<tb_user_followers>() {
                        @Override
                        public void done(List<tb_user_followers> list, BmobException e) {
                            if (e == null) {
                                if (list.size() == 1) {
                                    tb_user_followers the_user_followers = new tb_user_followers();
                                    the_user_followers.increment("follower_sum",0);
                                    the_user_followers.increment("message_fans_sum",0);
                                    the_user_followers.increment("message_fans_read",0);
                                    the_user_followers.increment("notification_read",0);
                                    the_user_followers.increment("message_sayings_read",0);
                                    the_user_followers.increment("message_sayings_sum",1);
                                    the_user_followers.increment("message_notes_read",0);
                                    the_user_followers.increment("message_notes_sum",0);
                                    the_user_followers.update(list.get(0).getObjectId(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException ee) {
                                            if(ee==null){
                                                //Toast.makeText(getApplication(), "消息数量增加成功", Toast.LENGTH_SHORT).show();
                                            }else{
                                                //Toast.makeText(getApplication(), "消息数量增加失败" + ee.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            } else {
                                //Toast.makeText(getApplication(), "失败: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    //建立新的通知
                    tb_message_topics new_message = new tb_message_topics();
                    new_message.setInitiator(BmobUser.getCurrentUser(_User.class));
                    new_message.setAcceptorId(userId);
                    new_message.setTopicId(objectId);
                    new_message.setTopicContent(content.getText().toString());
                    new_message.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                //Toast.makeText(getApplication(), "建立通知成功", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(getApplication(), "建立通知失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    sum = sum - 1;
                    likesSum.setText(Integer.toString(sum));
                    likes.setImageResource(R.drawable.ic_fav_border);
                    likes.setTag(0);
                    Toast.makeText(getApplicationContext(), "取消喜欢", Toast.LENGTH_SHORT).show();
                    // 移除
                    relation.remove(this_saying);
                    current_user.setFocusTopic(relation);
                    current_user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                //Toast.makeText(getApplication(), "关联成功", Toast.LENGTH_SHORT).show();
                            }else{
                                //Toast.makeText(getApplication(), "关联失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    // 该语录的收藏数量减1
                    tb_topic s = new tb_topic();
                    s.increment("like_sum", -1);
                    s.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //Toast.makeText(getApplication(), "该语录收藏量减少成功", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(getApplication(), "该语录收藏量减少失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdialog();
            }
        });

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TopicActivity.this, UserHomeActivity.class);
                it.putExtra("objectId", userId);
                startActivity(it);
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TopicActivity.this, UserHomeActivity.class);
                it.putExtra("objectId", userId);
                startActivity(it);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(TopicActivity.this, PicturePreviewActivity.class);
                Intent it = new Intent(TopicActivity.this, MainActivity.class);
                it.putExtra("url", imageUrl);
                startActivity(it);
            }
        });

    }

    public void createdialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("添加至以下哪本笔记？")
                //.setIcon(R.drawable.ic_favorite_24dp)
                .setSingleChoiceItems(arrayNote, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                selectedIndex = which;
                            }
                        })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        chooseNote(selectedIndex);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                }).create();
        dialog.show();
    }

    public void chooseNote(final Integer select) {
        // 根据下标找到选择的那个笔记本
        // 查询创建的所有笔记本
        BmobQuery<tb_collection> query4 = new BmobQuery<tb_collection>();
        // myCollectio是_User表中的字段，用来存储一个用户所创建的笔记本
        query4.addWhereRelatedTo("myCollection", new BmobPointer(user));
        query4.order("createdAt");
        query4.findObjects(new FindListener<tb_collection>() {
            @Override
            public void done(List<tb_collection> object3, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < object3.size(); i++) {
                        if (i == select) {
                            // 将语录列入该笔记本里
                            // 添加到多对多关联中
                            tb_topic this_saying = new tb_topic();
                            this_saying.setObjectId(objectId);
                            BmobRelation relat = new BmobRelation();
                            relat.add(this_saying);
                            object3.get(i).setCollectedSayings(relat);
                            object3.get(i).update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Toast.makeText(getApplication(), "添加成功", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplication(), "添加失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    // 如果判断出这是自己发布的语录，则页面格式发生改变
    // 增加右侧菜单可进行删除，并将爱心图标置于正文下面
    public void changeLayout(String id) {
        if (user.getObjectId().equals(id)) {
            likes.setVisibility(View.GONE);
            likesSum.setVisibility(View.GONE);
            boundary.setVisibility(View.VISIBLE);
            likesSumText.setVisibility(View.VISIBLE);
            likeIcon.setVisibility(View.VISIBLE);
            // Toolbar作为独立控件进行使用
            toolbar.inflateMenu(R.menu.toolbar_menu_a);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit:
//                            Intent it = new Intent(TopicActivity.this, EditSaying.class);
                            Intent it = new Intent(TopicActivity.this, MainActivity.class);
                            it.putExtra("objectId", objectId);
                            startActivityForResult(it, 0);
                            break;
                        case R.id.delete:
                            createDialog();
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
        }
    }

    public void createDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("确定删除此篇语录？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tb_topic s = new tb_topic();
                        s.setObjectId(objectId);
                        s.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(TopicActivity.this, "成功删除", Toast.LENGTH_SHORT).show();
                                    finish();
                                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                                }else{
                                    Toast.makeText(TopicActivity.this, "删除失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        alertDialog.show();
    }

    public void updateSaying() {
        BmobQuery<tb_topic> query5 = new BmobQuery<tb_topic>();
        query5.getObject(objectId, new QueryListener<tb_topic>() {

            @Override
            public void done(tb_topic object, BmobException e) {
                if(e==null) {
                    if (object.getImage() != null) {
                        String imageUrl = object.getImage().getFileUrl();
                        ImageLoader.getInstance().displayImage(imageUrl, image);
                    }
                    String text = object.getContent() + "\n";
                    if (!object.getAuthor().equals(""))
                        text = text + "\n" + "by " + object.getAuthor();
                    if (!object.getProvenance().equals(""))
                        text = text + "\n" + "『" + object.getProvenance().toString() + "』";
                    content.setText(text);
                }else{
                    Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                updateSaying();
                break;
            default:
                break;
        }
    }

}
