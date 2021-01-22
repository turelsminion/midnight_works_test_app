package com.midnightworks.testapp.dagger.component;

import androidx.annotation.NonNull;

import com.midnightworks.testapp.MainApplication;
import com.midnightworks.testapp.activities.BaseActivity;
import com.midnightworks.testapp.dagger.module.AppModule;
import com.midnightworks.testapp.dagger.module.DataModule;
import com.midnightworks.testapp.dagger.module.DbModule;
import com.midnightworks.testapp.data.local.LocalDataStore;
import com.midnightworks.testapp.data.remote.RemoteDataStore;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class, DbModule.class})
public interface AppComponent {

	void inject(@NonNull BaseActivity activity);

	void inject(@NonNull MainApplication application);

	void inject(@NonNull RemoteDataStore remoteDataStore);

	void inject(@NonNull LocalDataStore localDataStore);

}
