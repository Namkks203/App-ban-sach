package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namkks.appbansach123.R;
import com.namkks.appbansach123.adapter.ListBinhLuanAdapter;
import com.namkks.appbansach123.models.BinhLuan;
import com.namkks.appbansach123.models.Sach;

import java.text.DecimalFormat;

public class TTSPActivity extends AppCompatActivity {
    Toolbar toolbar;
    Sach sach;
    ImageView ctspImage;
    TextView giaTxt, giaGiamTxt, maHangTxt, tacGiaTxt, loaiTxt, tenSachCTtxt,
    themGHbtn, vietDGBtn, rateTxt, moTaSach, muangayBtn;
    RecyclerView list_danhgia;
    RatingBar ratingBar;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ttspactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        ActionBar();
        LoadData();
        ThemGioHang();
    }

    private void AnhXa(){
        ctspImage = findViewById(R.id.ctspImage);
        giaTxt = findViewById(R.id.giaTxt);
        giaGiamTxt = findViewById(R.id.giagiamTxt);
        maHangTxt = findViewById(R.id.maHangTxt);
        tacGiaTxt = findViewById(R.id.tacGiaTxt);
        loaiTxt = findViewById(R.id.loaiTxt);
        tenSachCTtxt = findViewById(R.id.tenSachCTtxt);
        themGHbtn = findViewById(R.id.themGHbtn);
        toolbar = findViewById(R.id.toolbarTK);
        vietDGBtn = findViewById(R.id.vietDGBtn);
        list_danhgia = findViewById(R.id.list_danhgia);
        rateTxt = findViewById(R.id.rateTxt);
        ratingBar = findViewById(R.id.ratingBar);
        moTaSach = findViewById(R.id.motaSach);
        muangayBtn = findViewById(R.id.muangayBtn);
    }
    private void ShowVietDG(){
        vietDGBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TTSPActivity.this);

// Lấy LayoutInflater để tải layout tùy chỉnh
                LayoutInflater inflater = getLayoutInflater();

// Nạp layout vào dialog
                View dialogView = inflater.inflate(R.layout.dialog_danhgia, null);

// Gán layout tùy chỉnh vào AlertDialog
                builder.setView(dialogView);

                TextView guiDGBtn = dialogView.findViewById(R.id.guiDGBtn);
                EditText danhgiaTxt = dialogView.findViewById(R.id.danhgiaTxt);
                RatingBar ratingBarDG = dialogView.findViewById(R.id.ratingBarDG);
                AlertDialog dialog = builder.create();
                dialog.show();
                guiDGBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if(LoginActivity.kh != null){
//                            BinhLuan bl = new BinhLuan();
//                            bl.setDanhGia(danhgiaTxt.getText().toString());
//                            bl.setSoSao(ratingBarDG.getRating());
//                            bl.setId_sach(id);
//                            bl.setId_khachhang(LoginActivity.kh.getId());
//                            if(bl.addBinhLuan()){
//                                Toast.makeText(TTSPActivity.this, "Đánh giá thành công!", Toast.LENGTH_SHORT).show();
//                                recreate();
//                            }
//                        }else{
//                            Toast.makeText(TTSPActivity.this, "Vui lòng đăng nhập để đánh giá", Toast.LENGTH_SHORT).show();
//                        }
                        dialog.dismiss();
                    }
                });

            }
        });
    }
    private void MuaNgay(int id_sach){
        muangayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TTSPActivity.this)
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc muốn mua sản phẩm này?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                if(LoginActivity.kh == null){
//                                    Toast.makeText(TTSPActivity.this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
//                                }else{
//                                    ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
//                                    chiTietDonHang.setId_khachhang(LoginActivity.kh.getId());
//                                    if(chiTietDonHang.muaNgay(id_sach)){
//                                        Toast.makeText(TTSPActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
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
    private void LoadData(){
        id = getIntent().getIntExtra("sanpham",0);
        sach = Sach.getSachById(id);
        Glide.with(this).load(sach.getAnh()).into(ctspImage);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String tienvnd = formatter.format(sach.getGiaBan()) + " đ";
        giaTxt.setText(tienvnd);
        float giagiam = sach.getGiaBan() + ((float) (sach.getGiaBan() * 10) / 100);
        String tiengiamvnd = formatter.format(giagiam) + " đ";
        giaGiamTxt.setText(tiengiamvnd);
        giaGiamTxt.setPaintFlags(giaGiamTxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tenSachCTtxt.setText(sach.getTenSach());
        tacGiaTxt.setText(sach.getNhaXuatBan());
        loaiTxt.setText(sach.getNamXuatBan());
        maHangTxt.setText(sach.getId() + "");
        moTaSach.setText(sach.getMoTa());
        ratingBar.setRating(BinhLuan.getSaoTB(id));
        rateTxt.setText(BinhLuan.getSaoTB(id) + "/5");
        ListBinhLuanAdapter listBinhLuanAdapter = new ListBinhLuanAdapter(BinhLuan.getBinhLuanOfSach(id), this);
        list_danhgia.setLayoutManager(new LinearLayoutManager(this));
        list_danhgia.setAdapter(listBinhLuanAdapter);
        ShowVietDG();
        MuaNgay(id);
    }
    @SuppressLint("RestrictedApi")
    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void ThemGioHang(){
        themGHbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(LoginActivity.kh != null){
//                    if(GioHang.ThemGioHang(LoginActivity.kh.getId(), sach.getId())){
//                        Toast.makeText(TTSPActivity.this, "Thêm giỏ hàng thành công!",Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(TTSPActivity.this, "Đã có sản phẩm này trong giỏ hàng!",Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    Toast.makeText(TTSPActivity.this, "Bạn phải đăng nhập để sử dụng chức năng này!",Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}