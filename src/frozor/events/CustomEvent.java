package frozor.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomEvent extends Event{
    private static final HandlerList handlers = new HandlerList();
    private String message;
    private boolean cancelled = false;

    public CustomEvent(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    public void setCancelled(boolean cancel){
        cancelled = cancel;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void callEvent(){
        Bukkit.getServer().getPluginManager().callEvent(this);
        //Bukkit.getServer().broadcastMessage(getMessage());
    }
}
