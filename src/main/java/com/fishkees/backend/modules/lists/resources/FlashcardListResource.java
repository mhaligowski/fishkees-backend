package com.fishkees.backend.modules.lists.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;

@Path("/flashcardlists")
@Produces(MediaType.APPLICATION_JSON)
public class FlashcardListResource {
	@Inject
	private FlashcardListDao flashcardListDao;
	
	@GET
	public List<FlashcardList> getFlashcardLists() {
		return flashcardListDao.findAll();
	}
}
