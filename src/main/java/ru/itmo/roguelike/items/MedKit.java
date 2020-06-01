package ru.itmo.roguelike.items;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.particles.MovingUpText;

import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import static ru.itmo.roguelike.items.BonusType.HP;

public class MedKit extends Collectible {
    {
        bonusType = HP;
        drawableDescriptor.setColor(Color.RED);
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
     * Heals actor by {@link MedKit#bonusSize} HP
     */
    @Override
    public void use(@NotNull Actor actor) {
        if (!used) {
            used = true;
            new MovingUpText(actor.getPosition(), "HP +" + bonusSize + "!", Color.RED);
            actor.heal(bonusSize);
        }
    }

    private static final Sign MEDKIT_SIGN = new Sign("MED", (i, p) -> {
        try {
            int val = i.readInt();
            if (val == 25) return new MedKitSmall();
            if (val == 50) return new MedKitMedium();
            return new MedKitBig();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    });

    @Override
    public Sign getSign() {
        return MEDKIT_SIGN;
    }

    @Override
    public void saveToFile(DataOutputStream output) throws IOException {
        super.saveToFile(output);
        output.writeInt(bonusSize);
    }
}
