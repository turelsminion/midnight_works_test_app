package com.midnightworks.testapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.midnightworks.testapp.R;
import com.midnightworks.testapp.data.local.models.CharacterModel;
import com.midnightworks.testapp.data.remote.response.ResCharacter;
import com.midnightworks.testapp.utils.ImageCache;
import com.midnightworks.testapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CharactersActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, CharacterAdapter.OnItemClickListener {

	@BindView(R.id.rootLayout)
	ConstraintLayout rootLayout;
	@BindView(R.id.refresh)
	SwipeRefreshLayout swipeRefreshLayout;
	@BindView(R.id.recycler)
	RecyclerView recyclerView;

	private CharacterAdapter adapter;
	private int lastCharacterId;

	private int page = 1;
	boolean isScrolling;
	int currentItems;
	int totalItems;
	int scrollOutItems;


	@Override
	protected int getLayoutResource() {
		return R.layout.activity_characters;
	}

	@Override
	protected void launchNextActivity() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ButterKnife.bind(this);
		mRepository.getLocalDataStore().clearCharacters();

		setupRecycler();
		adapter = new CharacterAdapter(this, new ArrayList<>(), this::onItemClick, Glide.with(this));
	}

	@Override
	protected void onResume() {
		super.onResume();
		getCharacters();
	}

	private void getCharacters() {
		// check for the internet connection
		if (!Utils.isNetworkConnected(this)) {
			showRetrySnackBar(getString(R.string.no_internet)).show();
			return;
		}

		// send API request
		getHandler().post(() -> getCharactersFromServer());
	}

	private void getCharactersFromServer() {
		addRxSubscription(mRepository.getCharacters(page)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(response -> {
					if (response != null) {
						mRepository.storeLastPage(page);
						this.lastCharacterId = response.getResults().get(response.getResults().size() - 1).getId();
						for (ResCharacter character : response.getResults()) {
							CharacterModel characterModel = new CharacterModel(character);
							mRepository.getLocalDataStore().saveCharacter(new CharacterModel(character));
							getEpisodeFromServer(Utils.getPathIdFromURL(characterModel.getFirstEpisodeURL()), character.getId());
						}
					} else {
						showRetrySnackBar(getString(R.string.server_message_error)).show();
					}
				}, error -> {
					Log.e(error.getMessage(), "Error get characters");
					showRetrySnackBar(getString(R.string.server_error)).show();
				}));
	}

	private void getEpisodeFromServer(int id, int characterId) {
		addRxSubscription(mRepository.getEpisode(id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(response -> {
					if (response != null) {
						mRepository.getLocalDataStore().updateCharacter(characterId, response.getName());
						if (characterId == this.lastCharacterId)
							refreshList();
					} else {
						showRetrySnackBar(getString(R.string.server_message_error)).show();
					}
				}, error -> {
					Log.e(error.getMessage(), "Error get episode");
					showRetrySnackBar(getString(R.string.server_error)).show();
				}));
	}

	@Override
	public void onRefresh() {
		page = 1;
		mRepository.getLocalDataStore().clearCharacters();
		getCharacters();
	}

	private void refreshList() {
		List<CharacterModel> characterModels = mRepository.getLocalDataStore().getCharactersFromLocalStore();

		if (recyclerView != null) {
			if (characterModels.size() > 0) {
				recyclerView.setVisibility(View.VISIBLE);
			} else {
				recyclerView.setVisibility(View.INVISIBLE);
			}

			if (recyclerView.getAdapter() == null) {
				recyclerView.setAdapter(this.adapter);
			}
		}
		swipeRefreshLayout.setRefreshing(false);
		adapter.updateCharacterListItems(characterModels);
	}

	private Snackbar showRetrySnackBar(String message) {
		Snackbar snackbar = Snackbar.make(rootLayout, message, Snackbar.LENGTH_LONG);
		snackbar.setAction(getString(R.string.retry), v -> {
			getCharacters();
			swipeRefreshLayout.setRefreshing(false);
			snackbar.dismiss();
		});
		snackbar.show();
		return snackbar;
	}

	private void setupRecycler() {
		LinearLayoutManager llm = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(llm);
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		swipeRefreshLayout.setOnRefreshListener(this);
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
					isScrolling = true;
			}

			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				currentItems = llm.getChildCount();
				totalItems = llm.getItemCount();
				scrollOutItems = llm.findFirstCompletelyVisibleItemPosition();
				if (isScrolling && currentItems + scrollOutItems == totalItems) {
					isScrolling = false;
					page++;
					getCharacters();
				}
			}
		});
	}

	@Override
	public void onItemClick(CharacterModel item) {
		startActivity(CharacterActivity.getNewIntent(this, item));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
