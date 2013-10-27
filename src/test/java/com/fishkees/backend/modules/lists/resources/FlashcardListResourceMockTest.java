package com.fishkees.backend.modules.lists.resources;

import static com.fishkees.backend.modules.lists.core.FlashcardListTestBuilder.*;
import static org.fest.assertions.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
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
import com.google.common.base.Optional;

@RunWith(MockitoJUnitRunner.class)
public class FlashcardListResourceMockTest {
	private static final String ID1 = "1";
	private static final String NONEXISTING = "nonexisting";

	@InjectMocks
	private FlashcardListResource testObj;

	@Mock
	private FlashcardListDao flashcardListDao;

	@After
	public void tearDown() {
		verifyNoMoreInteractions(flashcardListDao);
	}

	@Test
	public void should_return_the_list() throws IOException {
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
	public void should_return_the_appropriate_list() throws Exception {
		// given
		FlashcardList list = FlashcardListFixtures.single();
		when(flashcardListDao.findById(ID1)).thenReturn(Optional.of(list));

		// when
		Response result = testObj.find(ID1);

		// then
		assertEquals(list, result.getEntity());
		verify(flashcardListDao).findById(ID1);
	}

	@Test
	public void should_result_with_404_when_nonexisting() {
		// given
		when(flashcardListDao.findById(NONEXISTING)).thenReturn(
				Optional.<FlashcardList> absent());

		// when
		Response result = testObj.find(NONEXISTING);

		// then
		assertEquals(404, result.getStatus());
		verify(flashcardListDao).findById(NONEXISTING);
	}

	@Test
	public void should_return_200_with_object_when_creating() throws Exception {
		// given
		FlashcardList listData = FlashcardListFixtures.partial();
		FlashcardList newList = newListWithId(ID1).withTitle("abcd").build();
		when(flashcardListDao.createNewFromObject(listData))
				.thenReturn(newList);

		// when
		Response response = testObj.create(listData);

		// then
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		verify(flashcardListDao).createNewFromObject(listData);
	}

	@Test
	public void should_return_200_with_removed_object() throws Exception {
		// given
		FlashcardList flashcardList = FlashcardListFixtures.single();
		String id = flashcardList.getId();
		when(flashcardListDao.remove(id))
				.thenReturn(Optional.of(flashcardList));

		// when
		Response response = testObj.remove(id);

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		assertEquals(flashcardList, response.getEntity());

		verify(flashcardListDao).remove(id);
	}

	@Test
	public void should_return_404_when_returning_nonexisting() throws Exception {
		// given
		when(flashcardListDao.remove(NONEXISTING)).thenReturn(
				Optional.<FlashcardList> absent());
		
		// when
		Response response = testObj.remove(NONEXISTING);

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		verify(flashcardListDao).remove(NONEXISTING);
	}

	@Test
	public void should_return_409_when_updating_with_conflicting_ids()
			throws Exception {
		// when
		String OTHER_ID = "54321";
		Response response = testObj
				.update(ID1, newListWithId(OTHER_ID).build());

		// then
		assertNotNull(response);
		assertEquals(409, response.getStatus());
	}

	@Test
	public void should_return_404_when_updating_nonexisting_list()
			throws Exception {
		// given
		final FlashcardList fl1 = newListWithId(ID1).build();
		when(flashcardListDao.update(fl1)).thenReturn(Optional.<FlashcardList>absent());
		
		// when
		Response response = testObj.update(ID1, fl1);

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		verify(flashcardListDao).update(fl1);
	}

	@Test
	public void should_return_200_when_updating_properlu() throws Exception {
		// when
		FlashcardList fl1 = newListWithId(ID1).withTitle("abcd").build();
		when(flashcardListDao.update(fl1)).thenReturn(Optional.of(fl1));

		Response response = testObj.update(ID1, fl1);

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		assertEquals(fl1, response.getEntity());

		verify(flashcardListDao).update(fl1);
	}

}
