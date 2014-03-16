package com.fishkees.backend.dataaccess.morphia;

import org.bson.types.ObjectId;
import org.dozer.DozerConverter;

public class ObjectIdConverter extends DozerConverter<ObjectId, String> {

	public ObjectIdConverter() {
		super(ObjectId.class, String.class);
	}

	@Override
	public String convertTo(ObjectId source, String destination) {
		return source.toString();
	}

	@Override
	public ObjectId convertFrom(String source, ObjectId destination) {
		return new ObjectId(source);
	}

}
