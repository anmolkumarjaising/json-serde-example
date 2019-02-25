package com.example.jsonserdeexample;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class Sender {

	public static void main(String[] args) {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);


		DefaultKafkaProducerFactory<UUID, Account> pf = new DefaultKafkaProducerFactory<>(props);
		KafkaTemplate<UUID, Account> template = new KafkaTemplate<>(pf, true);
		template.setDefaultTopic("data-in");

		Account account = new Account();
		account.setId(123);
		account.setName("foobar");
		template.sendDefault(UUID.randomUUID(), account);
	}


}
