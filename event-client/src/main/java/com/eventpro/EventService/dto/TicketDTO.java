package com.eventpro.EventService.dto;

import java.math.BigDecimal;

public record TicketDTO(Integer id, Integer eventId, String type, BigDecimal price) {

}
