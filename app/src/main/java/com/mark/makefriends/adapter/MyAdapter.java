package com.mark.makefriends.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder{
        public ImageView user_img;
        public TextView user_info;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();

            view = mInflater.inflate(R.layout.item, viewGroup, false);
            holder.user_img = (ImageView)view.findViewById(R.id.user_img);
            holder.user_info = (TextView)view.findViewById(R.id.user_info);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }

        //holder.user_img.setImageURI(mData.get(i).get("img"));
        //holder.user_img.setBackgroundResource((Integer)mData.get(i).get("img"));
        holder.user_info.setText((String)mData.get(i).get("info"));

        return view;
    }

}
