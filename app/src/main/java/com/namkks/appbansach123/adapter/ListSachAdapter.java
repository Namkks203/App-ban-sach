package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.namkks.appbansach123.view.TTSPActivity;

import java.text.DecimalFormat;
import java.util.List;

public class ListSachAdapter extends RecyclerView.Adapter<ListSachAdapter.SachViewHolder> {

    private final List<Sach> listSach;

    // Tạo 1 lần – tái sử dụng
    private static final DecimalFormat PRICE_FORMAT =
            new DecimalFormat("###,###,###");

    public ListSachAdapter(List<Sach> listSach) {
        this.listSach = listSach;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemsach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        holder.bind(listSach.get(position));
    }

    @Override
    public long getItemId(int position) {
        return listSach.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return listSach == null ? 0 : listSach.size();
    }

    // ====================== ViewHolder ======================
    static class SachViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSach;
        TextView tenSachTxt, giaSachTxt;
        ProgressBar imageProgress;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSach = itemView.findViewById(R.id.item_anhSach);
            tenSachTxt = itemView.findViewById(R.id.tenSachTxt);
            giaSachTxt = itemView.findViewById(R.id.giaSachTxt);
            imageProgress = itemView.findViewById(R.id.imageProgress);
        }

        void bind(Sach sach) {
            tenSachTxt.setText(sach.getTenSach());
            giaSachTxt.setText(
                    PRICE_FORMAT.format(sach.getGiaBan()) + " đ"
            );

            loadImage(sach.getAnh());

            itemView.setOnClickListener(v -> {
                Context context = itemView.getContext();
                Intent intent = new Intent(context, TTSPActivity.class);
                intent.putExtra("sanpham", sach.getId());
                context.startActivity(intent);
            });
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
                    .into(imgSach);
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
