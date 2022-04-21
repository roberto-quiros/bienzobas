package com.bienzobas.springboot.app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bienzobas.springboot.app.models.entity.Metrics;
import com.bienzobas.springboot.app.service.IPlatformService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController
public class ApiController {

	@Autowired
	private IPlatformService iPlatformService;

	private static Log logger = LogFactory.getLog("ApiController");

	private static Properties p = null;

	@GetMapping("hello")
	public String hello() {
		return "Hola mundo";
	}

	@RequestMapping(value = "/test1/loadFile/{date}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> loadFile(@PathVariable("date") String date) {

		String url = "";

		try {
			url = buildFilename(date);
			iPlatformService.getJsonFromUrl(url);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("HTTP endpoint (/test1/loadfile/{date})...");
		}

		return ResponseEntity.ok(url);

	}

	@RequestMapping(value = "/test1/metrics", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> metrics() {

		final Metrics metrics = iPlatformService.metrics();

		final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		final String response = prettyGson.toJson(metrics);

		if (logger.isDebugEnabled()) {
			logger.debug("HTTP endpoint (/test1/loadfile/{date})...");
		}

		return ResponseEntity.ok(response);

	}

	private String buildFilename(String date) throws IOException {

		p = new Properties();
		InputStream propertiesStream = ClassLoader.getSystemResourceAsStream("file.properties");
		p.load(propertiesStream);
		propertiesStream.close();


		return p.getProperty("file.url").concat(p.getProperty("file.pttrn")).concat(date)
				.concat(p.getProperty("file.extension"));

	}

}
