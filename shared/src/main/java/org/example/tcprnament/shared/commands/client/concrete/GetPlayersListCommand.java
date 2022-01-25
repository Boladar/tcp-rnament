package org.example.tcprnament.shared.commands.client.concrete;

import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandType;

import java.util.List;

public class GetPlayersListCommand extends ClientCommand {
    private List<String> players;

    public GetPlayersListCommand(Long id, List<String> players) {
        super(id, ClientCommandType.GET_PLAYERS);
        this.players = players;
    }
}
