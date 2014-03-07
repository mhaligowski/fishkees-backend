package com.fishkees.backend.modules.lists.dao;

import static org.junit.Assert.*;
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

import com.fishkees.backend.modules.lists.FlashcardListFixtures;
import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.inmemory.FlashcardListInMemoryStorage;

import static com.fishkees.backend.modules.lists.core.FlashcardListTestBuilder.*;

import com.google.common.base.Optional;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryFlashcardListDaoTest {
	private static final String ID1 = "someNiceId1";
	private static final String ID2 = "someNiceId2";
	private static final String NONEXISTING = "1000";

	@InjectMocks
	private InMemoryFlashcardListDao testObj;

	@Mock
	private FlashcardListInMemoryStorage storage;

	private List<FlashcardList> lists;

	@Before
	public void setUp() {
		lists = FlashcardListFixtures.all();

		when(storage.all()).thenReturn(lists);
		when(storage.get(ID1)).thenReturn(Optional.of(lists.get(0)));
		when(storage.get(ID2)).thenReturn(Optional.of(lists.get(1)));

		when(storage.remove(ID1)).thenReturn(Optional.of(lists.get(0)));
		when(storage.remove(ID2)).thenReturn(Optional.of(lists.get(1)));
	}

	@After
	public void tearDown() {
		verifyNoMoreInteractions(storage);
	}

	@Test
	public void should_return_the_list_when_querying_for_all() throws Exception {
		// when
		List<FlashcardList> result = testObj.findAll();

		// then
		assertEquals(lists, result);
		verify(storage).all();
	}

	@Test
	public void should_create_new_object_from_passed_one_with_copied_data() {
		// given
		final FlashcardList fl = newListWithId(null).withTitle("abcd").build();
		when(storage.getNewId()).thenReturn(ID1);

		// when
		Optional<FlashcardList> resultFlashcardList = testObj
				.createNewFromObject(fl);

		// then
		verify(storage).getNewId();

		ArgumentCaptor<String> stringCaptor = ArgumentCaptor
				.forClass(String.class);
		ArgumentCaptor<FlashcardList> listCaptor = ArgumentCaptor
				.forClass(FlashcardList.class);

		verify(storage).put(stringCaptor.capture(), listCaptor.capture());

		String id = stringCaptor.getValue();
		FlashcardList newFlashcardListFromStorage = listCaptor.getValue();

		assertNotNull(id);
		assertEquals(id, newFlashcardListFromStorage.getId());
		assertEquals("abcd", newFlashcardListFromStorage.getTitle());
		assertEquals(newFlashcardListFromStorage, resultFlashcardList.get());
		assertNotNull(resultFlashcardList.get().getCreateDate());
	}

	@Test
	public void should_return_appropriate_objects_when_calling_find() {
		// when
		FlashcardList result1 = testObj.findById(ID1).get();
		FlashcardList result2 = testObj.findById(ID2).get();

		// then
		assertEquals(ID1, result1.getId());
		assertEquals(ID2, result2.getId());
		assertEquals("Spanish for beginners", result1.getTitle());
		assertEquals("Russian for intermediate", result2.getTitle());

		verify(storage).get(ID1);
		verify(storage).get(ID2);
	}

	@Test
	public void should_return_appropriate_object_when_removing() {
		// when
		Optional<FlashcardList> removed = testObj.remove(ID1);

		// then
		assertEquals(ID1, removed.get().getId());

		verify(storage).remove(ID1);
	}

	@Test
	public void should_return_null_if_removing_non_existing() {
		// given
		when(storage.remove(NONEXISTING)).thenReturn(
				Optional.<FlashcardList> absent());

		// when
		Optional<FlashcardList> removed = testObj.remove(NONEXISTING);

		// then
		assertFalse(removed.isPresent());
		verify(storage).remove(NONEXISTING);
	}

	@Test
	public void should_return_null_if_updateing_non_existing() {
		// given
		FlashcardList flashcardList = newListWithId(NONEXISTING).build();
		when(storage.update(NONEXISTING, flashcardList)).thenReturn(
				Optional.<FlashcardList> absent());

		// when
		Optional<FlashcardList> update = testObj.update(flashcardList);

		// then
		assertFalse(update.isPresent());
		verify(storage).update(NONEXISTING, flashcardList);
	}

	@Test
	public void should_return_the_updated_object() {
		// given
		FlashcardList fl = newListWithId(ID1).withTitle("new title").build();
		when(storage.update(ID1, fl)).thenReturn(Optional.of(fl));

		// when
		Optional<FlashcardList> updated = testObj.update(fl);

		// then
		assertEquals(fl, updated.get());
		verify(storage).update(ID1, fl);
	}
}
