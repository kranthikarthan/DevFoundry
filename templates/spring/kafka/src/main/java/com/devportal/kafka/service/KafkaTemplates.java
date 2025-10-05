package com.devportal.kafka.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTemplates {
  public static final String TOPIC = "devportal.templates";

  @Bean
  NewTopic topic() {
    return TopicBuilder.name(TOPIC).partitions(3).replicas(1).build();
  }
}
