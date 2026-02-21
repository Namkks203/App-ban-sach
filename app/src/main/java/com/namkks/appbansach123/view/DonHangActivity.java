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
import com.namkks.appbansach123.adapter.ListDonHangAdapter;
import com.namkks.appbansach123.adapter.ListGioHangAdapter;


public class DonHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewDH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewDH = findViewById(R.id.recycleviewDH);
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
//        recyclerViewDH.setLayoutManager(new LinearLayoutManager(this));
//        ListDonHangAdapter listGioHangAdapter = new ListDonHangAdapter(ChiTietDonHang.getListDonHang(LoginActivity.kh.getId()),
//                this);
//        recyclerViewDH.setAdapter(listGioHangAdapter);
//        listGioHangAdapter.setOnDataChangedListener(new ListGioHangAdapter.OnDataChangedListener() {
//            @Override
//            public void onDataChanged() {
//                recreate();
//            }
//        });
    }
}