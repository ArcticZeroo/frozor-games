package frozor.game.CastleSiege;

import frozor.component.DatapointParser;
import frozor.itemstack.ArmorSet;
import frozor.itemstack.ArmorSetType;
import frozor.util.UtilEnt;
import frozor.util.UtilItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class CastleKing {
    private CastleSiege castleSiege;
    private Zombie king;
    private Long lastDamage = (long) 0;
    private String kingType;
    private ChatColor kingColor;

    private void giveKingEquipment(Zombie king){
        king.getEquipment().setArmorContents(ArmorSet.getArmorSet(ArmorSetType.IRON));
        king.getEquipment().setHelmet(UtilItem.createUnbreakableItem(Material.GOLD_HELMET));
    }

    private Zombie createKing(){
        Location kingLocation = DatapointParser.parse(castleSiege.getMapConfig().getString("kings."+kingType), castleSiege.getGameWorld());

        castleSiege.getArcade().getDebugManager().print(String.format("Spawning a king at (%.2f, %.2f, %.2f), world = %s", kingLocation.getX(), kingLocation.getY(), kingLocation.getZ(), kingLocation.getWorld().getName()));

        Zombie king = (Zombie) UtilEnt.spawnNamedEntity(kingLocation, EntityType.ZOMBIE, kingColor + (ChatColor.BOLD + kingType + " King"));

        giveKingEquipment(king);

        UtilEnt.freeze(king);

        king.setMaxHealth(40);
        king.setHealth(40);

        return king;
    }

    public CastleKing(CastleSiege castleSiege, String kingType, ChatColor kingColor){
        this.castleSiege = castleSiege;

        this.kingType = kingType;
        this.kingColor = kingColor;

        this.king = createKing();
    }

    public Zombie getKing() {
        return king;
    }

    public void setLastDamage() {
        this.lastDamage = System.currentTimeMillis();
    }

    public void setLastDamage(Long lastDamage) {
        this.lastDamage = lastDamage;
    }

    public Long getLastDamage() {
        return lastDamage;
    }

    public boolean canRegen(){
        return ((getKing().getHealth() <= (double) 19) && (System.currentTimeMillis() - getLastDamage()) >= 3000);
    }

    public String getKingType() {
        return kingType;
    }

    public ChatColor getKingColor() {
        return kingColor;
    }

    public void regen(){
        regen(1);
    }

    public void regen(int amount){
        getKing().setHealth(getKing().getHealth() + (double) amount);
    }

    public void regen(double amount){
        getKing().setHealth(getKing().getHealth() + amount);
    }
}
