package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.namkks.appbansach123.R;
import com.namkks.appbansach123.img_upload.FileUtil;
import com.namkks.appbansach123.img_upload.ImageUploader;
import com.namkks.appbansach123.img_upload.UploadProgressDialog;
import com.namkks.appbansach123.models.Sach;
import com.namkks.appbansach123.models.SachTacGia;
import com.namkks.appbansach123.models.SachTheLoai;
import com.namkks.appbansach123.models.TacGia;
import com.namkks.appbansach123.models.TheLoai;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;

public class ThemSachActivity extends AppCompatActivity {

    private String imgUrl = "";

    private Toolbar toolbar;
    private EditText tenSachThemTxt, nhaXuatBanTxt, namXuatBanTxt,
            giaBanTxt, giaThueTxt, moTaTxt;
    private ImageView anhSachThem;
    private Button themSachBtn, themAnhSachBtn;
    private ProgressBar imageProgress;
    private MultiAutoCompleteTextView autoTacGia;
    private MultiAutoCompleteTextView autoTheLoai;

    private ArrayList<TacGia> listtacGia;
    private ArrayList<TheLoai> listTheLoai;

    // ================== Activity Result API ==================
    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri uri = result.getData().getData();
                            getContentResolver().takePersistableUriPermission(
                                    uri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );
                            handleImageUri(uri);
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_sach);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);
                    return insets;
                });

        initView();
        loadData();
        setupAdapter();
        setupToolbar();
        setupActions();
    }

    // ================== Init ==================
    private void initView() {
        tenSachThemTxt = findViewById(R.id.tenSachThemTxt);
        nhaXuatBanTxt = findViewById(R.id.nhaXuatBanThemTxt);
        namXuatBanTxt = findViewById(R.id.namXuatBanThemTxt);
        giaBanTxt = findViewById(R.id.giaBanTxt);
        giaThueTxt = findViewById(R.id.giaThueThemTxt);
        moTaTxt = findViewById(R.id.motaTxt);

        themAnhSachBtn = findViewById(R.id.themAnhSachBtn);
        themSachBtn = findViewById(R.id.themSachBtn);

        anhSachThem = findViewById(R.id.anhSachThem);
        imageProgress = findViewById(R.id.imageProgress);

        toolbar = findViewById(R.id.toolbarTK);

        autoTacGia = findViewById(R.id.autoTacGia);
        autoTheLoai = findViewById(R.id.autoTheLoai);
    }

    @SuppressLint("RestrictedApi")
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    // ================== Load Data ================
    private void loadData(){
        listtacGia = TacGia.getAllTacGia();
        if(listtacGia == null){
            listtacGia = new ArrayList<>();
        }

        listTheLoai = TheLoai.getAllTheLoai();
        if(listTheLoai == null){
            listTheLoai = new ArrayList<>();
        }
    }
    private void setupAdapter(){
        ArrayAdapter<TacGia> adapterTacGia = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                listtacGia
        );

        autoTacGia.setAdapter(adapterTacGia);

// QUAN TRỌNG: phân tách bằng dấu phẩy
        autoTacGia.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        ArrayAdapter<TheLoai> adapterTheLoai = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                listTheLoai
        );

        autoTheLoai.setAdapter(adapterTheLoai);

