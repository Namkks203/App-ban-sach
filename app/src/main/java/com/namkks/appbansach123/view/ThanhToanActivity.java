package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.adapter.ChoThanhToanAdapter;
import com.namkks.appbansach123.adapter.ListGioHangAdapter;
import com.namkks.appbansach123.models.GioHang;
import com.namkks.appbansach123.models.KhachHang;
import com.namkks.appbansach123.models.Sach;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThanhToanActivity extends AppCompatActivity {

    TextView soLSachInGioHang, tongGiaThue, tongGiaCoc, tongTienThue, tongTienCoc,
    hoTenKH, sdtKH, diaChiKH, textQR;
    Spinner spinnerTT;
    RecyclerView recycleViewCTDH;
    ImageView maQRTT;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_toan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        setupToolbar();
        loadData();
        setupActions();
    }

    private void initView(){
        soLSachInGioHang = findViewById(R.id.soLSachInGioHang);
        tongGiaThue = findViewById(R.id.tongGiaThue);
        tongGiaCoc = findViewById(R.id.tongTienCoc);
        tongTienThue = findViewById(R.id.TongTienThue);
        tongTienCoc = findViewById(R.id.TongTienCoc);
        hoTenKH = findViewById(R.id.hoTenKH);
        sdtKH = findViewById(R.id.sdtKH);
        diaChiKH = findViewById(R.id.diaChiKH);
        textQR = findViewById(R.id.textQR);
        spinnerTT = findViewById(R.id.spinerTT);
        recycleViewCTDH = findViewById(R.id.recycleViewCTDH);
        maQRTT = findViewById(R.id.qrThanhToan);
        toolbar = findViewById(R.id.toolbarTK);
    }

    @SuppressLint("RestrictedApi")
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitle("Sửa sách");
    }

    private void loadData(){
        if(LoginActivity.tk == null || !LoginActivity.tk.getLoaiTaiKhoan().equals("khach_hang"))
            return;
        KhachHang kh = KhachHang.getKhachHangByTaiKhoanId(LoginActivity.tk.getId());
        ArrayList<String> list = new ArrayList<>();
        list.add("Thanh toán khi nhận hàng.");
        list.add("Thanh toán chuyển khoản.");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                list
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTT.setAdapter(adapter);

        hoTenKH.setText(kh.getTen());
        sdtKH.setText(kh.getDienThoai());
        diaChiKH.setText(kh.getDiaChi());

        ArrayList<GioHang> gioHangs = GioHang.getGioHangByKhachHangId(kh.getId());
        int soL = 0;
        int giaThue = 0;
        int tienCoc = 0;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        for(GioHang gioHang: gioHangs){
            soL = soL + gioHang.getSoLuong();
            Sach sach = Sach.getSachById(gioHang.getSachId());
            giaThue += sach.getGiaThue() * gioHang.getSoLuong();
            tienCoc += sach.getGiaBan() * gioHang.getSoLuong();
        }

        soLSachInGioHang.setText(String.format("%d", soL));
        tongGiaThue.setText(String.format("%s đ", formatter.format(giaThue)));
        tongTienThue.setText(String.format("Tổng tiền thuê: %s đ", formatter.format(giaThue)));

        tongGiaCoc.setText(String.format("%s đ", formatter.format(tienCoc)));
        tongTienCoc.setText(String.format("Tổng tiền cọc: %s đ", formatter.format(tienCoc)));

        recycleViewCTDH.setLayoutManager(new LinearLayoutManager(this));
        ChoThanhToanAdapter choThanhToanAdapter = new ChoThanhToanAdapter(gioHangs);
        recycleViewCTDH.setAdapter(choThanhToanAdapter);
    }

    private void setupActions(){
        spinnerTT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if(selected.equals("Thanh toán chuyển khoản.")){
                    textQR.setVisibility(View.VISIBLE);
                    maQRTT.setVisibility(View.VISIBLE);
                }else {
                    textQR.setVisibility(View.GONE);
                    maQRTT.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
}