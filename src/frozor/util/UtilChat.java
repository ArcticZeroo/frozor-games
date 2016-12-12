package frozor.util;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UtilChat {
    public static TextComponent createHoverCommandComponent(String message, String hover_message, String command){
        TextComponent component = new TextComponent(message);
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(hover_message)).create()));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return component;
    }

    public static void SendComponent(Player player, TextComponent component){
        player.spigot().sendMessage(component);
    }

    public static String getKitEquipmentString(String item){
        return ChatColor.GREEN + "+ " + ChatColor.WHITE + item;
    }

    public static String getKitItemString(String item, int amount){
        return ChatColor.GREEN + "+ " + ChatColor.AQUA + Integer.toString(amount) + " " + ChatColor.WHITE + item;
    }

    public static String getKitNegativeString(String effect){
        return ChatColor.RED + "- " + ChatColor.WHITE + effect;
    }
}
