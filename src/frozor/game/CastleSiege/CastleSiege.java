package frozor.game.CastleSiege;

import frozor.component.TimeFormatter;
import frozor.enums.GameState;
import frozor.events.ChestRefillTimeChangeEvent;
import frozor.events.CustomPlayerSpawnEvent;
import frozor.events.GameEndEvent;
import frozor.events.GameStateChangeEvent;
import frozor.game.Game;
import frozor.kits.*;
import frozor.tasks.ChestRefillTimer;
import frozor.teams.PlayerTeam;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CastleSiege extends Game implements Listener{
    private CastleKing redKing;
    private CastleKing blueKing;
    private CastleKingRegen castleKingRegen;
    private CastleChestFiller chestFiller;
    private ChestRefillTimer chestRefillTimer;
    private CastleGameTimer castleGameTimer;

    public CastleSiege(){
        super("Castle Siege", new PlayerKit[]{new Knight(), new Archer(), new Scout(), new FireMage(), new Builder(), new Cactus(), new Medic(), new Airbender()}, new PlayerTeam[]{new PlayerTeam("Blue", ChatColor.BLUE), new PlayerTeam("Red", ChatColor.RED)}, "rivendale");

        getSettings().setPlayerMin(15);
        getSettings().setPlayerMax(30);
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

        //redKing.getKing().getLocation().setPitch(180F);
    }

    @Override
    public void onStart(){
        super.onStart();

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

        arcade.getGameScoreboard().setBlankLine(9);
        arcade.getGameScoreboard().setLine(10, ChatColor.YELLOW + (ChatColor.BOLD + "Time"));

        castleKingRegen = new CastleKingRegen(this);
        chestFiller = new CastleChestFiller(this);
        chestRefillTimer = new ChestRefillTimer(this.getArcade(), 60*5);
        castleGameTimer = new CastleGameTimer(this);

        chestFiller.fillChests();

        updateRefillTimer();
        updateGameTimer();

        arcade.getTeamManager().getTeams().get("Red").getScoreboardTeam().addEntry(Integer.toString(redKing.getKing().getEntityId()));
        arcade.getTeamManager().getTeams().get("Blue").getScoreboardTeam().addEntry(Integer.toString(blueKing.getKing().getEntityId()));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        //getServer().getPluginManager().registerEvents(this, this);

        arcade.RegisterEvents(this);
    }

    private void updateKingHealth(CastleKing king, int line){
        arcade.getGameScoreboard().setLine(line, String.format("%d Health", (int) Math.floor(king.getKing().getHealth())));
    }

    public void updateRedHealth(){
        if(arcade.getGameState() == GameState.END) return;
        updateKingHealth(redKing, 2);
    }

    public void updateBlueHealth(){
        if(arcade.getGameState() == GameState.END) return;
        updateKingHealth(blueKing, 5);
    }

    public void updateRefillTimer(){
        if(arcade.getGameState() == GameState.END) return;
        //arcade.getDebugManager().print("Updating refill timer");
        if(chestRefillTimer.getTime() > 60){
            arcade.getGameScoreboard().setLine(8, String.format("%.1f Minutes", chestRefillTimer.getTime() / 60F));
        }else{
            arcade.getGameScoreboard().setLine(8, String.format("%d Seconds", chestRefillTimer.getTime()));
        }
    }

    public void updateGameTimer(){
        if(arcade.getGameState() == GameState.END) return;
        //arcade.getDebugManager().print("Updating game timer");
        arcade.getGameScoreboard().setLine(11, TimeFormatter.toHumanReadable(castleGameTimer.getTime()));
    }

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event){
        if(event.getGameState() == GameState.PLAYING){
            arcade.getDebugManager().print("Starting regen timer");
            castleKingRegen.startRegenTimer();
            chestRefillTimer.startTimer();
            castleGameTimer.startTimer();
        }else if(event.getGameState() == GameState.END){
            castleKingRegen.cancel();
            chestRefillTimer.cancel();
            castleGameTimer.cancel();
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
        if(getGameWorld() == null) return false;
        if(!location.getWorld().getName().equals(getGameWorld().getName())) return false;

        if(location.getBlock().getType() == Material.CHEST){
            return true;
        }

        if(location.distance(redKing.getKing().getLocation()) < 10){
            return true;
        }

        if(location.distance(blueKing.getKing().getLocation()) < 10){
            return true;
        }

        return false;
    }

    @EventHandler
    public void onProtectedBlockBreak(BlockBreakEvent event){
        if(!event.getBlock().getLocation().getWorld().getName().equals(getGameWorldName())) return;
        if(isProtectedLocation(event.getBlock().getLocation())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(notificationManager.getError("You can't break blocks there!"));
        }
    }

    @EventHandler
    public void onProtectedBlockPlace(BlockPlaceEvent event){
        if(!event.getBlock().getLocation().getWorld().getName().equals(getGameWorldName())) return;
        if(isProtectedLocation(event.getBlock().getLocation())){
            event.setCancelled(true);
            event.getPlayer().sendMessage(notificationManager.getError("You can't place blocks there!"));
        }
    }

    @EventHandler
    public void onProtectedBlockExplode(BlockExplodeEvent event){
        List<Block> toRemove = new ArrayList<>();
        for (Block block : event.blockList()) {
            if (isProtectedLocation(block.getLocation())) {
                toRemove.add(block);
            }
        }
        event.blockList().removeAll(toRemove);
    }

    @EventHandler
    public void onProtectedEntityExplode(EntityExplodeEvent event){
        List<Block> toRemove = new ArrayList<>();
        for (Block block : event.blockList()) {
            if (isProtectedLocation(block.getLocation())) {
                toRemove.add(block);
            }
        }
        event.blockList().removeAll(toRemove);
    }

    @EventHandler
    public void onKingDeath(EntityDeathEvent event){
        if(arcade.getGameState() == GameState.PLAYING && event.getEntityType() == EntityType.ZOMBIE){
            if(event.getEntity() == redKing.getKing()){
                arcade.getDebugManager().print("Ending game - blue win");
                setWinScoreboard(ChatColor.BLUE + "Blue won!");
                new GameEndEvent("Red Won", arcade.getTeamManager().getTeams().get("Blue"), new ArrayList<>(Collections.singletonList(arcade.getTeamManager().getTeams().get("Red")))).callEvent();
            }else if(event.getEntity() == blueKing.getKing()){
                arcade.getDebugManager().print("Ending game - red");
                setWinScoreboard(ChatColor.RED + "Red won!");
                new GameEndEvent("Red Won", arcade.getTeamManager().getTeams().get("Red"), new ArrayList<>(Collections.singletonList(arcade.getTeamManager().getTeams().get("Blue")))).callEvent();
            }
        }
    }

    private void sendKingAttackMessage(PlayerTeam playerTeam){
        Set<String> teamPlayers = playerTeam.getScoreboardTeam().getEntries();
        for(String teamEntry : teamPlayers){
            Player player = Bukkit.getPlayer(teamEntry);
            if(player != null){
                player.sendMessage(notificationManager.getMessage("Your king is under attack!"));
            }
        }
    }

    @EventHandler
    public void onKingDamage(EntityDamageEvent event){
        if(event.getEntityType() == EntityType.ZOMBIE ) {
            if (arcade.getGameState() != GameState.PLAYING) {
                event.setCancelled(true);
                return;
            }

            if(event.getCause() == EntityDamageEvent.DamageCause.DROWNING){
                event.setCancelled(true);
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

            boolean sendDamageMessage = false;
            PlayerTeam alertTeam = null;

            if(event.getDamager() instanceof Player){
                event.setCancelled(true);

                Player player = (Player) event.getDamager();
                PlayerTeam playerTeam =  arcade.getTeamManager().getPlayerTeam(player);

                if(playerTeam.getScoreboardTeam().hasEntry(Integer.toString(event.getEntity().getEntityId()))) return;

                LivingEntity king = (LivingEntity) event.getEntity();

                if(king.getHealth() == 40){
                    sendDamageMessage = true;
                }

                king.damage(1D);
            }else{
                event.setCancelled(true);
                return;
            }

            if(event.getEntity() == redKing.getKing()){
                if(redKing.getKing().getHealth() < 1) return;
                redKing.setLastDamage();
                updateRedHealth();

                if(sendDamageMessage) alertTeam = arcade.getTeamManager().getTeams().get(redKing.getKingType());
            }else if(event.getEntity() == blueKing.getKing()){
                if(blueKing.getKing().getHealth() < 1) return;
                blueKing.setLastDamage();
                updateBlueHealth();

                if(sendDamageMessage) alertTeam = arcade.getTeamManager().getTeams().get(blueKing.getKingType());
            }else{
                return;
            }

            if(alertTeam != null && sendDamageMessage){
                sendKingAttackMessage(alertTeam);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event){
        if(event.getEntityType() == EntityType.ZOMBIE){
            if((event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) || (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || (event.getCause() == EntityDamageEvent.DamageCause.FIRE) || (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)){
                event.setCancelled(true);

                if(event.getEntity().getPassenger() != null){
                    Entity squid = event.getEntity().getPassenger();
                    Entity stand = squid.getPassenger();

                    squid.remove();
                    stand.remove();
                }
            }
        }else if(event.getEntity() instanceof Player){
            if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION){
                event.setDamage(event.getDamage() * 0.75);
            }else if(event.getCause() == EntityDamageEvent.DamageCause.FALL){
                if(event.getEntity().getTicksLived() < 30*20){
                    event.setCancelled(true);
                    return;
                }
                event.setDamage(event.getDamage() * 0.5);
            }
        }
    }

    @EventHandler
    public void onTnTExplode(PlayerVelocityEvent event){
        if(event.getPlayer().getLastDamageCause() == null) return;
        if(event.getPlayer().getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) return;
        if(event.getPlayer().getLastDamageCause().getEntityType() == EntityType.PRIMED_TNT){
            event.getPlayer().setVelocity(event.getVelocity().multiply(20));
        }
    }

    @EventHandler
    public void onCustomSpawn(CustomPlayerSpawnEvent event){
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 4));
    }

    private void setWinScoreboard(String winText){
        arcade.getGameScoreboard().setNewSidebarObjective("csWinSb").setDisplayName(ChatColor.GOLD + (ChatColor.BOLD + "Castle Siege"));
        arcade.getGameScoreboard().setBlankLine(0);
        arcade.getGameScoreboard().setLine(1, winText);
    }

    @EventHandler
    public void onJumpyStickClick(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if(item == null) return;
        if(item.getType() == Material.GOLD_SWORD && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Air Staff")){
            Vector newVelocity = event.getPlayer().getVelocity();
            if(newVelocity.getY() < 0){
                newVelocity.setY(0);
            }

            newVelocity.add(new Vector(0, 0.5, 0));

            newVelocity.add(event.getPlayer().getLocation().getDirection());

            event.getPlayer().setVelocity(newVelocity);

            if(item.getDurability() >= item.getType().getMaxDurability()){
                arcade.getDebugManager().print("Removing air staff");
                arcade.getDebugManager().print(String.format("Its durability is %d", item.getDurability()));
                event.getPlayer().getInventory().remove(item);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_BREAK, 1F, 1F);
            }else{
                item.setDurability((short)(item.getDurability() + (item.getType().getMaxDurability()/6)));
            }
        }
    }
}
