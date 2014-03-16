package com.fishkees.backend.dataaccess.morphia;

import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;

import com.fishkees.backend.modules.core.FishkeesEntity;

class MorphiaMappingBuilder<S extends MorphiaEntity, T extends FishkeesEntity>
		extends BeanMappingBuilder {

	private final Class<S> sourceClass;
	private final Class<T> targetClass;

	public MorphiaMappingBuilder(Class<S> sourceClass, Class<T> targetClass) {
		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
	}

	@Override
	protected void configure() {
		mapping(sourceClass, targetClass).fields("id", "id",
				FieldsMappingOptions.customConverter(ObjectIdConverter.class));
	}

}
