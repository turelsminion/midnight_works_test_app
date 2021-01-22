package com.midnightworks.testapp.dagger.module;

import android.content.Context;

import androidx.annotation.NonNull;

import com.midnightworks.testapp.data.local.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

	@Provides
	@Singleton
	DatabaseHelper provideSQLiteOpenHelper(@NonNull Context context) {
		return new DatabaseHelper(context);
	}

}
