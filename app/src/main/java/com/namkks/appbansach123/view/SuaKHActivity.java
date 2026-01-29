package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.KhachHang;

public class SuaKHActivity extends AppCompatActivity {
    EditText tenKHSuaTxt, sdtKHSuaTxt, mkSuaTxt, diaChiSuaTxt;
    Button suaKHBtn;
    Toolbar toolbar;
    KhachHang kh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sua_khactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        ActionBar();
        LoadData();
        SuaKH();
    }

    private void AnhXa(){
        toolbar = findViewById(R.id.toolbarTK);
        tenKHSuaTxt = findViewById(R.id.tenKHSuaTxt);
        sdtKHSuaTxt = findViewById(R.id.SDTSuaTxt);
        mkSuaTxt = findViewById(R.id.MKSuaTxt);
        diaChiSuaTxt = findViewById(R.id.DiaChiSuaTxt);
        suaKHBtn = findViewById(R.id.suaKHBtn);
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
        int idkh = getIntent().getIntExtra("id_kh", 0);
        kh = KhachHang.getKH(idkh);
        if(kh != null){
            tenKHSuaTxt.setText(kh.getTen());
            sdtKHSuaTxt.setText(kh.getDienThoai());
            mkSuaTxt.setText(kh.getEmail());
            diaChiSuaTxt.setText(kh.getDiaChi());
        }
    }
    private void SuaKH(){
        suaKHBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kh != null){
                    KhachHang kh2 = new KhachHang();
                    kh2.setId(kh.getId());
                    kh2.setTen(tenKHSuaTxt.getText().toString());
                    kh2.setDienThoai(sdtKHSuaTxt.getText().toString());
                    kh2.setEmail(mkSuaTxt.getText().toString());
                    kh2.setDiaChi(diaChiSuaTxt.getText().toString());

                    if(kh2.SuaKH()){
                        Toast.makeText(SuaKHActivity.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SuaKHActivity.this, ListKhachHangActivity.class);
                        finish();
                        startActivity(intent);
                    }else{
                        Toast.makeText(SuaKHActivity.this, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}