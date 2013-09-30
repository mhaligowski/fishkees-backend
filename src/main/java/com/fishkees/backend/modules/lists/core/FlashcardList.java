package com.fishkees.backend.modules.lists.core;

import java.util.Date;

public class FlashcardList {
	private final Long id;
	private final String title;
	private final Date createDate;
	
	public FlashcardList(Long id, String title, Date createDate) {
		this.id = id;
		this.title = title;
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Date getCreateDate() {
		return createDate;
	}
	
	
}
