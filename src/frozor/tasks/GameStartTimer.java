package frozor.tasks;

import frozor.arcade.Arcade;
import frozor.component.ActionBar;
import frozor.enums.GameState;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartTimer extends BukkitRunnable{
    private double startTime;
    private double timer;
    private boolean active;
    private Arcade arcade;
    private ActionBar actionBar;

    private String getActionBarMessage(){
        return String.format(ChatColor.YELLOW + ("Starting in %.1f seconds"), timer);
    }

    public GameStartTimer(Arcade arcade, int time){
        this.arcade = arcade;
        startTime = timer = time;
        actionBar = new ActionBar(getActionBarMessage());
    }

    public boolean isActive(){
        return active;
    }

    public void reset(){
        this.active = false;
        this.cancel();
        setTime(startTime);
    }

    public void setTime(double newTime){
        timer = newTime;
    }

    public double getTime(){
        return timer;
    }

    public double getStartTime() {
        return startTime;
    }

    public void startTimer(){
        active = true;
        this.runTaskTimer(arcade.getPlugin(), 2L, 2L);
    }

    @Override
    public void run(){
        if(timer > 0.1){
            timer -= 0.1;
            actionBar.setText(getActionBarMessage());
            actionBar.sendToAll();
        }else{
            arcade.setGameState(GameState.PLAYING);
            this.cancel();
        }
    }
}
