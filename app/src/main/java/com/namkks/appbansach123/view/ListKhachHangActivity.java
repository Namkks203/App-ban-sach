package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.adapter.ListKhachHangAdapter;
import com.namkks.appbansach123.models.KhachHang;

public class ListKhachHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText timKiemKHTxt;
    TextView searchKHBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_khach_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        ActionBar();
        LoadData();
        TimKiem();
    }

    private void AnhXa(){
        toolbar = findViewById(R.id.toolbarTK);
        recyclerView = findViewById(R.id.recycleviewSXKH);
        timKiemKHTxt = findViewById(R.id.timKiemKHTxt);
        searchKHBtn = findViewById(R.id.searchKHBtn);
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
    private void TimKiem(){
        searchKHBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timKiemKHTxt.getText().toString() == ""){
                    LoadData();
                }else {
                    ListKhachHangAdapter listKhachHangAdapter = new ListKhachHangAdapter
                            (ListKhachHangActivity.this, KhachHang.TimKiemKH(timKiemKHTxt.getText().toString()));
                    recyclerView.setAdapter(listKhachHangAdapter);
                }
            }
        });
    }
    private void LoadData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListKhachHangAdapter listKhachHangAdapter = new ListKhachHangAdapter(this, KhachHang.getAllKH());
        recyclerView.setAdapter(listKhachHangAdapter);
    }
}