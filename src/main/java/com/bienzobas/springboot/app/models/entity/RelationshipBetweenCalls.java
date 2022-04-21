package com.bienzobas.springboot.app.models.entity;

import org.springframework.stereotype.Component;

@Component
public class RelationshipBetweenCalls {

	private int ok = 0;
	private int ko = 0;
	
	
	public RelationshipBetweenCalls() {
		super();
	}

	
	public RelationshipBetweenCalls(int ok, int ko) {
		this();
		this.ok = ok;
		this.ko = ko;
	}

	
	public int getOk() {
		return ok;
	}

	
	public void increaseOk() {
		++this.ok;
	}

	
	public int getKo() {
		return ko;
	}
	
	
	public void increaseKo() {
		++this.ko;
	}

}