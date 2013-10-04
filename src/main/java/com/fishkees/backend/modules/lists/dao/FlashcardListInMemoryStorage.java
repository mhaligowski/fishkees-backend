package com.fishkees.backend.modules.lists.dao;

import java.util.Map;
import java.util.UUID;

import com.fishkees.backend.dataaccess.KeyValueStore;
import com.fishkees.backend.modules.lists.core.FlashcardList;

public class FlashcardListInMemoryStorage extends
		KeyValueStore<Long, FlashcardList> {

	public FlashcardListInMemoryStorage(Map<Long, FlashcardList> storageMap) {
		this.map = storageMap;
	}
	
	@Override
	public Long getNewId() {
		return UUID.randomUUID().getLeastSignificantBits();
	}

}
