package frozor.tasks;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.events.ChestRefillTimeChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestRefillTimer extends BukkitRunnable{
    private int startTime;
    private int timer;
    private boolean active;
    private Arcade arcade;

    public ChestRefillTimer(Arcade arcade, int time){
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
        new ChestRefillTimeChangeEvent(timer).callEvent();
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
        emitTime();
        if(timer > 0){
            timer--;
        }else{
            this.reset();
        }
    }
}
