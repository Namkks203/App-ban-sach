package com.namkks.appbansach123.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.models.SachTacGia;
import com.namkks.appbansach123.models.SachTheLoai;
import com.namkks.appbansach123.view.SuaSachActivity;

import java.util.ArrayList;


public class ListSachSuaXoaAdapter extends RecyclerView.Adapter<ListSachSuaXoaAdapter.ViewHolder> {
    private ArrayList<Sach> listSach;

    public ListSachSuaXoaAdapter(ArrayList<Sach> listSach) {
        this.listSach = listSach;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsachsuaxoa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listSach.get(position));
    }

    @Override
    public int getItemCount() {
        return listSach == null ? 0 : listSach.size();
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private OnDataChangedListener listener;

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_suaxoaImage;
        TextView suaxoaTenS;
        Button suaxoaSuaBtn, suaxoaXoaBtn;
        ProgressBar imageProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_suaxoaImage = itemView.findViewById(R.id.item_suaxoaImage);
            suaxoaTenS = itemView.findViewById(R.id.suaxoaTenS);
            suaxoaSuaBtn = itemView.findViewById(R.id.suaxoaSuaBtn);
            suaxoaXoaBtn = itemView.findViewById(R.id.suaxoaXoaBtn);
            imageProgress = itemView.findViewById(R.id.imageProgress);
        }

        void bind(Sach sach) {
            suaxoaTenS.setText(sach.getTenSach());

            loadImage(sach.getAnh());
            Context context = itemView.getContext();
            suaxoaSuaBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SuaSachActivity.class);
                    intent.putExtra("id_sach", sach.getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            suaxoaXoaBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle("Xác nhận")
                            .setMessage("Bạn có chắc muốn xóa sản phẩm này?")
                            .setCancelable(false)
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if (SachTacGia.xoaSachTacGia(sach.getId()) &
                                            SachTheLoai.xoaSachTheLoai(sach.getId()) &
                                            Sach.xoaSach(sach.getId())) {
                                        Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                        if (listener != null) {
                                            listener.onDataChanged();
                                        }
                                    } else {
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

        private void loadImage(String url) {
            imageProgress.setVisibility(View.VISIBLE);

            Glide.with(itemView)
                    .load(url)
                    .override(400, 600)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .transition(DrawableTransitionOptions.withCrossFade(200))
                    .listener(glideListener())
                    .into(item_suaxoaImage);
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
