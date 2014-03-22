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
		this.setId(id);
		this.setTitle(title);
		this.setCreateDate(createDate);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
