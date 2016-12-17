package frozor.game.CastleSiege;

import frozor.arcade.Arcade;
import frozor.enums.GameState;
import frozor.events.WaitingTimeChangeEvent;
import frozor.game.CastleSiege.CastleSiege;
import org.bukkit.scheduler.BukkitRunnable;

public class CastleGameTimer extends BukkitRunnable{
    private int timer = 0;
    private boolean active;
    private CastleSiege castleSiege;

    public CastleGameTimer(CastleSiege castleSiege){
        this.castleSiege = castleSiege;
    }

    public boolean isActive(){
        return active;
    }

    public void reset(){
        this.active = false;
        this.cancel();
        setTime(0);
    }

    public void setTime(int newTime){
        timer = newTime;
        castleSiege.updateGameTimer();
    }

    public int getTime(){
        return timer;
    }

    public void startTimer(){
        active = true;
        this.runTaskTimer(castleSiege, 20L, 20L);
    }

    @Override
    public void run(){
        setTime(timer+1);
    }
}
