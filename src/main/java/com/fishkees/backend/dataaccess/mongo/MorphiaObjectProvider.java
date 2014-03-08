package com.fishkees.backend.dataaccess.mongo;

import javax.inject.Provider;

import org.mongodb.morphia.Morphia;

class MorphiaObjectProvider implements Provider<Morphia> {
	@Override
	public Morphia get() {
		return new Morphia();
	}

}
