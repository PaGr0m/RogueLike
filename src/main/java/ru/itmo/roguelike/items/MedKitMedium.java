package ru.itmo.roguelike.items;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.Camera;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.HP;

public class MedKitMedium extends Collectible {
    {
        width = 10;
        height = 10;
        bonusType = HP;
        bonusSize = 50;
        color = Color.magenta;
    }


    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        super.draw(graphics, camera);
    }

    /**
     * Heals actor by {@link MedKitMedium#bonusSize} HP
     */
    @Override
    public void use(@NotNull Actor actor) {
        if (!used) {
            used = true;

            actor.heal(bonusSize);
        }
    }
}
