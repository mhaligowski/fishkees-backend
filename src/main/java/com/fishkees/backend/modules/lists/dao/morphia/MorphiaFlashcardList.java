package com.fishkees.backend.modules.lists.dao.morphia;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fishkees.backend.dataaccess.morphia.MorphiaEntity;

@Entity("flashcard_lists")
class MorphiaFlashcardList implements MorphiaEntity {
	private @Id ObjectId id;
	private String title;
	private Date createDate;
	
	private MorphiaFlashcardList() { }

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

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}	
}
