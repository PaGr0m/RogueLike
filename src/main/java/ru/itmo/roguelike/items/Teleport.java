package ru.itmo.roguelike.items;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.render.particles.TextWithPoint;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import static ru.itmo.roguelike.items.BonusType.TELEPORT;

public class Teleport extends Collectible {
    private IntCoordinate pos = null;
    public static final String SORT = "TEL";

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

    @Override
    public void saveToFile(DataOutputStream output) throws IOException {
        super.saveToFile(output);
        output.writeBoolean(pos == null);
        if (pos != null) {
            output.writeInt(pos.getX());
            output.writeInt(pos.getY());
        }
    }

    @Override
    public String getSort() {
        return SORT;
    }

    public static Teleport fromFile(DataInputStream inputStream, Player p) throws IOException {
        boolean posIsNull = inputStream.readBoolean();
        if (!posIsNull) {
            return new Teleport(new IntCoordinate(inputStream.readInt(), inputStream.readInt()));
        }
        return new Teleport();
    }
}
