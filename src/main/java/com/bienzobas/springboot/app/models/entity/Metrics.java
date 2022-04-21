package com.bienzobas.springboot.app.models.entity;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Metrics {

	private int rowsWithMissingFields = 0;

	private int messagesWithBlank = 0;

	private int rowsWithErrors = 0;

	private HashMap<String, Integer> CallsOriginGroupedByCountry = new HashMap<String, Integer>();

	@Autowired
	private RelationshipBetweenCalls relationshipBetweenCalls;

	private int AverageCallsDuration;

	private String filename;

	public Metrics() {
		super();
	}

	public Metrics(int rowsWithMissingFields) {
		super();
		this.rowsWithMissingFields = rowsWithMissingFields;
	}

	public int getRowsWithMissingFields() {
		return rowsWithMissingFields;
	}

	public void setRowsWithMissingFields(int rowsWithMissingFields) {
		this.rowsWithMissingFields = rowsWithMissingFields;
	}
	
	public void  incrementsMessagesWithBlank() {
		this.messagesWithBlank++;
	}
	
	public void  incrementsRowsWithErrors() {
		++this.rowsWithErrors;
	}

	public int getMessagesWithBlank() {
		return messagesWithBlank;
	}

	public void setMessagesWithBlank(int messagesWithBlank) {
		this.messagesWithBlank = messagesWithBlank;
	}

	public int getRowsWithErrors() {
		return rowsWithErrors;
	}

	public void setRowsWithErrors(int rowsWithErrors) {
		this.rowsWithErrors = rowsWithErrors;
	}

	public HashMap<String, Integer> getCallsOriginGroupedByCountry() {
		return CallsOriginGroupedByCountry;
	}

	public void setCallsOriginGroupedByCountry(HashMap<String, Integer> callsOriginGroupedByCountry) {
		CallsOriginGroupedByCountry = callsOriginGroupedByCountry;
	}

	public RelationshipBetweenCalls getRelationshipBetweenCalls() {
		return relationshipBetweenCalls;
	}

	public void setRelationshipBetweenCalls(RelationshipBetweenCalls relationshipBetweenCalls) {
		this.relationshipBetweenCalls = relationshipBetweenCalls;
	}

	public int getAverageCallsDuration() {
		return AverageCallsDuration;
	}

	public void setAverageCallsDuration(int averageCallsDuration) {
		AverageCallsDuration = averageCallsDuration;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
