package com.devportal.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessagingService {
  private final KafkaTemplate<String, String> kafkaTemplate;

  public KafkaMessagingService(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publish(String message) {
    kafkaTemplate.send(KafkaTemplates.TOPIC, message);
  }

  @KafkaListener(topics = KafkaTemplates.TOPIC, groupId = "devportal-templates")
  public void consume(String message) {
    // For template purposes, simply log
    System.out.println("Kafka consumed: " + message);
  }
}
