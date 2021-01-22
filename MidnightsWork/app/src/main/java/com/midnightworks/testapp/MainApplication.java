package com.midnightworks.testapp;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.midnightworks.testapp.dagger.component.AppComponent;
import com.midnightworks.testapp.dagger.component.DaggerAppComponent;
import com.midnightworks.testapp.dagger.module.AppModule;
import com.midnightworks.testapp.dagger.module.DataModule;
import com.midnightworks.testapp.dagger.module.DbModule;
import com.midnightworks.testapp.data.AppRepository;
import com.midnightworks.testapp.utils.Constants;

import javax.inject.Inject;

public final class MainApplication extends Application {

	//Constants
	private static final String TAG = "MainApplication";
	public static MainApplication INSTANCE;

	//State
	private static AppComponent mAppComponent;

	@Inject
	AppRepository mAppRepository;

	public static AppComponent getAppComponent() {
		return mAppComponent;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		INSTANCE = this;

		mAppComponent = DaggerAppComponent.builder()
				.appModule(new AppModule(this))
				.dbModule(new DbModule())
				.dataModule(new DataModule(Constants.BASE_URL))
				.build();

		mAppComponent.inject(this);

		Stetho.initializeWithDefaults(this);
	}
}
