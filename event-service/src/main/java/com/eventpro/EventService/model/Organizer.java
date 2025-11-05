package com.eventpro.EventService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Organizer {
	
	public Organizer() { }
	
	public Organizer(final Integer id, final String name) {
		this.id = id;
		this.name = name;
	}
	
	@Column(name = "organizer_id")
	private Integer id;
	
	@Column(name = "organizer_name")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}