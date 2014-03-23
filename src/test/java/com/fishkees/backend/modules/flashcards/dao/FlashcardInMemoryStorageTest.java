package com.fishkees.backend.modules.flashcards.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static com.fishkees.backend.modules.flashcards.core.FlashcardTestBuilder.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

public class FlashcardInMemoryStorageTest {
	private static final String ID1 = "fId1";
	private static final String ID2 = "fId2";
	private static final String ID3 = "fId3";
	private FlashcardInMemoryStorage testObj;
	private final Flashcard f1 = newFlashcardWithId(ID1).withParent("FL1").withValues("front 1", "back 1").build();;
	private Flashcard f2 = newFlashcardWithId(ID2).withParent("FL2").withValues("front 2", "back 2").build();
	private Flashcard f3 = newFlashcardWithId(ID3).withParent("FL3").withValues("front 3", "back 3").build();

	@Before
	public void setUp() {
		List<Flashcard> inputList = Lists.newArrayList(f1, f2, f3);
		this.testObj = new FlashcardInMemoryStorage(inputList);
	}

	@Test
	public void should_return_flashcard_id() {
		// given
		Flashcard f = newFlashcardWithId("12345").build();

		// when
		String actual = testObj.getId(f);

		// then
		assertEquals("12345", actual);
	}

	@Test
	public void should_save_new_flashcard() {
		// given
		Flashcard f = newFlashcardWithId("15").build();

		// when
		testObj.put(f.getId(), f);

		// then
		assertEquals(4, testObj.all().size());
		assertEquals(f, testObj.get("15").get());
	}

	@Test
	public void should_return_null_when_getting_for_non_existing_id() {
		assertFalse(testObj.get("0").isPresent());
	}

	@Test
	public void should_return_list_of_all_objects() {
		// when
		List<Flashcard> all = Lists.newArrayList(testObj.all());

		// then
		assertThat(all).hasSize(3).containsExactly(f1, f2, f3);
	}

	@Test
	public void should_find_the_proper_element() {
		// when
		Optional<Flashcard> f = testObj.get(ID2);

		// then
		assertEquals(f2, f.get());
	}

	@Test
	public void should_return_two_different_ids() {
		// when
		String string1 = testObj.getNewId();
		String string2 = testObj.getNewId();

		// then
		assertNotEquals(string1, string2);
	}

	@Test
	public void should_restore_the_initial_state() {
		// given
		Flashcard n = newFlashcardWithId("100").withParent("other list").withValues("other front", "other back")
				.build();
		this.testObj.put("100", n);
		assertThat(this.testObj.all()).containsExactly(f1, f2, f3, n);

		// when
		this.testObj.reset();

		// then
		assertThat(this.testObj.all()).containsExactly(f1, f2, f3);
	}

	@Test
	public void should_remove_the_given_flashcard_returning_the_removed_item() {
		// when
		Optional<Flashcard> removed = this.testObj.remove(ID3);

		// then
		assertThat(this.testObj.all()).containsExactly(f1, f2);
		assertTrue(removed.isPresent());
		assertEquals(ID3, removed.get().getId());
	}

	@Test
	public void should_return_null_if_removing_non_existing() {
		// when
		Optional<Flashcard> removed = this.testObj.remove("1000");

		// then
		assertThat(this.testObj.all()).containsExactly(f1, f2, f3);
		assertFalse(removed.isPresent());
	}

	@Test
	public void should_update_the_flashcard_and_return_when_stored() {
		// given
		final Flashcard f = newFlashcardWithId(ID1).withParent("updatedListId")
				.withValues("updated front", "updated back").build();

		// when
		Optional<Flashcard> update = testObj.update(ID1, f);

		// then
		assertTrue(update.isPresent());
		assertEquals(f, update.get());

		Flashcard fromStorage = testObj.get(ID1).get();
		assertEquals(fromStorage, update.get());
	}

	@Test
	public void should_do_nothing_when_updating_non_existing() {
		// given
		Flashcard f = newFlashcardWithId("4").withValues("updated front", "updated back").withParent("updatedList")
				.build();

		// when
		Optional<Flashcard> update = testObj.update("4", f);

		// then
		assertFalse(update.isPresent());

		Optional<Flashcard> fromStorage = testObj.get("4");
		assertFalse(fromStorage.isPresent());
	}

}
