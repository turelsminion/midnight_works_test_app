package com.midnightworks.testapp.data.local;

import android.content.Context;

import androidx.annotation.NonNull;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class DatabaseHelper {

	public DatabaseHelper(@NonNull Context context) {
		initialize(context);
	}

	private void initialize(Context context) {
		FlowManager.init(new FlowConfig.Builder(context).build());
	}

}
