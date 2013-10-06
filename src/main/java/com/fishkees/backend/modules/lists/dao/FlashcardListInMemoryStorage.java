package com.fishkees.backend.modules.lists.dao;

import java.util.Map;
import java.util.UUID;

import com.fishkees.backend.dataaccess.KeyValueStore;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class FlashcardListInMemoryStorage extends
		KeyValueStore<Long, FlashcardList> {
	
	private final Map<Long, FlashcardList> cachedMap;

	public FlashcardListInMemoryStorage(Map<Long, FlashcardList> storageMap) {
		this.map = storageMap;
		this.cachedMap = ImmutableMap.copyOf(this.map);
	}
	
	@Override
	public Long getNewId() {
		return Math.abs(UUID.randomUUID().getLeastSignificantBits());
	}

	@Override
	public void reset() {
		this.map = Maps.newHashMap(cachedMap);
	}

}
