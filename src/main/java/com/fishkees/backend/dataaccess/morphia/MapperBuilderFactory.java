package com.fishkees.backend.dataaccess.morphia;

import org.dozer.loader.api.BeanMappingBuilder;

import com.fishkees.backend.modules.core.FishkeesEntity;

interface MapperBuilderFactory {
	<S extends MorphiaEntity, T extends FishkeesEntity> BeanMappingBuilder create(
			Class<S> sourceClass, Class<T> targetClass);
}
