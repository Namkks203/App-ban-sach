package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.view.TTSPActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListSachAdapter extends RecyclerView.Adapter<ListSachAdapter.ViewHolder> {
    private ArrayList<Sach> listSach;
    Context context;

    public ListSachAdapter(Context context, ArrayList<Sach> listSach) {
        this.context = context;
        this.listSach = listSach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(listSach.get(i).getAnh()).into(viewHolder.itemsachImage);
        viewHolder.tenSachTxt.setText(listSach.get(i).getTenSach());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String tienvnd = formatter.format(listSach.get(i).getGiaTien()) + " đ";
        viewHolder.giaSachTxt.setText(tienvnd);

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(context, TTSPActivity.class);
                intent.putExtra("sanpham", listSach.get(pos).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listSach.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView itemsachImage;
        TextView tenSachTxt;
        TextView giaSachTxt;
        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View view) {
            super(view);
            itemsachImage = view.findViewById(R.id.item_sachimage);
            tenSachTxt = view.findViewById(R.id.tenSachTxt);
            giaSachTxt = view.findViewById(R.id.giaSachTxt);
            view.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition());
        }
    }
}
