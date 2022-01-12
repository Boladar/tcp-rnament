package com.example.server;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

/*
 * Sender gateway sets the connection id header.
 */
@MessagingGateway(defaultRequestChannel = "toTcp")
public interface Gateway {
    //void send(String in);
    void send(@Payload String data, @Header(IpHeaders.CONNECTION_ID) String connectionId);
}
