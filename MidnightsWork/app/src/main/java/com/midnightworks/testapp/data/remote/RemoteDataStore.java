package com.midnightworks.testapp.data.remote;

import android.util.Log;

import com.midnightworks.testapp.MainApplication;
import com.midnightworks.testapp.data.local.LocalDataStore;
import com.midnightworks.testapp.data.remote.api.ApiInterface;
import com.midnightworks.testapp.data.remote.response.ResCharacter;
import com.midnightworks.testapp.data.remote.response.ResCharacters;
import com.midnightworks.testapp.data.remote.response.ResEpisode;
import com.midnightworks.testapp.data.remote.response.ResLocation;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

public class RemoteDataStore {

	private static final String TAG = "RemoteDataStore";

	@Inject
	volatile ApiInterface apiClient;

	@Inject
	MainApplication app;

	LocalDataStore localDataStore;

	public RemoteDataStore() {
		MainApplication.getAppComponent().inject(this);
	}

	public void setLocalDataStore(LocalDataStore localDataStore) {
		this.localDataStore = localDataStore;
	}

	public Single<ResCharacters> getCharacters(int page) {
		return createAPIcall(apiClient.getCharacters(page));
	}

	public Single<ResCharacter> getCharacter(int id) {
		return createAPIcall(apiClient.getCharacter(id));
	}

	public Single<ResEpisode> getEpisode(int id) {
		return createAPIcall(apiClient.getEpisode(id));
	}

	public Single<ResLocation> getLocation(int id) {
		return createAPIcall(apiClient.getLocation(id));
	}


	/**
	 * Generic for creating api calls Single-s
	 *
	 * @param <T> - generic
	 */
	private <T> Single<T> createAPIcall(Single<Response<T>> single) {
		return Single.create(subscriber -> {
			Log.i("using apiClient %s", apiClient.toString());
			single
					.subscribeOn(Schedulers.io())
					.observeOn(Schedulers.io())
					.subscribe(response -> {
						if (response.isSuccessful()) {
							if (response.body() != null) {
								if (!subscriber.isDisposed()) subscriber.onSuccess(response.body());
							} else {
								if (!subscriber.isDisposed())
									subscriber.onError(new Exception("Body is null"));
							}
						} else {
							if (!subscriber.isDisposed())
								subscriber.onError(new HttpException(response));
						}
					}, throwable -> {
						throwable.printStackTrace();
						if (!subscriber.isDisposed()) subscriber.onError(throwable);
					});
		});
	}
}
