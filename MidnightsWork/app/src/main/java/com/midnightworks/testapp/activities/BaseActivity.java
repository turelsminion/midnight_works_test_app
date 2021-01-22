package com.midnightworks.testapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.midnightworks.testapp.MainApplication;
import com.midnightworks.testapp.data.AppRepository;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {

	protected String TAG = "BaseActivity";

	@Inject
	AppRepository mRepository;

	private CompositeDisposable compositeSubscription = new CompositeDisposable();
	private Handler mHandler;

	protected abstract int getLayoutResource();

	protected abstract void launchNextActivity();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());
		MainApplication.getAppComponent().inject(this);
		mHandler = new Handler(Looper.getMainLooper());
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(newBase);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		clearRxSubscription();
	}

	protected void addRxSubscription(Disposable subscription) {
		if (compositeSubscription != null)
			compositeSubscription.add(subscription);
	}

	private void clearRxSubscription() {
		if (compositeSubscription != null)
			compositeSubscription.clear();
	}

	public Handler getHandler() {
		return mHandler;
	}


}
