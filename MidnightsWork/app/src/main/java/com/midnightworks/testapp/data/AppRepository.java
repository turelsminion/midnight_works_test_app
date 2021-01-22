package com.midnightworks.testapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.midnightworks.testapp.data.local.LocalDataStore;
import com.midnightworks.testapp.data.remote.RemoteDataStore;
import com.midnightworks.testapp.data.remote.response.ResCharacter;
import com.midnightworks.testapp.data.remote.response.ResCharacters;
import com.midnightworks.testapp.data.remote.response.ResEpisode;
import com.midnightworks.testapp.data.remote.response.ResLocation;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class AppRepository {

	private final String PAGE = "PAGE";

	private final CompositeDisposable compositeDisposable = new CompositeDisposable();
	private LocalDataStore localDataStore;
	private RemoteDataStore remoteDataStore;
	private SharedPreferences sharedPreferences;
	private Context context;

	public AppRepository(Context context, LocalDataStore localDataStore, RemoteDataStore remoteDataStore, SharedPreferences sharedPreferences) {
		this.context = context;
		this.localDataStore = localDataStore;
		this.remoteDataStore = remoteDataStore;
		this.sharedPreferences = sharedPreferences;

		this.localDataStore.setRemoteDataStore(remoteDataStore);
		this.remoteDataStore.setLocalDataStore(localDataStore);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		compositeDisposable.dispose();
	}

	public LocalDataStore getLocalDataStore() {
		return localDataStore;
	}

	private void clearPreferences() {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.apply();
	}

	public void storeLastPage(int page) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(PAGE, page);
		editor.apply();
	}

	public int getLastPage() {
		return sharedPreferences.getInt(PAGE, 1);
	}

	private <T> Single<T> runAPIcall(Single<T> single) {
		return single;
	}

	public Single<ResCharacters> getCharacters(int page) {
		return remoteDataStore.getCharacters(page);
	}

	public Single<ResCharacter> getCharacter(int id) {
		return remoteDataStore.getCharacter(id);
	}

	public Single<ResEpisode> getEpisode(int id) {
		return remoteDataStore.getEpisode(id);
	}

	public Single<ResLocation> getLocation(int id) {
		return remoteDataStore.getLocation(id);
	}



}