// QUAN TRỌNG: phân tách bằng dấu phẩy
        autoTheLoai.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    // ================== Actions ==================
    private void setupActions() {
        themAnhSachBtn.setOnClickListener(v -> openFileManager());
        themSachBtn.setOnClickListener(v -> handleAddBook());
        autoTacGia.setOnItemClickListener((parent, view, position, id) -> {
            TacGia tg = (TacGia) parent.getItemAtPosition(position);

            int tacGiaId = tg.getId();
            String tenTacGia = tg.getTen();

            Toast.makeText(this,
                    "ID: " + tacGiaId + " - " + tenTacGia,
                    Toast.LENGTH_SHORT).show();
        });
    }

    // ================== Pick Image ==================
    private void openFileManager() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }

    private void handleImageUri(Uri uri) {
        Executors.newSingleThreadExecutor().execute(() -> {
            File file = FileUtil.from(this, uri);
            runOnUiThread(() -> uploadFile(file));
        });
    }

    // ================== Upload ==================
    private void uploadFile(File file) {
        if (file == null) {
            Toast.makeText(this, "Không đọc được file", Toast.LENGTH_SHORT).show();
            return;
        }

        ImageUploader uploader = new ImageUploader();
        UploadProgressDialog dialog =
                new UploadProgressDialog(this, uploader::cancelUpload);

        dialog.show();

        uploader.uploadImage(file, new ImageUploader.UploadCallback() {
            @Override
            public void onProgress(int percent) {
                runOnUiThread(() -> dialog.update(percent));
            }

            @Override
            public void onSuccess(String imageUrl) {
                runOnUiThread(() -> {
                    dialog.dismiss();
                    imgUrl = imageUrl;
                    loadImage(imageUrl);
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> dialog.error(error));
            }
        });
    }

    // ================== Load Image ==================
    private void loadImage(String url) {
        imageProgress.setVisibility(View.VISIBLE);

        Glide.with(this)
                .load(url)
                .override(600, 800)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .listener(glideListener())
                .into(anhSachThem);
    }

    private RequestListener<Drawable> glideListener() {
        return new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(
                    @Nullable GlideException e,
                    Object model,
                    Target<Drawable> target,
                    boolean isFirstResource) {

                imageProgress.setVisibility(View.GONE);
                Toast.makeText(ThemSachActivity.this,
                        "Load ảnh thất bại", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onResourceReady(
                    Drawable resource,
                    Object model,
                    Target<Drawable> target,
                    DataSource dataSource,
                    boolean isFirstResource) {

                imageProgress.setVisibility(View.GONE);
                return false;
            }
        };
    }

    // ================== Add Book ==================
    private void handleAddBook() {
        if (!isValidInput()) {
            Toast.makeText(this,
                    "Vui lòng nhập đầy đủ & đúng định dạng!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // ================== 1. Tạo sách ==================
        Sach s = new Sach();
        s.setTenSach(tenSachThemTxt.getText().toString().trim());
        s.setNhaXuatBan(nhaXuatBanTxt.getText().toString().trim());
        s.setNamXuatBan(Integer.parseInt(namXuatBanTxt.getText().toString()));
        s.setGiaBan(Integer.parseInt(giaBanTxt.getText().toString()));
        s.setGiaThue(Integer.parseInt(giaThueTxt.getText().toString()));
        s.setAnh(imgUrl);
        s.setMoTa(moTaTxt.getText().toString().trim());

        Sach sach2 = s.themSach();
        if (sach2 == null) {
            Toast.makeText(this, "Thêm sách thất bại!", Toast.LENGTH_SHORT).show();
            return;
        }

        // ================== 2. Parse input ==================
        ArrayList<String> tenTacGiaNhap = new ArrayList<>();
        for (String s1 : autoTacGia.getText().toString().split(",")) {
            if (!s1.trim().isEmpty()) tenTacGiaNhap.add(s1.trim());
        }

        ArrayList<String> tenTheLoaiNhap = new ArrayList<>();
        for (String s1 : autoTheLoai.getText().toString().split(",")) {
            if (!s1.trim().isEmpty()) tenTheLoaiNhap.add(s1.trim());
        }

        // ================== 3. Tạo Map DB ==================
        HashMap<String, TacGia> mapTacGia = new HashMap<>();
        for (TacGia tg : listtacGia) {
            mapTacGia.put(tg.getTen().toLowerCase(), tg);
        }

        HashMap<String, TheLoai> mapTheLoai = new HashMap<>();
        for (TheLoai tl : listTheLoai) {
            mapTheLoai.put(tl.getTen().toLowerCase(), tl);
        }

        // ================== 4. Xử lý Tác Giả ==================
        for (String ten : tenTacGiaNhap) {
            TacGia tg;

            if (mapTacGia.containsKey(ten.toLowerCase())) {
                tg = mapTacGia.get(ten.toLowerCase());
            } else {
                TacGia moi = new TacGia();
                moi.setTen(ten);
                tg = moi.addTacGia();
                if (tg == null) {
                    Toast.makeText(this, "Không thêm được tác giả: " + ten, Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            SachTacGia stg = new SachTacGia();
            stg.setSachId(sach2.getId());
            stg.setTacGiaId(tg.getId());
            stg.addSachTacGia();
        }

        // ================== 5. Xử lý Thể Loại ==================
        for (String ten : tenTheLoaiNhap) {
            TheLoai tl;

            if (mapTheLoai.containsKey(ten.toLowerCase())) {
                tl = mapTheLoai.get(ten.toLowerCase());
            } else {
                TheLoai moi = new TheLoai();
                moi.setTen(ten);
                tl = moi.addTheLoai();
                if (tl == null) {
                    Toast.makeText(this, "Không thêm được thể loại: " + ten, Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            SachTheLoai stl = new SachTheLoai();
            stl.setSachId(sach2.getId());
            stl.setTheLoaiId(tl.getId());
            stl.addSachTheLoai();
        }

        Toast.makeText(this, "Thêm sách thành công!", Toast.LENGTH_LONG).show();
        resetForm();
    }


    private boolean isValidInput() {
        if (tenSachThemTxt.getText().toString().trim().isEmpty()) return false;
        if (nhaXuatBanTxt.getText().toString().trim().isEmpty()) return false;
        if (moTaTxt.getText().toString().trim().isEmpty()) return false;
        if (imgUrl.isEmpty()) return false;

        try {
            Integer.parseInt(namXuatBanTxt.getText().toString());
            Integer.parseInt(giaBanTxt.getText().toString());
            Integer.parseInt(giaThueTxt.getText().toString());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    private void resetForm() {
        tenSachThemTxt.setText("");
        nhaXuatBanTxt.setText("");
        namXuatBanTxt.setText("");
        giaBanTxt.setText("");
        giaThueTxt.setText("");
        moTaTxt.setText("");

        autoTacGia.setText("");
        autoTheLoai.setText("");

        imgUrl = "";

        anhSachThem.setImageResource(R.drawable.ic_media_24);
        // 👆 icon mặc định, bạn đổi theo app của bạn

        imageProgress.setVisibility(View.GONE);
    }

}