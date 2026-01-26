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
import com.namkks.appbansach123.adapter.ListGioHangAdapter;
import com.namkks.appbansach123.adapter.ListSachSuaXoaAdapter;
import com.namkks.appbansach123.models.GioHang;
import com.namkks.appbansach123.models.Sach;

public class ListSachSuaXoaActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewSXSach;
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
        LoadData();
    }

    private void AnhXa(){
        toolbar = findViewById(R.id.toolbarTK);
        recyclerViewSXSach = findViewById(R.id.recycleviewSXSach);
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
        recyclerViewSXSach.setLayoutManager(new LinearLayoutManager(this));
        ListSachSuaXoaAdapter listSachSuaXoaAdapter = new ListSachSuaXoaAdapter(Sach.getAllSach());
        recyclerViewSXSach.setAdapter(listSachSuaXoaAdapter);
        listSachSuaXoaAdapter.setOnDataChangedListener(new ListGioHangAdapter.OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                LoadData();
            }
        });
    }
}