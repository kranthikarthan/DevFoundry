package com.example.kafka.web;

import com.example.kafka.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @PostMapping
  public ResponseEntity<Void> send(@RequestBody String body) {
    messageService.send(body);
    return ResponseEntity.accepted().build();
  }
}
