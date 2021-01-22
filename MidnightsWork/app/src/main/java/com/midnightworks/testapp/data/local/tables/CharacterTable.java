package com.midnightworks.testapp.data.local.tables;

import com.midnightworks.testapp.data.local.AppDatabase;
import com.midnightworks.testapp.data.local.models.CharacterModel;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(database = AppDatabase.class)
public class CharacterTable extends BaseModel {

	@PrimaryKey
	private int id;
	@Column
	private String name;
	@Column
	private String status;
	@Column
	private String species;
	@Column
	private String type;
	@Column
	private String gender;
	@Column
	private String imageURL;
	@Column
	private Date created;
	@Column
	private String originName;
	@Column
	private String originURL;
	@Column
	private String locationName;
	@Column
	private String locationURL;
	@Column
	private String firstEpisodeURL;
	@Column
	private String firstEpisodeName;
	@Column
	private String episodeList;

	public CharacterTable() {
	}

	public CharacterTable(CharacterModel character) {
		this.id = character.getId();
		this.name = character.getName();
		this.status = character.getStatus();
		this.species = character.getSpecies();
		this.type = character.getType();
		this.gender = character.getGender();
		this.imageURL = character.getImageURL();
		this.created = character.getCreated();
		this.originName = character.getOriginName();
		this.originURL = character.getOriginURL();
		this.locationName = character.getLocationName();
		this.locationURL = character.getLocationURL();
		this.firstEpisodeURL = character.getFirstEpisodeURL();
		this.firstEpisodeName = character.getFirstEpisodeName();
		this.episodeList = character.getEpisodeList();
	}

	public String getFirstEpisodeName() {
		return firstEpisodeName;
	}

	public void setFirstEpisodeName(String firstEpisodeName) {
		this.firstEpisodeName = firstEpisodeName;
	}

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

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getOriginURL() {
		return originURL;
	}

	public void setOriginURL(String originURL) {
		this.originURL = originURL;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationURL() {
		return locationURL;
	}

	public void setLocationURL(String locationURL) {
		this.locationURL = locationURL;
	}

	public String getEpisodeList() {
		return episodeList;
	}

	public void setEpisodeList(String episodeList) {
		this.episodeList = episodeList;
	}

	public String getFirstEpisodeURL() {
		return firstEpisodeURL;
	}

	public void setFirstEpisodeURL(String firstEpisodeURL) {
		this.firstEpisodeURL = firstEpisodeURL;
	}
}
