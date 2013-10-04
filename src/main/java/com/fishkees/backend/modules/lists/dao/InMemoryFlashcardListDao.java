package com.fishkees.backend.modules.lists.dao;

import java.util.List;

import javax.inject.Inject;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.google.common.collect.Lists;

public class InMemoryFlashcardListDao implements FlashcardListDao {
	@Inject
	private FlashcardListInMemoryStorage storage;

	public List<FlashcardList> findAll() {
		return Lists.newArrayList(storage.all());
	}

	public FlashcardList createNewFromObject(FlashcardList flashcardList) {
		Long newId = storage.getNewId();

		FlashcardList newList = new FlashcardList(newId,
				flashcardList.getTitle(), flashcardList.getCreateDate());

		storage.put(newId, newList);
		
		return newList;
	}

	@Override
	public FlashcardList findById(Long id) {
		return storage.get(id);
	}
}
