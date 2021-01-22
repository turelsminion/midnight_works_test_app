package com.midnightworks.testapp.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.midnightworks.testapp.data.AppRepository;
import com.midnightworks.testapp.data.local.LocalDataStore;
import com.midnightworks.testapp.data.remote.RemoteDataStore;
import com.midnightworks.testapp.data.remote.api.ApiInterface;
import com.midnightworks.testapp.data.remote.api.GsonHelper;
import com.midnightworks.testapp.data.remote.api.InterceptorLogging;
import com.midnightworks.testapp.utils.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

	String baseURL;

	public DataModule(String baseURL) {
		this.baseURL = baseURL;
	}

	@Provides
	@Singleton
	AppRepository providesAppRepository(Context context, LocalDataStore localDataStore,
	                                    RemoteDataStore remoteDataStore, SharedPreferences sharedPreferences) {
		return new AppRepository(context, localDataStore, remoteDataStore, sharedPreferences);
	}

	@Provides
	@Singleton
	LocalDataStore providesLocalDataStore() {
		return new LocalDataStore();
	}

	@Provides
	@Singleton
	RemoteDataStore providesRemoteDataStore() {
		return new RemoteDataStore();
	}

	@Provides
	@Singleton
	ApiInterface providesApiClient(Retrofit retroFit) {
		return retroFit.create(ApiInterface.class);
	}

	@Provides
	Retrofit getRetrofit(OkHttpClient okHttpClient) {
		return new Retrofit.Builder()
				.baseUrl(Constants.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create(GsonHelper.getGson()))
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.client(okHttpClient)
				.build();
	}

	@Provides
	OkHttpClient getOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
		return new OkHttpClient.Builder()
				.readTimeout(Constants.READ_TIMEOUT_SEC, TimeUnit.SECONDS)
				.connectTimeout(Constants.CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
				.writeTimeout(Constants.WRITE_TIMEOUT_SEC, TimeUnit.SECONDS)
				.addInterceptor(httpLoggingInterceptor)
				.addNetworkInterceptor(new StethoInterceptor())
				.addInterceptor(new InterceptorLogging())
				.build();
	}

	@Provides
	HttpLoggingInterceptor getHttpLoggingInterceptor() {
		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
		httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		return httpLoggingInterceptor;
	}
}
