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

public class SuaSachActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText tenSachThemTxt, tacGiaThemTxt,loaiThemTxt,nxbThemTxt,giaThemTxt,anhThemTxt, motaTxt;
    Button themSachBtn;
    Sach s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sua_sach);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        ActionBar();
        LoadData();
        SuaSach();
    }
    private void AnhXa(){
        tenSachThemTxt = findViewById(R.id.tenSachSuaTxt);
        tacGiaThemTxt = findViewById(R.id.tacGiaSuaTxt);
        loaiThemTxt = findViewById(R.id.loaiSuaTxt);
        nxbThemTxt = findViewById(R.id.nxbSuaTxt);
        giaThemTxt = findViewById(R.id.giaSuaTxt);
        anhThemTxt = findViewById(R.id.anhSuaTxt);
        themSachBtn = findViewById(R.id.suaSachBtn);
        toolbar = findViewById(R.id.toolbarTK);
        motaTxt = findViewById(R.id.motaTxt);
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
        int id_sach = getIntent().getIntExtra("id_sach", 0);
        s = Sach.getSach(id_sach);
        if(s != null){
            tenSachThemTxt.setText(s.getTenSach());
            tacGiaThemTxt.setText(s.getTacGia());
            loaiThemTxt.setText(s.getLoai());
            nxbThemTxt.setText(s.getNxb());
            giaThemTxt.setText(s.getGiaTien()+"");
            anhThemTxt.setText(s.getAnh());
            motaTxt.setText(s.getMoTa());
        }
    }
    private void SuaSach(){
        themSachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s != null){
                    Sach s1 = new Sach();
                    s1.setId(s.getId());
                    s1.setTenSach(tenSachThemTxt.getText().toString());
                    s1.setTacGia(tacGiaThemTxt.getText().toString());
                    s1.setLoai(loaiThemTxt.getText().toString());
                    s1.setNxb(nxbThemTxt.getText().toString());
                    s1.setGiaTien(Integer.parseInt(giaThemTxt.getText().toString()));
                    s1.setAnh(anhThemTxt.getText().toString());
                    s1.setMoTa(motaTxt.getText().toString());
                    if(s1.SuaSach()){
                        Toast.makeText(SuaSachActivity.this, "Sửa sách thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SuaSachActivity.this, ListSachSuaXoaActivity.class);
                        finish();
                        startActivity(intent);
                    }else{
                        Toast.makeText(SuaSachActivity.this, "Sửa sách thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}