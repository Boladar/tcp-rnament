package com.example.server.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("server.tcp")
@Getter
@Setter
public class TcpServerProperties {
    private int port;
}
