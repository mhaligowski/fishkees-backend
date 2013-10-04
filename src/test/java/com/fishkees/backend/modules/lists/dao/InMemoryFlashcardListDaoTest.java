package com.fishkees.backend.modules.lists.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;
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
		FlashcardList fl = new FlashcardList(null, "abcd", new Date());
		
		// when
		testObj.create(fl);
		
		// then
		verify(storage).getNewId();
		ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<FlashcardList> listCaptor = ArgumentCaptor.forClass(FlashcardList.class);
		
		verify(storage).put(longCaptor.capture(), listCaptor.capture());
		
		Long id = longCaptor.getValue();
		assertNotNull(id);
		
		FlashcardList newFl = listCaptor.getValue();
		assertEquals(id, newFl.getId());
		assertEquals("abcd", newFl.getTitle());
	}
}
