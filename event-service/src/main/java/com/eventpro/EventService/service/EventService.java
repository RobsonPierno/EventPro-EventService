package com.eventpro.EventService.service;

import java.time.LocalDate;
import java.util.List;

import com.eventpro.EventService.dto.EventDTO;

public interface EventService {

	public void create(final EventDTO event);
	
	public List<EventDTO> findAll(final String status, final Integer organizerId, final LocalDate limitDate);
	
	public EventDTO findById(final Integer id);
	
	public EventDTO cancel(final Integer id);
	
	public Long countParticipants(final Integer id);
}
