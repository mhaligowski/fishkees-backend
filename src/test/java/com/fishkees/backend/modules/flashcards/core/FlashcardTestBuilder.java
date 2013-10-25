package com.fishkees.backend.modules.flashcards.core;

import java.util.Date;
import java.util.UUID;

public final class FlashcardTestBuilder {

	private String id;
	private String flashcardListId;
	private String back;
	private String front;
	private Date createDate = new Date();
	
	private FlashcardTestBuilder(String id) { this.id = id; }
	
	public static FlashcardTestBuilder newFlashcardWithRandomId() {
		return new FlashcardTestBuilder(UUID.randomUUID().toString());
	}

	public static FlashcardTestBuilder newFlashcardWithId(String id) {
		return new FlashcardTestBuilder(id);
	}
	
	public FlashcardTestBuilder withParent(String flashcardListId) {
		this.flashcardListId = flashcardListId;
		return this;
	}
	
	public FlashcardTestBuilder withValues(String front, String back) {
		this.front = front;
		this.back = back;
		return this;
	}
	
	public FlashcardTestBuilder createdOn(Date date) {
		this.createDate = date;
		return this;
	}

	public FlashcardTestBuilder updateId(String newId) {
		this.id = newId;
		return this;
	}
	
	public Flashcard build() {
		return new Flashcard(id, flashcardListId, front, back, createDate);
	}
}
