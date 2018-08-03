package com.ls.www.petcommunity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.adapter.HomepageAdapter;
import com.ls.www.petcommunity.model.CollectionModel;
import com.ls.www.petcommunity.model.table._User;
import com.ls.www.petcommunity.model.table.tb_collection;
import com.ls.www.petcommunity.model.table.tb_message_fans;
import com.ls.www.petcommunity.model.table.tb_topic;
import com.ls.www.petcommunity.model.table.tb_user_followers;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomeActivity extends AppCompatActivity {

    private String objectId;
    private ImageView back;
    private TextView title;
    private CircleImageView headView;
    private String headViewUrl;
    private ImageView bgView;
    private TextView name;
    private TextView briefIntro;
    private TextView focus;
    private TextView focusIdSum;
    private TextView followers;
    private TextView followerSum;
    private Button focusOrNot;
    private Button userTopics;
    private Button userNotes;

    private String followerListId;//该页面用户对应的粉丝user_followers的object
    private Integer sum;
    private Integer noteSum = null;

    private ListView lv;
    private List<Map<String, Object>> dataObj = new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    private DisplayImageOptions options; // 设置图片显示相关参数

    private List<CollectionModel> dataCollection = new ArrayList<>();//必须初始化
    private RecyclerView recyclerView;
    private HomepageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Intent it = getIntent();
        objectId = it.getStringExtra("objectId");

        findView();
        initialization();
        clickEvents();

    }

    public void findView() {
        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        headView = (CircleImageView) findViewById(R.id.headView);
        bgView = (ImageView) findViewById(R.id.bgView);
        name = (TextView) findViewById(R.id.name);
        briefIntro = (TextView) findViewById(R.id.brief_intro);
        focus = (TextView) findViewById(R.id.focus);
        focusIdSum = (TextView) findViewById(R.id.focusId_sum);
        followers = (TextView) findViewById(R.id.followers);
        followerSum = (TextView) findViewById(R.id.follower_sum);
        focusOrNot = (Button) findViewById(R.id.focus_or_not);
        if (BmobUser.getCurrentUser(_User.class).getObjectId().equals(objectId)) {
            focusOrNot.setVisibility(View.GONE);//如果是自己的主页，则无法选择关注与否
        }
        userTopics = (Button) findViewById(R.id.user_sayings);
        userTopics.setTag(1);//表示被选择
        userNotes = (Button) findViewById(R.id.user_books);
        userNotes.setTag(0);//表示不被选择
        lv = (ListView) findViewById(R.id.listview);
        lv.setFocusable(false);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setFocusable(false);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.loading) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.loading) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.loading) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .build(); // 构建完成
    }

    public void initialization() {

        // 查看是否关注该用户
        // 查询当前用户的关注列表，多对多关联，因此查询的是_User
        BmobQuery<_User> query = new BmobQuery<_User>();
        _User current_user = BmobUser.getCurrentUser(_User.class);
        // focusId是_User表中的字段，用来存储一个用户所关注的对象
        query.addWhereRelatedTo("focusId", new BmobPointer(current_user));
        query.findObjects(new FindListener<_User>() {
            @Override
            public void done(List<_User> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 0) {
                        focusOrNot.setBackgroundResource(R.drawable.shape_3);
                        focusOrNot.setText("关注");
                        focusOrNot.setTextColor(getResources().getColor(R.color.colorBackground));
                        focusOrNot.setTag(0);
                        //Toast.makeText(getApplicationContext(), "你还没关注任何人哦", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        focusOrNot.setBackgroundResource(R.drawable.shape_3);
                        focusOrNot.setText("关注");
                        focusOrNot.setTextColor(getResources().getColor(R.color.colorBackground));
                        focusOrNot.setTag(0);
                        for (int i = 0; i < object.size(); i++) {
                            if (object.get(i).getObjectId().equals(objectId)) {
                                //Toast.makeText(getApplicationContext(), "你已收藏该语录", Toast.LENGTH_SHORT).show();
                                focusOrNot.setBackgroundResource(R.drawable.shape_4);
                                focusOrNot.setText("已关注");
                                focusOrNot.setTextColor(getResources().getColor(R.color.colorPrimary));
                                focusOrNot.setTag(1);
                                break;
                            }
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }
            }

        });

        //查询该用户的基本信息
        BmobQuery<_User> query2 = new BmobQuery<_User>();
        query2.include("follower_id");
        query2.getObject(objectId, new QueryListener<_User>() {
            @Override
            public void done(_User user, BmobException e) {
                if (e == null) {
                    //加载信息
                    headViewUrl = user.getHeadPortrait().getFileUrl();
                    ImageLoader.getInstance().displayImage(user.getHeadPortrait().getFileUrl(), headView);
                    if (user.getCoverPage() != null) {
                        ImageLoader.getInstance().displayImage(user.getCoverPage().getFileUrl(), bgView);
                    } else
                        bgView.setImageResource(R.mipmap.img_4);
                    title.setText(user.getNickName());
                    name.setText(user.getNickName());
                    if (user.getBriefIntro() == null || user.getBriefIntro().equals("")) {
                        briefIntro.setText("简介：这个人很懒，什么也没留下...");
                    } else
                        briefIntro.setText("简介："+user.getBriefIntro());
                    //focusId_sum.setText(Integer.toString(user.getFocusId_sum()));
                    sum = user.getFollowerId().getFollowerSum();
                    followerSum.setText(String.valueOf(user.getFollowerId().getFollowerSum()));
                    followerListId = user.getFollowerId().getObjectId().toString();
                } else {
                    Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //查询该用户的语录
        BmobQuery<tb_topic> query3 = new BmobQuery("saying");
        query3.addWhereEqualTo("userOnlyId", objectId);  // 查询当前用户的所有语录
        query3.include("userId");
        query3.order("-createdAt");
        query3.findObjects(new FindListener<tb_topic>() {
            @Override
            public void done(List<tb_topic> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        for (tb_topic t : list) {
                            Map<String,Object> temp = new LinkedHashMap<>();
                            temp.put("saying_id", t.getObjectId().toString());
                            temp.put("create_time", t.getCreatedAt().toString());
                            temp.put("saying_content", t.getContent().toString());
                            if (t.getImage() != null) {
                                BmobFile img = t.getImage();
                                String img_url = img.getFileUrl();
                                temp.put("saying_image", img_url);
                            } else {
                                temp.put("saying_image", "no_image");
                            }
                            dataObj.add(temp);
                        }
                        //列表模式：listview
                        simpleAdapter = new SimpleAdapter(
                                getApplicationContext(),
                                dataObj,
                                R.layout.item_topic_2,
                                new String[] {"saying_id","saying_content","create_time","saying_image"},
                                new int[] {R.id.saying_id, R.id.saying_content, R.id.create_time, R.id.saying_image});
                        // 在SimpleAdapter中需要一个数据源，用来存储数据的，在显示图片时我们要用HashMap<>存储一个url转为string的路径；
                        // 利用imageloader框架，对SimpleAdapter进行处理
                        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                            public boolean setViewValue(View view, Object data,
                                                        String textRepresentation) {
                                //判断是否为我们要处理的对象
                                if(view instanceof ImageView && data instanceof String){
                                    ImageView iv = (ImageView) view;
                                    if (data.equals("no_image"))
                                        iv.setVisibility(View.GONE);
                                    else {
                                        iv.setVisibility(View.VISIBLE);
                                        ImageLoader.getInstance().displayImage((String) data, iv, options);
                                    }
                                    return true;
                                }else
                                    return false;
                            }
                        });
                        lv.setAdapter(simpleAdapter);
                        setListViewHeightBasedOnChildren();
                        lv.setVisibility(View.VISIBLE);
                    } else
                        Toast.makeText(getApplicationContext(), "该用户还未发表任何语录哦", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "语录列表查询失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //查询该用户的笔记本
        BmobQuery<tb_collection> query4 = new BmobQuery("collection");
        query4.addWhereEqualTo("userOnlyId", objectId);// 查询当前用户的所有笔记本
        query4.order("createdAt");
        query4.findObjects(new FindListener<tb_collection>() {
            @Override
            public void done(List<tb_collection> list, BmobException e) {
                if (e == null) {
                    noteSum = list.size();
                    if (list.size() != 0) {
                        for (tb_collection t : list) {
                            CollectionModel flag = new CollectionModel(t.getObjectId(), t.getName(), t.getImage().getFileUrl());
                            dataCollection.add(flag);
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new HomepageAdapter(dataCollection);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "笔记本查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //查询关注人数
        _User thisUser = new _User();
        thisUser.setObjectId(objectId);
        BmobQuery<_User> query5 = new BmobQuery<_User>();
        // focusId是_User表中的字段，用来存储一个用户所关注的对象
        query5.addWhereRelatedTo("focusId", new BmobPointer(thisUser));
        query5.count(_User.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    focusIdSum.setText(String.valueOf(integer));
                } else {
                    //Toast.makeText(getApplicationContext(), "关注人数查询失败", Toast.LENGTH_SHORT).show();
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


        focusOrNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final _User current_user = BmobUser.getCurrentUser(_User.class);
                _User this_user = new _User();
                this_user.setObjectId(objectId);
                BmobRelation relation = new BmobRelation();

                if (focusOrNot.getTag().equals(0)) {
                    sum = sum + 1;
                    followerSum.setText(Integer.toString(sum));
                    focusOrNot.setBackgroundResource(R.drawable.shape_4);
                    focusOrNot.setText("已关注");
                    focusOrNot.setTextColor(getResources().getColor(R.color.colorPrimary));
                    focusOrNot.setTag(1);
                    // Toast.makeText(getApplicationContext(), "关注成功", Toast.LENGTH_SHORT).show();
                    // 将这个用户列入关注列表里
                    // 添加到多对多关联中
                    relation.add(this_user);
                    current_user.setFocusId(relation);
                    current_user.increment("focusId_sum");//关注人数自增1
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
                    // 这个用户的粉丝列表也发生相应改变：增加当前用户
                    BmobRelation rela = new BmobRelation();
                    rela.add(current_user);
                    final tb_user_followers fo = new tb_user_followers();
                    fo.setFollowerId(rela);
                    fo.increment("follower_sum");
                    fo.increment("message_fans_read",0);
                    fo.increment("notification_read",0);
                    fo.increment("message_fans_sum");
                    fo.increment("message_sayings_read",0);
                    fo.increment("message_sayings_sum",0);
                    fo.increment("message_notes_read",0);
                    fo.increment("message_notes_sum",0);
                    fo.update(followerListId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //Toast.makeText(getApplication(), "粉丝关联成功", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(getApplication(), "粉丝关联失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    //建立新的通知
                    tb_message_fans new_message = new tb_message_fans();
                    new_message.setInitiator(BmobUser.getCurrentUser(_User.class));
                    new_message.setAcceptor(this_user);
                    new_message.setAcceptorId(objectId);
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
                    followerSum.setText(String.valueOf(sum));
                    focusOrNot.setBackgroundResource(R.drawable.shape_3);
                    focusOrNot.setText("关注");
                    focusOrNot.setTextColor(getResources().getColor(R.color.colorBackground));
                    focusOrNot.setTag(0);
                    // Toast.makeText(getApplicationContext(), "取消关注", Toast.LENGTH_SHORT).show();
                    // 移除
                    relation.remove(this_user);
                    current_user.setFocusId(relation);
                    current_user.increment("focusId_sum", -1);//关注人数自减1
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

                    // 这个用户的粉丝列表也发生相应改变：移除当前用户
                    BmobRelation rela2 = new BmobRelation();
                    rela2.remove(current_user);
                    final tb_user_followers fo2 = new tb_user_followers();
                    fo2.setFollowerId(rela2);
                    fo2.increment("notification_read",0);
                    fo2.increment("message_fans_sum",0);
                    fo2.increment("message_fans_read",0);
                    fo2.increment("message_sayings_read",0);
                    fo2.increment("message_sayings_sum",0);
                    fo2.increment("message_notes_read",0);
                    fo2.increment("message_notes_sum",0);
                    fo2.increment("follower_sum",-1);
                    fo2.update(followerListId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //Toast.makeText(getApplication(), "解除粉丝成功", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(getApplication(), "解除粉丝失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(UserHomeActivity.this, FocusListActivity.class);
                Intent it = new Intent(UserHomeActivity.this, MainActivity.class);
                it.putExtra("objectId", objectId);
                startActivity(it);
            }
        });

        focusIdSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(UserHomeActivity.this, FocusListActivity.class);
                Intent it = new Intent(UserHomeActivity.this, MainActivity.class);
                it.putExtra("objectId", objectId);
                startActivity(it);
            }
        });

        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(UserHomeActivity.this, FollowersListActivity.class);
                Intent it = new Intent(UserHomeActivity.this, MainActivity.class);
                it.putExtra("objectId", followerListId);
                startActivity(it);
            }
        });

        followerSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(UserHomeActivity.this, FollowersListActivity.class);
                Intent it = new Intent(UserHomeActivity.this, MainActivity.class);
                it.putExtra("objectId", followerListId);
                startActivity(it);
            }
        });

        userTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userTopics.getTag().equals(0)) {
                    userTopics.setBackgroundResource(R.drawable.shape_6);
                    userNotes.setBackgroundResource(R.drawable.shape_7);
                    userTopics.setTag(1);
                    userNotes.setTag(0);
                    recyclerView.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                }
            }
        });

        userNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userNotes.getTag().equals(0)) {
                    userNotes.setBackgroundResource(R.drawable.shape_6);
                    userTopics.setBackgroundResource(R.drawable.shape_7);
                    userNotes.setTag(1);
                    userTopics.setTag(0);
                    lv.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                if (noteSum == 0)
                    Toast.makeText(getApplicationContext(), "该用户还未创建笔记本哦", Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // 点击获得该语录的objectId
                TextView t = (TextView) v.findViewById(R.id.saying_id);
                // Toast.makeText(getApplicationContext(), t.getText().toString(), Toast.LENGTH_SHORT).show();
                // 将该语录的objectId传递给语录详细页面
                Intent it = new Intent(UserHomeActivity.this, TopicActivity.class);
                it.putExtra("objectId", t.getText().toString());
                startActivity(it);
            }
        });

        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent it = new Intent(UserHomeActivity.this, PicturePreviewActivity.class);
                Intent it = new Intent(UserHomeActivity.this, MainActivity.class);
                it.putExtra("url", headViewUrl);
                startActivity(it);
            }
        });

    }

    //动态修改listview高度，使得listview能完全展开
    private void setListViewHeightBasedOnChildren() {
        if (lv == null) {
            return;
        }
        if (simpleAdapter == null) {
            return;
        }
        int totalHeight = 0;
        //Toast.makeText(getApplication(), Integer.toString(simpleAdapter.getCount()), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < simpleAdapter.getCount(); i++) {
            View listItem = simpleAdapter.getView(i, null, lv);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (simpleAdapter.getCount() - 1));
        lv.setLayoutParams(params);
    }



}
