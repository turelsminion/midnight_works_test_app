package com.midnightworks.testapp.data.local;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
	public static final String NAME = "AppDatabase";
	public static final int VERSION = 2;
}

/**
 * Version History
 * 1: original release
 */