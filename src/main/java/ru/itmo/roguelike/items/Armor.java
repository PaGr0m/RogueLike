package ru.itmo.roguelike.items;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.render.particles.MovingUpText;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Additional armor which player can put on and off
 * <p>
 * BonusSize is resistance of additional armor in percent
 * armorResistance = (100 - bonusSize)/100
 * Getting mob_damage*def*armorResistance points of damage
 */
public abstract class Armor extends Collectible {
    public static final String SORT = "ARM";

    {
        drawableDescriptor.setColor(Color.magenta);
    }

    @Override
    public String getSort() {
        return SORT;
    }

    public static Armor fromFile(DataInputStream inputStream, Player p) throws IOException {
        int val = inputStream.readInt();
        if (val == 15) return new LightArmor();
        if (val == 40) return new MediumArmor();
        return new HeavyArmor();
    }

    protected void use(Actor actor, String name) {
        new MovingUpText(actor.getPosition(),
                "Put on \"" + name + "\"!\n    " + bonusSize + "% to  resistance", Color.MAGENTA);
        actor.setArmor(this);
    }

    public float getArmorResistance() {
        return (100 - (float) bonusSize) / 100;
    }

    @Override
    public void saveToFile(DataOutputStream output) throws IOException {
        output.writeInt(bonusSize);
    }
}
