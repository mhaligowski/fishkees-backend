package com.fishkees.backend.modules.lists.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.common.collect.Lists;

public class InMemoryFlashcardListDao implements FlashcardListDao {
	private Map<Long, FlashcardList> flashcards = new HashMap<>();
	
	public InMemoryFlashcardListDao() {
		FlashcardList fl1 = new FlashcardList(1L, "Spanish for beginners",
				new Date());
		FlashcardList fl2 = new FlashcardList(2L, "Java. Performance",
				new Date());
		FlashcardList fl3 = new FlashcardList(3L,
				"Java Concurrency in Practice", new Date());

		flashcards.put(fl1.getId(), fl1);
		flashcards.put(fl2.getId(), fl2);
		flashcards.put(fl3.getId(), fl3);
	}
	
	public List<FlashcardList> getAll() {
		return Lists.newArrayList(flashcards.values());
	}
}
