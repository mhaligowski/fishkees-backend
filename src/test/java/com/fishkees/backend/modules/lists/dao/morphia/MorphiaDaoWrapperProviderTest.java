package com.fishkees.backend.modules.lists.dao.morphia;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;

import com.fishkees.backend.dataaccess.morphia.Mapper;

@RunWith(MockitoJUnitRunner.class)
public class MorphiaDaoWrapperProviderTest {

	@InjectMocks
	private MorphiaDaoWrapperProvider testObj;

	@Mock
	private Morphia morphia;

	@Mock(extraInterfaces = { Datastore.class }, answer = Answers.RETURNS_DEEP_STUBS)
	private DatastoreImpl datastore;

	@Mock
	private Mapper mapper;
	
	@Test
	public void should_map_if_not_mapped() {
		// given
		when(morphia.isMapped(MorphiaFlashcardList.class)).thenReturn(
				Boolean.TRUE);

		// when
		MorphiaDaoWrapper result = testObj.get();

		// then
		assertNotNull(result);
		verify(morphia, never()).map(MorphiaFlashcardList.class);
	}

	@Test
	public void should_not_map_if_already_mapped() {
		// given
		when(morphia.isMapped(MorphiaFlashcardList.class)).thenReturn(
				Boolean.FALSE);

		// when
		MorphiaDaoWrapper result = testObj.get();

		// then
		assertNotNull(result);
		verify(morphia).map(MorphiaFlashcardList.class);
	}

}
