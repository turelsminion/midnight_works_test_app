package com.midnightworks.testapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;


public class ImageCache {

	public static final String TAG = "ImageCache";

	private static final String CHILD_DIR = "images";
	private static final String TEMP_FILE_NAME = "img";
	private static final String FILE_EXTENSION = ".png";

	private static final int COMPRESS_QUALITY = 100;

	public File saveImgToCache(Context context, Bitmap bitmap, @Nullable String name) {
		File cachePath = null;
		String fileName = TEMP_FILE_NAME;
		if (!TextUtils.isEmpty(name)) {
			fileName = name;
		}
		try {
			cachePath = new File(context.getCacheDir(), CHILD_DIR);
			cachePath.mkdirs();

			FileOutputStream stream = new FileOutputStream(cachePath + "/" + fileName + FILE_EXTENSION);
			bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESS_QUALITY, stream);
			stream.close();
		} catch (IOException e) {
			Log.e(TAG, "saveImgToCache error: " + bitmap, e);
		}
		return cachePath;
	}

	public Uri getUriByFileName(Context context, String name) {
		String fileName;
		if (!TextUtils.isEmpty(name)) {
			fileName = name;
		} else {
			return null;
		}

		File imagePath = new File(context.getCacheDir(), CHILD_DIR);
		File newFile = new File(imagePath, fileName + FILE_EXTENSION);
		return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", newFile);
	}

	private Uri getImageUri(Context context, File fileDir, @Nullable String name) {
		String fileName = TEMP_FILE_NAME;
		if (!TextUtils.isEmpty(name)) {
			fileName = name;
		}
		File newFile = new File(fileDir, fileName + FILE_EXTENSION);
		return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", newFile);
	}

	public Uri saveToCacheAndGetUri(Context context, Bitmap bitmap, @Nullable String name) {
		File file = saveImgToCache(context, bitmap, name);
		return getImageUri(context, file, name);
	}


}
