package frozor.util;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Squid;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UtilEnt {
    public static Entity spawnNamedEntity(Location location, EntityType entityType, String name){
        Entity entity = location.getWorld().spawnEntity(location, entityType);

        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location.add(0, 2, 0), EntityType.ARMOR_STAND);

        armorStand.setVisible(false);
        armorStand.setCustomName(name);
        armorStand.setCustomNameVisible(true);

        armorStand.setGravity(false);
        armorStand.setBasePlate(false);
        armorStand.setMarker(true);

        Squid squid = (Squid) location.getWorld().spawnEntity(location.add(0, 2, 0), EntityType.SQUID);
        squid.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 0), true);
        squid.setPassenger(armorStand);

        entity.setPassenger(squid);

        return entity;
    }

    public static void freeze(Entity entity){
        UtilNms.setEntityNBT(entity, "NoAI", true);
    }
}