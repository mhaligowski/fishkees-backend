package com.fishkees.backend.modules.lists.resources;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.google.common.collect.Lists;

@Path("/flashcardlists")
@Produces(MediaType.APPLICATION_JSON)
public class FlashcardListResource {

	@GET
	public List<FlashcardList> getFlashcardLists() {
		FlashcardList fl1 = new FlashcardList(1L, "Spanish for beginners", new Date());
		FlashcardList fl2 = new FlashcardList(2L, "Java. Performance", new Date());
		FlashcardList fl3 = new FlashcardList(3L, "Java Concurrency in Practice", new Date());
		
		return Lists.newArrayList(fl1, fl2, fl3);
	}
}
