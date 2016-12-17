package frozor.util;

import frozor.perk.KitPerk;
import frozor.perk.PerkType;

import java.util.HashMap;
import java.util.List;

public class UtilKit {
    public static HashMap<PerkType, KitPerk> createPerkMap(List<KitPerk> perks){
        HashMap<PerkType, KitPerk> perkMap = new HashMap<>();
        for(KitPerk perk : perks){
            perkMap.put(perk.getPerkType(), perk);
        }
        return perkMap;
    }
}
