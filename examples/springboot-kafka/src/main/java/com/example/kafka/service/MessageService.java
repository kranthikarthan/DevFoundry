package com.example.kafka.service;

import com.example.kafka.config.KafkaConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
  private final KafkaTemplate<String, String> kafkaTemplate;

  public MessageService(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(String message) {
    kafkaTemplate.send(KafkaConfig.TOPIC_NAME, message);
  }

  @KafkaListener(topics = KafkaConfig.TOPIC_NAME, groupId = "devportal-example")
  public void listen(String message) {
    System.out.println("Received message: " + message);
  }
}
