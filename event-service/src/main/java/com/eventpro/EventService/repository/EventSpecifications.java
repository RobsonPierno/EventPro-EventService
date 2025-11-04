package com.eventpro.EventService.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.eventpro.EventService.enums.StatusEnum;
import com.eventpro.EventService.model.Event;

public class EventSpecifications {
	
    public static Specification<Event> hasStatus(String status) {
        return (root, query, cb) -> {
            if (status == null || status.isBlank()) return null;
            return cb.equal(root.get("status"), StatusEnum.valueOf(status.toUpperCase()));
        };
    }

    public static Specification<Event> hasOrganizerId(Integer organizerId) {
        return (root, query, cb) -> {
            if (organizerId == null) return null;
            return cb.equal(root.get("organizer").get("id"), organizerId);
        };
    }

    public static Specification<Event> hasLimitDate(LocalDate limitDate) {
        return (root, query, cb) -> {
            if (limitDate == null) return null;
            return cb.lessThanOrEqualTo(root.get("endDate"), limitDate);
        };
    }
}