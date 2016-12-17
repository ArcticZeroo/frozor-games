package frozor.perk;

public class KitPerk {
    protected PerkType perkType;
    protected double modifier = 1.0;
    protected boolean isStatic = false;

    public KitPerk(PerkType perkType, double modifier){
        this.perkType = perkType;
        this.modifier = modifier;
        this.isStatic = false;

    }

    public KitPerk(PerkType perkType, double modifier, boolean isStatic){
        this.perkType = perkType;
        this.modifier = modifier;
        this.isStatic = isStatic;

    }

    public double getModifier() {
        return modifier;
    }

    public PerkType getPerkType() {
        return perkType;
    }

    public boolean isStatic() {
        return isStatic;
    }
}
