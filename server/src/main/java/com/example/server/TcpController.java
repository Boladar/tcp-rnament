package com.example.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@MessageEndpoint
@Slf4j
@RequiredArgsConstructor
@DependsOn("gateway")
public class TcpController {

    private final AbstractServerConnectionFactory connectionFactory;
    private final Gateway gateway;

    @ServiceActivator(inputChannel = "fromTcp", outputChannel = "toTcp")
    public byte[] handleMessage(Message<?> message) {
        String text = new String((byte[]) message.getPayload(), StandardCharsets.UTF_8);
        log.info("Incoming msg : [body: {}]", text);

        if (text.equals("1"))
            connectionFactory.getOpenConnectionIds().forEach(s -> gateway.send("DUPA", s));

        return "ACK".getBytes(StandardCharsets.UTF_8);
    }
}
