package com.example.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@MessageEndpoint
@Slf4j
@RequiredArgsConstructor
@DependsOn("gateway")
public class TcpController {

    private final TournamentProtocolServerApplication tournamentProtocolServerApplication;

    @ServiceActivator(inputChannel = "fromTcp", outputChannel = "toTcp")
    public void handleMessage(Message<?> message) {
        byte[] bytes = (byte[]) message.getPayload();
        String connectionId = String.valueOf(message.getHeaders().get("ip_connectionId"));
        tournamentProtocolServerApplication.parse(bytes, connectionId);
    }
}
