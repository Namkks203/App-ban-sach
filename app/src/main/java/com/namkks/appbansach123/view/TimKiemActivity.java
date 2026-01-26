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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namkks.appbansach123.R;
import com.namkks.appbansach123.adapter.ListSachAdapter;
import com.namkks.appbansach123.models.Sach;

import java.util.ArrayList;

public class TimKiemActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView titleTK;
    RecyclerView recyclerViewTK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tim_kiem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        ActionBar();
        LoadData();
    }

    public void AnhXa(){
        titleTK = findViewById(R.id.titleTK);
        recyclerViewTK = findViewById(R.id.recycleviewTK);
        toolbar = findViewById(R.id.toolbartk);
    }
    private void LoadData(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerViewTK.setLayoutManager(gridLayoutManager);
        String tk = getIntent().getStringExtra("timkiem");
        if(tk != null){
            ListSachAdapter listSachAdapter = new ListSachAdapter(Sach.TiemKiemQuanAo(tk));
            recyclerViewTK.setAdapter(listSachAdapter);
        }else{
            tk = getIntent().getStringExtra("loai");
            ArrayList<Sach> tksach = Sach.ListQuanAoOfLoai(tk);
            ListSachAdapter listSachAdapter = new ListSachAdapter(Sach.ListQuanAoOfLoai(tk));
            recyclerViewTK.setAdapter(listSachAdapter);
        }

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
}