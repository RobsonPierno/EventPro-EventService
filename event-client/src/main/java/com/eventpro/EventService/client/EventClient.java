package com.eventpro.EventService.client;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.eventpro.EventService.dto.EventDTO;

@FeignClient(name = "event-service", url = "http://event:8081", path = "/event")
public interface EventClient {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody final EventDTO event);
	
	@GetMapping()
	public List<EventDTO> findAll(@RequestParam(required = false) final String status, 
			@RequestParam(required = false) final Integer organizerId, 
			@RequestParam(required = false) final LocalDate limitDate);
	
	@GetMapping("/{id}")
	public EventDTO findById(@PathVariable final Integer id);
	
	@GetMapping("/{id}/cancel")
	public EventDTO cancel(@PathVariable final Integer id);
	
	@GetMapping("/{id}/count")
	public Long countParticipants(@PathVariable final Integer id);
}
