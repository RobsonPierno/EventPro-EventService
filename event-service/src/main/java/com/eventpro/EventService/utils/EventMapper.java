package com.eventpro.EventService.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eventpro.EventService.dto.EventDTO;
import com.eventpro.EventService.model.Event;

@Mapper(componentModel = "spring", 
	imports = {
        com.eventpro.EventService.enums.StatusEnum.class,
        com.eventpro.EventService.model.Organizer.class,
        com.eventpro.EventService.model.Ticket.class
    })
public interface EventMapper {

    @Mapping(target = "status", expression = "java(dto.status() != null ? StatusEnum.valueOf(dto.status().toUpperCase()) : null)")
    @Mapping(target = "organizer", expression = "java(dto.organizerId() != null ? new Organizer(dto.organizerId()) : null)")
    @Mapping(target = "tickets", expression = "java(dto.ticketsIds() != null ? dto.ticketsIds().stream().map(Ticket::new).toList() : new java.util.ArrayList<>())")
    Event toEntity(EventDTO dto);

    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    @Mapping(target = "organizerId", expression = "java(entity.getOrganizer() != null ? entity.getOrganizer().getId() : null)")
    @Mapping(target = "ticketsIds", expression = "java(entity.getTickets() != null ? entity.getTickets().stream().map(Ticket::getId).toList() : new java.util.ArrayList<>())")
    EventDTO toDto(Event entity);
}
