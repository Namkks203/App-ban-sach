package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.NhanVien;
import com.namkks.appbansach123.models.PhieuThuChiVatTu;
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.models.TacGia;
import com.namkks.appbansach123.models.TonKho;

import java.util.ArrayList;

public class NhapVatTuActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AutoCompleteTextView autoTenSach;
    private EditText soLuongTxt, moTaTxt;
    private TextView themSachMoiTextView;
    private Button luuBtn;
    NhanVien nhanVien;
    Sach sach;

    private ArrayList<Sach> sachArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nhap_vat_tu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        setupToolbar();
        loadData();
        setupAdapter();
        setUpAction();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbarTK);
        autoTenSach = findViewById(R.id.autoTenSach);
        soLuongTxt = findViewById(R.id.soLuongTxt);
        moTaTxt = findViewById(R.id.motaTxt);
        themSachMoiTextView = findViewById(R.id.themSachMoiTextView);
        luuBtn = findViewById(R.id.luuBtn);
    }

    @SuppressLint("RestrictedApi")
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void loadData() {
        sachArrayList = Sach.getAllSach();
        if (sachArrayList == null) {
            sachArrayList = new ArrayList<>();
        }
        nhanVien = NhanVien.getNhanVien(LoginActivity.tk.getId());
    }

    private void setUpAction() {
        autoTenSach.setOnItemClickListener(
                (parent, view, position, id) -> {
                    Object item = parent.getItemAtPosition(position);

                    sach = (Sach) item;
                });

        luuBtn.setOnClickListener( v -> luuPhieuNhap());
    }

    private void setupAdapter() {
        ArrayAdapter<Sach> adapterSach = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                sachArrayList
        );

        autoTenSach.setAdapter(adapterSach);
    }

    private void luuPhieuNhap() {
        if (!isValidInput()) {
            Toast.makeText(this,
                    "Vui lòng nhập đầy đủ & đúng định dạng!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(sach == null){
            Toast.makeText(this,
                    "Chưa có sách này trong hệ thống!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        PhieuThuChiVatTu phieuVatTu = new PhieuThuChiVatTu();
        phieuVatTu.setLoaiPhieu("thu");
        phieuVatTu.setChiNhanhId(nhanVien.getChiNhanhId());
        phieuVatTu.setSachId(sach.getId());
        phieuVatTu.setSoLuong(Integer.parseInt(soLuongTxt.getText().toString()));
        phieuVatTu.setLyDo(moTaTxt.getText().toString());

        PhieuThuChiVatTu phieuThuChiVatTu = phieuVatTu.addPhieuThuChiVatTu();
        if(phieuThuChiVatTu != null){
            TonKho tonKho = new TonKho();
            tonKho.setChiNhanhId(nhanVien.getChiNhanhId());
            tonKho.setSachId(sach.getId());
            tonKho.setSoLuong(phieuThuChiVatTu.getSoLuong());

            TonKho tonKho1 = tonKho.themTonKho();
            if(tonKho1 != null){
                Toast.makeText(this,
                        "Đã lập phiếu nhập hàng thành công!",
                        Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }
        Toast.makeText(this,
                "Lập phiếu nhập hàng thất bại!",
                Toast.LENGTH_SHORT).show();
    }

    private boolean isValidInput() {
        if (autoTenSach.getText().toString().trim().isEmpty()) return false;
        if (soLuongTxt.getText().toString().trim().isEmpty()) return false;
        if (moTaTxt.getText().toString().trim().isEmpty()) return false;

        try {
            Integer.parseInt(soLuongTxt.getText().toString());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}