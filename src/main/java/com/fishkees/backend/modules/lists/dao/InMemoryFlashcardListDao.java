package com.fishkees.backend.modules.lists.dao;

import java.util.Date;
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
		String newId = storage.getNewId();

		FlashcardList newList = new FlashcardList(Long.parseLong(newId),
				flashcardList.getTitle(), new Date());

		storage.put(newId, newList);
		
		return newList;
	}

	@Override
	public FlashcardList findById(Long id) {
		return storage.get(id.toString());
	}
	
	@Override
	public FlashcardList remove(Long id) {
		return storage.remove(id.toString());
	}

	@Override
	public FlashcardList update(FlashcardList flashcardList) {
		return storage.update(flashcardList.getId().toString(), flashcardList);
	}
}
