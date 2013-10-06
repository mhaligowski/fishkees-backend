package com.fishkees.backend.dataaccess;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FlashcardListInMemoryStorageTest {
	private FlashcardListInMemoryStorage testObj;
	private Map<Long, FlashcardList> storageMap;

	@Before
	public void setUp() {
		this.storageMap = Maps.newHashMap();
		this.testObj = new FlashcardListInMemoryStorage(storageMap);
	}
	
	@Test
	public void testSavingAndRestoring() {
		// given
		FlashcardList fl = new FlashcardList(1l, "abcd", new Date());
		
		// when
		testObj.put(1l, fl);

		// then
		assertEquals(1, testObj.all().size());
		assertEquals(fl, testObj.get(1l));
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
		storageMap.put(1l, new FlashcardList(1l, "a", new Date()));
		storageMap.put(2l, new FlashcardList(2l, "bcde", new Date()));
		storageMap.put(3l, new FlashcardList(3l, "c", new Date()));
		this.testObj = new FlashcardListInMemoryStorage(storageMap);
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
	

}
