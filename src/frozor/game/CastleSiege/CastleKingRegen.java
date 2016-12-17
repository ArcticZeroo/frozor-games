package frozor.game.CastleSiege;

import org.bukkit.scheduler.BukkitRunnable;

public class CastleKingRegen extends BukkitRunnable
{
    private CastleSiege castleSiege;

    public CastleKingRegen(CastleSiege castleSiege){
        this.castleSiege = castleSiege;
    }

    public void startRegenTimer(){
        this.runTaskTimer(castleSiege, 0L, 60L);
    }

    @Override
    public void run() {
        if(castleSiege.getRedKing().canRegen()){
            castleSiege.getRedKing().regen();
            castleSiege.updateRedHealth();
        }

        if(castleSiege.getBlueKing().canRegen()){
            castleSiege.getBlueKing().regen();
            castleSiege.updateBlueHealth();
        }
    }
}
