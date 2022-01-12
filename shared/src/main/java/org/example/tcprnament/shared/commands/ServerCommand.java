package org.example.tcprnament.shared.commands;

public class ServerCommand extends Command {
    protected ServerCommandType type;

    public ServerCommand(ServerCommandType type) {
        super(CommandSide.SERVER);
        this.type = type;
    }
}
