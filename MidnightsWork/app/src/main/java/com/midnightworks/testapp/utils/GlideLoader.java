package com.midnightworks.testapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.midnightworks.testapp.R;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

public class GlideLoader {

	private static final String TAG = GlideLoader.class.getName();

	private static GlideLoader glideLoader = null;
	private Context context;

	private GlideLoader(Context context) {
		this.context = context;
	}

	public static synchronized GlideLoader getInstance(final Context context) {
		if (glideLoader == null) {
			if (context == null) {
				NullPointerException exception = new NullPointerException("Context can't be null while initializing GlideLoader singleton!");
				Log.e(TAG, "Context can't be null while initializing GlideLoader");
				throw exception;
			}
			glideLoader = new GlideLoader(context);
		}
		return glideLoader;
	}

	public void loadImage(RequestManager requestManager, String imageUrl, ImageView imageView) {
		requestManager
				.load(imageUrl)
				.apply(RequestOptions.centerCropTransform())
				//.placeholder(placeholderResource)
				//.error(errorResource)
				.into(imageView);
	}

	public void loadAndCache(RequestManager requestManager, String imageUrl, ImageView imageView, String name) {
		requestManager.load(imageUrl).apply(new RequestOptions()
				.signature(new ObjectKey(name)))
				.into(imageView);
	}

	public void loadOnlyFromCache(RequestManager requestManager, String imageUrl, ImageView imageView, String name) {
		requestManager.load(imageUrl)
				.apply(new RequestOptions()
				.onlyRetrieveFromCache(true)
				.signature(new ObjectKey(name)))
				.into(imageView);
	}
}
