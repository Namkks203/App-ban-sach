package com.namkks.appbansach123.img_upload;

import com.namkks.appbansach123.BuildConfig;

import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageUploader {
    private Call currentCall;
    private static final String API_KEY = BuildConfig.IMGBB_API_KEY;
    private static final String UPLOAD_URL =
            "https://api.imgbb.com/1/upload?key=" + API_KEY;

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build();

    public interface UploadCallback {
        void onProgress(int percent);
        void onSuccess(String imageUrl);
        void onError(String error);
    }

    public void uploadImage(File file, UploadCallback callback) {

        RequestBody fileBody =
                RequestBody.create(file, MediaType.parse("image/*"));

        MultipartBody multipartBody =
                new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image", file.getName(), fileBody)
                        .build();

        ProgressRequestBody progressBody =
                new ProgressRequestBody(multipartBody, (written, total) -> {
                    int percent = (int) ((written * 100) / total);
                    callback.onProgress(percent);
                });

        Request request = new Request.Builder()
                .url(UPLOAD_URL)
                .post(progressBody)
                .build();

        currentCall = client.newCall(request);
        currentCall.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, java.io.IOException e) {
                if (call.isCanceled()) {
                    callback.onError("Upload đã bị huỷ");
                } else {
                    callback.onError(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String res = response.body().string();
                    JSONObject json = new JSONObject(res);

                    if (json.getBoolean("success")) {
                        String url = json
                                .getJSONObject("data")
                                .getString("url");
                        callback.onSuccess(url);
                    } else {
                        callback.onError("Upload failed");
                    }
                } catch (Exception e) {
                    callback.onError(e.getMessage());
                }
            }
        });
    }
    public void cancelUpload() {
        if (currentCall != null && !currentCall.isCanceled()) {
            currentCall.cancel();
        }
    }
}
