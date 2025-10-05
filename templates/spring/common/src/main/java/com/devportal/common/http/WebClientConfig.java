package com.devportal.common.http;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.boot.web.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class WebClientConfig {
  @Bean
  public WebClient webClient() {
    ConnectionProvider provider = ConnectionProvider.builder("webclient")
        .maxConnections(200)
        .pendingAcquireMaxCount(1000)
        .maxIdleTime(Duration.ofSeconds(30))
        .build();

    HttpClient httpClient = HttpClient.create(provider)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
        .responseTimeout(Duration.ofSeconds(5))
        .doOnConnected(conn -> conn
            .addHandlerLast(new ReadTimeoutHandler(5))
            .addHandlerLast(new WriteTimeoutHandler(5)));

    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(cfg -> cfg.defaultCodecs().maxInMemorySize(8 * 1024 * 1024))
            .build())
        .build();
  }
}
