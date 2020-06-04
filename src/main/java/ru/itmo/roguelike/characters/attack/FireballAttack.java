package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.FileUtils;
import ru.itmo.roguelike.utils.FuncUtils;

import java.awt.*;
import java.io.DataInputStream;

public class FireballAttack extends Attack {
    public static final int COOLDOWN_TIME = 10;
    public final static String SORT = "FRB";
    private static final Image IMAGE = FileUtils.loadImage("pic/fire.png");

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
        fireball.setPosition(actor.getPosition());
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
