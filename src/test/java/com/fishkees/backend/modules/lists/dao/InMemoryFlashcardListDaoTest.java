package com.fishkees.backend.modules.lists.dao;

import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
		Collection<FlashcardList> lists = flashcardList();
		when(storage.all()).thenReturn(lists);
		
		// when
		List<FlashcardList> result = testObj.getAll();
		
		assertEquals(lists,  result);
		verify(storage).all();
		
	}

	@SuppressWarnings("unchecked")
	private Collection<FlashcardList> flashcardList() throws Exception {
		return (List<FlashcardList>) fromJson(
				jsonFixture("fixtures/lists/all.json"), List.class);
	}

}
