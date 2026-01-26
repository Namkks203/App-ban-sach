package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.ChiTietDonHang;
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.view.ChiTietDonHangActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListDonHangAdapter extends RecyclerView.Adapter<ListDonHangAdapter.ViewHolder>{
    ArrayList<ChiTietDonHang> arrayList;
    Context context;

    public ListDonHangAdapter(ArrayList<ChiTietDonHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sach s = Sach.getSachById(arrayList.get(position).getId_sach());
        Glide.with(context).load(s.getAnh()).into(holder.itemdonhangImage);
        holder.item_donhangTenSach.setText(s.getTenSach());
        holder.item_donhangslsp.setText(ChiTietDonHang.getSoL(arrayList.get(position).getId()) + " sản phẩm");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String tienvnd = formatter.format(ChiTietDonHang.getTongTien(arrayList.get(position).getId())) + " đ";
        String tongtien = "Tổng tiền: " + tienvnd;
        SpannableString spannableString = new SpannableString(tongtien);

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 11, tongtien.length(), 0);

        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 11, tongtien.length(), 0);

        holder.item_donhangTT.setText(spannableString);
        int iddh = arrayList.get(position).getId();
        holder.item_donhangChiTietBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietDonHangActivity.class);
                intent.putExtra("iddh", iddh);
                context.startActivity(intent);
            }
        });
        holder.item_donhangHuyDonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc muốn hủy đơn hàng này?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(ChiTietDonHang.XoaDonHang(iddh)){
                                    Toast.makeText(context, "Hủy thành công!", Toast.LENGTH_SHORT).show();
                                    if (listener != null) {
                                        listener.onDataChanged();
                                    }
                                }else{
                                    Toast.makeText(context, "Hủy thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private ListGioHangAdapter.OnDataChangedListener listener;

    public void setOnDataChangedListener(ListGioHangAdapter.OnDataChangedListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView itemdonhangImage;
        TextView item_donhangTenSach, item_donhangslsp,item_donhangTT, item_donhangHuyDonBtn, item_donhangChiTietBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemdonhangImage = itemView.findViewById(R.id.item_donhangImage);
            item_donhangTenSach = itemView.findViewById(R.id.item_donhangTenSach);
            item_donhangslsp = itemView.findViewById(R.id.item_donhangslsp);
            item_donhangTT = itemView.findViewById(R.id.item_donhangTT);
            item_donhangHuyDonBtn = itemView.findViewById(R.id.item_donhangHuyDonBtn);
            item_donhangChiTietBtn = itemView.findViewById(R.id.item_donhangChiTietBtn);

        }
    }
}
