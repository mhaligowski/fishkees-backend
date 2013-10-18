package com.fishkees.backend.modules.flashcards.resources;

import static org.junit.Assert.*;
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
import com.sun.jersey.api.client.ClientResponse;
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
		ClientResponse clientResponse = client().resource("/flashcardlists/otherListId/flashcards/someId1")
				.get(ClientResponse.class);
		
		// then
		assertNotNull(clientResponse);
		assertEquals(404, clientResponse.getStatus());
		
		verify(dao).findByListIdAndId("otherListId", "someId1");
	}

	@Test
	public void testFindOne_existing() {
		// given
		when(dao.findByListIdAndId("flashcardListId1", "someId1")).thenReturn(flashcards.get(0));
		
		// when
		ClientResponse clientResponse = client().resource("/flashcardlists/flashcardListId1/flashcards/someId1")
				.get(ClientResponse.class);
		
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

}
