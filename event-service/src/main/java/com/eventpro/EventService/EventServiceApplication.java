package com.eventpro.EventService;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.eventpro.EventService.dto.AlertDTO;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients(basePackages = "com.eventpro.OrganizerService.client")
@EnableKafkaStreams
public class EventServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventServiceApplication.class, args);
	}

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaBootsrap;
	
    @Bean(name = org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "participant-checkin-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaBootsrap);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put("spring.json.trusted.packages", "com.eventpro.AttendeeService.dto");
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
        return new KafkaStreamsConfiguration(props);
    }
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	
    @Bean
    public ConsumerFactory<String, AlertDTO> alertCF() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaBootsrap);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        
        JsonDeserializer<AlertDTO> deserializer = new JsonDeserializer<>(AlertDTO.class);
        deserializer.addTrustedPackages("com.eventpro.EventService.dto");
        
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AlertDTO> alertKafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, AlertDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.alertCF());
        return factory;
    }
}
