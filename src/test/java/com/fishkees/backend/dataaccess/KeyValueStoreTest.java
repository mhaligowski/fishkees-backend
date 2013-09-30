package com.fishkees.backend.dataaccess;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class KeyValueStoreTest {
	private static class MockKeyValueStore extends
			KeyValueStore<Integer, String> {
	}

	private MockKeyValueStore testObj;

	@Before
	public void setUp() {
		testObj = new MockKeyValueStore();
	}

	@Test
	public void testSavingAndRestoring() {
		testObj.put(1, "abcd");
		assertEquals("abcd", testObj.get(1));
	}

	@Test
	public void testRestoringNonExistend() {
		assertNull(testObj.get(0));
	}

	@Test
	public void testGetAll() {
		// when
		testObj.put(1, "abcd");
		testObj.put(2, "abcd");
		testObj.put(3, "abcd");
		
		Collection<String> all = testObj.all();
		
		assertEquals(3, all.size());
		String[] strings = all.toArray(new String[3]);
		assertEquals("abcd", strings[0]);
		assertEquals("abcd", strings[1]);
		assertEquals("abcd", strings[2]);

	}

}
