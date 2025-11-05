package com.eventpro.EventService.service;

import java.util.List;

import com.eventpro.EventService.dto.TicketDTO;

public interface TicketService {

	public void create(TicketDTO ticket);
	
	public TicketDTO findById(Integer id);
	
	public List<TicketDTO> findAll(Integer eventId);
}
