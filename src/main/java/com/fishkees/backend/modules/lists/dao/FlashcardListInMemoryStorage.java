package com.fishkees.backend.modules.lists.dao;

import com.fishkees.backend.dataaccess.KeyValueStore;
import com.fishkees.backend.modules.lists.core.FlashcardList;

public class FlashcardListInMemoryStorage extends
		KeyValueStore<Long, FlashcardList> {

}
