package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.view.SuaSachActivity;

import java.util.ArrayList;


public class ListSachSuaXoaAdapter extends RecyclerView.Adapter<ListSachSuaXoaAdapter.ViewHolder>{
    private ArrayList<Sach> listSach;
    private Context context;

    public ListSachSuaXoaAdapter(ArrayList<Sach> listSach, Context context) {
        this.listSach = listSach;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsachsuaxoa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(listSach.get(position).getAnh()).into(holder.item_suaxoaImage);
        holder.suaxoaTenS.setText(listSach.get(position).getTenSach());
        int id = position;
        holder.suaxoaSuaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SuaSachActivity.class);
                intent.putExtra("id_sach", listSach.get(id).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.suaxoaXoaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc muốn xóa sản phẩm này?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(Sach.XoaSach(listSach.get(id).getId())){
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
        return listSach.size();
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private ListGioHangAdapter.OnDataChangedListener listener;

    public void setOnDataChangedListener(ListGioHangAdapter.OnDataChangedListener listener) {
        this.listener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView item_suaxoaImage;
        TextView suaxoaTenS;
        Button suaxoaSuaBtn, suaxoaXoaBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_suaxoaImage = itemView.findViewById(R.id.item_suaxoaImage);
            suaxoaTenS = itemView.findViewById(R.id.suaxoaTenS);
            suaxoaSuaBtn = itemView.findViewById(R.id.suaxoaSuaBtn);
            suaxoaXoaBtn = itemView.findViewById(R.id.suaxoaXoaBtn);
        }
    }
}
