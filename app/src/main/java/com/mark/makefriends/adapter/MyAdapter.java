package com.mark.makefriends.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mark.makefriends.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/4.
 */
public class MyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Map<String, Object>> mData;

    public MyAdapter(Context context, List<Map<String, Object>> data){
        this.mInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder{
        public SimpleDraweeView user_img;
        public TextView nick;
        public ImageView gender;
        public TextView age;
        public TextView location;
        public TextView sign;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null){
            holder = new ViewHolder();

            view = mInflater.inflate(R.layout.item, viewGroup, false);
            holder.user_img = (SimpleDraweeView)view.findViewById(R.id.user_img);
            holder.nick = (TextView)view.findViewById(R.id.nick);
            holder.gender = (ImageView)view.findViewById(R.id.gender);
            holder.age = (TextView)view.findViewById(R.id.age);
            holder.location = (TextView)view.findViewById(R.id.location);
            holder.sign = (TextView)view.findViewById(R.id.sign);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }

        holder.user_img.setImageURI((Uri) mData.get(i).get("img"));
        holder.nick.setText((String) mData.get(i).get("nick") + "  ");
        holder.age.setText(Integer.toString((Integer)mData.get(i).get("age")) + "岁  ");
        holder.location.setText((String) mData.get(i).get("location"));
        holder.sign.setText((String) mData.get(i).get("sign"));
        if ((Integer)mData.get(i).get("gender") == 0){
            holder.gender.setImageResource(R.drawable.male);
        }else {
            holder.gender.setImageResource(R.drawable.female);
        }

        return view;
    }

}
