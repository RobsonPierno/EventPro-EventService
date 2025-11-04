package com.eventpro.EventService.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eventpro.EventService.dto.EventDTO;
import com.eventpro.EventService.enums.StatusEnum;
import com.eventpro.EventService.model.Event;
import com.eventpro.EventService.model.Organizer;
import com.eventpro.EventService.repository.EventRepository;
import com.eventpro.EventService.repository.EventSpecifications;
import com.eventpro.EventService.service.EventService;
import com.eventpro.EventService.utils.EventMapper;
import com.eventpro.OrganizerService.client.OrganizerClient;
import com.eventpro.OrganizerService.dto.OrganizerDTO;

@Service
public class EventServiceImpl implements EventService {
	
	private static final Logger log = LogManager.getLogger(EventServiceImpl.class);
	
	@Autowired
	private EventRepository repository;
	
	@Autowired
	private EventMapper mapper;
	
	@Autowired
	private OrganizerClient organizerClient;

	@Override
	public void create(final EventDTO eventDTO) {
		log.debug("create({})", eventDTO);
		
		OrganizerDTO organizerDTO = this.organizerClient.findById(eventDTO.organizerId());
		
		Event event = this.mapper.toEntity(eventDTO);
		
		Organizer org = event.getOrganizer();
		org.setName(organizerDTO.name());
		
		this.repository.save(event);
	}

	@Override
	public List<EventDTO> findAll(final String status, final Integer organizerId, final LocalDate limitDate) {
		log.debug("findAll({}, {}, {})", status, organizerId, limitDate);
		
        var spec = Specification.where(EventSpecifications.hasStatus(status))
                				.and(EventSpecifications.hasOrganizerId(organizerId))
                				.and(EventSpecifications.hasLimitDate(limitDate));

        return this.repository.findAll(spec).stream()
							                .map(mapper::toDto)
							                .toList();
	}

	@Override
	public EventDTO findById(final Integer id) {
		log.debug("findById({})", id);
		
		EventDTO event = this.repository.findById(id)
							.map(e -> this.mapper.toDto(e))
							.orElseThrow(RuntimeException::new);
		return event;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public EventDTO cancel(final Integer id) {
		log.debug("cancel({})", id);
		
		Event event = this.repository.findById(id).orElseThrow(RuntimeException::new);
		
		event.setStatus(StatusEnum.CANCELED);
		
		return this.mapper.toDto(event);
	}

}
