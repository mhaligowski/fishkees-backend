package com.fishkees.backend.modules.lists.dao.morphia;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.fishkees.backend.dataaccess.morphia.Mapper;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.common.base.Optional;

class MorphiaFlashcardListDao implements FlashcardListDao {

	@Inject
	private MorphiaDaoWrapper morphiaDaoWrapper;
	
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
		MorphiaFlashcardList morphiaEntity = mapper.map(flashcardList, MorphiaFlashcardList.class);
		
		morphiaEntity.setId(ObjectId.get());
		morphiaEntity.setCreateDate(new Date());
		
		morphiaDaoWrapper.save(morphiaEntity);
		
		final FlashcardList resultFlashcardList = mapper.map(morphiaEntity, FlashcardList.class);
		return Optional.of(resultFlashcardList);
	}

	@Override
	public Optional<FlashcardList> findById(String id) {
		return null;
	}

	@Override
	public Optional<FlashcardList> remove(String id) {
		return null;
	}

	@Override
	public Optional<FlashcardList> update(FlashcardList flashcardList) {
		// TODO Auto-generated method stub
		return null;
	}

}
