package com.fishkees.backend.dataaccess.morphia;

import javax.inject.Inject;

import org.dozer.loader.api.BeanMappingBuilder;

import com.fishkees.backend.modules.core.FishkeesEntity;
import com.google.inject.assistedinject.Assisted;

class MorphiaMappingBuilder<S extends MorphiaEntity, T extends FishkeesEntity>
		extends BeanMappingBuilder {

	private Class<S> sourceClass;
	private Class<T> targetClass;

	@Inject
	public MorphiaMappingBuilder(@Assisted Class<S> sourceClass,
			@Assisted Class<T> targetClass) {
		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
	}

	@Override
	protected void configure() {

	}

}
