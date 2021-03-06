package com.fishkees.backend.modules.lists.dao;

import java.util.List;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.google.common.base.Optional;

public interface FlashcardListDao {
	List<FlashcardList> findAll();
	Optional<FlashcardList> createNewFromObject(FlashcardList flashcardList);
	Optional<FlashcardList> findById(String id);
	Optional<FlashcardList> remove(String id);
	Optional<FlashcardList> update(FlashcardList flashcardList);
}
