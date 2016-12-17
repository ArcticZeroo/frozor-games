package frozor.events;

import org.bukkit.entity.Player;

public class CustomPlayerSpawnEvent extends CustomEvent {
    private Player player;

    public CustomPlayerSpawnEvent(Player player) {
        super("");
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
