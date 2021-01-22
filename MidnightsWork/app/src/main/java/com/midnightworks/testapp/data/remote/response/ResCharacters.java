package com.midnightworks.testapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResCharacters {

	@SerializedName("info")
	private ResInfo info;

	@SerializedName("results")
	private List<ResCharacter> results;

	public ResInfo getInfo() {
		return info;
	}

	public void setInfo(ResInfo info) {
		this.info = info;
	}

	public List<ResCharacter> getResults() {
		return results;
	}

	public void setResults(List<ResCharacter> results) {
		this.results = results;
	}
}
