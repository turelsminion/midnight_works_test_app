package com.midnightworks.testapp.activities;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.midnightworks.testapp.data.local.models.CharacterModel;

import java.util.List;

public class CharactersDiffCallback extends DiffUtil.Callback {

	private final List<CharacterModel> oldList;
	private final List<CharacterModel> newList;

	public CharactersDiffCallback(List<CharacterModel> oldList, List<CharacterModel> newList) {
		this.oldList = oldList;
		this.newList = newList;
	}

	@Override
	public int getOldListSize() {
		return oldList == null ? 0 : oldList.size();
	}

	@Override
	public int getNewListSize() {
		return newList == null ? 0 : newList.size();
	}

	@Override
	public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
		CharacterModel oldItem = oldList.get(oldItemPosition);
		CharacterModel newItem = newList.get(newItemPosition);

		return oldItem.getId() == newItem.getId();
	}

	@Override
	public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
		final CharacterModel oldItem = oldList.get(oldItemPosition);
		final CharacterModel newItem = newList.get(newItemPosition);

		return oldItem.equals(newItem);
	}

	@Nullable
	@Override
	public Object getChangePayload(int oldItemPosition, int newItemPosition) {
		// Implement method if you're going to use ItemAnimator
		return super.getChangePayload(oldItemPosition, newItemPosition);
	}
}
