package com.fishkees.backend.modules.flashcards.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class InMemoryFlashcardDao implements FlashcardDao {

	@Inject
	private FlashcardInMemoryStorage storage;

	@Override
	public List<Flashcard> findAll() {
		return Lists.newArrayList(storage.all());
	}

	@Override
	public Flashcard createNewFromObject(Flashcard flashcard) {
		String newId = storage.getNewId();

		Flashcard newList = new Flashcard(newId,
				flashcard.getFlashcardListId(), flashcard.getFront(),
				flashcard.getBack(), new Date());

		storage.put(newId, newList);

		return newList;
	}

	@Override
	public Flashcard findById(String id) {
		return storage.get(id.toString());
	}

	@Override
	public Flashcard remove(String id) {
		return storage.remove(id.toString());
	}

	@Override
	public Flashcard update(Flashcard flashcard) {
		return storage.update(flashcard.getId().toString(), flashcard);
	}

	@Override
	public List<Flashcard> findAllByListId(final String listId) {
		List<Flashcard> all = storage.all();

		Collection<Flashcard> filtered = Collections2.filter(all,
				new Predicate<Flashcard>() {
					@Override
					public boolean apply(Flashcard input) {
						return input.getFlashcardListId().equals(listId);
					}
				});

		return Lists.newArrayList(filtered);
	}

	@Override
	public Flashcard findByListIdAndId(String listId, String id) {
		Flashcard flashcard = storage.get(id);
		
		if (flashcard == null) {
			return null;
		}
		
		if (listId.equals(flashcard.getFlashcardListId())) {
			return flashcard;
		} else {
			return null;
		}
	}

}
