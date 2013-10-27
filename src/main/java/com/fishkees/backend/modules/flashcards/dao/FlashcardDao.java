package com.fishkees.backend.modules.flashcards.dao;

import java.util.List;

import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.google.common.base.Optional;

public interface FlashcardDao {
	List<Flashcard> findAll();
	List<Flashcard> findAllByListId(String listId);
	Optional<Flashcard> findById(String id);
	Optional<Flashcard> findByListIdAndId(String listId, String id);
	Flashcard createNewFromObject(Flashcard flashcard);
	Flashcard remove(String id);
	Flashcard removeByListIdAndId(String listId, String flashcardId);
	Flashcard update(Flashcard flashcard);
}
