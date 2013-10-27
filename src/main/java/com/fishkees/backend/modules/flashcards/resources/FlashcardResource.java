package com.fishkees.backend.modules.flashcards.resources;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.fishkees.backend.modules.flashcards.core.Flashcard;
import com.fishkees.backend.modules.flashcards.dao.FlashcardDao;
import com.google.common.base.Optional;

@Produces(MediaType.APPLICATION_JSON)
@Path("/flashcardlists/{listId}/flashcards")
public class FlashcardResource {
	private static final String CONFLICT_ID = "Flashcard id from URL does not match the one from body";
	private static final String CONFLICT_LIST = "The flashcard list id from URL does not match the one from entity";
	@Inject
	private FlashcardDao flashcardDao;

	@GET
	public Response findAll(@PathParam("listId") String listId) {
		List<Flashcard> flashcards = flashcardDao.findAllByListId(listId);

		if (flashcards == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok(flashcards).build();
		}
	}

	@GET
	@Path("/{flashcardId}")
	public Response find(@PathParam("listId") String listId,
			@PathParam("flashcardId") String flashcardId) {
		Optional<Flashcard> flashcard = flashcardDao.findByListIdAndId(listId,
				flashcardId);

		if (flashcard.isPresent()) {
			return Response.ok(flashcard.get()).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@PathParam("listId") String listId,
			@Valid Flashcard flashcard) {
		if (!flashcard.getFlashcardListId().equals(listId)) {
			return Response.status(Response.Status.CONFLICT)
					.entity("Conflicting listIds").build();
		}

		final Flashcard newFlashcard = flashcardDao
				.createNewFromObject(flashcard);

		UriBuilder builder = UriBuilder.fromPath("/{cardId}");
		URI uri = builder.build(newFlashcard.getId());

		return Response.created(uri).entity(newFlashcard).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{flashcardId}")
	public Response update(@PathParam("listId") String listId,
			@PathParam("flashcardId") String flashcardId,
			@Valid Flashcard toUpdate) {
		if (!listId.equals(toUpdate.getFlashcardListId())) {
			return Response.status(Status.CONFLICT).entity(CONFLICT_LIST)
					.build();
		}

		if (!flashcardId.equals(toUpdate.getId())) {
			return Response.status(Status.CONFLICT).entity(CONFLICT_ID).build();
		}

		Flashcard updated = flashcardDao.update(toUpdate);

		if (updated == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(updated).build();
	}

	@DELETE
	@Path("/{flashcardId}")
	public Response remove(@PathParam("listId") String listId,
			@PathParam("flashcardId") String flashcardId) {
		Flashcard removed = flashcardDao.removeByListIdAndId(listId, flashcardId);

		if (removed == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(removed).build();
	}
}
