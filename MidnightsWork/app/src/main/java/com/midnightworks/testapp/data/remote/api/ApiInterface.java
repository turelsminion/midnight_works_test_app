package com.midnightworks.testapp.data.remote.api;

import com.midnightworks.testapp.data.remote.response.ResCharacter;
import com.midnightworks.testapp.data.remote.response.ResCharacters;
import com.midnightworks.testapp.data.remote.response.ResEpisode;
import com.midnightworks.testapp.data.remote.response.ResLocation;
import com.midnightworks.testapp.utils.Constants;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

	@Headers({"Content-Type: application/json"})
	@GET(Constants.API_PREFIX + "character")
	Single<Response<ResCharacters>> getCharacters(@Query("page") int page);

	@Headers({"Content-Type: application/json"})
	@GET(Constants.API_PREFIX + "character/{id}")
	Single<Response<ResCharacter>> getCharacter(@Path("id") int id);

	@Headers({"Content-Type: application/json"})
	@GET(Constants.API_PREFIX + "episode/{id}")
	Single<Response<ResEpisode>> getEpisode(@Path("id") int id);

	@Headers({"Content-Type: application/json"})
	@GET(Constants.API_PREFIX + "location/{id}")
	Single<Response<ResLocation>> getLocation(@Path("id") int id);

}
