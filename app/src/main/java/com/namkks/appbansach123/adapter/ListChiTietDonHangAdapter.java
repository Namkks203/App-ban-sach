package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.ChiTietDonHang;
import com.namkks.appbansach123.models.Sach;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListChiTietDonHangAdapter extends RecyclerView.Adapter<ListChiTietDonHangAdapter.ViewHolder>{
    ArrayList<ChiTietDonHang> arrayList;
    Context context;

    public ListChiTietDonHangAdapter(ArrayList<ChiTietDonHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchitietdonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sach s = Sach.getSachById(arrayList.get(position).getId_sach());
        Glide.with(context).load(s.getAnh()).into(holder.item_ctdhImage);
        holder.item_ctdhTenSach.setText(s.getTenSach());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String tienvnd = formatter.format(s.getGiaBan()) + " đ";
        holder.item_ctdhGia.setText(tienvnd);
        holder.item_ctdhSoL.setText("x"+ arrayList.get(position).getSoLuong());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView item_ctdhImage;
        TextView item_ctdhTenSach, item_ctdhSoL, item_ctdhGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            item_ctdhImage = itemView.findViewById(R.id.item_ctdhImage);
//            item_ctdhTenSach = itemView.findViewById(R.id.item_ctdhTenSach);
//            item_ctdhSoL = itemView.findViewById(R.id.item_ctdhSoL);
//            item_ctdhGia = itemView.findViewById(R.id.item_ctdhGia);
        }
    }
}
