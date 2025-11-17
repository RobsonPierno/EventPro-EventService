package com.eventpro.EventService.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.eventpro.EventService.dto.AlertDTO;

@Component
public class KafkaConsumer {
	
	private static final Logger log = LogManager.getLogger(KafkaConsumer.class);

	@KafkaListener(
			topics = "${kafka.topic.alert}",
			containerFactory = "alertKafkaListener"
	)
	public void alert(final AlertDTO alert) {
		log.info("{}!!!!\n{}", alert.title(), alert.description());
	}
}
