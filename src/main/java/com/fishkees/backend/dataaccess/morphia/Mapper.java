package com.fishkees.backend.dataaccess.morphia;

import java.util.List;

import org.bson.types.ObjectId;

import com.fishkees.backend.modules.core.FishkeesEntity;

public interface Mapper {
	<S extends MorphiaEntity, T extends FishkeesEntity> void register(Class<S> sourceClass, Class<T> targetClass);

	<S, T> T map(S object, Class<T> targetClass);

	<S, T> List<T> map(List<S> objects, Class<T> targetClass);

	ObjectId map(String id);

	String map(ObjectId objectId);
}
