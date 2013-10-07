package com.fishkees.backend.modules.lists.resources;

import static org.fest.assertions.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.modules.lists.FlashcardListFixtures;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;

@RunWith(MockitoJUnitRunner.class)
public class FlashcardListResourceMockTest {
	@InjectMocks
	private FlashcardListResource testObj;

	@Mock
	private FlashcardListDao flashcardListDao;

	@After
	public void teardDown() {
		verifyNoMoreInteractions(flashcardListDao);
	}
	
	@Test
	public void test_getAll() throws IOException {
		// given
		List<FlashcardList> lists = FlashcardListFixtures.all();
		when(flashcardListDao.findAll()).thenReturn(lists);

		// when
		List<FlashcardList> result = testObj.findAll();

		// then
		assertThat(result).containsAll(lists);
		verify(flashcardListDao).findAll();
	}

	@Test
	public void test_Find() throws Exception {
		// given
		FlashcardList list = FlashcardListFixtures.single();
		when(flashcardListDao.findById(1L)).thenReturn(list);

		// when
		FlashcardList result = testObj.find(1L);

		// then
		assertEquals(list, result);
		verify(flashcardListDao).findById(1L);
	}

	@Test
	public void test_Create() throws Exception {
		// given
		FlashcardList listData = FlashcardListFixtures.partial();
		FlashcardList newList = new FlashcardList(1L, "abcd", new Date());
		when(flashcardListDao.createNewFromObject(listData)).thenReturn(newList);
		
		// when
		Response response = testObj.create(listData);
		
		// then
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		verify(flashcardListDao).createNewFromObject(listData);
	}
	
	@Test
	public void testRemove_existing() throws Exception {
		// given
		FlashcardList flashcardList = FlashcardListFixtures.single();
		Long id = flashcardList.getId();
		when(flashcardListDao.remove(id)).thenReturn(flashcardList);
		
		// when
		Response response = testObj.remove(id);
		
		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		assertEquals(flashcardList, response.getEntity());
		
		verify(flashcardListDao).remove(id);
	}
	
	@Test
	public void testRemove_nonExisting() throws Exception {
		// when
		Response response = testObj.remove(12345l);
		
		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());
		
		verify(flashcardListDao).remove(12345l);
	}
	
}
