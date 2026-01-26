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
import com.namkks.appbansach123.models.GioHang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListGioHangAdapter extends RecyclerView.Adapter<ListGioHangAdapter.ViewHolder>{
    private ArrayList<GioHang> listGH;
    private int id_kh;
    Context context;

    public ListGioHangAdapter(ArrayList<GioHang> listGH, Context context, int id_kh) {
        this.listGH = listGH;
        this.context = context;
        this.id_kh = id_kh;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemgiohang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(listGH.get(position).getAnh()).into(holder.itemghImage);
        holder.tenSachghTxt.setText(listGH.get(position).getTenSach());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String tienvnd = formatter.format(listGH.get(position).getGiaBan()) + " đ";
        holder.giaTienghTxt.setText(tienvnd);
        holder.soLuongghTxt.setText(listGH.get(position).getSoLuong() + "");

        int item = position;
        int sol = listGH.get(position).getSoLuong();
        holder.trusolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int solMoi = sol - 1;
                if(solMoi <= 0){
                    listGH.get(item).DeleteItemGH(id_kh);
                }else{
                    listGH.get(item).UpdateSoLuong(id_kh, solMoi);
                    holder.soLuongghTxt.setText(listGH.get(item).getSoLuong() + "");
                }
                if (listener != null) {
                    listener.onDataChanged();
                }
            }
        });
        holder.tangslBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int solMoi = sol + 1;
                listGH.get(item).UpdateSoLuong(id_kh, solMoi);
                holder.soLuongghTxt.setText(listGH.get(item).getSoLuong() + "");
                if (listener != null) {
                    listener.onDataChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGH.size();
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private OnDataChangedListener listener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemghImage;
        TextView tenSachghTxt,giaTienghTxt,trusolBtn,soLuongghTxt,tangslBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemghImage = itemView.findViewById(R.id.itemghImage);
            tenSachghTxt = itemView.findViewById(R.id.tenSachghTxt);
            giaTienghTxt = itemView.findViewById(R.id.giaTienghTxt);
            trusolBtn = itemView.findViewById(R.id.trusolBtn);
            soLuongghTxt = itemView.findViewById(R.id.soLuongghTxt);
            tangslBtn = itemView.findViewById(R.id.tangslBtn);
        }
    }
}
