package com.fishkees.backend.modules.flashcards.resources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

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

}
