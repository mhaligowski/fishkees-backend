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
		testObj.put(fl.getId(), fl);

		// then
		assertEquals(4, testObj.all().size());
		assertEquals(fl, testObj.get(15l));
	}

	@Test
	public void testRestoringNonExistent() {
		assertNull(testObj.get(0l));
	}

	@Test
	public void testGetAll() {
		// given
		fillStorage();

		// when
		List<FlashcardList> all = Lists.newArrayList(testObj.all());

		// then
		assertEquals(3, all.size());
		assertEquals(1l, all.get(0).getId().longValue());
		assertEquals(2l, all.get(1).getId().longValue());
		assertEquals(3l, all.get(2).getId().longValue());
	}

	private void fillStorage() {
	}

	@Test
	public void testFind() {
		// given
		fillStorage();
		
		// when
		FlashcardList flashcardList = testObj.get(2l);
		
		// then
		assertEquals(2l, flashcardList.getId().longValue());
		assertEquals("bcde", flashcardList.getTitle());
	}
	
	@Test
	public void testRandomUUID() {
		// when
		Long long1 = testObj.getNewId();
		Long long2 = testObj.getNewId();

		assertNotEquals(long1, long2);
		assertTrue(long1 > 0);
		assertTrue(long2 > 0);
	}

	@Test
	public void testReset() {
		// given
		fillStorage();
		assertEquals(3, this.testObj.all().size());
		
		this.testObj.put(100L, new FlashcardList(1l, "qwer", new Date()));
		assertEquals(4, this.testObj.all().size());
		
		// when
		this.testObj.reset();
		
		// then
		assertEquals(3,  this.testObj.all().size());
	}
	
	@Test
	public void testRemove_exists() {
		// given
		fillStorage();
		assertEquals(3, this.testObj.all().size());

		// when
		FlashcardList removed = this.testObj.remove(3l);
		
		// then
		assertEquals(2, this.testObj.all().size());
		assertNotNull(removed);
		assertEquals(3l, removed.getId().longValue());
	}

	@Test
	public void testRemove_notExists() {
		// given
		fillStorage();
		assertEquals(3,  this.testObj.all().size());
		
		// when
		FlashcardList removed = this.testObj.remove(1000l);
	
		// then
		assertEquals(3,  this.testObj.all().size());
		assertNull(removed);
	}
	

}
