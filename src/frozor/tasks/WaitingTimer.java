package frozor.tasks;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.events.WaitingTimeChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class WaitingTimer extends BukkitRunnable{
    private int startTime;
    private int timer;
    private boolean active;
    private Arcade arcade;

    public WaitingTimer(Arcade arcade, int time){
        this.arcade = arcade;
        startTime = timer = time;
    }

    public boolean isActive(){
        return active;
    }

    public void reset(){
        this.active = false;
        this.cancel();
        setTime(startTime);
    }

    private void emitTime(){
        //arcade.getDebugManager().print(String.format("Game starting in %d seconds...", timer));
        new WaitingTimeChangeEvent(String.format("Starting in %d seconds...", timer), timer).callEvent();
    }

    public void setTime(int newTime){
        timer = newTime;
        emitTime();
    }

    public int getTime(){
        return timer;
    }

    public int getStartTime() {
        return startTime;
    }

    public void startTimer(){
        active = true;
        this.runTaskTimer(arcade.getPlugin(), 20L, 20L);
    }

    @Override
    public void run(){
        if(timer > 0){
            timer--;
            emitTime();
        }else{
            this.reset();
            arcade.setGameState(GameState.START);
        }
    }
}
