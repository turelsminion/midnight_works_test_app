package com.midnightworks.testapp.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.midnightworks.testapp.R;
import com.midnightworks.testapp.data.local.models.CharacterModel;
import com.midnightworks.testapp.utils.GlideLoader;
import com.midnightworks.testapp.utils.ImageCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;

import java.net.URL;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharactersViewHolder>{

	private static final String TAG = "CharacterAdapter";

	public interface OnItemClickListener {
		void onItemClick(CharacterModel item);
	}

	private final OnItemClickListener listener;
	private List<CharacterModel> list;
	private Context context;
	private RequestManager requestManager;
	private ImageCache imageCache;

	public CharacterAdapter(Context context, List<CharacterModel> list, OnItemClickListener listener, RequestManager requestManager) {
		this.listener = listener;
		this.list = list;
		this.context = context;
		this.requestManager = requestManager;
		this.imageCache = new ImageCache();
	}

	@NonNull
	@Override
	public CharactersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character, parent, false);

		return new CharactersViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull CharactersViewHolder holder, int position) {
		holder.bindView(position);
	}

	@Override
	public int getItemCount() {
		return list == null ? 0 : list.size();
	}

	public void updateCharacterListItems(List<CharacterModel> models) {
		final CharactersDiffCallback diffCallback = new CharactersDiffCallback(this.list, models);
		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

		this.list.clear();
		this.list.addAll(models);
		diffResult.dispatchUpdatesTo(this);
	}


	class CharactersViewHolder extends RecyclerView.ViewHolder {

		CardView rootLayout;
		ImageView characterImage;
		TextView characterName;
		TextView characterLocation;
		TextView characterEpisode;

		public CharactersViewHolder(@NonNull View itemView) {
			super(itemView);
			this.characterImage = itemView.findViewById(R.id.characterImage);
			characterName = itemView.findViewById(R.id.characterName);
			characterLocation = itemView.findViewById(R.id.characterLocation);
			characterEpisode = itemView.findViewById(R.id.characterEpisode);
			rootLayout = itemView.findViewById(R.id.rootLayout);
		}

		private void bindView(int position) {
			CharacterModel character = list.get(position);

			requestManager.clear(characterImage);
			GlideLoader.getInstance(context).loadAndCache(requestManager,
					character.getImageURL(),
					characterImage,
					context.getString(R.string.cache_images_name_format, character.getId(), character.getName()));
			//test method to check if images was cached
//			GlideLoader.getInstance(context).loadOnlyFromCache(requestManager, character.getImageURL(), characterImage, character.getCreated());

			characterName.setText(character.getName());
			characterLocation.setText(character.getLocationName());
			characterEpisode.setText(character.getFirstEpisodeName());

			rootLayout.setOnClickListener(v -> listener.onItemClick(character));
		}
	}
}
