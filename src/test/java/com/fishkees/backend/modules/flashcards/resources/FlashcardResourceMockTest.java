package com.fishkees.backend.modules.flashcards.resources;

import static com.fishkees.backend.modules.flashcards.core.FlashcardTestBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.modules.flashcards.FlashcardFixtures;
import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.flashcards.core.FlashcardTestBuilder;
import com.fishkees.backend.modules.flashcards.dao.FlashcardDao;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class FlashcardResourceMockTest {

	private static final String DEFAULT_BACK = "back text";
	private static final String DEFAULT_FRONT = "front text";
	private static final String DEFAULT_ID = "someId";
	private static final String DEFAULT_PARENT_ID = "flashcardListId";

	@InjectMocks
	private FlashcardResource testObj;

	@Mock
	private FlashcardDao dao;

	private FlashcardTestBuilder flashcardBuilder;

	@Before
	public void setUp() {
		flashcardBuilder = newFlashcardWithId(DEFAULT_ID).withParent(
				DEFAULT_PARENT_ID).withValues(DEFAULT_FRONT, DEFAULT_BACK);
	}

	@After
	public void tearDown() {
		verifyNoMoreInteractions(dao);
	}

	@Test
	public void should_return_201_after_properly_creating() {
		// given
		Flashcard flashcard = FlashcardFixtures.partial();
		Flashcard expected = flashcardBuilder.build();
		when(dao.createNewFromObject(flashcard)).thenReturn(expected);

		// when
		Response response = testObj.create(DEFAULT_PARENT_ID, flashcard);

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
				Optional.of(flashcard));

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
		// given
		when(dao.findByListIdAndId("otherList", "someId1")).thenReturn(
				Optional.<Flashcard> absent());

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
		Flashcard updated = flashcardBuilder.withValues("updated front",
				"updated back").build();
		when(dao.update(updated)).thenReturn(updated);

		// when
		Response response = testObj.update(DEFAULT_PARENT_ID, DEFAULT_ID,
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
		Response response = testObj.update("otherList", DEFAULT_ID, toUpdate);

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
		Response response = testObj.update(DEFAULT_PARENT_ID,
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
		Response response = testObj.update(DEFAULT_PARENT_ID, DEFAULT_ID,
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
		when(dao.removeByListIdAndId(DEFAULT_PARENT_ID, DEFAULT_ID))
				.thenReturn(flashcardToRemove);

		// when
		Response response = testObj.remove(DEFAULT_PARENT_ID, DEFAULT_ID);

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());
		assertEquals(flashcardToRemove, response.getEntity());

		// verify
		verify(dao).removeByListIdAndId(DEFAULT_PARENT_ID, DEFAULT_ID);
	}

	@Test
	public void should_return_404_when_either_list_or_flashcard_was_not_found() {
		// when
		Response response = testObj.remove("otherListId", DEFAULT_ID);

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		// verify
		verify(dao).removeByListIdAndId("otherListId", DEFAULT_ID);
	}

}
