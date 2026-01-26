package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.ChiTietHoaDon;
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.view.ChiTietHoaDonActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListHoaDonAdapter extends RecyclerView.Adapter<ListHoaDonAdapter.ViewHolder>{
    ArrayList<ChiTietHoaDon> arrayList;
    Context context;

    public ListHoaDonAdapter(ArrayList<ChiTietHoaDon> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhoadon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sach s = Sach.getSachById(arrayList.get(position).getId_sach());
        Glide.with(context).load(s.getAnh()).into(holder.itemdonhangImage);
        holder.item_donhangTenSach.setText(s.getTenSach());
        holder.item_donhangslsp.setText(ChiTietHoaDon.getSoLuong(arrayList.get(position).getId()) + " sản phẩm");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String tienvnd = formatter.format(ChiTietHoaDon.getTongTien(arrayList.get(position).getId())) + " đ";
        String tongtien = "Tổng tiền: " + tienvnd;
        SpannableString spannableString = new SpannableString(tongtien);

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 11, tongtien.length(), 0);

        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 11, tongtien.length(), 0);

        holder.item_donhangTT.setText(spannableString);
        int iddh = arrayList.get(position).getId();
        holder.item_donhangChiTietBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietHoaDonActivity.class);
                intent.putExtra("iddh", iddh);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView itemdonhangImage;
        TextView item_donhangTenSach, item_donhangslsp,item_donhangTT, item_donhangChiTietBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemdonhangImage = itemView.findViewById(R.id.item_hoadonImage);
            item_donhangTenSach = itemView.findViewById(R.id.item_hoadonTenSach);
            item_donhangslsp = itemView.findViewById(R.id.item_hoadonslsp);
            item_donhangTT = itemView.findViewById(R.id.item_hoadonTT);
            item_donhangChiTietBtn = itemView.findViewById(R.id.item_hoadonChiTietBtn);

        }
    }
}
