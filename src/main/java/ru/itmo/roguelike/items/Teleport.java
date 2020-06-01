package ru.itmo.roguelike.items;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.render.particles.TextWithPoint;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import static ru.itmo.roguelike.items.BonusType.TELEPORT;

public class Teleport extends Collectible {
    private IntCoordinate pos = null;

    {
        bonusType = TELEPORT;
        bonusSize = 0;
    }

    public Teleport() {
        drawableDescriptor.setColor(Color.ORANGE);
    }

    private Teleport(IntCoordinate pos) {
        this.pos = pos;
        drawableDescriptor.setColor(Color.BLUE);
    }

    @Deprecated
    @Override
    public void use(@NotNull Actor actor) {
        if (pos == null) {
            pos = new IntCoordinate(actor.getPosition());
            new TextWithPoint(actor.getPosition(), "TELEPORT", new Color(0x380255));
            drawableDescriptor.setColor(Color.BLUE);
        } else {
            used = true;
            actor.setPosition(pos);
            new MovingUpText(actor.getPosition(), "TELEPORTED", new Color(0x380255));
        }
    }


    private static final Sort TELEPORT_SORT = new Sort("TEL", (i, p) -> {
        try {
            boolean posIsNull = i.readBoolean();
            if (!posIsNull) {
                return new Teleport(new IntCoordinate(i.readInt(), i.readInt()));
            }
            return new Teleport();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    });

    @Override
    public void saveToFile(DataOutputStream output) throws IOException {
        super.saveToFile(output);
        output.writeBoolean(pos == null);
        if (pos != null) {
            output.writeInt(pos.getX());
            output.writeInt(pos.getY());
        }
    }

    @Deprecated
    @Override
    public Sort getSign() {
        return TELEPORT_SORT;
    }
}
