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
import com.namkks.appbansach123.models.Sach;

public class ThemSachActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText tenSachThemTxt, tacGiaThemTxt,loaiThemTxt,nxbThemTxt,giaThemTxt,anhThemTxt, moTaTxt;
    Button themSachBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_sach);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        ActionBar();
        ThemSach();
    }
    private void AnhXa(){
        tenSachThemTxt = findViewById(R.id.tenSachThemTxt);
        tacGiaThemTxt = findViewById(R.id.tacGiaThemTxt);
        loaiThemTxt = findViewById(R.id.loaiThemTxt);
        nxbThemTxt = findViewById(R.id.nxbThemTxt);
        giaThemTxt = findViewById(R.id.giaThemTxt);
        anhThemTxt = findViewById(R.id.anhThemTxt);
        themSachBtn = findViewById(R.id.themSachBtn);
        toolbar = findViewById(R.id.toolbarTK);
        moTaTxt = findViewById(R.id.motaTxt);
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
    private void ThemSach(){
        themSachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sach s = new Sach();
                s.setTenSach(tenSachThemTxt.getText().toString());
                s.setTacGia(tacGiaThemTxt.getText().toString());
                s.setLoai(loaiThemTxt.getText().toString());
                s.setNxb(nxbThemTxt.getText().toString());
                s.setGiaTien(Integer.parseInt(giaThemTxt.getText().toString()));
                s.setAnh(anhThemTxt.getText().toString());
                s.setMoTa(moTaTxt.getText().toString());
                if(s.ThemSach()){
                    Toast.makeText(ThemSachActivity.this, "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }else{
                    Toast.makeText(ThemSachActivity.this, "Thêm sách thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}