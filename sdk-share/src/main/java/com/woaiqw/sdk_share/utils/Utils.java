package com.woaiqw.sdk_share.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by haoran on 2018/8/3.
 */

public class Utils {

    public static String getSerializePath(Application app) {
        File dir;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = app.getExternalFilesDir("share-sdk");
        } else {
            dir = app.getCacheDir();
        }
        assert dir != null;
        File serializeFile = new File(dir, "sdk.txt");
        return serializeFile.getAbsolutePath();
    }


    public static Bitmap getWxShareBitmap(Bitmap targetBitmap) {
        float scale = Math.min((float) 150 / targetBitmap.getWidth(), (float) 150 / targetBitmap.getHeight());
        Bitmap fixedBmp = Bitmap.createScaledBitmap(targetBitmap, (int) (scale * targetBitmap.getWidth()), (int) (scale * targetBitmap.getHeight()), false);
        return fixedBmp;
    }


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    static String md5(String content) {
        try {
            if (TextUtils.isEmpty(content)) {
                return "";
            }
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(content.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
