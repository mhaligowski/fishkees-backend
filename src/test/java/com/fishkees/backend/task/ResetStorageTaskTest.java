package com.fishkees.backend.task;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fishkees.backend.modules.flashcards.dao.FlashcardInMemoryStorage;
import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;

@RunWith(MockitoJUnitRunner.class)
public class ResetStorageTaskTest {

	@InjectMocks
	private ResetStorageTask testObj;
	
	@Mock
	private FlashcardListInMemoryStorage flashcardListStorage;
	
	@Mock
	private PrintWriter printWriter;
	
	@Mock
	private FlashcardInMemoryStorage flashcardStorage;
	
	@After
	public void tearDown() {
		verifyNoMoreInteractions(flashcardListStorage, flashcardStorage, printWriter);
	}
	
	@Test
	public void should_call_reset_for_two_storages() throws Exception {
		// when
		testObj.execute(null, printWriter);
		
		// then
		verify(printWriter, times(4)).format(anyString(), anyInt());
		
		verify(flashcardStorage, times(2)).all();
		verify(flashcardStorage).reset();
		
		verify(flashcardListStorage, times(2)).all();
		verify(flashcardListStorage).reset();
	}

}
