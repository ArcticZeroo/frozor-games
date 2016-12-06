package frozor.managers;

import net.md_5.bungee.api.ChatColor;

public class NotificationManager {
    private String prefix;

    public NotificationManager(String prefix){
        this.prefix = prefix;
    }

    public String getColoredPrefix(){
        return ChatColor.BLUE + prefix + "> ";
    }

    public String getMessage(String message){
        return getColoredPrefix() + ChatColor.GRAY + message;
    }

    public String getError(String message){
        return getColoredPrefix() + ChatColor.RED + message;
    }
}
