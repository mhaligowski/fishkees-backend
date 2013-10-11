package com.fishkees.backend.modules.lists.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fishkees.backend.dataaccess.KeyValueStore;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class FlashcardListInMemoryStorage extends
		KeyValueStore<Long, FlashcardList> {

	private final Map<Long, FlashcardList> cachedMap;

	public FlashcardListInMemoryStorage(FlashcardList... flashcardLists) {
		this.map = new HashMap<>();
		for (FlashcardList flashcardList : flashcardLists) {
			this.map.put(flashcardList.getId(), flashcardList);
		}

		this.cachedMap = ImmutableMap.copyOf(this.map);
	}

	@Override
	public Long getNewId() {
		Long longValue = UUID.randomUUID().getLeastSignificantBits();

		return Math.abs((long) longValue.intValue());
	}

	@Override
	public void reset() {
		this.map = Maps.newHashMap(cachedMap);
	}
}
