package com.fishkees.backend.modules.flashcards.resources;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.flashcards.dao.FlashcardDao;
import com.google.common.collect.Lists;

@Produces(MediaType.APPLICATION_JSON)
@Path("/flashcardlists/{listId}/flashcards")
public class FlashcardResource {
	@Inject
	private FlashcardDao flashcardDao;

	@GET
	public List<Flashcard> findAll(@PathParam("listId") String listId) {
		return Lists.newArrayList();
	}

	@GET
	@Path("/{flashcardId}")
	public Flashcard find(@PathParam("listId") String listId,
			@PathParam("flashcardId") String flashcardId) {
		return null;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@PathParam("listId") String listId,
			@Valid Flashcard flashcard) {
		if (!flashcard.getFlashcardListId().equals(listId)) {
			return Response.status(Response.Status.CONFLICT)
					.entity("Conflicting listIds").build();
		}
		
		final Flashcard newFlashcard = flashcardDao.createNewFromObject(flashcard);
		
		UriBuilder builder = UriBuilder.fromPath("/{listId}/flashcards/{cardId}");
		URI uri = builder.build(listId, newFlashcard.getId());
		
		return Response.created(uri).entity(newFlashcard).build();
	}

}
