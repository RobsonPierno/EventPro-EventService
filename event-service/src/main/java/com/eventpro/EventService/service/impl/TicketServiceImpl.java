package com.eventpro.EventService.service.impl;

import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventpro.EventService.dto.TicketDTO;
import com.eventpro.EventService.model.Ticket;
import com.eventpro.EventService.repository.TicketRepository;
import com.eventpro.EventService.service.EventService;
import com.eventpro.EventService.service.TicketService;
import com.eventpro.EventService.utils.TicketMapper;

@Service
public class TicketServiceImpl implements TicketService {
	
	private static final Logger log = LogManager.getLogger(TicketServiceImpl.class);

	@Autowired
	public TicketRepository repository;
	
	@Autowired
	public EventService eventService;
	
	@Autowired
	public TicketMapper mapper;
	
	@Override
	public void create(TicketDTO ticketDto) {
		log.debug("create({})", ticketDto);
		
		this.eventService.findById(ticketDto.eventId());
		
		Ticket ticket = this.mapper.toEntity(ticketDto);
		
		this.repository.save(ticket);
	}

	@Override
	public TicketDTO findById(Integer id) {
		log.debug("findById({})", id);
		
		Ticket ticket = this.repository.findById(id).orElseThrow(RuntimeException::new);
		TicketDTO ticketDto = this.mapper.toDto(ticket);
		
		return ticketDto;
	}

	@Override
	public List<TicketDTO> findAll(Integer eventId) {
		log.debug("findAll({})", eventId);
		
		List<TicketDTO> tickets;
		Function<Ticket, TicketDTO> fromTicketToDTO = t -> this.mapper.toDto(t);
		
		if (eventId != null) {
			tickets = this.repository.findAllByEventId(eventId).stream().map(fromTicketToDTO).toList();
			return tickets;
		}
		
		tickets = this.repository.findAll().stream().map(fromTicketToDTO).toList();
		return tickets;
	}

}
