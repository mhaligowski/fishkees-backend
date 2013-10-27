package com.fishkees.backend.dataaccess;

import static com.fishkees.backend.modules.lists.core.FlashcardListTestBuilder.*;
import static org.fest.assertions.api.Assertions.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

public class FlashcardListInMemoryStorageTest {
	private static final String TITLE3 = "c";
	private static final String TITLE2 = "bcde";
	private static final String TITLE1 = "a";
	private static final String ID1 = "someId1";
	private static final String ID2 = "someId2";
	private static final String ID3 = "someId3";
	private FlashcardListInMemoryStorage testObj;
	private FlashcardList fl1;
	private FlashcardList fl2;
	private FlashcardList fl3;

	@Before
	public void setUp() {
		fl1 = newListWithId(ID1).withTitle(TITLE1).build();
		fl2 = newListWithId(ID2).withTitle(TITLE2).build();
		fl3 = newListWithId(ID3).withTitle(TITLE3).build();
		List<FlashcardList> inputList = Lists.newArrayList(fl1, fl2, fl3);

		this.testObj = new FlashcardListInMemoryStorage(inputList);
	}

	@Test
	public void should_return_id_from_flashcard_list() {
		// when
		String actual = testObj.getId(fl1);

		// then
		assertEquals(ID1, actual);
	}

	@Test
	public void should_contain_new_flashcardlist_after_putting() {
		// given
		FlashcardList newFl = newListWithRandomId().build();

		// when
		testObj.put(newFl.getId(), newFl);

		// then
		assertThat(testObj.all()).containsOnly(fl1, fl2, fl3, newFl);
		assertEquals(newFl, testObj.get(newFl.getId()).get());
	}

	@Test
	public void should_return_null_when_finding_nonexisting() {
		// given
		String NONEXISTING_ID = "0";
		
		// when
		Optional<FlashcardList> result = testObj.get(NONEXISTING_ID);
		
		assertNotNull(result);
		assertFalse(result.isPresent());
	}

	@Test
	public void should_return_list_with_all() {
		// when
		List<FlashcardList> all = Lists.newArrayList(testObj.all());

		// then
		assertThat(all).containsOnly(fl1, fl2, fl3);
	}

	@Test
	public void should_return_proper_one_when_querying() {
		// when
		Optional<FlashcardList> flashcardList = testObj.get(ID2);

		// then
		assertEquals(ID2, flashcardList.get().getId());
		assertEquals(TITLE2, flashcardList.get().getTitle());
	}

	@Test
	public void should_return_different_ids_every_time() {
		// when
		String string1 = testObj.getNewId();
		String string2 = testObj.getNewId();

		assertNotEquals(string1, string2);
	}

	@Test
	public void should_have_the_same_elements_after_reset() {
		// given
		String NEW_ID = "100";
		FlashcardList newFlashcardList = newListWithId(NEW_ID).build(); 

		// when
		this.testObj.put(NEW_ID, newFlashcardList);
		this.testObj.reset();

		// then
		assertThat(testObj.all()).containsOnly(fl1, fl2, fl3);
	}

	@Test
	public void should_return_removed_item_when_item_exists() {
		// when
		FlashcardList removed = this.testObj.remove(ID2);

		// then
		assertThat(this.testObj.all()).containsOnly(fl1, fl3);
		assertEquals(fl2, removed);
	}

	@Test
	public void should_return_null_when_removing_nonexisting() {
		// when
		String NONEXISTING = "1000";
		FlashcardList removed = this.testObj.remove(NONEXISTING);

		// then
		assertThat(this.testObj.all()).containsOnly(fl1, fl2, fl3);
		assertNull(removed);
	}

	@Test
	public void should_return_updated_item() {
		// given
		FlashcardList fl = newListWithId(ID1).build(); 

		// when
		FlashcardList update = testObj.update(ID1, fl);

		// then
		assertEquals(fl, update);
		assertThat(this.testObj.all()).containsOnly(fl, fl2, fl3);
	}

	@Test
	public void should_return_null_when_updateing_non_existing() {
		// given
		String NONEXISTING = "nonexisting";
		FlashcardList fl = newListWithId(NONEXISTING).build(); 

		// when
		FlashcardList update = testObj.update(NONEXISTING, fl);

		// then
		assertNull(update);
		assertThat(this.testObj.all()).containsOnly(fl1, fl2, fl3);
	}

}
