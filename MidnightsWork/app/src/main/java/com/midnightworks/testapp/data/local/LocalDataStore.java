package com.midnightworks.testapp.data.local;

import com.midnightworks.testapp.MainApplication;
import com.midnightworks.testapp.data.local.models.CharacterModel;
import com.midnightworks.testapp.data.local.tables.CharacterTable;
import com.midnightworks.testapp.data.local.tables.CharacterTable_Table;
import com.midnightworks.testapp.data.remote.RemoteDataStore;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

public class LocalDataStore {

	private static final String TAG = "LocalDataStore";

	@Inject
	DatabaseHelper databaseHelper;

	RemoteDataStore remoteDataStore;

	public LocalDataStore() {
		MainApplication.getAppComponent().inject(this);
	}

	public void setRemoteDataStore(RemoteDataStore remoteDataStore) {
		this.remoteDataStore = remoteDataStore;
	}

	public void deleteAllTables() {

	}

	public void updateCharacter(int id, String episodeName) {
		CharacterTable character = SQLite.select()
				.from(CharacterTable.class)
				.where(CharacterTable_Table.id.eq(id))
				.querySingle();
		if (character != null) {
			character.setFirstEpisodeName(episodeName);
			character.save();
		}
	}

	public void saveCharacter(CharacterModel model) {
		CharacterTable character = new CharacterTable(model);
		character.save();
	}

	public List<CharacterModel> getCharactersFromLocalStore() {
		List<CharacterTable> list = SQLite.select()
				.from(CharacterTable.class)
				.queryList();

		HashSet<CharacterModel> characterModelHashSet = new HashSet<>();
		for (CharacterTable character : list) {
			characterModelHashSet.add(new CharacterModel(character));
		}
		ArrayList<CharacterModel> characters = new ArrayList<>(characterModelHashSet);
		Collections.sort(characters, (model, t1) -> (int) (model.getId() - t1.getId()));
		return characters;
	}

	public void clearCharacters() {
		Delete.table(CharacterTable.class);
	}

}
