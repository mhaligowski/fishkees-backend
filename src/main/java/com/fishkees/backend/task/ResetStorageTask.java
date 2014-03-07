package com.fishkees.backend.task;

import java.io.PrintWriter;

import javax.inject.Inject;

import com.fishkees.backend.modules.flashcards.dao.FlashcardInMemoryStorage;
import com.fishkees.backend.modules.lists.dao.inmemory.FlashcardListInMemoryStorage;
import com.google.common.collect.ImmutableMultimap;
import com.yammer.dropwizard.tasks.Task;

public class ResetStorageTask extends Task {

	@Inject
	private FlashcardListInMemoryStorage flashcardListStorage;
	
	@Inject
	private FlashcardInMemoryStorage flashcardStorage;
	
	public ResetStorageTask() {
		super("resetStorage");
	}
	
	@Override
	public void execute(ImmutableMultimap<String, String> parameters,
			PrintWriter output) throws Exception {
		printSize(output);
		doResetStorages();
		printSize(output);
	}

	private void printSize(PrintWriter output) {
		int listSizeAfter = flashcardListStorage.all().size();
		int flashcardSizeAfter = flashcardStorage.all().size();
		output.format("Lists storage resetted: %d\n", listSizeAfter);
		output.format("Flashcards storage resetted: %d\n", flashcardSizeAfter);
	}

	private void doResetStorages() {
		flashcardListStorage.reset();
		flashcardStorage.reset();
	}
}
