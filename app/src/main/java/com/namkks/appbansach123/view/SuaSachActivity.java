package com.namkks.appbansach123.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.File;
import java.util.concurrent.Executors;

public class SuaSachActivity extends AppCompatActivity {
    private String imgUrl = "";

    private Toolbar toolbar;
    private EditText tenSachThemTxt, nhaXuatBanTxt, namXuatBanTxt,
            giaBanTxt, giaThueTxt, moTaTxt;
    private ImageView anhSachThem;
    private Button themSachBtn, themAnhSachBtn;
    private ProgressBar imageProgress;
    private Sach s;

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
        setContentView(R.layout.activity_sua_sach);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int id_sach = getIntent().getIntExtra("id_sach", 0);
        s = Sach.getSachById(id_sach);
        initView();
        setupToolbar();
        setupActions();
        LoadData();
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
    }
    @SuppressLint("RestrictedApi")
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    // ================== Actions ==================
    private void setupActions() {
        themAnhSachBtn.setOnClickListener(v -> openFileManager());
        themSachBtn.setOnClickListener(v -> handleSuaSach());
    }
    private void LoadData(){
        if(s != null){
            tenSachThemTxt.setText(s.getTenSach());
            nhaXuatBanTxt.setText(s.getNhaXuatBan());
            namXuatBanTxt.setText(s.getNamXuatBan());
            giaBanTxt.setText(s.getGiaBan() + "");
            giaThueTxt.setText(s.getGiaThue() +"");
            loadImage(s.getAnh());
            moTaTxt.setText(s.getMoTa());
        }
    }
    // ================== Add Book ==================
    private void handleSuaSach() {
        if (!isValidInput()) {
            Toast.makeText(this,
                    "Vui lòng nhập đầy đủ & đúng định dạng!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Sach s = new Sach();
        s.setTenSach(tenSachThemTxt.getText().toString().trim());
        s.setNhaXuatBan(nhaXuatBanTxt.getText().toString().trim());
        s.setNamXuatBan(Integer.parseInt(namXuatBanTxt.getText().toString()));
        s.setGiaBan(Integer.parseInt(giaBanTxt.getText().toString()));
        s.setGiaThue(Integer.parseInt(giaThueTxt.getText().toString()));
        s.setAnh(imgUrl);
        s.setMoTa(moTaTxt.getText().toString().trim());

        if (s.suaSach()) {
            Toast.makeText(this, "Sửa sách thành công!", Toast.LENGTH_SHORT).show();
            recreate();
        } else {
            Toast.makeText(this, "Sửa sách thất bại!", Toast.LENGTH_SHORT).show();
        }
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
                Toast.makeText(SuaSachActivity.this,
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
}