package com.fishkees.backend.modules.flashcards.dao;

import java.util.List;

import com.fishkees.backend.dataaccess.inmemory.KeyValueStore;
import com.fishkees.backend.modules.flashcards.core.Flashcard;

public class FlashcardInMemoryStorage extends KeyValueStore<String, Flashcard>{

	public FlashcardInMemoryStorage(List<Flashcard> initialValues) {
		super(initialValues);
	}

	@Override
	public String getId(Flashcard value) {
		return value.getId();
	}

}
