package com.fishkees.backend.modules.flashcards;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.yammer.dropwizard.json.JsonSnakeCase;

@JsonSnakeCase
public class Flashcard {
	private String id;
	private String flashcardListId;
	private String front;
	private String back;
	private Date createDate;

	@SuppressWarnings({ "unused" })
	private Flashcard() {}

	public Flashcard(String id, String flashcardListId, String front,
			String back, Date createDate) {
		super();
		this.id = id;
		this.flashcardListId = flashcardListId;
		this.front = front;
		this.back = back;
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	@NotNull
	public String getFlashcardListId() {
		return flashcardListId;
	}

	public String getFront() {
		return front;
	}

	public String getBack() {
		return back;
	}

	public Date getCreateDate() {
		return createDate;
	}
}
