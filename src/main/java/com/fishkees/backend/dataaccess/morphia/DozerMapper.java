package com.fishkees.backend.dataaccess.morphia;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;

import com.fishkees.backend.modules.core.FishkeesEntity;

class DozerMapper implements Mapper {

	private final DozerBeanMapper dozerBeanMapper;

	@Inject
	public DozerMapper(DozerBeanMapper mapper) {
		this.dozerBeanMapper = mapper;
	}

	@Override
	public <S extends MorphiaEntity, T extends FishkeesEntity> void register(
			Class<S> sourceClass, Class<T> targetClass) {

	}

	@Override
	public <S, T> T map(S object, Class<T> targetClass) {
		return dozerBeanMapper.map(object, targetClass);
	}

}
