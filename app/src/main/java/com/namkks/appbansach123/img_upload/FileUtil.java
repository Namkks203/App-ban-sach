package com.namkks.appbansach123.img_upload;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtil {

    public static File from(Context context, Uri uri) {
        try {
            InputStream input = context.getContentResolver().openInputStream(uri);
            String fileName = getFileName(context, uri);

            File file = new File(context.getCacheDir(), fileName);
            FileOutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }

            input.close();
            output.close();
            return file;

        } catch (Exception e) {
            return null;
        }
    }

    private static String getFileName(Context context, Uri uri) {
        String name = "image.jpg";
        Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (index >= 0) name = cursor.getString(index);
            cursor.close();
        }
        return name;
    }
}
