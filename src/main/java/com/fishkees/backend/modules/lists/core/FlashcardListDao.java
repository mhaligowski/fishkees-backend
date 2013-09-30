package com.fishkees.backend.modules.lists.core;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

public class FlashcardListDao {
	public List<FlashcardList> getAll() {
		FlashcardList fl1 = new FlashcardList(1L, "Spanish for beginners",
				new Date());
		FlashcardList fl2 = new FlashcardList(2L, "Java. Performance",
				new Date());
		FlashcardList fl3 = new FlashcardList(3L,
				"Java Concurrency in Practice", new Date());

		return Lists.newArrayList(fl1, fl2, fl3);

	}
}
