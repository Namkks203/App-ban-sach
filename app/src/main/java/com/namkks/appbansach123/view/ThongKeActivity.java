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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.namkks.appbansach123.R;
import com.namkks.appbansach123.models.ChiTietHoaDon;
import com.namkks.appbansach123.models.GioHang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity {
    Toolbar toolbar;
    PieChart pieChart;
    TextView DoanhThuTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_ke);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        ActionBar();
        LoadData();
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
        pieChart = findViewById(R.id.pieChart);
        DoanhThuTxt = findViewById(R.id.DoanhThuTxt);
    }
    private void LoadData(){
        // Tạo danh sách dữ liệu
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (String loai: ChiTietHoaDon.getLoaiInHD()) {
            entries.add(new PieEntry(ChiTietHoaDon.getSLLoai(loai), loai));
        }

// Tạo dataset và cấu hình
        PieDataSet dataSet = new PieDataSet(entries, "Loại quần áo");
        dataSet.setColors(
                getResources().getColor(android.R.color.holo_red_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_blue_light)
        );
        dataSet.setValueTextColor(getResources().getColor(android.R.color.black));
        dataSet.setValueTextSize(16f);

// Tạo PieData
        PieData pieData = new PieData(dataSet);

// Cấu hình PieChart
        pieChart.setData(pieData);
        pieChart.setCenterText("Biểu đồ loại quần áo đã bán");
        pieChart.setCenterTextSize(20f);
        pieChart.setEntryLabelTextSize(16f);
        pieChart.invalidate(); // Refresh PieChart

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String tienvnd = formatter.format(ChiTietHoaDon.getTongTien()) + " đ";
        DoanhThuTxt.setText("Doanh thu tháng này: " + tienvnd);

    }
}