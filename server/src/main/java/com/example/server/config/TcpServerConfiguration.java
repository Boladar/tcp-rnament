package com.example.server.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.TcpSendingMessageHandler;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.TcpCodecs;
import org.springframework.messaging.MessageChannel;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableIntegration
public class TcpServerConfiguration implements ApplicationListener<TcpConnectionEvent> {

    private final TcpServerProperties tcpServerProperties;

    @Bean
    public AbstractServerConnectionFactory tcpServer() {
        log.info("Starting TCP server with port: {}", tcpServerProperties.getPort());
        TcpNetServerConnectionFactory serverCf = new TcpNetServerConnectionFactory(tcpServerProperties.getPort());
        serverCf.setSerializer(TcpCodecs.crlf());
        serverCf.setDeserializer(TcpCodecs.crlf());
        serverCf.setSoTcpNoDelay(true);
        serverCf.setSoKeepAlive(true);
        return serverCf;
    }

    @Bean
    MessageChannel toTcp() {
        log.info("creating toTcp DirectChannel");
        DirectChannel dc = new DirectChannel();
        dc.setBeanName("toTcp");

        return dc;
    }

    @Bean
    public MessageChannel fromTcp() {
        log.info("creating fromTcp DirectChannel");
        DirectChannel dc = new DirectChannel();
        dc.setBeanName("fromTcp");

        return dc;
    }


    // Inbound channel adapter. This receives the data from the client
    @Bean
    public TcpReceivingChannelAdapter inboundAdapter(AbstractServerConnectionFactory connectionFactory) {
        log.info("Creating inbound adapter");
        TcpReceivingChannelAdapter inbound = new TcpReceivingChannelAdapter();

        inbound.setConnectionFactory(connectionFactory);
        inbound.setOutputChannel(fromTcp());

        return inbound;
    }

    // Outbound channel adapter. This sends the data to the client
    @Bean
    @ServiceActivator(inputChannel = "toTcp")
    public TcpSendingMessageHandler outboundAdapter(AbstractServerConnectionFactory connectionFactory) {
        log.info("Creating outbound adapter");
        TcpSendingMessageHandler outbound = new TcpSendingMessageHandler();
        outbound.setConnectionFactory(connectionFactory);
        return outbound;
    }

    @Override
    public void onApplicationEvent(TcpConnectionEvent event) {
        log.info("Received TcpConnectionEvent, {}", event);
    }
}
