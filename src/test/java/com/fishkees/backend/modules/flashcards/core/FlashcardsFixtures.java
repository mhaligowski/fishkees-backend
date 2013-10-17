package com.fishkees.backend.modules.flashcards.core;

import static com.yammer.dropwizard.testing.JsonHelpers.*;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;

public final class FlashcardsFixtures {
	private FlashcardsFixtures() {
	}

	public static Flashcard single() {
		TypeReference<Flashcard> ref = new TypeReference<Flashcard>() {
		};

		Flashcard result;
		try {
			result = fromJson(jsonFixture("fixtures/flashcards/single.json"),
					ref);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

}
