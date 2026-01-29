package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.BinhLuan;
import java.util.ArrayList;

public class ListBinhLuanAdapter extends RecyclerView.Adapter<ListBinhLuanAdapter.ViewHolder> {
    ArrayList<BinhLuan> array;
    Context context;

    public ListBinhLuanAdapter(ArrayList<BinhLuan> array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhgia,parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.ratingBarDG.setRating(array.get(position).getSoSao());
        viewHolder.tenKhTxt.setText(array.get(position).getTen());
        viewHolder.danggiaTxt.setText(array.get(position).getDanhGia());
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RatingBar ratingBarDG;
        TextView tenKhTxt, danggiaTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBarDG = itemView.findViewById(R.id.ratingBarDG);
            tenKhTxt = itemView.findViewById(R.id.tenKHTxt);
            danggiaTxt = itemView.findViewById(R.id.danggiaTxT);
        }
    }
}
