package com.paytm.baymax.ws.SampleWebfluxWebsocket.client;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

public class SampleWebsocketClient {
    public static void main(String[] args) throws InterruptedException {
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
                URI.create("ws://localhost:8080/ws/hello"),
                session -> session.send(
                        Mono.just(session.textMessage("from client"))
                ).thenMany(session.receive()
                                .map(WebSocketMessage::getPayloadAsText)
                                .log()
                ).then()
                ).block(Duration.ofSeconds(10L)
        );
    }
}
