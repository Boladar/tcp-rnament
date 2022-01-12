package org.example.tcprnament.shared.commands;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Command implements Serializable {
    protected CommandSide side;
}
