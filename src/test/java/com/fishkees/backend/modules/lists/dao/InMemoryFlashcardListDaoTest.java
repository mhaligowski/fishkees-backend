package com.fishkees.backend.modules.lists.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
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
		List<FlashcardList> result = testObj.getAll();

		assertEquals(lists, result);
		verify(storage).all();

	}
}
