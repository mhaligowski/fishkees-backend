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
	Optional<Flashcard> remove(String id);
	Optional<Flashcard> removeByListIdAndId(String listId, String flashcardId);
	Optional<Flashcard> update(Flashcard flashcard);
}
