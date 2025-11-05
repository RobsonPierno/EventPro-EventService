package com.eventpro.EventService.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.eventpro.EventService.dto.EventDTO;
import com.eventpro.EventService.model.Event;

@Mapper(componentModel = "spring", 
	uses = com.eventpro.EventService.utils.TicketMapper.class,
	imports = {
        com.eventpro.EventService.enums.StatusEnum.class,
        com.eventpro.EventService.model.Organizer.class
    })
public interface EventMapper {

    @Mapping(target = "status", expression = "java(dto.status() != null ? StatusEnum.valueOf(dto.status().toUpperCase()) : null)")
    @Mapping(target = "organizer", expression = "java(dto.organizerId() != null ? new Organizer(dto.organizerId(), dto.organizerName()) : null)")
    Event toEntity(EventDTO dto);

    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? entity.getStatus().name() : null)")
    @Mapping(target = "organizerId", expression = "java(entity.getOrganizer() != null ? entity.getOrganizer().getId() : null)")
    @Mapping(target = "organizerName", expression = "java(entity.getOrganizer() != null ? entity.getOrganizer().getName() : null)")
    EventDTO toDto(Event entity);
}
