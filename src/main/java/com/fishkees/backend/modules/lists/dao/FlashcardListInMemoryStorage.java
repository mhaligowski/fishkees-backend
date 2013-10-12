package com.fishkees.backend.modules.lists.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fishkees.backend.dataaccess.KeyValueStore;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class FlashcardListInMemoryStorage extends
		KeyValueStore<String, FlashcardList> {

	private final Map<String, FlashcardList> cachedMap;

	public FlashcardListInMemoryStorage(FlashcardList... flashcardLists) {
		this.map = new HashMap<>();
		for (FlashcardList flashcardList : flashcardLists) {
			this.map.put(flashcardList.getId().toString(), flashcardList);
		}

		this.cachedMap = ImmutableMap.copyOf(this.map);
	}

	@Override
	public String getNewId() {
		Long longValue = UUID.randomUUID().getLeastSignificantBits();

		return Long.valueOf(Math.abs((long) longValue.intValue())).toString();
	}

	@Override
	public void reset() {
		this.map = Maps.newHashMap(cachedMap);
	}
}
