package ru.itmo.roguelike.items;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.inventory.Usable;

import java.awt.*;

public abstract class Armor extends Collectible {
    boolean onActor = false;
    /**
     * Resistance of additional armor in percent
     * Getting mob_damage*def*armorResistance points of damage
     */
    protected float armorResistance;
    protected Image image;

    @Override
    public boolean isOnActor() {
        return onActor;
    }

    @Override
    public String getSort() {
        return null;
    }

    public float getArmorResistance() {
        return armorResistance;
    }

    @Override
    public void use(Actor actor) {
        actor.setArmor(this);
        this.onActor = true;
    }

    @Override
    public void renderInInventory(Graphics2D graphics, int x, int y, int width, int height) {
        Usable.renderImageInInventory(graphics, x, y, width, height, image);
    }
}
