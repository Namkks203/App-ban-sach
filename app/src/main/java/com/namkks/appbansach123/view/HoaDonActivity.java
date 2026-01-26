package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.adapter.ListHoaDonAdapter;
import com.namkks.appbansach123.models.ChiTietHoaDon;

import java.util.ArrayList;

public class HoaDonActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewHD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hoa_don);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewHD = findViewById(R.id.recycleviewHD);
        toolbar = findViewById(R.id.toolbarTK);
        ActionBar();
        LoadData();
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
    private void LoadData(){
//        recyclerViewHD.setLayoutManager(new LinearLayoutManager(this));
//        ArrayList<ChiTietHoaDon> listHD = ChiTietHoaDon.getListHoaDon(LoginActivity.kh.getId());
//        ListHoaDonAdapter listGioHangAdapter = new ListHoaDonAdapter(listHD, this);
//        recyclerViewHD.setAdapter(listGioHangAdapter);
    }
}