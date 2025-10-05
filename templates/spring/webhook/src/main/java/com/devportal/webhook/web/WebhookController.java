package com.devportal.webhook.web;

import com.devportal.webhook.service.SignatureVerifier;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhooks")
public class WebhookController {
  private final SignatureVerifier verifier = new SignatureVerifier(System.getenv().getOrDefault("WEBHOOK_SECRET", "devsecret"));

  @PostMapping("/example")
  public ResponseEntity<Map<String, Object>> handle(
      @RequestHeader(name = "X-Signature", required = false) String signature,
      @RequestBody @NotBlank String payload) {
    if (signature == null || !verifier.verify(payload, signature)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "invalid_signature"));
    }
    return ResponseEntity.accepted().body(Map.of("status", "accepted"));
  }
}
