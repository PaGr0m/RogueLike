package ru.itmo.roguelike.items;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.inventory.Usable;

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
    protected Image image;

    {
        drawableDescriptor.setColor(Color.magenta);
    }

    @Override
    public String getSort() {
        return null;
    }

    public static Armor fromFile(DataInputStream inputStream, Player p) throws IOException {
        int val = inputStream.readInt();
        if (val == 5) return new LeatherJacket();
        if (val == 15) return new VampiresCowl();
        return new TunicOfTheCyclopsKing();
    }

    @Override
    public void use(Actor actor) {
        actor.setArmor(this);
    }

    @Override
    public void renderInInventory(Graphics2D graphics, int x, int y, int width, int height) {
        Usable.renderImageInInventory(graphics, x, y, width, height, image);
    }

    public float getArmorResistance() {
        return (100 - (float) bonusSize) / 100;
    }

    @Override
    public void saveToFile(DataOutputStream output) throws IOException {
        output.writeInt(bonusSize);
    }
}
