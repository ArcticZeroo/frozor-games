package frozor.events;

import org.bukkit.entity.Player;

public class CustomPlayerDeathEvent extends CustomEvent {
    private Player player;
    public CustomPlayerDeathEvent(Player whoDied) {
        super("");
        player = whoDied;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getWhoDied(){
        return getPlayer();
    }
}
