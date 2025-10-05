package com.devportal.redis.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
  @Cacheable(cacheNames = "greetings", key = "#name")
  public String getGreeting(String name) {
    return "Hello, " + name + "!";
  }
}
