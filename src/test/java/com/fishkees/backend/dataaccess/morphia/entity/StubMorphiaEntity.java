package com.fishkees.backend.dataaccess.morphia.entity;

import org.bson.types.ObjectId;

import com.fishkees.backend.dataaccess.morphia.MorphiaEntity;

public class StubMorphiaEntity implements MorphiaEntity {
	private ObjectId objectId;
	private String field;
	
	@Override
	public ObjectId getId() {
		return objectId;
	}

	public void setId(ObjectId objectId) {
		this.objectId = objectId;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
