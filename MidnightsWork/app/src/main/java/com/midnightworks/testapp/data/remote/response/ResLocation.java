package com.midnightworks.testapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class ResLocation {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("type")
	private String type;

	@SerializedName("dimension")
	private String dimension;

	@SerializedName("residents")
	private List<String> residents;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public List<String> getResidents() {
		return residents;
	}

	public void setResidents(List<String> residents) {
		this.residents = residents;
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
