package com.paytm.baymax.ws.SampleWebfluxWebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableWebFlux
public class SampleWebfluxWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleWebfluxWebsocketApplication.class, args);
    }

    @Bean
    WebSocketService webSocketService() {
        return new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy());
    }

    @Bean
    WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter(webSocketService());
    }

    @Bean
    HandlerMapping handlerMapping() {
        final Map<String, WebSocketHandler> handlerMappings = new HashMap<>();
        handlerMappings.put("/ws/hello", new HellowebsocketHandler());

        final SimpleUrlHandlerMapping urlHandlerMapping = new SimpleUrlHandlerMapping();
        urlHandlerMapping.setUrlMap(handlerMappings);
        urlHandlerMapping.setOrder(-1);
        return urlHandlerMapping;
    }
}
