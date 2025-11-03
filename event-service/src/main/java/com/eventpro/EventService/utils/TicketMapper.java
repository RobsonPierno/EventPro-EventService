package com.eventpro.EventService.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eventpro.EventService.dto.TicketDTO;
import com.eventpro.EventService.model.Ticket;

@Mapper(componentModel = "spring", 
	imports = {
        com.eventpro.EventService.enums.TicketType.class,
        com.eventpro.EventService.model.Event.class
    })
public interface TicketMapper {

    @Mapping(target = "type", expression = "java(dto.type() != null ? TicketType.valueOf(dto.type().toUpperCase()) : null)")
    @Mapping(target = "event", expression = "java(dto.eventId() != null ? new Event(dto.eventId()) : null)")
    Ticket toEntity(TicketDTO dto);

    @Mapping(target = "type", expression = "java(entity.getType() != null ? entity.getType().name() : null)")
    @Mapping(target = "eventId", expression = "java(entity.getEvent() != null ? entity.getEvent().getId() : null)")
    TicketDTO toDto(Ticket entity);
}
