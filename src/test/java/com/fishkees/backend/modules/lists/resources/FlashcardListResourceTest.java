package com.fishkees.backend.modules.lists.resources;

import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.mockito.Mockito.*;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.fishkees.backend.modules.lists.resources.FlashcardListResource;

@RunWith(MockitoJUnitRunner.class)
public class FlashcardListResourceTest {
	@InjectMocks
	private FlashcardListResource testObj;
	
	@Mock
	private FlashcardListDao flashcardListDao;
	
	@Test
	@SuppressWarnings({ "unchecked" })
	public void test_getAll() throws IOException {
		// given
		List<FlashcardList> lists = (List<FlashcardList>) fromJson(jsonFixture("fixtures/lists/all.json"), List.class);
		when(flashcardListDao.findAll()).thenReturn(lists);
		
		// when
		List<FlashcardList> result = testObj.getFlashcardLists();
		
		// then
		assertThat(result).containsAll(lists);
		verify(flashcardListDao).findAll();
	}
	


}
