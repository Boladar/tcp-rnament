package org.example.tcprnament.shared.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGameCommand extends Command {
    private String name;
    private String password;
}
