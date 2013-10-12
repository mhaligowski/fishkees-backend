package com.fishkees.backend.modules.lists.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Date;
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
import com.google.common.collect.Lists;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.yammer.dropwizard.testing.ResourceTest;

@RunWith(MockitoJUnitRunner.class)
public class FlashcardListResourceTest extends ResourceTest {
	private final FlashcardList flashcardList1 = new FlashcardList("12345",
			"abcd", new Date());

	@InjectMocks
	private FlashcardListResource testObj;

	@Override
	protected void setUpResources() throws Exception {
		when(dao.findAll()).thenReturn(Lists.newArrayList(flashcardList1));
		when(dao.findById(12345l)).thenReturn(flashcardList1);
		when(dao.createNewFromObject(any(FlashcardList.class))).thenReturn(
				flashcardList1);
		when(dao.remove(12345l)).thenReturn(flashcardList1);
		addResource(testObj);
	}

	@After
	public void tearDown() {
		verifyNoMoreInteractions(dao);
	}

	@Mock
	private FlashcardListDao dao;

	@Test
	public void testGetFlashcardLists() {
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
	public void testCreate() throws IOException {
		// given
		FlashcardList flashcardList = new FlashcardList(null, "abcd",
				new Date());

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

		verify(dao).createNewFromObject(any(FlashcardList.class));
	}

	@Test
	public void testFind() {
		// when
		FlashcardList result = client().resource("/flashcardlists/12345")
									   .get(FlashcardList.class);

		// then
		assertEquals(flashcardList1.getId(), result.getId());
		assertEquals(flashcardList1.getTitle(), result.getTitle());
		assertEquals(flashcardList1.getCreateDate(), result.getCreateDate());

		verify(dao).findById(12345l);
	}

	@Test
	public void testRemove_existing() {
		// when
		FlashcardList result = client().resource("/flashcardlists/12345")
									   .delete(FlashcardList.class);

		// then
		assertEquals(flashcardList1.getId(), result.getId());
		assertEquals(flashcardList1.getTitle(), result.getTitle());
		assertEquals(flashcardList1.getCreateDate(), result.getCreateDate());

		verify(dao).remove(12345l);
	}

	@Test
	public void testRemove_nonexisting() {
		// when
		ClientResponse response = client().resource("/flashcardlists/1")
										  .delete(ClientResponse.class);

		// then
		assertEquals(404, response.getStatus());

		verify(dao).remove(1l);
	}

	@Test
	public void testUpdate_differentIds() {
		// given
		FlashcardList fl = new FlashcardList("54321", "updatedTitle", new Date());

		// when
		ClientResponse response = client().resource("/flashcardlists/12345")
										  .type(MediaType.APPLICATION_JSON)
										  .put(ClientResponse.class, fl);

		assertEquals(409, response.getStatus());
		assertEquals("Conflicting ids", response.getEntity(String.class));
	}

	@Test
	public void testUpdate_nonExisting() {
		// given
		FlashcardList fl = new FlashcardList("54321", "updatedTitle", new Date());

		// when
		ClientResponse response = client().resource("/flashcardlists/54321")
										  .type(MediaType.APPLICATION_JSON)
										  .put(ClientResponse.class, fl);

		// then
		assertEquals(404, response.getStatus());

		verify(dao).update(any(FlashcardList.class));
	}

	@Test
	public void testUpdate_existing() {
		// given
		FlashcardList fl = new FlashcardList("12345", "updatedTitle", new Date());
		when(dao.update(any(FlashcardList.class))).thenReturn(fl);

		// when
		ClientResponse response = client().resource("/flashcardlists/12345")
										  .type(MediaType.APPLICATION_JSON)
										  .put(ClientResponse.class, fl);

		// then
		assertEquals(200, response.getStatus());
		
		FlashcardList entity = response.getEntity(FlashcardList.class);
		assertEquals(fl.getId(), entity.getId());
		assertEquals(fl.getTitle(), entity.getTitle());
		assertEquals(fl.getCreateDate(), entity.getCreateDate());
		
		verify(dao).update(any(FlashcardList.class));
	}
}
