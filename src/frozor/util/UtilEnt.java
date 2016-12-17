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
        System.out.println("Spawning a named entity - " + name);
        System.out.println("Spawning the entity");
        Entity entity = location.getWorld().spawnEntity(location, entityType);

        System.out.println("Spawning the armor stand");
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location.add(0, 2, 0), EntityType.ARMOR_STAND);

        armorStand.setVisible(false);
        armorStand.setCustomName(name);
        armorStand.setCustomNameVisible(true);

        armorStand.setGravity(false);
        armorStand.setBasePlate(false);
        armorStand.setMarker(true);
        armorStand.setSmall(true);

        /*Squid squid = (Squid) location.getWorld().spawnEntity(location.add(0, 2, 0), EntityType.SQUID);
        squid.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 0), true);
        squid.setPassenger(armorStand);

        entity.setPassenger(squid);*/
        entity.setPassenger(armorStand);

        System.out.println("yay");
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
