package com.fishkees.backend.modules.lists.dao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

public class InMemoryFlashcardListDao implements FlashcardListDao {
	@Inject
	private FlashcardListInMemoryStorage storage;

	public List<FlashcardList> findAll() {
		return Lists.newArrayList(storage.all());
	}

	public Optional<FlashcardList> createNewFromObject(FlashcardList flashcardList) {
		String newId = storage.getNewId();

		FlashcardList newList = new FlashcardList(newId,
				flashcardList.getTitle(), new Date());

		storage.put(newId, newList);

		return Optional.of(newList);
	}

	@Override
	public Optional<FlashcardList> findById(String id) {
		return storage.get(id.toString());
	}

	@Override
	public Optional<FlashcardList> remove(String id) {
		return storage.remove(id.toString());
	}

	@Override
	public Optional<FlashcardList> update(FlashcardList flashcardList) {
		return storage.update(flashcardList.getId().toString(), flashcardList);
	}
}
