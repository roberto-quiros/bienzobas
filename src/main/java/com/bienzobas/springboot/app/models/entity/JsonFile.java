package com.bienzobas.springboot.app.models.entity;

import org.springframework.stereotype.Component;

@Component
public class JsonFile {

	private String filename;
	
	private String[] contentFile;

	public JsonFile(String filename, String[] contentFile) {
		super();
		this.filename = filename;
		this.contentFile = contentFile;
	}

	public JsonFile() {
		super();
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String[] getContentFile() {
		return contentFile;
	}

	public void setContentFile(String[] contentFile) {
		this.contentFile = contentFile;
	}
	
}
