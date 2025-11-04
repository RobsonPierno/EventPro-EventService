package com.eventpro.EventService.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record EventDTO(Integer id, String name, LocalDate startDate, LocalDate endDate, Integer maxPeople, String status, BigDecimal cost, BigDecimal amountCollected, Integer organizerId, List<Integer> ticketsIds) {

}
