package com.fishkees.backend.modules.lists.resources;

import static com.fishkees.backend.modules.lists.core.FlashcardListTestBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.yammer.dropwizard.testing.ResourceTest;

@RunWith(MockitoJUnitRunner.class)
public class FlashcardListResourceTest extends ResourceTest {
	private static final String TITLE1 = "abcd";
	private static final String ID1 = "12345";

	private final FlashcardList flashcardList1 = newListWithId(ID1).withTitle(
			TITLE1).build();

	@InjectMocks
	private FlashcardListResource testObj;

	@Mock
	private FlashcardListDao dao;

	@Override
	protected void setUpResources() throws Exception {
		when(dao.findAll()).thenReturn(Lists.newArrayList(flashcardList1));
		when(dao.findById(ID1)).thenReturn(Optional.of(flashcardList1));
		when(dao.createNewFromObject(any(FlashcardList.class))).thenReturn(
				flashcardList1);
		when(dao.remove(ID1)).thenReturn(Optional.of(flashcardList1));
		addResource(testObj);
	}

	@After
	public void tearDown() {
		verifyNoMoreInteractions(dao);
	}

	@Test
	public void should_return_appropriate_flashcard_list() {
		// when
		GenericType<List<FlashcardList>> type = new GenericType<List<FlashcardList>>() {
		};
		List<FlashcardList> list = client().resource("/flashcardlists").get(
				type);

		// then
		assertEquals(1, list.size());
		assertEquals(flashcardList1.getId(), list.get(0).getId());

		verify(dao).findAll();
	}

	@Test
	public void should_return_200_with_object_when_creating()
			throws IOException {
		// given
		FlashcardList flashcardList = newListWithId(null).withTitle(TITLE1)
				.build();

		// when
		ClientResponse response = client().resource("/flashcardlists")
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, flashcardList);

		// then
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		assertEquals("application/json",
				response.getHeaders().get("Content-Type").get(0));
		assertEquals("/flashcardlists/12345",
				response.getHeaders().get("Location").get(0));

		FlashcardList entity = response.getEntity(FlashcardList.class);
		assertEquals(ID1, entity.getId());
		assertEquals(TITLE1, entity.getTitle());
		assertNotNull(entity.getCreateDate());

		verify(dao).createNewFromObject(any(FlashcardList.class));
	}

	@Test
	public void should_return_200_and_object_when_finding_one() {
		// when
		FlashcardList result = client().resource("/flashcardlists/12345").get(
				FlashcardList.class);

		// then
		assertEquals(flashcardList1.getId(), result.getId());
		assertEquals(flashcardList1.getTitle(), result.getTitle());
		assertEquals(flashcardList1.getCreateDate(), result.getCreateDate());

		verify(dao).findById(ID1);
	}

	@Test
	public void should_return_404_when_finding_non_existing() {
		// given
		when(dao.findById("nonExisting")).thenReturn(Optional.<FlashcardList>absent());
			
		// when
		ClientResponse clientResponse = client().resource(
				"/flashcardlists/nonExisting").get(ClientResponse.class);

		// then
		assertNotNull(clientResponse);
		assertEquals(404, clientResponse.getStatus());

		verify(dao).findById("nonExisting");
	}

	@Test
	public void should_return_200_and_object_when_removing_object() {
		// when
		FlashcardList result = client().resource("/flashcardlists/12345")
				.delete(FlashcardList.class);

		// then
		assertEquals(flashcardList1.getId(), result.getId());
		assertEquals(flashcardList1.getTitle(), result.getTitle());
		assertEquals(flashcardList1.getCreateDate(), result.getCreateDate());

		verify(dao).remove(ID1);
	}

	@Test
	public void should_return_404_when_removing_nonexisting() {
		// given
		when(dao.remove("nonexisting")).thenReturn(Optional.<FlashcardList>absent());
		
		// when
		ClientResponse response = client().resource(
				"/flashcardlists/nonexisting").delete(ClientResponse.class);

		// then
		assertEquals(404, response.getStatus());

		verify(dao).remove("nonexisting");
	}

	@Test
	public void should_return_409_when_updating_conflicting_ids() {
		// given
		FlashcardList fl = newListWithId("conflicting").withTitle("updated")
				.build();

		// when
		ClientResponse response = client().resource("/flashcardlists/12345")
				.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, fl);

		assertEquals(409, response.getStatus());
		assertEquals("Conflicting ids", response.getEntity(String.class));
	}

	@Test
	public void should_return_404_when_updating_nonexisting() {
		// given
		String NONEXISTING = "54321";
		FlashcardList fl = newListWithId(NONEXISTING).withTitle("updated")
				.build();

		// when
		ClientResponse response = client().resource("/flashcardlists/54321")
				.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, fl);

		// then
		assertEquals(404, response.getStatus());

		verify(dao).update(any(FlashcardList.class));
	}

	@Test
	public void should_return_200_when_succesful_update() {
		// given
		FlashcardList fl = newListWithId(ID1).withTitle("updated").build();
		when(dao.update(any(FlashcardList.class))).thenReturn(fl);

		// when
		ClientResponse response = client().resource("/flashcardlists/12345")
				.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, fl);

		// then
		assertEquals(200, response.getStatus());

		FlashcardList entity = response.getEntity(FlashcardList.class);
		assertEquals(fl.getId(), entity.getId());
		assertEquals(fl.getTitle(), entity.getTitle());
		assertEquals(fl.getCreateDate(), entity.getCreateDate());

		verify(dao).update(any(FlashcardList.class));
	}
}
