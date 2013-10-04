package com.fishkees.backend.modules.lists.resources;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;

@Path("/flashcardlists")
public class FlashcardListResource {
	@Inject
	private FlashcardListDao flashcardListDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<FlashcardList> getFlashcardLists() {
		return flashcardListDao.findAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(@Valid FlashcardList flashcardList) {
		final FlashcardList newFlashcardList = flashcardListDao.createNewFromObject(flashcardList);
		
		UriBuilder builder = UriBuilder.fromResource(FlashcardListResource.class);
		URI uri = builder.build(newFlashcardList.getId());
		Response response = Response.created(uri).build(); 
		
		return response;
	}
}
