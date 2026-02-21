package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import com.namkks.appbansach123.models.DonHang;
import com.namkks.appbansach123.models.Sach;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListDonHangNVAdapter extends RecyclerView.Adapter<ListDonHangNVAdapter.ViewHolder>{
    ArrayList<DonHang> arrayList;
    Context context;

    public ListDonHangNVAdapter(ArrayList<DonHang> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanlydonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id_qldh.setText("#" + arrayList.get(position).getId());
        Sach s = Sach.getSachById(0);
        Glide.with(context).load(s.getAnh()).into(holder.itemdonhangImage);
        holder.item_donhangTenSach.setText(s.getTenSach());
        holder.item_donhangslsp.setText(" sản phẩm");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String tienvnd = formatter.format(0) + " đ";
        String tongtien = "Tổng tiền: " + tienvnd;
        SpannableString spannableString = new SpannableString(tongtien);

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 11, tongtien.length(), 0);

        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 11, tongtien.length(), 0);

        holder.item_donhangTT.setText(spannableString);
        int iddh = arrayList.get(position).getId();
        int i = position;
//        holder.item_qldhDaThanhToan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(LoginActivity.nv != null){
//                    LocalDate day = LocalDate.now();
//                    String dayLap = day.getDayOfMonth() +"-" +day.getMonthValue() +"-"+day.getYear();
//                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
//                    chiTietHoaDon.setNgayLapHoaDon(dayLap);
//                    chiTietHoaDon.setId_khachhang(arrayList.get(i).getId_khachhang());
//                    chiTietHoaDon.setId_nhanven(LoginActivity.nv.getId());
//                    if(chiTietHoaDon.addChiTietHoaDon(iddh)){
//                        Toast.makeText(context, "Đã thanh toán thành công!", Toast.LENGTH_SHORT).show();
//                        if (listener != null) {
//                            listener.onDataChanged();
//                        }
//                    }else{
//                        Toast.makeText(context, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
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
    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private OnDataChangedListener listener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView itemdonhangImage;
        TextView item_donhangTenSach, item_donhangslsp,item_donhangTT, item_donhangHuyDonBtn, item_qldhDaThanhToan, id_qldh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemdonhangImage = itemView.findViewById(R.id.item_donhangImage);
            item_donhangTenSach = itemView.findViewById(R.id.item_donhangTenSach);
            item_donhangslsp = itemView.findViewById(R.id.item_donhangslsp);
            item_donhangTT = itemView.findViewById(R.id.item_donhangTT);
            item_donhangHuyDonBtn = itemView.findViewById(R.id.item_donhangHuyDonBtn);
            item_qldhDaThanhToan = itemView.findViewById(R.id.item_qldhDaThanhToan);
            id_qldh = itemView.findViewById(R.id.id_qldh);
        }
    }
}
