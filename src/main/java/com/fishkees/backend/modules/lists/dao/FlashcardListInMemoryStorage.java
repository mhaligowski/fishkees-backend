package com.fishkees.backend.modules.lists.dao;

import java.util.List;

import com.fishkees.backend.dataaccess.KeyValueStore;
import com.fishkees.backend.modules.lists.core.FlashcardList;

public class FlashcardListInMemoryStorage extends
		KeyValueStore<String, FlashcardList> {
	
	public FlashcardListInMemoryStorage(List<FlashcardList> initialValues) {
		super(initialValues);
	}

	@Override
	public String getId(FlashcardList value) {
		return value.getId();
	}

}
