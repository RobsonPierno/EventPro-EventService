package com.eventpro.EventService.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EventDTO(Integer id, String name, LocalDate startDate, LocalDate endDate, Integer maxPeople, String status, BigDecimal cost, BigDecimal amountCollected, Integer organizerId, Integer ticketId) {

}
