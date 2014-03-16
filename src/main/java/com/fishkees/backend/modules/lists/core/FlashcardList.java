package com.fishkees.backend.modules.lists.core;

import java.util.Date;

import com.fishkees.backend.modules.core.FishkeesEntity;
import com.yammer.dropwizard.json.JsonSnakeCase;

@JsonSnakeCase
public class FlashcardList implements FishkeesEntity {
	private String id;
	private String title;
	private Date createDate;
	
	@SuppressWarnings("unused")
	private FlashcardList() { }
	
	public FlashcardList(String id, String title, Date createDate) {
		this.id = id;
		this.title = title;
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Date getCreateDate() {
		return createDate;
	}

}
