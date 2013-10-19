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

import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.flashcards.core.FlashcardFixtures;
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
	public void test_createProperly() {
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
	public void test_createImproperly() {
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
	public void testFind_existing() {
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
	public void testFind_nonExisting() {
		// when
		Response response = testObj.find("otherList", "someId1");

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		verify(dao).findByListIdAndId("otherList", "someId1");
	}

	@Test
	public void testFindAll_existing() {
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
	public void testFindAll_notExisting() {
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
	public void testUpdate_OK() {
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
	public void testUpdate_conflictingLists() {
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
	public void testUpdate_conflictingFlashcards() {
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
	public void testUpdate_notExisting() {
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
	public void testRemove_OK() {
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
	public void testRemove_daoReturnsNull() {
		// when
		Response response = testObj.remove("otherListId", "someId");

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		// verify
		verify(dao).removeByListIdAndId("otherListId", "someId");
	}

}
