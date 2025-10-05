package com.devportal.webhook.service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class SignatureVerifier {
  private final byte[] secret;

  public SignatureVerifier(String secret) {
    this.secret = secret.getBytes(StandardCharsets.UTF_8);
  }

  public boolean verify(String payload, String signatureHeader) {
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(secret, "HmacSHA256"));
      byte[] digest = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
      String expected = HexFormat.of().formatHex(digest);
      return expected.equalsIgnoreCase(signatureHeader);
    } catch (Exception e) {
      return false;
    }
  }
}
