package com.eventpro.EventService.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.eventpro.EventService.dto.TicketDTO;

@FeignClient(name = "ticket-service", url = "http://event:8081", path = "/ticket")
public interface TicketClient {

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public void create(@RequestBody final TicketDTO ticket);
	
	@GetMapping("/{id}")
	public TicketDTO findById(@PathVariable final Integer id);
	
	@GetMapping
	public List<TicketDTO> findAll(@RequestParam(required = false) final Integer eventId);
	
}
