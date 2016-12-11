package frozor.game.CastleSiege;

import com.sun.org.apache.xpath.internal.operations.Bool;
import frozor.component.DatapointParser;
import frozor.enums.GameState;
import frozor.events.ChestRefillTimeChangeEvent;
import frozor.events.GameStateChangeEvent;
import frozor.game.Game;
import frozor.kits.*;
import frozor.tasks.ChestRefillTimer;
import frozor.teams.PlayerTeam;
import frozor.util.UtilEnt;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CastleSiege extends Game implements Listener{
    private CastleKing redKing;
    private CastleKing blueKing;
    private CastleKingRegen castleKingRegen;
    private CastleChestFiller chestFiller;
    private ChestRefillTimer chestRefillTimer;

    public CastleSiege(){
        super("Castle Siege", new PlayerKit[]{new Knight(), new Archer(), new Scout(), new FireMage(), new Builder()}, new PlayerTeam[]{new PlayerTeam("Blue", ChatColor.BLUE), new PlayerTeam("Red", ChatColor.RED)}, "rivendale");

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
        blueKing = new CastleKing(this, "Blue", ChatColor.BLUE);

        redKing.getKing().setBaby(false);
        blueKing.getKing().setBaby(false);
        redKing.getKing().setVillager(true);
        blueKing.getKing().setVillager(true);

        redKing.getKing().getLocation().setYaw(180F);
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
        arcade.getGameScoreboard().setBlankLine(6);
        arcade.getGameScoreboard().setLine(7, ChatColor.GREEN + (ChatColor.BOLD + "Chest Refill"));

        castleKingRegen = new CastleKingRegen(this);
        chestFiller = new CastleChestFiller(this);
        chestRefillTimer = new ChestRefillTimer(this.getArcade(), 60*5);
        chestFiller.fillChests();
        chestRefillTimer.startTimer();

        updateRefillTimer();

        arcade.getTeamManager().getTeams().get("Red").getScoreboardTeam().addEntry(Integer.toString(redKing.getKing().getEntityId()));
        arcade.getTeamManager().getTeams().get("Blue").getScoreboardTeam().addEntry(Integer.toString(blueKing.getKing().getEntityId()));
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

    public void updateRefillTimer(){
        if(arcade.getGameState() == GameState.END) return;
        arcade.getDebugManager().print("Updating refill timer");
        if(chestRefillTimer.getTime() > 60){
            arcade.getGameScoreboard().setLine(8, String.format("%.1f Minutes", chestRefillTimer.getTime() / 60F));
        }else{
            arcade.getGameScoreboard().setLine(8, String.format("%d Seconds", chestRefillTimer.getTime()));
        }
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.PLAYING){
            arcade.getDebugManager().print("Starting regen timer");
            castleKingRegen.startRegenTimer();
        }else if(event.getGameState() == GameState.END){
            castleKingRegen.cancel();
            chestRefillTimer.cancel();
        }
    }

    @EventHandler
    public void onRefillTimerChange(ChestRefillTimeChangeEvent event){
        if(arcade.getGameState() == GameState.PLAYING){
            updateRefillTimer();
            if(event.getTime() == 0){
                chestFiller.fillChests();
                getServer().broadcastMessage(ChatColor.GREEN + (ChatColor.BOLD + "Chests have been refilled!"));
            }
        }
    }

    private Boolean isProtectedLocation(Location location){
        if(location.getBlock().getType() == Material.CHEST){
            return true;
        }

        if(location.distance(redKing.getKing().getLocation()) < 6){
            return true;
        }

        if(location.distance(redKing.getKing().getLocation()) < 6){
            return true;
        }

        return false;
    }

    @EventHandler
    public void onProtectedBlockBreak(BlockBreakEvent event){
        if(isProtectedLocation(event.getBlock().getLocation())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(notificationManager.getError("You can't break blocks there!"));
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKingDamageByPlayer(EntityDamageByEntityEvent event){
        if(event.getEntityType() == EntityType.ZOMBIE ){
            if(arcade.getGameState() != GameState.PLAYING){
                event.setCancelled(true);
                return;
            }

            if(event.getDamager() instanceof Player){
                event.setCancelled(true);

                Player player = (Player) event.getDamager();
                PlayerTeam playerTeam =  arcade.getTeamManager().getPlayerTeam(player);

                if(playerTeam.getScoreboardTeam().hasEntry(Integer.toString(event.getEntity().getEntityId()))) return;

                LivingEntity king = (LivingEntity) event.getEntity();
                king.damage(1D);
            }else{
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

    @EventHandler
    public void onKingDamage(EntityDamageEvent event){
        if(event.getEntityType() == EntityType.ZOMBIE){
            if((event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) || (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || (event.getCause() == EntityDamageEvent.DamageCause.FIRE)){
                event.setCancelled(true);

                Entity squid = event.getEntity().getPassenger();
                Entity stand = squid.getPassenger();

                squid.remove();
                stand.remove();
            }
        }
    }

    private void setWinScoreboard(String winText){
        arcade.getGameScoreboard().setNewSidebarObjective("csWinSb").setDisplayName(ChatColor.GOLD + (ChatColor.BOLD + "Castle Siege"));
        arcade.getGameScoreboard().setBlankLine(0);
        arcade.getGameScoreboard().setLine(1, winText);
    }
}
