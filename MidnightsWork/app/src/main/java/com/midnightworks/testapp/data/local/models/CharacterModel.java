package com.midnightworks.testapp.data.local.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.midnightworks.testapp.data.local.tables.CharacterTable;
import com.midnightworks.testapp.data.remote.response.ResCharacter;
import com.midnightworks.testapp.data.remote.response.ResSimple;
import com.raizlabs.android.dbflow.StringUtils;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;

import java.util.Date;
import java.util.Objects;

public class CharacterModel implements Parcelable {

	private int id;
	private String name;
	private String status;
	private String species;
	private String type;
	private String gender;
	private String imageURL;
	private Date created;
	private String originName;
	private String originURL;
	private String locationName;
	private String locationURL;
	private String firstEpisodeURL;
	private String firstEpisodeName;
	private String episodeList;

	public CharacterModel() {
	}

	public CharacterModel(ResCharacter character) {
		this.id = character.getId();
		this.name = character.getName();
		this.status = character.getStatus();
		this.species = character.getSpecies();
		this.type = character.getType();
		this.gender = character.getGender();
		this.imageURL = character.getImageURL();
		this.created = character.getCreated();
		if (character.getOrigin() != null) {
			this.originName = character.getOrigin().getName();
			this.originURL = character.getOrigin().getUrl();
		}
		if (character.getLocation() != null) {
			this.locationName = character.getLocation().getName();
			this.locationURL = character.getLocation().getUrl();
		}
		if (character.getEpisodeList() != null && character.getEpisodeList().size() > 0) {
			this.firstEpisodeURL = character.getEpisodeList().get(0);
			this.episodeList = TextUtils.join(",", character.getEpisodeList());
		}
	}

	public CharacterModel(CharacterTable character) {
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

	protected CharacterModel(Parcel in) {
		id = in.readInt();
		name = in.readString();
		status = in.readString();
		species = in.readString();
		type = in.readString();
		gender = in.readString();
		imageURL = in.readString();
		originName = in.readString();
		originURL = in.readString();
		locationName = in.readString();
		locationURL = in.readString();
		firstEpisodeURL = in.readString();
		firstEpisodeName = in.readString();
		episodeList = in.readString();
	}

	public static final Creator<CharacterModel> CREATOR = new Creator<CharacterModel>() {
		@Override
		public CharacterModel createFromParcel(Parcel in) {
			return new CharacterModel(in);
		}

		@Override
		public CharacterModel[] newArray(int size) {
			return new CharacterModel[size];
		}
	};

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

	public String getFirstEpisodeURL() {
		return firstEpisodeURL;
	}

	public void setFirstEpisodeURL(String firstEpisodeURL) {
		this.firstEpisodeURL = firstEpisodeURL;
	}

	public String getEpisodeList() {
		return episodeList;
	}

	public void setEpisodeList(String episodeList) {
		this.episodeList = episodeList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CharacterModel)) return false;
		CharacterModel that = (CharacterModel) o;
		return id == that.id &&
				Objects.equals(name, that.name) &&
				Objects.equals(status, that.status) &&
				Objects.equals(species, that.species) &&
				Objects.equals(type, that.type) &&
				Objects.equals(gender, that.gender) &&
				Objects.equals(imageURL, that.imageURL) &&
				Objects.equals(created, that.created) &&
				Objects.equals(originName, that.originName) &&
				Objects.equals(originURL, that.originURL) &&
				Objects.equals(locationName, that.locationName) &&
				Objects.equals(locationURL, that.locationURL) &&
				Objects.equals(firstEpisodeURL, that.firstEpisodeURL) &&
				Objects.equals(firstEpisodeName, that.firstEpisodeName) &&
				Objects.equals(episodeList, that.episodeList);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(status);
		dest.writeString(species);
		dest.writeString(type);
		dest.writeString(gender);
		dest.writeString(imageURL);
		dest.writeString(originName);
		dest.writeString(originURL);
		dest.writeString(locationName);
		dest.writeString(locationURL);
		dest.writeString(firstEpisodeURL);
		dest.writeString(firstEpisodeName);
		dest.writeString(episodeList);
	}
}
