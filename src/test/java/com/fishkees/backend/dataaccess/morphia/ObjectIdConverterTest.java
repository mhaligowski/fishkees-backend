package com.fishkees.backend.dataaccess.morphia;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.google.common.base.Strings;

public class ObjectIdConverterTest {

	private ObjectIdConverter testObj = new ObjectIdConverter();
	
	@Test
	public void should_convert_object_id_to_string() {
		// given
		String expected = Strings.repeat("abcd", 6);
		ObjectId objId = new ObjectId(expected);
		
		// when
		String actual = testObj.convertTo(objId);
		
		// then
		assertEquals(expected, actual);
	}

	@Test
	public void should_convert_null_string_to_null_object_id() {
		// when
		ObjectId objectId = testObj.convertFrom(null);
		
		// then
		assertNull(objectId);
	}
	
	@Test
	public void should_convert_meaningful_string_to_object_id() {
		// given
		String expected = Strings.repeat("abcd", 6);
		
		// when
		ObjectId actual = testObj.convertFrom(expected);
		
		// then
		assertEquals(expected, actual.toString());
	}

}
