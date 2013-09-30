package com.fishkees.backend.modules.lists.core;

import java.util.Date;

import com.yammer.dropwizard.json.JsonSnakeCase;

@JsonSnakeCase
public class FlashcardList {
	private Long id;
	private String title;
	private Date createDate;
	
	@SuppressWarnings("unused")
	private FlashcardList() { }
	
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
