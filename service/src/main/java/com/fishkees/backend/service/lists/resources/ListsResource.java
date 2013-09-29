package com.fishkees.backend.service.lists.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fishkees.backend.api.lists.FlashcardList;

@Path("/flashcardlist")
@Produces(MediaType.APPLICATION_JSON)
public class ListsResource {

	@GET
	public FlashcardList get() {
		return new FlashcardList(1l, "abcd");
	}
}
