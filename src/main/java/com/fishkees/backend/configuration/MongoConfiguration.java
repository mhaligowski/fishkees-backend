package com.fishkees.backend.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class MongoConfiguration extends Configuration {
	@JsonProperty
	private String host;

	@JsonProperty
	private int port;

	@JsonProperty
	private String username;

	@JsonProperty
	private String password;

	@JsonProperty
	private String db;
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getDb() {
		return db;
	}

}
