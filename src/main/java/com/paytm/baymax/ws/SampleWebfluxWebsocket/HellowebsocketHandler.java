package com.paytm.baymax.ws.SampleWebfluxWebsocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Slf4j
public class HellowebsocketHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(final WebSocketSession webSocketSession) {
        log.info("new connection: {}", webSocketSession.getId());
        final Flux<WebSocketMessage> result =
                webSocketSession
                        .receive()
                        .delaySubscription(Duration.ofSeconds(2L))
                        .map(WebSocketMessage::getPayloadAsText)
                        .flatMap(text -> this.handle(text, webSocketSession.bufferFactory()));
        return webSocketSession.send(result);
    }

    private Mono<WebSocketMessage> handle(final String input, final DataBufferFactory factory) {
        final String message = "Hello, " + input;
        log.info("coming: {}, going: {}", input, message);
        final DataBuffer buffer = factory.wrap(message.getBytes(StandardCharsets.UTF_8));
        final WebSocketMessage webSocketMessage =
                new WebSocketMessage(WebSocketMessage.Type.TEXT, buffer);
        return Mono.just(webSocketMessage);
    }

}
