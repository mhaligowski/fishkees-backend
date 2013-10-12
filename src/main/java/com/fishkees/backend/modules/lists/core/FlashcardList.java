package com.fishkees.backend.modules.lists.core;

import java.util.Date;

import com.yammer.dropwizard.json.JsonSnakeCase;

@JsonSnakeCase
public class FlashcardList {
	private String id;
	private String title;
	private Date createDate;
	
	@SuppressWarnings("unused")
	private FlashcardList() { }
	
	public FlashcardList(Long id, String title, Date createDate) {
		this.id = id == null ? null : id.toString();
		this.title = title;
		this.createDate = createDate;
	}

	public Long getId() {
		return id == null ? null : Long.parseLong(id);
	}

	public String getTitle() {
		return title;
	}

	public Date getCreateDate() {
		return createDate;
	}

}
