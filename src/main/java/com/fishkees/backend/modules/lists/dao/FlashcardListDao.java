package com.fishkees.backend.modules.lists.dao;

import java.util.List;

import com.fishkees.backend.modules.lists.core.FlashcardList;

public interface FlashcardListDao {
	public List<FlashcardList> getAll();
}