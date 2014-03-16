package com.fishkees.backend.dataaccess.morphia.entity;

import com.fishkees.backend.modules.core.FishkeesEntity;

public class StubFishkeesEntity implements FishkeesEntity {
	private String id;
	private String field;
	
	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	
}
