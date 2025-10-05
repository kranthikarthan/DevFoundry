package com.devportal.postgres.service;

import com.devportal.postgres.entity.UserEntity;
import com.devportal.postgres.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public UserEntity upsertUser(String email, String name) {
    return userRepository.findByEmail(email)
        .map(u -> { u.setName(name); return userRepository.save(u); })
        .orElseGet(() -> {
          UserEntity e = new UserEntity();
          e.setEmail(email);
          e.setName(name);
          return userRepository.save(e);
        });
  }
}
