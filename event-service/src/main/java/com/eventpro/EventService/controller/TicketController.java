package com.eventpro.EventService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventpro.EventService.client.TicketClient;
import com.eventpro.EventService.dto.TicketDTO;
import com.eventpro.EventService.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController implements TicketClient {
	
	@Autowired
	public TicketService service;

	@Override
	public void create(TicketDTO ticket) {
		this.service.create(ticket);
	}

	@Override
	public TicketDTO findById(Integer id) {
		TicketDTO ticket = this.service.findById(id);
		return ticket;
	}

	@Override
	public List<TicketDTO> findAll(Integer eventId) {
		List<TicketDTO> tickets = this.service.findAll(eventId);
		return tickets;
	}

}
