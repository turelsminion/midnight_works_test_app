package com.midnightworks.testapp.data.remote.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.midnightworks.testapp.utils.Utils.DATE_PATTERN;

public class DateSerializer implements JsonSerializer<Date> {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);

	public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(dateFormat.format(date));
	}

}
