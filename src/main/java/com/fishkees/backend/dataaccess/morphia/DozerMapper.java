package com.fishkees.backend.dataaccess.morphia;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import com.fishkees.backend.modules.core.FishkeesEntity;

class DozerMapper implements Mapper {

	private final DozerBeanMapper dozerBeanMapper;
	private final MapperBuilderFactory mapperBuilderFactory;

	@Inject
	public DozerMapper(DozerBeanMapper mapper,
			MapperBuilderFactory mapperBuilderFactory) {
		this.dozerBeanMapper = mapper;
		this.mapperBuilderFactory = mapperBuilderFactory;
	}

	@Override
	public <S extends MorphiaEntity, T extends FishkeesEntity> void register(
			Class<S> sourceClass, Class<T> targetClass) {
		BeanMappingBuilder builder = mapperBuilderFactory.create(sourceClass,
				targetClass);
		dozerBeanMapper.addMapping(builder);
	}

	@Override
	public <S, T> T map(S object, Class<T> targetClass) {
		return dozerBeanMapper.map(object, targetClass);
	}

}
