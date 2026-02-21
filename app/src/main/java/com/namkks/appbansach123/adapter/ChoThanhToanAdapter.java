package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.GioHang;
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.view.ChiTietDonHangActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChoThanhToanAdapter extends RecyclerView.Adapter<ChoThanhToanAdapter.ViewHolder>{
    ArrayList<GioHang> arrayList;

    public ChoThanhToanAdapter(ArrayList<GioHang> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchitietdonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView itemdonhangImage;
        TextView item_ctdhtenSach, item_ctdhGiaBan,item_ctdhGiaThue, item_ctdhSoL;
        ProgressBar imageProgress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemdonhangImage = itemView.findViewById(R.id.item_anhSach);
            item_ctdhtenSach = itemView.findViewById(R.id.item_ctdhTenSach);
            item_ctdhGiaBan = itemView.findViewById(R.id.item_ctdhGiaBan);
            item_ctdhGiaThue = itemView.findViewById(R.id.item_ctdhGiaThue);
            item_ctdhSoL = itemView.findViewById(R.id.item_ctdhSoL);
            imageProgress = itemView.findViewById(R.id.imageProgress);

        }

        public void bind(GioHang gioHang){
            Sach sach = Sach.getSachById(gioHang.getSachId());

            loadImage(sach.getAnh());
            item_ctdhtenSach.setText(sach.getTenSach());
            item_ctdhSoL.setText(String.format("x%d", gioHang.getSoLuong()));
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            item_ctdhGiaBan.setText(String.format("Giá Bán: %s đ", formatter.format(sach.getGiaBan())));
            item_ctdhGiaThue.setText(String.format("Giá thuê: %s đ", formatter.format(sach.getGiaThue())));
        }

        private void loadImage(String url) {
            imageProgress.setVisibility(View.VISIBLE);

            Glide.with(itemView)
                    .load(url)
                    .override(400, 600)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .transition(DrawableTransitionOptions.withCrossFade(200))
                    .listener(glideListener())
                    .into(itemdonhangImage);
        }

        private RequestListener<Drawable> glideListener() {
            return new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(
                        @Nullable GlideException e,
                        Object model,
                        Target<Drawable> target,
                        boolean isFirstResource) {

                    imageProgress.setVisibility(View.GONE);
                    Toast.makeText(itemView.getContext(),
                            "Load ảnh thất bại",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onResourceReady(
                        Drawable resource,
                        Object model,
                        Target<Drawable> target,
                        DataSource dataSource,
                        boolean isFirstResource) {

                    imageProgress.setVisibility(View.GONE);
                    return false;
                }
            };
        }
    }
}
