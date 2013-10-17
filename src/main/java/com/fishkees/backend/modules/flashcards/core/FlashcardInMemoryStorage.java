package com.fishkees.backend.modules.flashcards.core;

import java.util.List;

import com.fishkees.backend.dataaccess.KeyValueStore;

public class FlashcardInMemoryStorage extends KeyValueStore<String, Flashcard>{

	public FlashcardInMemoryStorage(List<Flashcard> initialValues) {
		super(initialValues);
	}

	@Override
	public String getId(Flashcard value) {
		return value.getId();
	}

}
