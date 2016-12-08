package frozor.managers;

import org.bukkit.ChatColor;

public class NotificationManager {
    private String prefix;
    private ChatColor prefixColor = ChatColor.BLUE;

    public NotificationManager(String prefix){
        this.prefix = prefix;
    }

    public String getColoredPrefix(){
        return prefixColor + prefix + "> ";
    }

    public void setPrefixColor(ChatColor prefixColor) {
        this.prefixColor = prefixColor;
    }

    public String getMessage(String message){
        return getColoredPrefix() + ChatColor.GRAY + message;
    }

    public String getError(String message){
        return getColoredPrefix() + ChatColor.RED + message;
    }
}
