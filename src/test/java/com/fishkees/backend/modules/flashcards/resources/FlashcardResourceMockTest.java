package com.fishkees.backend.modules.flashcards.resources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.modules.flashcards.FlashcardFixtures;
import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.flashcards.dao.FlashcardDao;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class FlashcardResourceMockTest {

	@InjectMocks
	private FlashcardResource testObj;

	@Mock
	private FlashcardDao dao;

	@After
	public void tearDown() {
		verifyNoMoreInteractions(dao);
	}

	@Test
	public void should_return_201_after_properly_creating() {
		// given
		Flashcard flashcard = FlashcardFixtures.partial();
		Flashcard expected = new Flashcard("someId", "someListId",
				"front text", "back text", new Date());
		when(dao.createNewFromObject(flashcard)).thenReturn(expected);

		// when
		Response response = testObj.create("someListId", flashcard);

		// then
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		assertEquals(expected, response.getEntity());

		verify(dao).createNewFromObject(flashcard);

	}

	@Test
	public void should_return_409_when_trying_to_create_with_mismatching_ids() {
		// given
		Flashcard flashcard = FlashcardFixtures.partial();

		// when
		Response response = testObj.create("differentListId", flashcard);

		// then
		assertNotNull(response);
		assertEquals(409, response.getStatus());

		verify(dao, never()).createNewFromObject(flashcard);
	}

	@Test
	public void should_return_200_with_object_for_proper_search() {
		// given
		Flashcard flashcard = FlashcardFixtures.single();
		when(dao.findByListIdAndId("flashcardListId1", "someId1")).thenReturn(
				flashcard);

		// when
		Response response = testObj.find("flashcardListId1", "someId1");

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		assertEquals(flashcard, response.getEntity());

		verify(dao).findByListIdAndId("flashcardListId1", "someId1");
	}

	@Test
	public void should_return_404_for_nonexisting_flashcard() {
		// when
		Response response = testObj.find("otherList", "someId1");

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		verify(dao).findByListIdAndId("otherList", "someId1");
	}

	@Test
	public void should_return_all_the_objects_with_identical_data() {
		// given
		Flashcard resultFlashcard = FlashcardFixtures.all().get(0);
		when(dao.findAllByListId("flashcardListId1")).thenReturn(
				Lists.newArrayList(resultFlashcard));

		// when
		Response response = testObj.findAll("flashcardListId1");

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());

		@SuppressWarnings("unchecked")
		List<Flashcard> entity = (List<Flashcard>) response.getEntity();
		assertEquals(1, entity.size());
		assertEquals(resultFlashcard.getId(), entity.get(0).getId());
		assertEquals(resultFlashcard.getFlashcardListId(), entity.get(0)
				.getFlashcardListId());
		assertEquals(resultFlashcard.getFront(), entity.get(0).getFront());
		assertEquals(resultFlashcard.getBack(), entity.get(0).getBack());

		verify(dao).findAllByListId("flashcardListId1");
	}

	@Test
	public void should_return_404_for_nonexisting_list() {
		// given
		when(dao.findAllByListId(anyString())).thenReturn(null);

		// when
		Response response = testObj.findAll("flashcardListId1000000");

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		verify(dao).findAllByListId("flashcardListId1000000");
	}

	@Test
	public void should_return_200_with_updated_object_when_update_is_successful() {
		// given
		Flashcard updated = new Flashcard("someId1", "flashcardListId",
				"updated front", "updated back", new Date());
		when(dao.update(updated)).thenReturn(updated);

		// when
		Response response = testObj.update("flashcardListId", "someId1",
				updated);

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		assertEquals(updated, response.getEntity());

		verify(dao).update(updated);
	}

	@Test
	public void should_return_409_for_update_with_mismatching_list_ids() {
		// given
		Flashcard toUpdate = FlashcardFixtures.single();

		// when
		Response response = testObj.update("otherList", "someId1", toUpdate);

		// then
		assertNotNull(response);
		assertEquals(409, response.getStatus());

		verify(dao, never()).update(toUpdate);
	}

	@Test
	public void should_return_409_for_update_with_mismatching_flashcard_ids() {
		// given
		Flashcard toUpdate = FlashcardFixtures.single();

		// when
		Response response = testObj.update("flashcardListId",
				"otherFlashcardId", toUpdate);

		// then
		assertNotNull(response);
		assertEquals(409, response.getStatus());

		// verify
		verify(dao, never()).update(toUpdate);
	}

	@Test
	public void should_return_404_when_updating_non_existing_list() {
		// given
		Flashcard flashcard = FlashcardFixtures.single();

		// when
		Response response = testObj.update("flashcardListId", "someId",
				flashcard);

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		verify(dao).update(flashcard);
	}

	@Test
	public void should_return_200_when_removing_went_ok() {
		// given
		Flashcard flashcardToRemove = FlashcardFixtures.single();
		when(dao.removeByListIdAndId("flashcardListId", "someId")).thenReturn(
				flashcardToRemove);

		// when
		Response response = testObj.remove("flashcardListId", "someId");

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		assertEquals(flashcardToRemove, response.getEntity());

		// verify
		verify(dao).removeByListIdAndId("flashcardListId", "someId");
	}

	@Test
	public void should_return_404_when_either_list_or_flashcard_was_not_found() {
		// when
		Response response = testObj.remove("otherListId", "someId");

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		// verify
		verify(dao).removeByListIdAndId("otherListId", "someId");
	}

}
