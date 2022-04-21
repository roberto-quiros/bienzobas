package com.bienzobas.springboot.app.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bienzobas.springboot.app.models.entity.JsonFile;
import com.bienzobas.springboot.app.models.entity.Metrics;
import com.bienzobas.springboot.app.service.validators.Validator;

@Service
public class PlatformServiceImpl implements IPlatformService {

	private static Log logger = LogFactory.getLog("PlatformServiceImpl");

	@Autowired
	private Metrics metrics;

	@Autowired
	private Validator validator;

	private void init() {
		this.metrics = new Metrics(3);

	}

	@Override
	public String getJsonFromUrl(String url) {

		String json = getJsonFromFile(url);
		JsonFile jsonFile = convertToJsonFile(url, json);

		this.metrics = new Metrics();

		for (String line : jsonFile.getContentFile()) {
			validator.validateFields(line, metrics);

		}

		return url;

	}

	public String getJsonFromFile(String url) {

		try {

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

			return new RestTemplate().getForObject(builder.toUriString(), String.class);
		} catch (Exception ioException) {
			logger.error(ioException);
			if (ioException instanceof HttpClientErrorException) {
				if (HttpStatus.NOT_FOUND.equals(((HttpClientErrorException) ioException).getStatusCode())) {
					logger.error(String.format("FILE NOT FOUND: %s", url));

				}
			}

			return "FILE NOT FOUND";

		}

	}

	private JsonFile convertToJsonFile(String url, String file) {

		final String[] lines = file.split("\n");

		final JsonFile jsonFile = new JsonFile(url, lines);

		return jsonFile;

	}

	@Override
	public Metrics metrics() {

		return this.metrics;
	}

}
