package frozor.managers;

import frozor.arcade.Arcade;

public class DebugManager {
    private Arcade arcade;
    public DebugManager(Arcade arcade){
        this.arcade = arcade;
    }

    public void print(String message){
        System.out.println(message);
        //arcade.getPlugin().getServer().broadcastMessage(message);
    }
}
