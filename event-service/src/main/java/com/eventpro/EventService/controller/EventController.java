package com.eventpro.EventService.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventpro.EventService.client.EventClient;
import com.eventpro.EventService.dto.EventDTO;
import com.eventpro.EventService.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController implements EventClient {
	
	@Autowired
	private EventService service;

	@Override
	public void create(EventDTO event) {
		this.service.create(event);
	}

	@Override
	public List<EventDTO> findAll(String status, Integer organizerId, LocalDate limitDate) {
		List<EventDTO> events = this.service.findAll(status, organizerId, limitDate);
		return events;
	}

	@Override
	public EventDTO findById(Integer id) {
		EventDTO event = this.service.findById(id);
		return event;
	}

	@Override
	public EventDTO cancel(Integer id) {
		EventDTO event = this.service.cancel(id);
		return event;
	}

}
