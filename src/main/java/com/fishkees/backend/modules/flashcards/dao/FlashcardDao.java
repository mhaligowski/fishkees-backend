package com.fishkees.backend.modules.flashcards.dao;

import java.util.List;

import com.fishkees.backend.modules.flashcards.core.Flashcard;

public interface FlashcardDao {
	List<Flashcard> findAll();
	Flashcard createNewFromObject(Flashcard flashcard);
	Flashcard findById(String id);
	Flashcard remove(String id);
	Flashcard update(Flashcard flashcard);
}
