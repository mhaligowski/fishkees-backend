package com.fishkees.backend.modules.flashcards.dao;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.flashcards.core.FlashcardFixtures;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryFlashcardDaoTest {
	private static final String ID1 = "someId1";
	private static final String ID2 = "someId2";
	private static final String ID3 = "someId3";

	@InjectMocks
	private InMemoryFlashcardDao testObj;

	@Mock
	private FlashcardInMemoryStorage storage;

	@Mock
	private FlashcardListDao listDao;

	private List<Flashcard> flashcards;

	@Before
	public void setUp() {
		flashcards = FlashcardFixtures.all();

		when(storage.all()).thenReturn(flashcards);
		when(storage.get(ID1)).thenReturn(flashcards.get(0));
		when(storage.get(ID2)).thenReturn(flashcards.get(1));
		when(storage.get(ID3)).thenReturn(flashcards.get(2));

		when(storage.remove(ID1)).thenReturn(flashcards.get(0));
		when(storage.remove(ID2)).thenReturn(flashcards.get(1));
		when(storage.remove(ID3)).thenReturn(flashcards.get(2));

	}

	@After
	public void tearDown() {
		verifyNoMoreInteractions(storage, listDao);
	}

	@Test
	public void testGetAll() throws Exception {
		// given

		// when
		List<Flashcard> result = testObj.findAll();

		// then
		assertEquals(flashcards, result);
		verify(storage).all();
	}

	@Test
	public void testCreate() {
		// given
		Flashcard f = new Flashcard(null, "flashcardListId1", "front 1",
				"back 1", null);
		when(storage.getNewId()).thenReturn(ID1);

		// when
		Flashcard resultFlashcard = testObj.createNewFromObject(f);

		// then
		verify(storage).getNewId();

		ArgumentCaptor<String> stringCaptor = ArgumentCaptor
				.forClass(String.class);
		ArgumentCaptor<Flashcard> listCaptor = ArgumentCaptor
				.forClass(Flashcard.class);

		verify(storage).put(stringCaptor.capture(), listCaptor.capture());

		String id = stringCaptor.getValue();
		Flashcard newFlashcardFromStorage = listCaptor.getValue();

		assertNotNull(id);
		assertEquals(id, newFlashcardFromStorage.getId());
		assertEquals("flashcardListId1",
				newFlashcardFromStorage.getFlashcardListId());
		assertEquals(newFlashcardFromStorage, resultFlashcard);
		assertNotNull(resultFlashcard.getCreateDate());
	}

	@Test
	public void testFind() {
		// when
		Flashcard result1 = testObj.findById(ID1);
		Flashcard result2 = testObj.findById(ID2);

		// then
		assertEquals(ID1, result1.getId());
		assertEquals(ID2, result2.getId());
		assertEquals("front 1", result1.getFront());
		assertEquals("front 2", result2.getFront());

		verify(storage).get(ID1);
		verify(storage).get(ID2);
	}

	@Test
	public void testRemove_existing() {
		// when
		Flashcard removed = testObj.remove(ID1);

		// then
		assertEquals(ID1, removed.getId());

		verify(storage).remove(ID1);
	}

	@Test
	public void testRemove_nonExisting() {
		// when
		Flashcard removed = testObj.remove("1000");

		// then
		assertNull(removed);

		verify(storage).remove("1000");
	}

	@Test
	public void testUpdate_nonExisting() {
		// given
		Flashcard f = new Flashcard("10", "flashcardList10", "updated front",
				"updated back", new Date());

		// when
		Flashcard update = testObj.update(f);

		// then
		assertNull(update);
		verify(storage).update("10", f);
	}

	@Test
	public void testUpdate_existing() {
		// given
		Flashcard f = new Flashcard(ID1, "flashcardList1", "front 1", "back 1",
				new Date());
		when(storage.update(ID1, f)).thenReturn(f);

		// when
		Flashcard updated = testObj.update(f);

		// then
		assertEquals(f, updated);
		verify(storage).update(ID1, f);
	}

	@Test
	public void testFindAllByListId_found() {
		// given
		when(listDao.findById("flashcardListId1")).thenReturn(
				mock(FlashcardList.class));
		
		// when
		List<Flashcard> result = testObj.findAllByListId("flashcardListId1");

		// then
		assertEquals(1, result.size());
		assertEquals(flashcards.get(0), result.get(0));

		verify(storage).all();
		verify(listDao).findById("flashcardListId1");
	}

	@Test
	public void testFindAllBylistId_noList() {
		// given
		when(listDao.findById(anyString())).thenReturn(null);

		// when
		List<Flashcard> result = testObj.findAllByListId("flashcardListId100");

		// then
		assertNull(result);
		
		verify(listDao).findById("flashcardListId100");
		verify(storage, never()).get(anyString());
	}

	@Test
	public void testFindAllByListId_notFound() {
		// given
		when(listDao.findById(anyString())).thenReturn(
				mock(FlashcardList.class));

		// when
		List<Flashcard> result = testObj.findAllByListId("flashcardListId100");

		// then
		assertEquals(0, result.size());

		verify(storage).all();
		verify(listDao).findById("flashcardListId100");
	}

	@Test
	public void testFindByListIdAndId_found() {
		// when
		Flashcard actual = testObj.findByListIdAndId("flashcardListId1", ID1);

		// then
		assertEquals(flashcards.get(0), actual);

		verify(storage).get(ID1);
	}

	@Test
	public void testFindByListIdAndId_notMatchingIds() {
		// when
		Flashcard actual = testObj.findByListIdAndId("flashcardListId2", ID1);

		// then
		assertNull(actual);

		verify(storage).get(ID1);
	}

	@Test
	public void testFindByListIdAndId_notFound() {
		// when
		Flashcard actual = testObj.findByListIdAndId("flashcardListId2",
				"id2000");

		// then
		assertNull(actual);

		verify(storage).get("id2000");
	}
}
