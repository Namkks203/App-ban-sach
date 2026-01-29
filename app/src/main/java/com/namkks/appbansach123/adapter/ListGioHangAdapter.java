package com.namkks.appbansach123.adapter;

import android.content.Context;
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
import com.namkks.appbansach123.models.GioHang;
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.view.TTSPActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListGioHangAdapter extends RecyclerView.Adapter<ListGioHangAdapter.ViewHolder> {
    private ArrayList<GioHang> listGH;

    public ListGioHangAdapter(ArrayList<GioHang> listGH) {
        this.listGH = listGH;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemgiohang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listGH.get(position), listener);
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

        ProgressBar progressBar;
        ImageView itemghImage;
        TextView tenSachghTxt, giaTienghTxt, trusolBtn, soLuongghTxt, tangslBtn, giaThueTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemghImage = itemView.findViewById(R.id.item_anhSach);
            tenSachghTxt = itemView.findViewById(R.id.tenSachghTxt);
            giaTienghTxt = itemView.findViewById(R.id.giaTienghTxt);
            trusolBtn = itemView.findViewById(R.id.trusolBtn);
            soLuongghTxt = itemView.findViewById(R.id.soLuongghTxt);
            tangslBtn = itemView.findViewById(R.id.tangslBtn);
            progressBar = itemView.findViewById(R.id.imageProgress);
            giaThueTxt = itemView.findViewById(R.id.giaThueTxt);
        }

        public void bind(GioHang gioHang, OnDataChangedListener listener) {
            Sach sach = Sach.getSachById(gioHang.getSachId());
            loadImage(sach.getAnh());
            tenSachghTxt.setText(sach.getTenSach());
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            String tienvnd = formatter.format(sach.getGiaBan()) + " đ";
            giaTienghTxt.setText(String.format("Giá bán: %s", tienvnd));
            String tienThue = formatter.format(sach.getGiaThue()) + " đ";
            giaThueTxt.setText(String.format("Giá thuê: %s", tienThue));
            soLuongghTxt.setText(String.format("%d", gioHang.getSoLuong()));

            trusolBtn.setOnClickListener(v -> giamSoLuong(gioHang, listener));
            tangslBtn.setOnClickListener(v -> tangSoLuong(gioHang, listener));
        }

        private void giamSoLuong(GioHang gioHang, OnDataChangedListener listener) {
            int solMoi = gioHang.getSoLuong() - 1;
            if (solMoi <= 0) {
                GioHang.DeleteItemGH(gioHang.getKhachHangId(), gioHang.getSachId());
            } else {
                GioHang.UpdateSoLuong(gioHang.getKhachHangId(), gioHang.getSachId(), solMoi);
            }

            if (listener != null) {
                listener.onDataChanged();
            }
        }

        private void tangSoLuong(GioHang gioHang, OnDataChangedListener listener) {
            int solMoi = gioHang.getSoLuong() + 1;

            GioHang.UpdateSoLuong(gioHang.getKhachHangId(), gioHang.getSachId(), solMoi);

            if (listener != null) {
                listener.onDataChanged();
            }
        }

        private void loadImage(String url) {
            progressBar.setVisibility(View.VISIBLE);

            Glide.with(itemView)
                    .load(url)
                    .override(400, 600)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .transition(DrawableTransitionOptions.withCrossFade(200))
                    .listener(glideListener())
                    .into(itemghImage);
        }

        private RequestListener<Drawable> glideListener() {
            return new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(
                        @Nullable GlideException e,
                        Object model,
                        Target<Drawable> target,
                        boolean isFirstResource) {

                    progressBar.setVisibility(View.GONE);
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

                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            };
        }
    }
}
