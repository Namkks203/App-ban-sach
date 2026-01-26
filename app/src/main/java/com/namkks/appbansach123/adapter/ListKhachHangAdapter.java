package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.KhachHang;
import com.namkks.appbansach123.view.SuaKHActivity;

import java.util.ArrayList;

public class ListKhachHangAdapter extends RecyclerView.Adapter<ListKhachHangAdapter.ViewHolder>{
    private ArrayList<KhachHang> listKH;
    private Context context;

    public ListKhachHangAdapter(Context context, ArrayList<KhachHang> listKH) {
        this.context = context;
        this.listKH = listKH;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_khanh_hang_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i = position;
        holder.idKH.setText("id: " + listKH.get(i).getId());
        holder.HoTenKH.setText(listKH.get(i).getHoTen());

        holder.SuaKHBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuaKHActivity.class);
                intent.putExtra("id_kh", listKH.get(i).getId());
                context.startActivity(intent);
            }
        });
        holder.XoaKHBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc muốn xóa khách hàng này?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int ia) {
                                if(KhachHang.XoaKH(listKH.get(i).getId())){
                                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                    if (listener != null) {
                                        listener.onDataChanged();
                                    }
                                }else {
                                    Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
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
        return listKH.size();
    }
    public interface OnDataChangedListener {
        void onDataChanged();
    }
    private OnDataChangedListener listener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView idKH, HoTenKH;
        Button SuaKHBtn, XoaKHBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idKH = itemView.findViewById(R.id.idKH);
            HoTenKH = itemView.findViewById(R.id.HoTenKH);
            SuaKHBtn = itemView.findViewById(R.id.SuaKHBtn);
            XoaKHBtn = itemView.findViewById(R.id.XoaKHBtn);
        }
    }
}
