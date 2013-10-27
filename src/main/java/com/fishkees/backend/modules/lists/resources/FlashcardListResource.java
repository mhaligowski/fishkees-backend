package com.fishkees.backend.modules.lists.resources;

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

import com.fishkees.backend.modules.lists.core.FlashcardList;
import com.fishkees.backend.modules.lists.dao.FlashcardListDao;
import com.google.common.base.Optional;

@Produces(MediaType.APPLICATION_JSON)
@Path("/flashcardlists")
public class FlashcardListResource {
	@Inject
	private FlashcardListDao flashcardListDao;

	@GET
	public List<FlashcardList> findAll() {
		return flashcardListDao.findAll();
	}

	@GET
	@Path("/{listId}")
	public Response find(@PathParam("listId") String listId) {
		Optional<FlashcardList> flashcardList = flashcardListDao
				.findById(listId);

		if (flashcardList.isPresent()) {
			return Response.ok(flashcardList.get()).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(@Valid FlashcardList flashcardList) {
		final FlashcardList newFlashcardList = flashcardListDao
				.createNewFromObject(flashcardList);

		UriBuilder builder = UriBuilder.fromPath("/{listId}");
		URI uri = builder.build(newFlashcardList.getId());
		Response response = Response.created(uri).entity(newFlashcardList)
				.build();

		return response;
	}

	@DELETE
	@Path("/{listId}")
	public Response remove(@PathParam("listId") String id) {
		Optional<FlashcardList> removed = flashcardListDao.remove(id);

		if (removed.isPresent()) {
			return Response.ok(removed.get()).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{listId}")
	public Response update(@PathParam("listId") String id,
			@Valid FlashcardList flashcardList) {
		if (!id.equals(flashcardList.getId())) {
			return Response.status(Response.Status.CONFLICT)
					.entity("Conflicting ids").build();
		}

		Optional<FlashcardList> updated = flashcardListDao
				.update(flashcardList);

		if (updated.isPresent()) {
			return Response.ok(updated.get()).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}
