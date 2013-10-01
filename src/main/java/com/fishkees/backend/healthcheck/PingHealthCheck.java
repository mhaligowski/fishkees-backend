package com.fishkees.backend.healthcheck;

import java.io.BufferedReader;

import javax.inject.Inject;
import javax.inject.Named;

import com.yammer.metrics.core.HealthCheck;

public class PingHealthCheck extends HealthCheck {
	private static final String HEALTHCHECK_NAME = "ping";

	private String urlString;
	private URLWrapper urlWrapper;

	@Inject 
	public PingHealthCheck(@Named("url") String urlString, URLWrapper urlWrapper) {
		super(HEALTHCHECK_NAME);
		this.urlString = urlString;
		this.urlWrapper = urlWrapper;
	}

	@Override
	protected Result check() {
		try {
			BufferedReader reader = urlWrapper.getReader(urlString);
			while (reader.readLine() != null)
				;
		} catch (Exception e) {
			return Result.unhealthy(e);
		}

		return Result.healthy();
	}
}
