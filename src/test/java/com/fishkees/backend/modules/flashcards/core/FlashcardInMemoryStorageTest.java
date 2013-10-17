package com.fishkees.backend.modules.flashcards.core;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class FlashcardInMemoryStorageTest {
	private static final String ID1 = "fId1";
	private static final String ID2 = "fId2";
	private static final String ID3 = "fId3";
	private FlashcardInMemoryStorage testObj;

	@Before
	public void setUp() {
		Flashcard f1 = new Flashcard(ID1, "FL1", "front 1", "back 1",
				new Date());
		Flashcard f2 = new Flashcard(ID2, "FL2", "front 2", "back 2",
				new Date());
		Flashcard f3 = new Flashcard(ID3, "FL3", "front 3", "back 3",
				new Date());
		List<Flashcard> inputList = Lists.newArrayList(f1, f2, f3);

		this.testObj = new FlashcardInMemoryStorage(inputList);
	}

	@Test
	public void testGettingId() {
		// given
		Flashcard f = new Flashcard("12345", "any", "any", "any", new Date());

		// when
		String actual = testObj.getId(f);

		// then
		assertEquals("12345", actual);
	}

	@Test
	public void testSavingAndRestoring() {
		// given
		Flashcard f = new Flashcard("15", "anyList", "anyFront", "anyBack",
				new Date());

		// when
		testObj.put(f.getId().toString(), f);

		// then
		assertEquals(4, testObj.all().size());
		assertEquals(f, testObj.get("15"));
	}

	@Test
	public void testRestoringNonExistent() {
		assertNull(testObj.get("0"));
	}

	@Test
	public void testGetAll() {
		// when
		List<Flashcard> all = Lists.newArrayList(testObj.all());

		// then
		assertEquals(3, all.size());
		assertEquals(ID1, all.get(0).getId());
		assertEquals(ID2, all.get(1).getId());
		assertEquals(ID3, all.get(2).getId());
	}

	@Test
	public void testFind() {
		// when
		Flashcard f = testObj.get(ID2);

		// then
		assertEquals(ID2, f.getId());
		assertEquals("FL2", f.getFlashcardListId());
		assertEquals("front 2", f.getFront());
		assertEquals("back 2", f.getBack());
	}

	@Test
	public void testRandomUUID() {
		// when
		String string1 = testObj.getNewId();
		String string2 = testObj.getNewId();

		assertNotEquals(string1, string2);
	}

	@Test
	public void testReset() {
		assertEquals(3, this.testObj.all().size());

		this.testObj.put("100", new Flashcard("100", "other List",
				"other front", "other back", new Date()));
		assertEquals(4, this.testObj.all().size());

		// when
		this.testObj.reset();

		// then
		assertEquals(3, this.testObj.all().size());
	}

	@Test
	public void testRemove_exists() {
		// given
		assertEquals(3, this.testObj.all().size());

		// when
		Flashcard removed = this.testObj.remove(ID3);

		// then
		assertEquals(2, this.testObj.all().size());
		assertNotNull(removed);
		assertEquals(ID3, removed.getId());
	}

	@Test
	public void testRemove_notExists() {
		// given
		assertEquals(3, this.testObj.all().size());

		// when
		Flashcard removed = this.testObj.remove("1000");

		// then
		assertEquals(3, this.testObj.all().size());
		assertNull(removed);
	}

	@Test
	public void testUpdate_exists() {
		// given
		Flashcard f = new Flashcard(ID1, "updatedListId", "updated front",
				"updated back", new Date());

		// when
		Flashcard update = testObj.update(ID1, f);

		// then
		assertNotNull(update);

		assertEquals(f, update);
		Flashcard fromStorage = testObj.get(ID1);
		assertEquals("updated front", fromStorage.getFront());
		assertEquals("updated back", fromStorage.getBack());
		assertEquals(ID1, fromStorage.getId());
	}

	@Test
	public void testUpdate_not_exists() {
		// given
		Flashcard f = new Flashcard("4", "updatedList", "updated front",
				"updated back", new Date());

		// when
		Flashcard update = testObj.update("4", f);

		// then
		assertNull(update);

		Flashcard fromStorage = testObj.get("4");
		assertNull(fromStorage);
	}

}
