package com.fishkees.backend.modules.lists;

import static com.yammer.dropwizard.testing.JsonHelpers.*;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fishkees.backend.modules.lists.core.FlashcardList;

public class FlashcardListFixtures {
	public static List<FlashcardList> all() {
		TypeReference<List<FlashcardList>> ref = new TypeReference<List<FlashcardList>>() {
		};
		List<FlashcardList> result;
		
		try {
			result = fromJson(jsonFixture("fixtures/lists/all.json"), ref);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}

	private FlashcardListFixtures() {
	}
	
	public static FlashcardList single() {
		TypeReference<FlashcardList> ref = new TypeReference<FlashcardList>() {
		};
		
		FlashcardList result;
		try {
			result = fromJson(jsonFixture("fixtures/lists/single.json"), ref);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
}
