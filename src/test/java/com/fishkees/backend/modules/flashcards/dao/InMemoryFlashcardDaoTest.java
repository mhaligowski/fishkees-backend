package com.fishkees.backend.modules.flashcards.dao;

import static org.fest.assertions.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.modules.flashcards.FlashcardFixtures;
import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.flashcards.core.FlashcardTestBuilder;
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
	public void should_return_full_list_of_flashcards() throws Exception {
		// when
		List<Flashcard> result = testObj.findAll();

		// then
		assertEquals(flashcards, result);
		verify(storage).all();
	}

	@Test
	public void should_store_new_flashcard_from_partial() {
		// given
		Flashcard f = FlashcardTestBuilder.withId(null)
				.withParent("flashcardListId1").withValues("front 1", "back 1")
				.build();
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
	public void should_return_the_objects() {
		// when
		Flashcard result1 = testObj.findById(ID1);
		Flashcard result2 = testObj.findById(ID2);

		// then
		assertThat(this.flashcards).contains(result1, result2);

		verify(storage).get(ID1);
		verify(storage).get(ID2);
	}

	@Test
	public void should_return_removed_element() {
		// when
		Flashcard removed = testObj.remove(ID1);

		// then
		assertEquals(ID1, removed.getId());

		verify(storage).remove(ID1);
	}

	@Test
	public void should_return_null_if_removing_non_exsiting() {
		// when
		Flashcard removed = testObj.remove("1000");

		// then
		assertNull(removed);

		verify(storage).remove("1000");
	}

	@Test
	public void should_return_null_if_updating_non_existing() {
		// given
		String NON_EXISTING = "10";
		Flashcard f = FlashcardTestBuilder.withId(NON_EXISTING).build();

		// when
		Flashcard update = testObj.update(f);

		// then
		assertNull(update);
		verify(storage).update(NON_EXISTING, f);
	}

	@Test
	public void should_return_updated_value() {
		// given
		Flashcard f = FlashcardTestBuilder.withId(ID1)
				.withParent("flashcardList1").withValues("front 1", "back 1")
				.build();
		when(storage.update(ID1, f)).thenReturn(f);

		// when
		Flashcard updated = testObj.update(f);

		// then
		assertEquals(f, updated);
		verify(storage).update(ID1, f);
	}

	@Test
	public void should_return_flashcard_when_searching_by_list_id() {
		// given
		when(listDao.findById("flashcardListId1")).thenReturn(
				mock(FlashcardList.class));

		// when
		List<Flashcard> result = testObj.findAllByListId("flashcardListId1");

		// then
		assertThat(result).containsExactly(flashcards.get(0));

		verify(storage).all();
		verify(listDao).findById("flashcardListId1");
	}

	@Test
	public void should_return_empty_list_when_looking_for_non_existing() {
		// given
		when(listDao.findById(anyString())).thenReturn(null);

		// when
		List<Flashcard> resultList = testObj.findAllByListId("flashcardListId100");

		// then
		assertThat(resultList).isNull();

		verify(listDao).findById("flashcardListId100");
		verify(storage, never()).get(anyString());
	}

	@Test
	public void should_return_empty_list_when_looking_by_empty_list() {
		// given
		when(listDao.findById(anyString())).thenReturn(
				mock(FlashcardList.class));

		// when
		List<Flashcard> result = testObj.findAllByListId("flashcardListId100");

		// then
		assertThat(result).isEmpty();

		verify(storage).all();
		verify(listDao).findById("flashcardListId100");
	}

	@Test
	public void should_return_the_list_when_searching_by_list_id_and_id() {
		// when
		Flashcard actual = testObj.findByListIdAndId("flashcardListId1", ID1);

		// then
		assertEquals(flashcards.get(0), actual);

		verify(storage).get(ID1);
	}

	@Test
	public void should_return_null_if_looking_for_non_existing_list() {
		// when
		Flashcard actual = testObj.findByListIdAndId("flashcardListId2", ID1);

		// then
		assertNull(actual);

		verify(storage).get(ID1);
	}

	@Test
	public void should_return_null_() {
		// when
		Flashcard actual = testObj.findByListIdAndId("flashcardListId2",
				"id2000");

		// then
		assertNull(actual);

		verify(storage).get("id2000");
	}

	@Test
	public void should_return_the_removed_flashcard() {
		// when
		Flashcard flashcard = testObj.removeByListIdAndId("flashcardListId1",
				ID1);

		// then
		assertEquals(flashcard, flashcards.get(0));

		// verify
		verify(storage).get(ID1);
		verify(storage).remove(ID1);
	}

	@Test
	public void should_return_null_if_removing_non_existing_object() {
		// when
		Flashcard flashcard = testObj.removeByListIdAndId(
				"flashcardListId11000", ID1);

		// then
		assertNull(flashcard);

		// verify
		verify(storage).get(ID1);
		verify(storage, never()).remove(ID1);
	}

}
