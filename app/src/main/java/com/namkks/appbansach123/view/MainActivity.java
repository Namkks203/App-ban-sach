package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.namkks.appbansach123.R;
import com.namkks.appbansach123.adapter.ListSachAdapter;
import com.namkks.appbansach123.adapter.LoaiSPAdapter;
import com.namkks.appbansach123.models.Sach;
import com.google.android.material.navigation.NavigationView;
import com.namkks.appbansach123.models.TaiKhoan;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LinearLayout tkLayout, cartLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewMHC;
    NavigationView navigationView;
    ListView listViewMHC;
    DrawerLayout drawerLayout;
    EditText timKiemTxt;
    TextView searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        ActionBar();

        if(isConnected(this)){
            LoadData();
            ActionViewFlipper();
            getLoaiSanPham();
            getListSanPham();
            ChuyenTKLayOut();
            TimKiem();
            ChuyenCartLayOut();
        }else{
            Toast.makeText(getApplicationContext(), "khong co internet, Vui lòng kết nối Internet!", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void getLoaiSanPham() {
        LoaiSPAdapter loaiSPAdapter = new LoaiSPAdapter(this, new Sach().getLoaiSach());
        listViewMHC.setAdapter(loaiSPAdapter);
    }

    private void getListSanPham(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerViewMHC.setLayoutManager(gridLayoutManager);

        ListSachAdapter listSachAdapter = new ListSachAdapter( new Sach().getSachHot());
        recyclerViewMHC.setAdapter(listSachAdapter);
    }

    private void ActionViewFlipper() {
        List<String> mangqc = new ArrayList<>();
        mangqc.add("https://down-vn.img.susercontent.com/file/vn-11134258-820l4-mf82135tct8s16@resize_w797_nl.webp");
        mangqc.add("https://down-vn.img.susercontent.com/file/sg-11134258-821fo-mhahx79m57nw7e@resize_w797_nl.webp");
        mangqc.add("https://cf.shopee.vn/file/vn-50009109-727a24a85a60935da5ccb9008298f681");
        for (int i = 0; i < mangqc.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangqc.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);

    }

    @SuppressLint("RestrictedApi")
    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void AnhXa(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewMHC = findViewById(R.id.recycleview);
        listViewMHC = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        tkLayout = findViewById(R.id.tklayout);
        timKiemTxt = findViewById(R.id.timKiemTxt);
        searchBtn = findViewById(R.id.searchBtn);
        cartLayout = findViewById(R.id.cartLayout);
    }

    private void TimKiem(){
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimKiemActivity.class);
                intent.putExtra("timkiem", timKiemTxt.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void ChuyenTKLayOut(){
        tkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public void ChuyenCartLayOut(){
        cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }else{
            return false;
        }
    }

    private void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            String username = sharedPreferences.getString("username", "");
            String password = sharedPreferences.getString("password", "");

            LoginActivity.tk = TaiKhoan.dangNhap(username, password);
        }

    }

}