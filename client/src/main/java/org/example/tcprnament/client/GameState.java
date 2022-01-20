package org.example.tcprnament.client;

public enum GameState {
    SET_NICK("nick"),
    MENU("menu"),
    CREATE_GAME("create"),
    LOGIN_PANEL("login"),
    GAME_PANEL("game"),
    WAITING_ROOM("wait"),
    GAME_OVER("over");

    private final String text;

    GameState(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
