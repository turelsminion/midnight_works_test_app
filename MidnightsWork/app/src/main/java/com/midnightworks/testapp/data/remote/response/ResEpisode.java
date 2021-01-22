package com.midnightworks.testapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class ResEpisode {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("air_date")
	private String airDate;

	@SerializedName("episode")
	private String episode;

	@SerializedName("characters")
	private List<String> charactersURLList;

	@SerializedName("url")
	private String url;

	@SerializedName("created")
	private Date created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAirDate() {
		return airDate;
	}

	public void setAirDate(String airDate) {
		this.airDate = airDate;
	}

	public String getEpisode() {
		return episode;
	}

	public void setEpisode(String episode) {
		this.episode = episode;
	}

	public List<String> getCharactersURLList() {
		return charactersURLList;
	}

	public void setCharactersURLList(List<String> charactersURLList) {
		this.charactersURLList = charactersURLList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
