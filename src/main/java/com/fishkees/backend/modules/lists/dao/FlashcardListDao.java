package com.fishkees.backend.modules.lists.dao;

import java.util.List;

import com.fishkees.backend.modules.lists.core.FlashcardList;

public interface FlashcardListDao {
	List<FlashcardList> findAll();
	FlashcardList createNewFromObject(FlashcardList flashcardList);
	FlashcardList findById(String id);
	FlashcardList remove(String id);
	FlashcardList update(FlashcardList flashcardList);
}
