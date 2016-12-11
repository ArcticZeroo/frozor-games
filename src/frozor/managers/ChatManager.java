package frozor.managers;

import frozor.arcade.Arcade;
import frozor.teams.PlayerTeam;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener{
    private Arcade arcade;

    public ChatManager(Arcade arcade){
        this.arcade = arcade;
        arcade.RegisterEvents(this);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        PlayerTeam playerTeam = arcade.getTeamManager().getPlayerTeam(event.getPlayer());
        ChatColor playerNameColor = ChatColor.GRAY;

        if(playerTeam != null){
            playerNameColor = playerTeam.getTeamColor();
        }

        event.setFormat(playerNameColor + event.getPlayer().getName() + ChatColor.WHITE + " " + event.getMessage());
    }
}
