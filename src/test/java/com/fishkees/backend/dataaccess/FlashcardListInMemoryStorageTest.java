package com.fishkees.backend.dataaccess;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;
import com.google.common.collect.Lists;

public class FlashcardListInMemoryStorageTest {
	private FlashcardListInMemoryStorage testObj;

	@Before
	public void setUp() {
		FlashcardList fl1 = new FlashcardList(1l, "a", new Date());
		FlashcardList fl2 = new FlashcardList(2l, "bcde", new Date());
		FlashcardList fl3 = new FlashcardList(3l, "c", new Date());

		this.testObj = new FlashcardListInMemoryStorage(fl1, fl2, fl3);
	}
	
	@Test
	public void testSavingAndRestoring() {
		// given
		FlashcardList fl = new FlashcardList(15l, "abcd", new Date());
		
		// when
		testObj.put(fl.getId().toString(), fl);

		// then
		assertEquals(4, testObj.all().size());
		assertEquals(fl, testObj.get("15"));
	}

	@Test
	public void testRestoringNonExistent() {
		assertNull(testObj.get("0"));
	}

	@Test
	public void testGetAll() {
		// when
		List<FlashcardList> all = Lists.newArrayList(testObj.all());

		// then
		assertEquals(3, all.size());
		assertEquals(3l, all.get(0).getId().longValue());
		assertEquals(2l, all.get(1).getId().longValue());
		assertEquals(1l, all.get(2).getId().longValue());
	}

	@Test
	public void testFind() {
		// when
		FlashcardList flashcardList = testObj.get("2");
		
		// then
		assertEquals(2l, flashcardList.getId().longValue());
		assertEquals("bcde", flashcardList.getTitle());
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
		
		this.testObj.put("100", new FlashcardList(100l, "qwer", new Date()));
		assertEquals(4, this.testObj.all().size());
		
		// when
		this.testObj.reset();
		
		// then
		assertEquals(3,  this.testObj.all().size());
	}
	
	@Test
	public void testRemove_exists() {
		// given
		assertEquals(3, this.testObj.all().size());

		// when
		FlashcardList removed = this.testObj.remove("3");
		
		// then
		assertEquals(2, this.testObj.all().size());
		assertNotNull(removed);
		assertEquals(3l, removed.getId().longValue());
	}

	@Test
	public void testRemove_notExists() {
		// given
		assertEquals(3,  this.testObj.all().size());
		
		// when
		FlashcardList removed = this.testObj.remove("1000");
	
		// then
		assertEquals(3,  this.testObj.all().size());
		assertNull(removed);
	}

	@Test
	public void testUpdate_exists() {
		// given
		FlashcardList fl = new FlashcardList(1L, "new title", new Date());
		
		// when
		FlashcardList update = testObj.update("1", fl);
		
		// then
		assertNotNull(update);
		
		FlashcardList fromStorage = testObj.get("1");
		assertEquals("new title", fromStorage.getTitle());
		assertEquals(1l, fromStorage.getId().longValue());
	}
	
	@Test
	public void testUpdate_not_exists() {
		// given
		FlashcardList fl = new FlashcardList(4L, "new title", new Date());
		
		// when
		FlashcardList update = testObj.update("4", fl);
		
		// then
		assertNull(update);
		
		FlashcardList fromStorage = testObj.get("4");
		assertNull(fromStorage);
	}

}
