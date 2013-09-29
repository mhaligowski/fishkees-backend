package com.fishkees.backend.api.lists;

public class FlashcardList {
	private final Long id;
	private final String title;
	
	public FlashcardList(Long id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
}
