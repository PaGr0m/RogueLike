package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.inventory.Usable;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.FileUtils;

import java.awt.*;

public class FireballAttack extends Attack {
    public static final int COOLDOWN_TIME = 10;
    private static final Image IMAGE = FileUtils.loadImage("pic/fire.png");

    public FireballAttack(Actor actor) {
        super(COOLDOWN_TIME, actor);
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
        Usable.renderImageInInventory(graphics, x, y, width, height, IMAGE);
    }

    private final static Sort FIREBALL_SORT = new Sort("FRB", (i, p) -> new FireballAttack(p));

    @Override
    public Sort getSign() {
        return FIREBALL_SORT;
    }
}
