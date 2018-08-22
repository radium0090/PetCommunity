package com.ls.www.petcommunity.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ls.www.petcommunity.R;
import com.ls.www.petcommunity.activity.AllArticlesActivity;
import com.ls.www.petcommunity.activity.HotNotesActivity;
import com.ls.www.petcommunity.activity.HotTopicsActivity;
import com.ls.www.petcommunity.activity.HotUsersActivity;

import java.util.List;

public class FragmentRecyclerAdapter extends RecyclerView.Adapter<FragmentRecyclerAdapter.ViewHolder> {

    private List<Integer> cardList;
    private OnItemClickListener mOnItemClickListener = null;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View cardView;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            cardView = view;
            image = (ImageView) view.findViewById(R.id.image);
        }
    }

    public FragmentRecyclerAdapter(List<Integer> cardList) {
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_recycler, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 直接在adapter设置监听，点击后进行传值、页面跳转
                int position = holder.getAdapterPosition();
                switch (position) {
                    case 0:
                        Intent it1 = new Intent(v.getContext(), HotUsersActivity.class);
                        v.getContext().startActivity(it1);
                        break;
                    case 1:
                        Intent it2 = new Intent(v.getContext(), HotTopicsActivity.class);
                        v.getContext().startActivity(it2);
                        break;
                    case 2:
                        Intent it3 = new Intent(v.getContext(), HotNotesActivity.class);
                        v.getContext().startActivity(it3);
                        break;
                    case 3:
                        Intent it4 = new Intent(v.getContext(), AllArticlesActivity.class);
                        v.getContext().startActivity(it4);
                        break;
                    default:
                        break;
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Integer image_id = cardList.get(position);
        holder.image.setImageResource(image_id);

        //设置点击和长按事件
        if (mOnItemClickListener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public interface OnItemClickListener {//回调接口
        void onClick(int position);//单击，设置为view是因为我想获得子控件的值
        void onLongClick(int position);//长按
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
