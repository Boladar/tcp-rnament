package org.example.tcprnament.shared.commands.server.concrete;

import lombok.Getter;
import lombok.Setter;
import org.example.tcprnament.shared.commands.client.ClientCommand;
import org.example.tcprnament.shared.commands.client.ClientCommandType;
import org.example.tcprnament.shared.commands.server.ServerCommand;
import org.example.tcprnament.shared.commands.server.ServerCommandType;

import java.util.List;

@Getter
@Setter
public class ShowPlayersListCommand extends ServerCommand {
    private List<String> players;
 
    public ShowPlayersListCommand(List<String> players) {
        super(ServerCommandType.SHOW_PLAYERS_LIST);
        this.players = players;
    }

}
