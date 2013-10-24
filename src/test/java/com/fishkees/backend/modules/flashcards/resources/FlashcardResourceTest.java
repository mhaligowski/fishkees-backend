package com.fishkees.backend.modules.flashcards.resources;

import static org.fest.assertions.api.Assertions.*;
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

import com.fishkees.backend.modules.flashcards.FlashcardFixtures;
import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.flashcards.dao.FlashcardDao;
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.yammer.dropwizard.testing.ResourceTest;
import com.yammer.dropwizard.validation.InvalidEntityException;

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
	public void should_return_201_after_properly_creating() {
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
	public void should_return_409_when_trying_to_create_with_mismatching_ids() {
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

	@Test(expected = InvalidEntityException.class)
	public void should_throw_exception_when_the_incoming_entity_is_invalid() {
		// given
		Flashcard flashcard = new Flashcard("someId", null, "newFront",
				"newBack", new Date());

		// when
		client().resource("/flashcardlists/otherListId/flashcards")
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, flashcard);

	}

	@Test
	public void should_return_404_when_findin_non_existing() {
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
	public void should_return_the_proper_element() {
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
	public void should_return_empty_collection_when_looking_for_existing_list() {
		// when
		ClientResponse response = client().resource(
				"/flashcardlists/flashcardListId1000/flashcards").get(
				ClientResponse.class);

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());

		verify(dao).findAllByListId("flashcardListId1000");
		GenericType<List<Flashcard>> type = new GenericType<List<Flashcard>>() {
		};
		assertThat(response.getEntity(type)).isEmpty();;

	}

	@Test
	public void should_return_list_of_flashcard_when_calling_existing_list_id() {
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

		GenericType<List<Flashcard>> type = new GenericType<List<Flashcard>>() {
		};
		List<Flashcard> entity = response.getEntity(type);

		assertEquals(1, entity.size());
		assertEquals(expectedFlashcard.getId(), entity.get(0).getId());
		assertEquals(expectedFlashcard.getCreateDate(), entity.get(0)
				.getCreateDate());
		assertEquals(expectedFlashcard.getFront(), entity.get(0).getFront());
		assertEquals(expectedFlashcard.getBack(), entity.get(0).getBack());

	}

	@Test
	public void should_return_404_when_looking_for_flashcards_from_nonexisting_list() {
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

	@Test
	public void should_return_200_and_object_when_updating_properly() {
		// given
		Flashcard toUpdate = new Flashcard("someId", "flashcardListId",
				"updated front", "updated back", new Date());
		when(dao.update(any(Flashcard.class))).thenReturn(toUpdate);

		// when
		ClientResponse response = client()
				.resource("/flashcardlists/flashcardListId/flashcards/someId")
				.type(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, toUpdate);

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());

		Flashcard updated = response.getEntity(Flashcard.class);
		assertEquals(toUpdate.getId(), updated.getId());
		assertEquals(toUpdate.getFlashcardListId(),
				updated.getFlashcardListId());
		assertEquals(toUpdate.getFront(), updated.getFront());
		assertEquals(toUpdate.getBack(), updated.getBack());

		// verify
		verify(dao).update(any(Flashcard.class));
	}

	@Test
	public void should_return_409_when_mismatching_ids() {
		// given
		Flashcard toUpdate = new Flashcard("someId", "flashcardListId",
				"updated front", "updated back", new Date());

		// when
		ClientResponse response = client()
				.resource(
						"/flashcardlists/otherFlashcardList/flashcards/someId")
				.type(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, toUpdate);

		// then
		assertNotNull(response);
		assertEquals(409, response.getStatus());

		// verify
		verify(dao, never()).update(any(Flashcard.class));
	}

	@Test
	public void should_return_409_when_mismathing_flashcard_ids() {

		// given
		Flashcard toUpdate = new Flashcard("someId", "flashcardListId",
				"updated front", "updated back", new Date());

		// when
		ClientResponse response = client()
				.resource("/flashcardlists/flashcardListId/flashcards/otherId")
				.type(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, toUpdate);

		// then
		assertNotNull(response);
		assertEquals(409, response.getStatus());

		// verify
		verify(dao, never()).update(any(Flashcard.class));
	}

	@Test(expected = InvalidEntityException.class)
	public void should_throw_exception_when_the_incoming_entity_is_invalid_in_update() {
		// given
		Flashcard toUpdate = new Flashcard("someId", null, "updated front",
				"updated back", new Date());

		// when
		client().resource("/flashcardlists/flashcardListId/flashcards/someId")
				.type(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, toUpdate);

	}

	@Test
	public void should_return_404_when_updating_non_existing() {
		// given
		Flashcard toUpdate = new Flashcard("notFoundId", "flashcardListId",
				"updated front", "updated back", new Date());

		// when
		ClientResponse response = client()
				.resource(
						"/flashcardlists/flashcardListId/flashcards/notFoundId")
				.type(MediaType.APPLICATION_JSON)
				.put(ClientResponse.class, toUpdate);

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		// verify
		verify(dao).update(any(Flashcard.class));
	}

	@Test
	public void should_return_200_and_removed_object_if_removing_is_successful() {
		// given
		Flashcard single = FlashcardFixtures.single();
		when(dao.removeByListIdAndId("flashcardListId", "someId")).thenReturn(
				single);

		// when
		ClientResponse response = client().resource(
				"/flashcardlists/flashcardListId/flashcards/someId").delete(
				ClientResponse.class);

		// then
		assertNotNull(response);
		assertEquals(200, response.getStatus());

		Flashcard entity = response.getEntity(Flashcard.class);
		assertNotNull(entity);
		assertEquals("someId", entity.getId());
		assertEquals("flashcardListId", entity.getFlashcardListId());

		// verify
		verify(dao).removeByListIdAndId("flashcardListId", "someId");

	}

	@Test
	public void should_return_404_when_not_existing_list_or_flashcard() {
		// when
		ClientResponse response = client().resource(
				"/flashcardlists/flashcardListId/flashcards/someId").delete(
				ClientResponse.class);

		// then
		assertNotNull(response);
		assertEquals(404, response.getStatus());

		// verify
		verify(dao).removeByListIdAndId("flashcardListId", "someId");
	}

}
