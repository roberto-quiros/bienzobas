package com.bienzobas.springboot.app.service;

import com.bienzobas.springboot.app.models.entity.Metrics;

public interface IPlatformService {

	public String getJsonFromUrl(String url);

	public Metrics metrics();
	
}
