package com.namkks.appbansach123.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.models.TonKho;

import java.util.ArrayList;

public class ListQuanLyVatTuAdater extends RecyclerView.Adapter<ListQuanLyVatTuAdater.ViewHolder>{
    private ArrayList<TonKho> tonKhoArrayList;

    public ListQuanLyVatTuAdater(ArrayList<TonKho> tonKhoArrayList) {
        this.tonKhoArrayList = tonKhoArrayList;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ListQuanLyVatTuAdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanlyvattu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListQuanLyVatTuAdater.ViewHolder holder, int position) {
        holder.bind(tonKhoArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return tonKhoArrayList == null ? 0 : tonKhoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ProgressBar imageProgress;
        ImageView item_anhQuanLyVatTu;
        TextView item_tenSachQuanLyVatTu;
        TextView item_soLuongTonKho;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_anhQuanLyVatTu = itemView.findViewById(R.id.item_anhQuanLyVatTu);
            item_tenSachQuanLyVatTu = itemView.findViewById(R.id.item_tenSachQuanLyVatTu);
            item_soLuongTonKho = itemView.findViewById(R.id.item_soLuongTonKho);
            imageProgress = itemView.findViewById(R.id.imageProgress);
        }
        @SuppressLint("DefaultLocale")
        void bind(TonKho tonKho){
            Sach sach = Sach.getSachById(tonKho.getSachId());
            loadImage(sach.getAnh());
            item_tenSachQuanLyVatTu.setText(sach.getTenSach());
            item_soLuongTonKho.setText(String.format("Tồn kho: %d", tonKho.getSoLuong()));
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
                    .into(item_anhQuanLyVatTu);
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
