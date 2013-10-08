package com.fishkees.backend.task;

import java.io.PrintWriter;

import javax.inject.Inject;

import com.fishkees.backend.modules.lists.dao.FlashcardListInMemoryStorage;
import com.google.common.collect.ImmutableMultimap;
import com.yammer.dropwizard.tasks.Task;

public class ResetStorageTask extends Task {

	@Inject
	private FlashcardListInMemoryStorage storage;
	
	public ResetStorageTask() {
		super("resetStorage");
	}
	
	@Override
	public void execute(ImmutableMultimap<String, String> parameters,
			PrintWriter output) throws Exception {
		int sizeBefore = storage.all().size();
		output.println("Resetting the storage: " + sizeBefore);
		
		storage.reset();
		
		int sizeAfter = storage.all().size();
		output.println("Storage resetted: " + sizeAfter);
	}

}
