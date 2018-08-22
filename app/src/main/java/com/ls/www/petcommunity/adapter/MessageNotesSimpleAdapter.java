package com.ls.www.petcommunity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.activity.NoteActivity;
import com.ls.www.petcommunity.activity.UserHomeActivity;

import java.util.List;
import java.util.Map;

public class MessageNotesSimpleAdapter extends SimpleAdapter {

    Context context;
    List<? extends Map<String, ?>> Data;

    public MessageNotesSimpleAdapter(Context context, List<? extends Map<String, ?>> data,
                                       int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.Data = data; //获取各项数值用于不同intent的传值
        // TODO Auto-generated constructor stub
    }

    public View getView(int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = super.getView(position, convertView, parent);

        int p = position;
        ImageView image = (ImageView) v.findViewById(R.id.book_cover_image);
        TextView user_name = (TextView) v.findViewById(R.id.user_name);
        TextView book_name = (TextView) v.findViewById(R.id.book_name);

        final String user_id = Data.get(p).get("user_id").toString();
        final String book_id = Data.get(p).get("book_id").toString();

        // 点击不同控件,获取不同控件的值，触发不同事件(即进入不同界面)

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(v.getContext(), NoteActivity.class);
                it.putExtra("objectId", book_id);
                v.getContext().startActivity(it);
            }
        });

        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(v.getContext(), UserHomeActivity.class);
                it.putExtra("objectId", user_id);
                v.getContext().startActivity(it);
            }
        });

        book_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(v.getContext(), NoteActivity.class);
                it.putExtra("objectId", book_id);
                v.getContext().startActivity(it);
            }
        });

        return v;
    }
}
