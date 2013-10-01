package com.fishkees.backend.healthcheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class URLWrapper {
	public BufferedReader getReader(String urlString) throws IOException {
		URL url = new URL(urlString);
		return new BufferedReader(new InputStreamReader(url.openStream()));
	}

}
