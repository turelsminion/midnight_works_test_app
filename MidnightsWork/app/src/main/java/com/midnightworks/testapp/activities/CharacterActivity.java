package com.midnightworks.testapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.midnightworks.testapp.R;
import com.midnightworks.testapp.data.local.models.CharacterModel;
import com.midnightworks.testapp.utils.GlideLoader;
import com.midnightworks.testapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CharacterActivity extends BaseActivity implements CharacterAdapter.OnItemClickListener{

	private final static String CHARACTER_KEY = "CHARACTER_KEY";
	private final static String ALIVE = "Alive";
	private final static String DEAD = "Dead";

	@BindView(R.id.rootLayout)
	ConstraintLayout rootLayout;
	@BindView(R.id.characterName)
	TextView characterNameTV;
	@BindView(R.id.characterImage)
	ImageView characterImage;
	@BindView(R.id.lastKnownLocation)
	TextView lastKnownLocation;
	@BindView(R.id.firstEpisode)
	TextView firstEpisode;
	@BindView(R.id.imageStatus)
	ImageView imageStatus;
	@BindView(R.id.characterStatus)
	TextView characterStatus;
	@BindView(R.id.alsoFrom)
	TextView alsoFrom;
	@BindView(R.id.recycler)
	RecyclerView recyclerView;

	private CharacterAdapter adapter;

	private CharacterModel character;
	private List<CharacterModel> charactersList;
	private int lastCharacterId;

	@Override
	protected int getLayoutResource() {
		return R.layout.activity_character;
	}

	@Override
	protected void launchNextActivity() {

	}

	protected static Intent getNewIntent(Context context, CharacterModel model) {
		Intent intent = new Intent(context, CharacterActivity.class);
		Bundle args = new Bundle();
		args.putParcelable(CHARACTER_KEY, model);
		intent.putExtras(args);
		return intent;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.bind(this);

		character = (CharacterModel) getIntent().getExtras().get(CHARACTER_KEY);
		charactersList = new ArrayList<>();
		initData();

		LinearLayoutManager llm = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(llm);
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		adapter = new CharacterAdapter(this, new ArrayList<>(), this::onItemClick, Glide.with(this));
	}

	@OnClick(R.id.back)
	public void onBackClicked() {
		Intent intent = new Intent(this, CharactersActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		onBackClicked();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getLocation();
	}

	private void getLocation() {
		// check for the internet connection
		if (!Utils.isNetworkConnected(this)) {
			Snackbar.make(rootLayout, getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();
			return;
		}

		// send API request
		getHandler().post(() -> getLocationFromServer());
	}

	private void getLocationFromServer() {
		addRxSubscription(mRepository.getEpisode(Utils.getPathIdFromURL(character.getLocationURL()))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(response -> {
					if (response != null) {
						lastCharacterId = Utils.getPathIdFromURL(response.getCharactersURLList().get(response.getCharactersURLList().size() - 1));
						for (String characterUrl : response.getCharactersURLList()) {
							getCharacterFromServer(Utils.getPathIdFromURL(characterUrl));
						}
					} else {
						Snackbar.make(rootLayout, getString(R.string.server_message_error), Snackbar.LENGTH_LONG).show();
					}
				}, error -> {
					Log.e(error.getMessage(), "Error get location");
					Snackbar.make(rootLayout, getString(R.string.server_error), Snackbar.LENGTH_LONG).show();
				}));
	}

	private void getCharacterFromServer(int id) {
		addRxSubscription(mRepository.getCharacter(id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(response -> {
					if (response != null) {
						charactersList.add(new CharacterModel(response));
						getEpisodeFromServer(Utils.getPathIdFromURL(response.getEpisodeList().get(0)), response.getId());
					} else {
						Snackbar.make(rootLayout, getString(R.string.server_message_error), Snackbar.LENGTH_LONG).show();
					}
				}, error -> {
					Log.e(error.getMessage(), "Error get character");
					Snackbar.make(rootLayout, getString(R.string.server_error), Snackbar.LENGTH_LONG).show();
				}));
	}

	private void getEpisodeFromServer(int id, int characterId) {
		addRxSubscription(mRepository.getEpisode(id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(response -> {
					if (response != null) {
						CharacterModel cm = findCharacterById(characterId);
						if (cm != null)
							cm.setFirstEpisodeName(response.getName());
						if (lastCharacterId == characterId)
							refreshList();
					} else {
						Snackbar.make(rootLayout, getString(R.string.server_message_error), Snackbar.LENGTH_LONG).show();
					}
				}, error -> {
					Log.e(error.getMessage(), "Error get episode");
					Snackbar.make(rootLayout, getString(R.string.server_error), Snackbar.LENGTH_LONG).show();
				}));
	}

	void initData() {
		GlideLoader.getInstance(this).loadAndCache(Glide.with(this),
				character.getImageURL(),
				characterImage,
				getString(R.string.cache_images_name_format, character.getId(), character.getName()));
		characterNameTV.setText(character.getName());
		lastKnownLocation.setText(character.getLocationName());
		firstEpisode.setText(character.getFirstEpisodeName());

		if (character.getStatus().equals(ALIVE))
			this.imageStatus.setImageDrawable(getDrawable(R.drawable.shape_oval_green));
		else if (character.getStatus().equals(DEAD))
			this.imageStatus.setImageDrawable(getDrawable(R.drawable.shape_oval_red));
		else
			this.imageStatus.setVisibility(View.GONE);

		characterStatus.setText(character.getStatus());
		alsoFrom.setText(getString(R.string.also_from, character.getLocationName()));
	}

	private void refreshList() {
		if (recyclerView != null) {
			if (charactersList.size() > 0) {
				recyclerView.setVisibility(View.VISIBLE);
			} else {
				recyclerView.setVisibility(View.INVISIBLE);
			}

			if (recyclerView.getAdapter() == null) {
				recyclerView.setAdapter(this.adapter);
			}
		}

		adapter.updateCharacterListItems(charactersList);
	}

	private CharacterModel findCharacterById(int id) {
		for (CharacterModel cm : this.charactersList) {
			if (cm.getId() == id)
				return cm;
		}
		return null;
	}

	@Override
	public void onItemClick(CharacterModel item) {
		startActivity(CharacterActivity.getNewIntent(this, item));
		this.finish();
	}
}
