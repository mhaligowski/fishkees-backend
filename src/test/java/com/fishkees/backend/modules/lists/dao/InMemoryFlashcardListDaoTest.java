package com.fishkees.backend.modules.lists.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.modules.lists.FlashcardListFixtures;
import com.fishkees.backend.modules.lists.core.FlashcardList;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryFlashcardListDaoTest {
	@InjectMocks
	private InMemoryFlashcardListDao testObj;

	@Mock
	FlashcardListInMemoryStorage storage;

	@Test
	public void testGetAll() throws Exception {
		// given
		List<FlashcardList> lists = FlashcardListFixtures.all();
		when(storage.all()).thenReturn(lists);

		// when
		List<FlashcardList> result = testObj.findAll();

		// then
		assertEquals(lists, result);
		verify(storage).all();
	}
	
	@Test
	public void testCreate() {
		// given
		FlashcardList fl = new FlashcardList(null, "abcd", null);
		
		// when
		FlashcardList resultFlashcardList = testObj.createNewFromObject(fl);
		
		// then
		verify(storage).getNewId();

		ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<FlashcardList> listCaptor = ArgumentCaptor.forClass(FlashcardList.class);
		
		verify(storage).put(longCaptor.capture(), listCaptor.capture());
		
		Long id = longCaptor.getValue();
		FlashcardList newFlashcardListFromStorage = listCaptor.getValue();

		assertNotNull(id);
		assertEquals(id, newFlashcardListFromStorage.getId());
		assertEquals("abcd", newFlashcardListFromStorage.getTitle());
		assertEquals(newFlashcardListFromStorage, resultFlashcardList);
		assertNotNull(resultFlashcardList.getCreateDate());
	}
	
	@Test
	public void testFind() {
		// given
		List<FlashcardList> lists = FlashcardListFixtures.all();
		when(storage.get(1l)).thenReturn(lists.get(0));
		when(storage.get(2l)).thenReturn(lists.get(1));

		// when
		FlashcardList result1 = testObj.findById(1l);
		FlashcardList result2 = testObj.findById(2l);

		// then
		assertEquals(1l, result1.getId().longValue());
		assertEquals(2l, result2.getId().longValue());
		assertEquals("Spanish for beginners", result1.getTitle());
		assertEquals("Russian for intermediate", result2.getTitle());
		
		verify(storage).get(1l);
		verify(storage).get(2l);
	}
}
