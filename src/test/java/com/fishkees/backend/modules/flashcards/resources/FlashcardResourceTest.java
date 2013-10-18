package com.fishkees.backend.modules.flashcards.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

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
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.yammer.dropwizard.testing.ResourceTest;

@RunWith(MockitoJUnitRunner.class)
public class FlashcardResourceTest extends ResourceTest {

	@InjectMocks
	private FlashcardResource testObj;

	@Mock
	private FlashcardDao dao;

	private List<Flashcard> flashcards;

	@Override
	protected void setUpResources() throws Exception {
		flashcards = FlashcardFixtures.all();
		when(dao.findAll()).thenReturn(flashcards);

		addResource(testObj);
	}

	@After
	public void tearDown() {
		verifyNoMoreInteractions(dao);
	}

	@Test
	public void testCreate_properly() {
		// given
		Flashcard flashcard = FlashcardFixtures.partial();
		Flashcard expected = new Flashcard("someId", "someListId",
				"front text", "back text", new Date());
		when(dao.createNewFromObject(any(Flashcard.class)))
				.thenReturn(expected);

		// when
		ClientResponse response = client()
				.resource("/flashcardlists/someListId/flashcards")
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, flashcard);

		// then
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		assertEquals("application/json",
				response.getHeaders().get("Content-Type").get(0));
		assertEquals("/flashcardlists/someListId/flashcards/someId", response
				.getHeaders().get("Location").get(0));

		verify(dao).createNewFromObject(any(Flashcard.class));
	}

	@Test
	public void testCreate_improperly() {
		// given
		Flashcard flashcard = FlashcardFixtures.partial();

		// when
		ClientResponse response = client()
				.resource("/flashcardlists/otherListId/flashcards")
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, flashcard);

		// then
		assertNotNull(response);
		assertEquals(409, response.getStatus());

		verify(dao, never()).createNewFromObject(any(Flashcard.class));
	}

	@Test
	public void testFindOne_nonExisting() {
		// when
		ClientResponse clientResponse = client().resource(
				"/flashcardlists/otherListId/flashcards/someId1").get(
				ClientResponse.class);

		// then
		assertNotNull(clientResponse);
		assertEquals(404, clientResponse.getStatus());

		verify(dao).findByListIdAndId("otherListId", "someId1");
	}

	@Test
	public void testFindOne_existing() {
		// given
		when(dao.findByListIdAndId("flashcardListId1", "someId1")).thenReturn(
				flashcards.get(0));

		// when
		ClientResponse clientResponse = client().resource(
				"/flashcardlists/flashcardListId1/flashcards/someId1").get(
				ClientResponse.class);

		// then
		assertNotNull(clientResponse);
		assertEquals(200, clientResponse.getStatus());

		Flashcard expected = flashcards.get(0);
		Flashcard entity = clientResponse.getEntity(Flashcard.class);
		assertEquals(expected.getId(), entity.getId());
		assertEquals(expected.getCreateDate(), entity.getCreateDate());
		assertEquals(expected.getFront(), entity.getFront());
		assertEquals(expected.getBack(), entity.getBack());

		verify(dao).findByListIdAndId("flashcardListId1", "someId1");
	}

	@Test
	public void testFindAll_emptyCollection() {
		// when
		ClientResponse response = client().resource(
				"/flashcardlists/flashcardListId1000/flashcards").get(
				ClientResponse.class);

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());

		verify(dao).findAllByListId("flashcardListId1000");
		GenericType<List<Flashcard>> type = new GenericType<List<Flashcard>>() {};
		List<Flashcard> entity = response.getEntity(type);
		
		assertEquals(0, entity.size());

	}

	@Test
	public void testFindAll_existing() {
		// given
		Flashcard expectedFlashcard = flashcards.get(0);
		when(dao.findAllByListId("flashcardListId1")).thenReturn(
				Lists.newArrayList(expectedFlashcard));

		// when
		ClientResponse response = client().resource(
				"/flashcardlists/flashcardListId1/flashcards").get(
				ClientResponse.class);

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());

		verify(dao).findAllByListId("flashcardListId1");
		
		GenericType<List<Flashcard>> type = new GenericType<List<Flashcard>>() {};
		List<Flashcard> entity = response.getEntity(type);
		
		assertEquals(1, entity.size());
		assertEquals(expectedFlashcard.getId(), entity.get(0).getId());
		assertEquals(expectedFlashcard.getCreateDate(), entity.get(0).getCreateDate());
		assertEquals(expectedFlashcard.getFront(), entity.get(0).getFront());
		assertEquals(expectedFlashcard.getBack(), entity.get(0).getBack());

	}

	@Test
	public void testFindAll_noSuchList() {
		// given
		when(dao.findAllByListId("otherList")).thenReturn(null);

		// when
		ClientResponse response = client().resource(
				"/flashcardlists/otherList/flashcards/").get(
				ClientResponse.class);

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		verify(dao).findAllByListId("otherList");
	}

}
