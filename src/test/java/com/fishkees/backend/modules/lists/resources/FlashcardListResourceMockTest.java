package com.fishkees.backend.modules.lists.resources;

import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.fest.assertions.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;

@RunWith(MockitoJUnitRunner.class)
public class FlashcardListResourceMockTest {
	@InjectMocks
	private FlashcardListResource testObj;

	@Mock
	private FlashcardListDao flashcardListDao;

	@Test
	@SuppressWarnings({ "unchecked" })
	public void test_getAll() throws IOException {
		// given
		List<FlashcardList> lists = (List<FlashcardList>) fromJson(
				jsonFixture("fixtures/lists/all.json"), List.class);
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
		FlashcardList list = fromJson(
				jsonFixture("fixtures/lists/single.json"), FlashcardList.class);
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
		FlashcardList listData = fromJson(
				jsonFixture("fixtures/lists/partial.json"), FlashcardList.class);
		FlashcardList newList = new FlashcardList(1L, "abcd", new Date());
		when(flashcardListDao.createNewFromObject(listData)).thenReturn(newList);
		
		// when
		Response response = testObj.create(listData);
		
		// then
		assertNotNull(response);
		assertEquals(201, response.getStatus());
	}

}
