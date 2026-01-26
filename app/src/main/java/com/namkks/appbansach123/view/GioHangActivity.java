package com.namkks.appbansach123.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.adapter.ListGioHangAdapter;
import com.namkks.appbansach123.models.ChiTietDonHang;
import com.namkks.appbansach123.models.GioHang;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {
    TextView thanhTienTxt;
    LinearLayout trangchuLayout, tkLayout, thongBaoLayout;
    RecyclerView recyclerViewGH;
    Button thanhToanBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gio_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        LoadData();
        ChuyenTKLayOut();
        ChuyenTCLayOut();
        ThanhToanBtn();
    }

    public void AnhXa(){
        recyclerViewGH = findViewById(R.id.recycleviewGH);
        trangchuLayout = findViewById(R.id.trangChuLayout);
        tkLayout = findViewById(R.id.tklayout);
        thanhTienTxt = findViewById(R.id.thanhTienTxt);
        thanhToanBtn = findViewById(R.id.thanhToanBtn);
    }
    public void ThanhToanBtn(){
//        thanhToanBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(LoginActivity.kh != null){
//                    if(GioHang.getGioHang(LoginActivity.kh.getId()).isEmpty()){
//                        Toast.makeText(GioHangActivity.this, "Giỏ hàng bạn đang trống.", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        ChiTietDonHang ctdh = new ChiTietDonHang();
//                        ctdh.setId_khachhang(LoginActivity.kh.getId());
//                        if(ctdh.addCTDH()){
//                            Toast.makeText(GioHangActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
//                            recreate();
//                        }else{
//                            Toast.makeText(GioHangActivity.this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }else{
//                    Toast.makeText(GioHangActivity.this, "Bạn phải đăng nhập để sử dụng chức năng này.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
    public void ChuyenTKLayOut(){
        tkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GioHangActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public void ChuyenTCLayOut(){
        trangchuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GioHangActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void LoadData(){
        recyclerViewGH.setLayoutManager(new LinearLayoutManager(this));
//        if (LoginActivity.kh != null){
//            ListGioHangAdapter listGioHangAdapter = new ListGioHangAdapter(GioHang.getGioHang(LoginActivity.kh.getId()),
//                    this, LoginActivity.kh.getId());
//            recyclerViewGH.setAdapter(listGioHangAdapter);
//            DecimalFormat formatter = new DecimalFormat("###,###,###");
//            String tienvnd = formatter.format(GioHang.GetTongTien(LoginActivity.kh.getId())) + " đ";
//            thanhTienTxt.setText(tienvnd);
//            listGioHangAdapter.setOnDataChangedListener(new ListGioHangAdapter.OnDataChangedListener() {
//                @Override
//                public void onDataChanged() {
//                    recreate();
//                }
//            });
//        }

    }
}