package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.KhachHang;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView idProfile, tenProfile, sdtProfile, diaChiProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        ActionBar();
//        if(LoginActivity.kh != null){
//            KhachHang kh = LoginActivity.kh;
//            idProfile.setText(""+kh.getId());
//            tenProfile.setText(kh.getHoTen());
//            sdtProfile.setText(kh.getSdt());
//            diaChiProfile.setText(kh.getDiaChi());
//        }
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

    private void AnhXa(){
        toolbar = findViewById(R.id.toolbarTK);
        idProfile = findViewById(R.id.idProfile);
        tenProfile = findViewById(R.id.tenProfile);
        sdtProfile = findViewById(R.id.sdtProfile);
        diaChiProfile = findViewById(R.id.diaChiProfile);
    }
}