package frozor.game.CastleSiege;

import frozor.component.DatapointParser;
import frozor.enums.GameState;
import frozor.events.GameStateChangeEvent;
import frozor.game.Game;
import frozor.kits.*;
import frozor.teams.PlayerTeam;
import frozor.util.UtilEnt;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CastleSiege extends Game implements Listener{
    private CastleKing redKing;
    private CastleKing blueKing;
    private CastleKingRegen castleKingRegen;

    public CastleSiege(){
        super("Castle Siege", new PlayerKit[]{new Knight(), new Archer(), new Scout(), new FireMage()}, new PlayerTeam[]{new PlayerTeam("Blue", ChatColor.BLUE), new PlayerTeam("Blue", ChatColor.RED)}, "rivendale");

        getSettings().setPlayerMin(2);
        getSettings().setPlayerMax(3);
    }

    public CastleKing getRedKing() {
        return redKing;
    }

    public CastleKing getBlueKing() {
        return blueKing;
    }

    private void createKings(){
        redKing = new CastleKing(this, "Red", ChatColor.RED);

        redKing.getKing().getLocation().setYaw(180F);

        blueKing = new CastleKing(this, "Blue", ChatColor.BLUE);

        redKing.getKing().setBaby(false);
        blueKing.getKing().setBaby(false);
        redKing.getKing().setVillager(true);
        blueKing.getKing().setVillager(true);
    }

    @Override
    public void onStart(){
        //Spawn kings
        createKings();

        //Make scoreboard
        arcade.getGameScoreboard().setNewSidebarObjective("csGame");
        arcade.getGameScoreboard().setDisplayName(ChatColor.GOLD + (ChatColor.BOLD + "Castle Siege"));
        arcade.getGameScoreboard().setBlankLine(0);
        arcade.getGameScoreboard().setLine(1, ChatColor.RED + (ChatColor.BOLD + "Red King"));
        updateRedHealth();
        arcade.getGameScoreboard().setBlankLine(3);
        arcade.getGameScoreboard().setLine(4, ChatColor.BLUE + (ChatColor.BOLD + "Blue King"));
        updateBlueHealth();

        castleKingRegen = new CastleKingRegen(this);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        //getServer().getPluginManager().registerEvents(this, this);

        arcade.RegisterEvents(this);
    }

    public void updateRedHealth(){
        if(arcade.getGameState() == GameState.END) return;
        arcade.getDebugManager().print("Updating red health");
        arcade.getGameScoreboard().setLine(2, String.format("%d Health", (int) Math.floor(redKing.getKing().getHealth())));
    }

    public void updateBlueHealth(){
        if(arcade.getGameState() == GameState.END) return;
        arcade.getDebugManager().print("Updating blue health");
        arcade.getGameScoreboard().setLine(5, String.format("%d Health", (int) Math.floor(blueKing.getKing().getHealth())));
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.PLAYING){
            arcade.getDebugManager().print("Starting regen timer");
            castleKingRegen.startRegenTimer();
        }
    }

    @EventHandler
    public void onKingDeath(EntityDeathEvent event){
        if(arcade.getGameState() == GameState.PLAYING && event.getEntityType() == EntityType.ZOMBIE){
            if(event.getEntity() == redKing.getKing()){
                arcade.getDebugManager().print("Ending game - blue win");
                arcade.setGameState(GameState.END);
                setWinScoreboard(ChatColor.BLUE + "Blue won!");
            }else if(event.getEntity() == blueKing.getKing()){
                arcade.getDebugManager().print("Ending game - red");
                arcade.setGameState(GameState.END);
                setWinScoreboard(ChatColor.RED + "Red won!");
            }
        }
    }

    @EventHandler
    public void onKingDamage(EntityDamageEvent event){
        if(event.getEntityType() == EntityType.ZOMBIE ){
            if(arcade.getGameState() != GameState.PLAYING){
                event.setCancelled(true);
                return;
            }

            if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
                event.setCancelled(true);
                LivingEntity king = (LivingEntity) event.getEntity();
                king.damage(1D);
            }else if(event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION){
                event.setCancelled(true);
                return;
            }

            if(event.getEntity() == redKing.getKing()){
                if(redKing.getKing().getHealth() < 1) return;
                redKing.setLastDamage();
                updateRedHealth();
            }else if(event.getEntity() == blueKing.getKing()){
                if(blueKing.getKing().getHealth() < 1) return;
                blueKing.setLastDamage();
                updateBlueHealth();
            }
        }
    }

    private void setWinScoreboard(String winText){
        arcade.getGameScoreboard().setNewSidebarObjective("csWinSb").setDisplayName(ChatColor.GOLD + (ChatColor.BOLD + "Castle Siege"));
        arcade.getGameScoreboard().setBlankLine(0);
        arcade.getGameScoreboard().setLine(1, winText);
    }
}
