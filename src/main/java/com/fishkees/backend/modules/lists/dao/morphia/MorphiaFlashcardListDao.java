package com.fishkees.backend.modules.lists.dao.morphia;

import java.util.List;

import javax.inject.Inject;

import org.dozer.Mapper;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.common.base.Optional;

class MorphiaFlashcardListDao implements FlashcardListDao {

	@Inject
	private MorphiaDaoWrapper morphiaDao;
	
	@Inject
	private Mapper mapper;
	
	@Override
	public List<FlashcardList> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<FlashcardList> createNewFromObject(
			FlashcardList flashcardList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<FlashcardList> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<FlashcardList> remove(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<FlashcardList> update(FlashcardList flashcardList) {
		// TODO Auto-generated method stub
		return null;
	}

}
