package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.settings.GameSettings;
import ru.itmo.roguelike.utils.FileUtils;
import ru.itmo.roguelike.utils.FuncUtils;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.DataInputStream;

public class FireballAttack extends Attack {
    public static final int COOLDOWN_TIME = 10;
    public final static String SORT = "FRB";
    private static final Image IMAGE = FileUtils.loadImage(GameSettings.ImagePath.FIREBALL);

    public FireballAttack(Actor actor) {
        super(COOLDOWN_TIME, actor);
    }

    public static FireballAttack fromFile(DataInputStream inputStream, Player p) {
        return new FireballAttack(p);
    }

    /**
     * Throws {@link Fireball} in the direction of this.direction
     *
     * @param field -- game field
     */
    @Override
    public void runAttack(Field field) {
        Fireball fireball;
        fireball = new Fireball(direction, actor);

        Rectangle2D actorBBox = actor.getShapeAtPosition().getBounds2D();
        IntCoordinate position = new IntCoordinate((int) actorBBox.getCenterX() - 5, (int) actorBBox.getCenterY() - 5);

        fireball.setPosition(position);
    }

    @Override
    public void renderInInventory(Graphics2D graphics, int x, int y, int width, int height) {
        FuncUtils.renderImage(graphics, x, y, width, height, IMAGE);
    }

    @Override
    public String getSort() {
        return SORT;
    }
}
