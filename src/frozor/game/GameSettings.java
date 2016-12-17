package frozor.game;

import org.bukkit.GameMode;

public class GameSettings {
    private boolean allowRespawns = true;
    private boolean allowPlayerDamage = true;
    private boolean giveResistanceOnSpawn = true;
    private GameMode gameMode = GameMode.SURVIVAL;
    private int playerMin = 8;
    private int playerMax = 16;

    public void setAllowPlayerDamage(boolean allowPlayerDamage) {
        this.allowPlayerDamage = allowPlayerDamage;
    }

    public boolean isPlayerDamageAllowed() {
        return allowPlayerDamage;
    }

    public void setAllowRespawns(boolean allowRespawns) {
        this.allowRespawns = allowRespawns;
    }

    public boolean isRespawnAllowed(){
        return allowRespawns;
    }

    public void setGiveResistanceOnSpawn(boolean giveResistanceOnSpawn) {
        this.giveResistanceOnSpawn = giveResistanceOnSpawn;
    }

    public boolean isResistanceGivenOnSpawn(){
        return giveResistanceOnSpawn;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setPlayerMax(int playerMax) {
        this.playerMax = playerMax;
    }

    public int getPlayerMax() {
        return playerMax;
    }

    public void setPlayerMin(int playerMin) {
        this.playerMin = playerMin;
    }

    public int getPlayerMin() {
        return playerMin;
    }
}
