package com.fishkees.backend.modules.lists.core;

import java.util.Date;
import java.util.UUID;

public class FlashcardListTestBuilder {
	private String id;
	private String title;
	private Date createDate = new Date();

	private FlashcardListTestBuilder(String id) {
		this.id = id;
	}

	public static FlashcardListTestBuilder newListWithRandomId() {
		String newRandomId = UUID.randomUUID().toString();
		return new FlashcardListTestBuilder(newRandomId);
	}

	public static FlashcardListTestBuilder newListWithId(String id) {
		return new FlashcardListTestBuilder(id);
	}

	public FlashcardListTestBuilder withTitle(String title) {
		this.title = title;
		return this;
	}

	public FlashcardListTestBuilder withCreateDate(Date date) {
		this.createDate = date;
		return this;
	}
	
	public FlashcardList build() {
		return new FlashcardList(this.id, this.title, this.createDate);
	}
}
