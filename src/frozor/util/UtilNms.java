package frozor.util;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class UtilNms {
    public static void setEntityNBT(Entity entity, String property, boolean value){
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();

        NBTTagCompound compound = new NBTTagCompound();

        nmsEntity.c(compound);

        compound.setByte(property, (byte) ((value) ? 1 : 0));

        nmsEntity.f(compound);
    }
}
