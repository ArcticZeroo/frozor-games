package frozor.component;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar {
    private String barText;

    public ActionBar(String barText){
        this.barText = barText;
    }

    private PacketPlayOutChat getPacket(String text){
        return new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
    }

    public void sendToPlayer(Player player, PacketPlayOutChat packet){
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void sendToPlayer(Player player){
        PacketPlayOutChat packet = getPacket(barText);
        sendToPlayer(player, packet);
    }

    public void sendToAll(){
        PacketPlayOutChat packet = getPacket(barText);
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            sendToPlayer(player, packet);
        }
    }

    public void setText(String barText) {
        this.barText = barText;
    }
}
