package com.midnightworks.testapp.data.remote.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

public class GsonHelper {

	private GsonHelper() {
	}

	public static Gson getGson() {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new DateSerializer())
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create();

		return gson;
	}

}
