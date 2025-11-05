package com.eventpro.EventService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eventpro.EventService.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	List<Ticket> findAllByEventId(Integer eventId);

}
