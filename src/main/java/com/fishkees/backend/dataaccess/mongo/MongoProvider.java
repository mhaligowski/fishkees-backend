package com.fishkees.backend.dataaccess.mongo;

import java.net.UnknownHostException;

import javax.inject.Inject;
import javax.inject.Provider;

import com.fishkees.backend.configuration.MongoConfiguration;
import com.google.common.collect.Lists;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoProvider implements Provider<Mongo> {

	@Inject
	private MongoConfiguration conf;

	@Override
	public Mongo get() {
		final int port = conf.getPort();
		final String host = conf.getHost();

		final String username = conf.getUsername();
		final String db = conf.getDb();
		final char[] password = conf.getPassword().toCharArray();

		try {
			ServerAddress serverAddress = new ServerAddress(host, port);
			MongoCredential credential = MongoCredential
					.createMongoCRCredential(username, db, password);

			return new MongoClient(Lists.newArrayList(serverAddress),
					Lists.newArrayList(credential));
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}
}
