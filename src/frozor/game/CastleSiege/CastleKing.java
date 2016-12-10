package frozor.game.CastleSiege;

import frozor.component.DatapointParser;
import frozor.kits.ArmorSet;
import frozor.kits.ArmorSetType;
import frozor.util.UtilEnt;
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

    private void giveKingEquipment(Zombie king){
        king.getEquipment().setArmorContents(ArmorSet.getArmorSet(ArmorSetType.IRON));
        king.getEquipment().setHelmet(new ItemStack(Material.GOLD_HELMET));
    }

    private Zombie createKing(String kingType, ChatColor kingColor){
        Location kingLocation = DatapointParser.parse(castleSiege.getMapConfig().getString("kings."+kingType));

        Zombie king = (Zombie) UtilEnt.spawnNamedEntity(kingLocation, EntityType.ZOMBIE, kingColor + (ChatColor.BOLD + kingType + " King"));

        giveKingEquipment(king);

        UtilEnt.freeze(king);

        return king;
    }

    public CastleKing(CastleSiege castleSiege, String kingType, ChatColor kingColor){
        this.castleSiege = castleSiege;
        this.king = createKing(kingType, kingColor);
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

    public void regen(){
        getKing().setHealth(getKing().getHealth() + (double) 1);
    }

    public void regen(int amount){
        getKing().setHealth(getKing().getHealth() + (double) amount);
    }

    public void regen(double amount){
        getKing().setHealth(getKing().getHealth() + amount);
    }
}
