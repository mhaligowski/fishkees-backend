package com.fishkees.backend.modules.lists.resources;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

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
	private FlashcardList flashcardList1 = new FlashcardList(12345l, "abcd",
			new Date());

	@InjectMocks
	private FlashcardListResource testObj;

	@Override
	protected void setUpResources() throws Exception {
		when(dao.findAll()).thenReturn(Lists.newArrayList(flashcardList1));
		when(dao.findById(12345l)).thenReturn(flashcardList1);
		when(dao.createNewFromObject(any(FlashcardList.class))).thenReturn(
				flashcardList1);
		addResource(testObj);
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
	}

	@Test
	public void testCreate() throws IOException {
		// given
		FlashcardList flashcardList = new FlashcardList(null, "abcd", new Date());
		
		// when
		ClientResponse response = client().resource("/flashcardlists")
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, flashcardList);

		assertNotNull(response);
		assertEquals(201, response.getStatus());
		assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
		assertEquals("/flashcardlists/12345", response.getHeaders().get("Location").get(0));
	}
	
	@Test
	public void testFind() {
		// when
		FlashcardList result = client().resource("/flashcardlists/12345").get(FlashcardList.class);
		
		assertEquals(flashcardList1.getId(), result.getId());
		assertEquals(flashcardList1.getTitle(), result.getTitle());
		assertEquals(flashcardList1.getCreateDate(), result.getCreateDate());
	}
}
