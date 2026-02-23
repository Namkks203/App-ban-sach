package com.namkks.appbansach123.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.*;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    public static TaiKhoan tk;

    private LinearLayout loreform, tk_profile, trangchuLayout, ghLayout, thongBaoLayout;
    private NestedScrollView tk_NVprofile;

    private EditText sdtDNtxt, mkDNtxt,
            hoTenDKtxt, sdtDKtxt, emailDKtxt,
            tenDNtxt, passDKtxt, diaChiDKtxt;

    private TextView loginTxt, registerTxt,
            tenKhtxt, tenNVtxt,
            soLChoXuLy, soLDangChuanBi,
            soLDangGiao, soLHoanTat;

    private ViewFlipper viewFlipperLogin;
    private boolean isLoginForm = true;

    private Button dnBtn, dkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        initView();
        setupNavigation();
        setupAuthActions();
        setupFlipForm();

        loadProfileIfLogged();
    }

    private void setupNavigation() {
        ghLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });
        trangchuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // =============================
    // INIT
    // =============================


    private void initView() {
        loreform = findViewById(R.id.loreform);
        tk_profile = findViewById(R.id.tk_profile);
        tk_NVprofile = findViewById(R.id.tk_NVprofile);

        sdtDNtxt = findViewById(R.id.sdtDNtxt);
        mkDNtxt = findViewById(R.id.mkDNtxt);

        hoTenDKtxt = findViewById(R.id.hoTenDKtxt);
        sdtDKtxt = findViewById(R.id.sdtDKtxt);
        emailDKtxt = findViewById(R.id.emailDKtxt);
        tenDNtxt = findViewById(R.id.tenDNTxt);
        passDKtxt = findViewById(R.id.passDKtxt);
        diaChiDKtxt = findViewById(R.id.diaChiDKtxt);

        dnBtn = findViewById(R.id.dnBtn);
        dkBtn = findViewById(R.id.dkBtn);

        loginTxt = findViewById(R.id.loginTxt);
        registerTxt = findViewById(R.id.registerTxt);
        viewFlipperLogin = findViewById(R.id.viewflipperlogin);

        tenKhtxt = findViewById(R.id.tenKHtxt);
        tenNVtxt = findViewById(R.id.tenNVtxt);

        soLChoXuLy = findViewById(R.id.soLChoXuLy);
        soLDangChuanBi = findViewById(R.id.soLDangChuanBi);
        soLDangGiao = findViewById(R.id.soLDangGiao);
        soLHoanTat = findViewById(R.id.soLHoanTat);

        trangchuLayout = findViewById(R.id.trangChuLayout);
        ghLayout = findViewById(R.id.cartLayout);
    }

    // =============================
    // AUTH
    // =============================

    private void setupAuthActions() {

        dnBtn.setOnClickListener(v -> login());
        dkBtn.setOnClickListener(v -> register());
    }

    private void login() {

        String username = sdtDNtxt.getText().toString().trim();
        String password = mkDNtxt.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        tk = TaiKhoan.dangNhap(username, password);

        if (tk == null) {
            showToast("Thông tin tài khoản hoặc mật khẩu không chính xác.");
            return;
        }

        if ("khoa".equals(tk.getTrangThai())) {
            showToast("Tài khoản này đã bị khóa!");
            return;
        }

        saveLoginState();
        showToast("Đăng nhập thành công!");
        loadProfileIfLogged();
    }

    private void register() {

        if (!isValidInput()) {
            showToast("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        if (KhachHang.KiemTraTT(
                sdtDKtxt.getText().toString(),
                emailDKtxt.getText().toString())) {

            showToast("Số điện thoại hoặc email đã được đăng ký!");
            return;
        }

        TaiKhoan newTK = new TaiKhoan();
        newTK.setTenDangNhap(tenDNtxt.getText().toString());
        newTK.setMatKhau(passDKtxt.getText().toString());
        newTK.setLoaiTaiKhoan("khach_hang");

        TaiKhoan tkTmp = newTK.addTaiKhoan();

        if (tkTmp == null) {
            showToast("Đăng ký thất bại!");
            return;
        }

        KhachHang kh = new KhachHang();
        kh.setTen(hoTenDKtxt.getText().toString());
        kh.setDienThoai(sdtDKtxt.getText().toString());
        kh.setEmail(emailDKtxt.getText().toString());
        kh.setDiaChi(diaChiDKtxt.getText().toString());
        kh.setTaiKhoanId(tkTmp.getId());

        if (kh.addKhachHang() != null) {
            showToast("Đăng ký thành công! Vui lòng đăng nhập.");
            recreate();
        }
    }

    // =============================
    // PROFILE
    // =============================

    private void loadProfileIfLogged() {

        if (tk == null) return;

        if ("khach_hang".equals(tk.getLoaiTaiKhoan())) {
            showCustomerProfile();
        } else {
            showEmployeeProfile();
        }
    }

    private void showCustomerProfile() {

        KhachHang kh = KhachHang.getKhachHangByTaiKhoanId(tk.getId());
        if (kh == null) return;

        loreform.setVisibility(View.GONE);
        tk_profile.setVisibility(View.VISIBLE);

        tenKhtxt.setText(kh.getTen());

        ArrayList<DonHang> list = DonHang.getDonHangsByKhachHangId(kh.getId());
        int cho = 0, chuanBi = 0, giao = 0, hoanTat = 0;

        for (DonHang d : list) {
            switch (d.getTrangThai()) {
                case "cho_xu_ly": cho++; break;
                case "dang_chuan_bi": chuanBi++; break;
                case "dang_giao": giao++; break;
                default: hoanTat++; break;
            }
        }

        setCounter(soLChoXuLy, cho);
        setCounter(soLDangChuanBi, chuanBi);
        setCounter(soLDangGiao, giao);
        setCounter(soLHoanTat, hoanTat);
    }

    private void showEmployeeProfile() {

        NhanVien nv = NhanVien.getNhanVien(tk.getId());
        if (nv == null) return;

        loreform.setVisibility(View.GONE);
        tk_NVprofile.setVisibility(View.VISIBLE);
        tenNVtxt.setText(nv.getHoTen());
    }

    // =============================
    // FORM SWITCH
    // =============================

    private void setupFlipForm() {

        Animation in = AnimationUtils.loadAnimation(this, R.anim.login_in_right);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.register_in_left);

        viewFlipperLogin.setInAnimation(in);
        viewFlipperLogin.setOutAnimation(out);

        loginTxt.setOnClickListener(v -> switchForm(true));
        registerTxt.setOnClickListener(v -> switchForm(false));
    }

    private void switchForm(boolean showLogin) {

        if (isLoginForm == showLogin) return;

        isLoginForm = showLogin;
        viewFlipperLogin.showNext();

        loginTxt.setTextColor(showLogin ? Color.parseColor("#ff9933") : Color.BLACK);
        registerTxt.setTextColor(showLogin ? Color.BLACK : Color.parseColor("#ff9933"));
    }

    // =============================
    // HELPERS
    // =============================

    private void saveLoginState() {
        SharedPreferences.Editor editor =
                getSharedPreferences("MyAppPrefs", MODE_PRIVATE).edit();

        editor.putString("username", tk.getTenDangNhap());
        editor.putString("password", tk.getMatKhau());
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }

    private void setCounter(TextView tv, int value) {
        if (value > 0) {
            tv.setText(String.valueOf(value));
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidInput() {
        return !hoTenDKtxt.getText().toString().trim().isEmpty()
                && !sdtDKtxt.getText().toString().trim().isEmpty()
                && !emailDKtxt.getText().toString().trim().isEmpty()
                && !tenDNtxt.getText().toString().trim().isEmpty()
                && !passDKtxt.getText().toString().trim().isEmpty()
                && !diaChiDKtxt.getText().toString().trim().isEmpty();
    }
}