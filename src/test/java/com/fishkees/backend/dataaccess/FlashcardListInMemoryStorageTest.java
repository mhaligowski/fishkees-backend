package com.fishkees.backend.dataaccess;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RunWith(MockitoJUnitRunner.class)
public class FlashcardListInMemoryStorageTest {
	@InjectMocks
	private FlashcardListInMemoryStorage testObj;

	@Spy
	private final Map<Long, FlashcardList> storageMap = Maps.newHashMap();

	@Test
	public void testSavingAndRestoring() {
		FlashcardList fl = new FlashcardList(1l, "abcd", new Date());
		testObj.put(1l, fl);

		assertEquals(1, storageMap.size());
		assertEquals(fl, storageMap.get(1l));
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

}
