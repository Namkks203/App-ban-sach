package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.adapter.ListDonHangNVAdapter;

public class QuanLyDonHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewDH;
    EditText timkiemTxt;
    TextView searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewDH = findViewById(R.id.recycleviewDH);
        toolbar = findViewById(R.id.toolbarTK);
        timkiemTxt = findViewById(R.id.timKiemTxt);
        searchBtn = findViewById(R.id.searchBtn);
        ActionBar();
        LoadData();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timkiemTxt.getText().toString().equals("")){
                    Toast.makeText(QuanLyDonHangActivity.this, "Ô tìm kiếm đang trống!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(QuanLyDonHangActivity.this, QuanLyDonHangActivity.class);
                    intent.putExtra("tk", timkiemTxt.getText().toString());
                    finish();
                    startActivity(intent);
                }
            }
        });
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
        recyclerViewDH.setLayoutManager(new LinearLayoutManager(this));

    }
}