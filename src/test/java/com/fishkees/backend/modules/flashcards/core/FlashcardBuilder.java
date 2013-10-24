package com.fishkees.backend.modules.flashcards.core;

import java.util.Date;
import java.util.UUID;

public final class FlashcardBuilder {

	private String id;
	private String flashcardListId;
	private String back;
	private String front;
	private Date createDate = new Date();
	
	private FlashcardBuilder() { }
	
	public static FlashcardBuilder nullFlashcard() {
		return new FlashcardBuilder();
	}
	
	public FlashcardBuilder withRandomId() {
		this.id = UUID.randomUUID().toString();
		return this;
	}
	
	public FlashcardBuilder withId(String id) {
		this.id = id;
		return this;
	}
	
	public FlashcardBuilder withParent(String flashcardListId) {
		this.flashcardListId = flashcardListId;
		return this;
	}
	
	public FlashcardBuilder withValues(String front, String back) {
		this.front = front;
		this.back = back;
		return this;
	}
	
	public FlashcardBuilder createdOn(Date date) {
		this.createDate = date;
		return this;
	}
	
	public Flashcard build() {
		return new Flashcard(id, flashcardListId, front, back, createDate);
	}
}
