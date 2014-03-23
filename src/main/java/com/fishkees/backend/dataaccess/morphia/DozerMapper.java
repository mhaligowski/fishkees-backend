package com.fishkees.backend.dataaccess.morphia;

import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import com.fishkees.backend.modules.core.FishkeesEntity;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

class DozerMapper implements Mapper {

	private final DozerBeanMapper dozerBeanMapper;
	private final MapperBuilderFactory mapperBuilderFactory;

	@Inject
	public DozerMapper(DozerBeanMapper mapper, MapperBuilderFactory mapperBuilderFactory) {
		this.dozerBeanMapper = mapper;
		this.mapperBuilderFactory = mapperBuilderFactory;
	}

	@Override
	public <S extends MorphiaEntity, T extends FishkeesEntity> void register(Class<S> sourceClass, Class<T> targetClass) {
		BeanMappingBuilder builder = mapperBuilderFactory.create(sourceClass, targetClass);
		dozerBeanMapper.addMapping(builder);
	}

	@Override
	public <S, T> T map(S object, Class<T> targetClass) {
		return dozerBeanMapper.map(object, targetClass);
	}

	@Override
	public ObjectId map(String id) {
		return new ObjectId(id);
	}

	@Override
	public String map(ObjectId objectId) {
		return objectId.toString();
	}

	@Override
	public <S, T> List<T> map(List<S> objects, final Class<T> targetClass) {
		final Function<S, T> function = new Function<S, T>() {
			@Override
			public T apply(S input) {
				return map(input, targetClass);
			}
		};
		
		return FluentIterable.from(objects).transform(function).toList();
	}

}
