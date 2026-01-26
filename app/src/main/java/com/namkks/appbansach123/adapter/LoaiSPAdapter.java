package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.view.TimKiemActivity;

import java.util.List;

public class LoaiSPAdapter extends BaseAdapter {
    List<String> array;
    Context context;

    public LoaiSPAdapter(Context context, List<String> array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView texttensp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.itemloaisach, null);
            viewHolder.texttensp = view.findViewById(R.id.item_tensp);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.texttensp.setText(array.get(i));
        viewHolder.texttensp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TimKiemActivity.class);
                intent.putExtra("loai", array.get(i));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
