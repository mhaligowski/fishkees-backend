package com.fishkees.backend.modules.lists.dao;

import java.util.List;

import javax.inject.Inject;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.google.common.collect.Lists;

public class InMemoryFlashcardListDao implements FlashcardListDao {
	@Inject
	private FlashcardListInMemoryStorage storage;
	
	
	public List<FlashcardList> getAll() {
		return Lists.newArrayList(storage.all());
	}
}
