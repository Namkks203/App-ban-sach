package com.namkks.appbansach123.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.adapter.ChoThanhToanAdapter;
import com.namkks.appbansach123.models.*;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ThanhToanActivity extends AppCompatActivity {

    private TextView soLSach, tongGiaThue, tongGiaCoc,
            tongTienThue, tongTienCoc, hoTenKH, sdtKH, diaChiKH, textQR;

    private LinearLayout layoutChonNgay;
    private EditText etDate;
    private Spinner spinnerTT;
    private RecyclerView recyclerView;
    private ImageView maQR;
    private Toolbar toolbar;
    private Button hoanTatBtn, btnPickDate;

    private String trangThai;
    private Date ngayHenTra;

    private int soLuong = 0;
    private int giaThue = 0;
    private int tienCoc = 0;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_toan);

        trangThai = getIntent().getStringExtra("trang_thai");

        initView();
        setupToolbar();
        loadData();
        setupActions();
    }

    private void initView() {
        soLSach = findViewById(R.id.soLSachInGioHang);
        tongGiaThue = findViewById(R.id.tongGiaThue);
        tongGiaCoc = findViewById(R.id.tongTienCoc);
        tongTienThue = findViewById(R.id.TongTienThue);
        tongTienCoc = findViewById(R.id.TongTienCoc);
        hoTenKH = findViewById(R.id.hoTenKH);
        sdtKH = findViewById(R.id.sdtKH);
        diaChiKH = findViewById(R.id.diaChiKH);
        textQR = findViewById(R.id.textQR);
        spinnerTT = findViewById(R.id.spinerTT);
        recyclerView = findViewById(R.id.recycleViewCTDH);
        maQR = findViewById(R.id.qrThanhToan);
        toolbar = findViewById(R.id.toolbarTK);
        hoanTatBtn = findViewById(R.id.hoanTatBtn);
        layoutChonNgay = findViewById(R.id.layoutChonNgay);
        btnPickDate = findViewById(R.id.btnPickDate);
        etDate = findViewById(R.id.etDate);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitle("Thanh toán");
    }

    private void loadData() {

        if (LoginActivity.tk == null ||
                !"khach_hang".equals(LoginActivity.tk.getLoaiTaiKhoan()))
            return;

        KhachHang kh = KhachHang.getKhachHangByTaiKhoanId(LoginActivity.tk.getId());
        if (kh == null) return;

        hoTenKH.setText(kh.getTen());
        sdtKH.setText(kh.getDienThoai());
        diaChiKH.setText(kh.getDiaChi());

        setupSpinner();

        ArrayList<GioHang> gioHangs =
                GioHang.getGioHangByKhachHangId(kh.getId());

        calculateCart(gioHangs);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ChoThanhToanAdapter(gioHangs));
    }

    private void setupSpinner() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Thanh toán khi nhận hàng");
        list.add("Thanh toán chuyển khoản");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                list
        );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerTT.setAdapter(adapter);

        if ("mua".equals(trangThai)) {
            layoutChonNgay.setVisibility(View.GONE);
        }
    }

    private void calculateCart(ArrayList<GioHang> gioHangs) {

        DecimalFormat formatter = new DecimalFormat("###,###,###");

        for (GioHang gioHang : gioHangs) {

            soLuong += gioHang.getSoLuong();

            Sach sach = Sach.getSachById(gioHang.getSachId());
            if (sach == null) continue;

            giaThue += sach.getGiaThue() * gioHang.getSoLuong();
            tienCoc += sach.getGiaBan() * gioHang.getSoLuong();
        }

        if ("mua".equals(trangThai)) {
            giaThue = tienCoc;
        }

        soLSach.setText(String.valueOf(soLuong));
        tongGiaThue.setText(formatter.format(giaThue) + " đ");
        tongTienThue.setText("Tổng tiền: " +
                formatter.format(giaThue) + " đ");

        tongGiaCoc.setText(formatter.format(tienCoc) + " đ");
        tongTienCoc.setText("Tổng tiền cọc: " +
                formatter.format(tienCoc) + " đ");
    }

    private void setupActions() {

        spinnerTT.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view,
                                               int position,
                                               long id) {

                        boolean isCK =
                                position == 1;

                        textQR.setVisibility(
                                isCK ? View.VISIBLE : View.GONE);

                        maQR.setVisibility(
                                isCK ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {}
                });

        btnPickDate.setOnClickListener(
                v -> showDatePicker());

        hoanTatBtn.setOnClickListener(
                v -> hoanTatThanhToan());
    }

    private void showDatePicker() {

        Calendar cal = Calendar.getInstance();

        new DatePickerDialog(this,
                (view, year, month, day) -> {

                    Calendar selected =
                            Calendar.getInstance();

                    selected.set(year, month, day);

                    etDate.setText(
                            dateFormat.format(
                                    selected.getTime()));

                    ngayHenTra =
                            new Date(selected.getTimeInMillis());

                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void hoanTatThanhToan() {

        if ("thue".equals(trangThai)
                && ngayHenTra == null) {

            Toast.makeText(this,
                    "Vui lòng chọn ngày hẹn trả!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (createDonHang()) {

            Toast.makeText(this,
                    "Tạo đơn hàng thành công!",
                    Toast.LENGTH_SHORT).show();

            KhachHang kh =
                    KhachHang.getKhachHangByTaiKhoanId(
                            LoginActivity.tk.getId());

            GioHang.DeleteGH(kh.getId());

            onBackPressed();

        } else {

            Toast.makeText(this,
                    "Tạo đơn hàng thất bại!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean createDonHang() {

        KhachHang kh =
                KhachHang.getKhachHangByTaiKhoanId(
                        LoginActivity.tk.getId());

        ArrayList<GioHang> gioHangs =
                GioHang.getGioHangByKhachHangId(
                        kh.getId());

        DonHang donHang = new DonHang();
        donHang.setKhachHangId(kh.getId());
        donHang.setLoaiDon(
                "mua".equals(trangThai)
                        ? "don_ban"
                        : "don_thue");
        donHang.setTongTien(giaThue);
        donHang.setTongTienCoc(tienCoc);

        DonHang tmp = donHang.addDonHang();
        if (tmp == null) return false;

        return "don_ban".equals(tmp.getLoaiDon())
                ? createDonBan(tmp, gioHangs)
                : createDonThue(tmp, gioHangs);
    }

    private boolean createDonBan(DonHang donHang,
                                 ArrayList<GioHang> gioHangs) {

        DonBan donBan = new DonBan();
        donBan.setDonHangId(donHang.getId());

        DonBan tmp = donBan.addDonBan();
        if (tmp == null) return false;

        for (GioHang gh : gioHangs) {

            Sach sach =
                    Sach.getSachById(gh.getSachId());
            if (sach == null) continue;

            ChiTietDonBan ct =
                    new ChiTietDonBan();

            ct.setDonBanId(tmp.getId());
            ct.setSachId(sach.getId());
            ct.setSoLuong(gh.getSoLuong());
            ct.setDonGia(sach.getGiaBan());

            if (ct.addChiTietDonBan() == null)
                return false;
        }

        return true;
    }

    private boolean createDonThue(DonHang donHang,
                                  ArrayList<GioHang> gioHangs) {

        DonThue donThue = new DonThue();
        donThue.setDonHangId(donHang.getId());
        donThue.setNgayHenTra(ngayHenTra);

        DonThue tmp = donThue.addDonThue();
        if (tmp == null) return false;

        for (GioHang gh : gioHangs) {

            Sach sach =
                    Sach.getSachById(gh.getSachId());
            if (sach == null) continue;

            ChiTietDonThue ct =
                    new ChiTietDonThue();

            ct.setDonThueId(tmp.getId());
            ct.setSachId(sach.getId());
            ct.setSoLuong(gh.getSoLuong());
            ct.setGiaThue(sach.getGiaThue());

            if (ct.addChiTietDonThue() == null)
                return false;
        }

        return true;
    }
}