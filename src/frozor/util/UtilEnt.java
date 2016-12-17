package frozor.util;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UtilEnt {
    public static Entity spawnNamedEntity(Location location, EntityType entityType, String name){
        location.getWorld().loadChunk(location.getWorld().getChunkAt(location));

        Entity entity = location.getWorld().spawnEntity(location, entityType);

        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location.add(0, 2, 0), EntityType.ARMOR_STAND);

        armorStand.setVisible(false);
        armorStand.setCustomName(name);
        armorStand.setCustomNameVisible(true);

        armorStand.setGravity(false);
        armorStand.setBasePlate(false);
        armorStand.setMarker(true);
        armorStand.setSmall(true);

        Silverfish silverfish = (Silverfish)  vegetate(location.getWorld().spawnEntity(location.add(0, 2, 0), EntityType.SILVERFISH));

        silverfish.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 0), true);
        silverfish.setPassenger(armorStand);

        entity.setPassenger(silverfish);
        //entity.setPassenger(armorStand);

        return entity;
    }

    public static Entity freeze(Entity entity){
        UtilNms.setEntityNBT(entity, "NoAI", true);
        return entity;
    }

    public static Entity silence(Entity entity){
        UtilNms.setEntityNBT(entity, "Silent", true);
        return entity;
    }

    public static Entity vegetate(Entity entity){
        return freeze(silence(entity));
    }
}
