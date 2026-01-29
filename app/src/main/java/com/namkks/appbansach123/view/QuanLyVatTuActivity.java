package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.adapter.ListQuanLyVatTuAdater;
import com.namkks.appbansach123.adapter.ListSachSuaXoaAdapter;
import com.namkks.appbansach123.models.NhanVien;
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.models.TonKho;

public class QuanLyVatTuActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewSXSach;
    TextView suaXoaSachTitle;
    ImageView menuIcon;
    DrawerLayout drawerLayout;
    LinearLayout addMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_sach_sua_xoa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        ActionBar();
        setAction();
        LoadData();
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarTK);
        recyclerViewSXSach = findViewById(R.id.recycleviewSXSach);
        suaXoaSachTitle = findViewById(R.id.suaXoaSachTitle);
        menuIcon = findViewById(R.id.menuIcon);
        drawerLayout = findViewById(R.id.drawerlayout);
        addMaterial = findViewById(R.id.addMaterial);
    }

    @SuppressLint("RestrictedApi")
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        suaXoaSachTitle.setText("Quản lý vật tư");
    }

    private void LoadData() {
        recyclerViewSXSach.setLayoutManager(new LinearLayoutManager(this));
        NhanVien nhanVien = NhanVien.getNhanVien(LoginActivity.tk.getId());
        ListQuanLyVatTuAdater listQuanLyVatTuAdater = new ListQuanLyVatTuAdater(
                TonKho.getListTonKhoByChiNhanhId(nhanVien.getChiNhanhId()));
        recyclerViewSXSach.setAdapter(listQuanLyVatTuAdater);
    }
    private void setAction(){
        menuIcon.setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.END);
        });

        addMaterial.setOnClickListener(view -> {
            Intent intent = new Intent(
                    QuanLyVatTuActivity.this,
                    NhapVatTuActivity.class);
            startActivity(intent);
        });
    }
}