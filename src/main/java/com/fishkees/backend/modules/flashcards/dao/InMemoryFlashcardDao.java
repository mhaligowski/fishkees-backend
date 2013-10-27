package com.fishkees.backend.modules.flashcards.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class InMemoryFlashcardDao implements FlashcardDao {

	@Inject
	private FlashcardInMemoryStorage storage;

	@Inject
	private FlashcardListDao listDao;
	
	@Override
	public List<Flashcard> findAll() {
		return Lists.newArrayList(storage.all());
	}

	@Override
	public Optional<Flashcard> createNewFromObject(Flashcard flashcard) {
		String newId = storage.getNewId();

		Flashcard newList = new Flashcard(newId,
				flashcard.getFlashcardListId(), flashcard.getFront(),
				flashcard.getBack(), new Date());

		storage.put(newId, newList);

		return Optional.of(newList);
	}

	@Override
	public Optional<Flashcard> findById(String id) {
		return storage.get(id.toString());
	}

	@Override
	public Optional<Flashcard> remove(String id) {
		return storage.remove(id.toString());
	}
	
	@Override
	public Optional<Flashcard> removeByListIdAndId(String listId, String flashcardId) {
		Optional<Flashcard> flashcard = findByListIdAndId(listId, flashcardId);
		if (!flashcard.isPresent()) {
			return Optional.absent();
		}
		
		return storage.remove(flashcardId);
	}

	@Override
	public Optional<Flashcard> update(Flashcard flashcard) {
		return storage.update(flashcard.getId().toString(), flashcard);
	}

	@Override
	public List<Flashcard> findAllByListId(final String listId) {
		Optional<FlashcardList> flashcardList = listDao.findById(listId);
		if (!flashcardList.isPresent()) {
			return null;
		}
		
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
	public Optional<Flashcard> findByListIdAndId(String listId, String id) {
		Optional<Flashcard> flashcard = storage.get(id);
		
		if (!flashcard.isPresent()) {
			return Optional.absent();
		}
		
		String flashcardListId = flashcard.get().getFlashcardListId();
		if (listId.equals(flashcardListId)) {
			return flashcard;
		} else {
			return Optional.absent();
		}
	}

}
