package ru.itmo.roguelike.items;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.particles.MovingUpText;

import java.awt.*;

import static ru.itmo.roguelike.items.BonusType.HP;

public class MedKit extends Collectible {
    {
        bonus = HP;
        bonusSize = 75;
    }

    protected MedKit() {
        super((graphics, x, y) -> {
            graphics.setColor(Color.RED);
            graphics.fillRect(x, y, 10, 10);
            graphics.setColor(Color.WHITE);
            graphics.fillRect(x + 4, y, 2, 10);
            graphics.fillRect(x, y + 4, 10, 2);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(x - 1, y - 1, 11, 11);
        });
    }

    /**
     * Heals actor by {@link MedKitBig#bonusSize} HP
     */
    @Override
    public void use(@NotNull Actor actor) {
        if (!used) {
            used = true;
            new MovingUpText(actor.getPosition(), "HP +" + bonusSize + "!", Color.RED);
            actor.heal(bonusSize);
        }
    }
}
