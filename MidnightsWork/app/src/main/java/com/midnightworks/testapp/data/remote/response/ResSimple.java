package com.midnightworks.testapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class ResSimple {

	@SerializedName("name")
	private String name;

	@SerializedName("url")
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
