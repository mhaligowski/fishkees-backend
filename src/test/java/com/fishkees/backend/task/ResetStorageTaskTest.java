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

import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;

@RunWith(MockitoJUnitRunner.class)
public class ResetStorageTaskTest {

	@InjectMocks
	private ResetStorageTask testObj;
	
	@Mock
	private FlashcardListInMemoryStorage storage;
	
	@Mock
	private PrintWriter printWriter;
	
	@After
	public void tearDown() {
		verifyNoMoreInteractions(storage, printWriter);
	}
	
	@Test
	public void testTaskExecution() throws Exception {
		// when
		testObj.execute(null, printWriter);
		
		// then
		verify(printWriter, times(2)).println(anyString());
		verify(storage, times(2)).all();
		verify(storage).reset();
	}

}
