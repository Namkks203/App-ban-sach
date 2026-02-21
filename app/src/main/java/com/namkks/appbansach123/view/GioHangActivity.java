package com.namkks.appbansach123.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.adapter.ListGioHangAdapter;
import com.namkks.appbansach123.models.GioHang;
import com.namkks.appbansach123.models.KhachHang;
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.models.SachTacGia;
import com.namkks.appbansach123.models.SachTheLoai;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
    @Override
    protected void onResume() {
        super.onResume();
        LoadData();   // hàm load lại dữ liệu
    }

    public void AnhXa(){
        recyclerViewGH = findViewById(R.id.recycleviewGH);
        trangchuLayout = findViewById(R.id.trangChuLayout);
        tkLayout = findViewById(R.id.tklayout);
        thanhTienTxt = findViewById(R.id.thanhTienTxt);
        thanhToanBtn = findViewById(R.id.thanhToanBtn);
    }
    public void ThanhToanBtn(){
        thanhToanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.tk != null){
                    KhachHang kh = KhachHang.getKhachHangByTaiKhoanId(LoginActivity.tk.getId());
                    if(GioHang.getGioHangByKhachHangId(kh.getId()) == null){
                        Toast.makeText(GioHangActivity.this, "Giỏ hàng bạn đang trống.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        showDialogThueOrMua();
                    }
                }else{
                    Toast.makeText(GioHangActivity.this, "Bạn phải đăng nhập để sử dụng chức năng này.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        if (LoginActivity.tk != null && LoginActivity.tk.getLoaiTaiKhoan().equals("khach_hang")){
            KhachHang kh = KhachHang.getKhachHangByTaiKhoanId(LoginActivity.tk.getId());
            ArrayList<GioHang> gioHangs = GioHang.getGioHangByKhachHangId(kh.getId());
            if(gioHangs == null)
                gioHangs = new ArrayList<>();
            ListGioHangAdapter listGioHangAdapter = new ListGioHangAdapter(gioHangs);
            recyclerViewGH.setAdapter(listGioHangAdapter);
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            int tongTien = 0;
            for (GioHang gioHang : gioHangs){
                Sach sach = Sach.getSachById(gioHang.getSachId());
                tongTien = tongTien + sach.getGiaBan() * gioHang.getSoLuong();
            }
            String tienvnd = formatter.format(tongTien) + " đ";
            thanhTienTxt.setText(tienvnd);
            listGioHangAdapter.setOnDataChangedListener(new ListGioHangAdapter.OnDataChangedListener() {
                @Override
                public void onDataChanged() {
                    recreate();
                }
            });
        }

    }
    private void showDialogThueOrMua(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_mua_or_thue, null);

        builder.setView(dialogView);

        TextView huyBtn = dialogView.findViewById(R.id.huyBtn);
        TextView thueBtn = dialogView.findViewById(R.id.thueBtn);
        TextView muaBtn = dialogView.findViewById(R.id.muaBtn);

        AlertDialog dialog = builder.create();
        dialog.show();

        huyBtn.setOnClickListener(v -> dialog.dismiss());
        thueBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ThanhToanActivity.class);
            intent.putExtra("trang_thai", "thue");
            startActivity(intent);
        });
        muaBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ThanhToanActivity.class);
            intent.putExtra("trang_thai", "mua");
            startActivity(intent);
        });
    }
}