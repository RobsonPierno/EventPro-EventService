package com.eventpro.EventService.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import com.eventpro.AttendeeService.dto.CheckInDTO;

@Service
public class ParticipantCheckinStream {
	
	private static final Logger log = LogManager.getLogger(ParticipantCheckinStream.class);
	
	private static final String KTableName = "participants-per-event-store";
	
	private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

	@Autowired
    public ParticipantCheckinStream(StreamsBuilder builder, 
						    		StreamsBuilderFactoryBean streamsBuilderFactoryBean,
						            @Value("${kafka.topic.participant.checkedin}") String participantCheckedInTopic) {
    	
		this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
		
        JsonSerde<CheckInDTO> participantSerde = new JsonSerde<>(CheckInDTO.class);

        KStream<String, CheckInDTO> checkinStream = builder.stream(
            participantCheckedInTopic,
            Consumed.with(Serdes.String(), participantSerde)
        );

        KTable<String, Long> participantsPerEvent = checkinStream
            .groupBy(
                (key, value) -> value.eventId().toString(), Grouped.with(Serdes.String(), participantSerde)
            )
            .count(Materialized.as(KTableName));
        
        participantsPerEvent.toStream()
	        .peek((eventId, count) -> {
	            if (count == 100) {
	                log.info("Event reached 100 people. EventId: {}", eventId);
	            }
	        });
    }
	
	public Long getParticipantsCount(final Integer eventId) {
		ReadOnlyKeyValueStore<String, Long> store = streamsBuilderFactoryBean.getKafkaStreams()
																	.store(StoreQueryParameters.fromNameAndType(
															            KTableName,
															            QueryableStoreTypes.keyValueStore()
															        ));
		Long count = store.get(eventId.toString());
        return count != null ? count : 0L;
	}
}
