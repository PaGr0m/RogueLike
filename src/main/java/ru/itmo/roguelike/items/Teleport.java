package ru.itmo.roguelike.items;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.render.particles.TextWithPoint;
import ru.itmo.roguelike.settings.GameSettings;
import ru.itmo.roguelike.utils.FileUtils;
import ru.itmo.roguelike.utils.FuncUtils;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static ru.itmo.roguelike.items.BonusType.TELEPORT;

public class Teleport extends Collectible {
    public static final String SORT = "TEL";
    private static final Image T_IN = FileUtils.loadImage(GameSettings.ImagePath.TELEPORT_IN);
    private static final Image T_OUT = FileUtils.loadImage(GameSettings.ImagePath.TELEPORT_OUT);
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

    public static Teleport fromFile(DataInputStream inputStream, Player p) throws IOException {
        boolean posIsNull = inputStream.readBoolean();
        if (!posIsNull) {
            return new Teleport(new IntCoordinate(inputStream.readInt(), inputStream.readInt()));
        }
        return new Teleport();
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

    @Override
    public void renderInInventory(Graphics2D graphics, int x, int y, int width, int height) {
        Image curr = pos == null ? T_IN : T_OUT;
        FuncUtils.renderImage(graphics, x, y, width, height, curr);
    }
}
