package com.midnightworks.testapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ResCharacter {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("status")
	private String status;

	@SerializedName("species")
	private String species;

	@SerializedName("type")
	private String type;

	@SerializedName("gender")
	private String gender;

	@SerializedName("image")
	private String imageURL;

	@SerializedName("created")
	private Date created;

	@SerializedName("origin")
	private ResSimple origin;

	@SerializedName("location")
	private ResSimple location;

	@SerializedName("episode")
	private List<String> episodeList;

	@SerializedName("url")
	private String url;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public ResSimple getOrigin() {
		return origin;
	}

	public void setOrigin(ResSimple origin) {
		this.origin = origin;
	}

	public ResSimple getLocation() {
		return location;
	}

	public void setLocation(ResSimple location) {
		this.location = location;
	}

	public List<String> getEpisodeList() {
		return episodeList;
	}

	public void setEpisodeList(List<String> episodeList) {
		this.episodeList = episodeList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ResCharacter)) return false;
		ResCharacter that = (ResCharacter) o;
		return id == that.id &&
				Objects.equals(name, that.name) &&
				Objects.equals(status, that.status) &&
				Objects.equals(species, that.species) &&
				Objects.equals(type, that.type) &&
				Objects.equals(gender, that.gender) &&
				Objects.equals(imageURL, that.imageURL) &&
				Objects.equals(created, that.created) &&
				Objects.equals(origin, that.origin) &&
				Objects.equals(location, that.location) &&
				Objects.equals(episodeList, that.episodeList);
	}
}
