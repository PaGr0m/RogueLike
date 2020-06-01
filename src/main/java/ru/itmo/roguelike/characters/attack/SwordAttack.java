package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.inventory.Usable;
import ru.itmo.roguelike.characters.projectiles.Sword;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.FileUtils;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;

public class SwordAttack extends Attack {
    public static final int COOLDOWN_TIME = 20;
    private static final Image IMAGE = FileUtils.loadImage("pic/sword.png");
    private Sword sword;

    public SwordAttack(Actor actor) {
        super(COOLDOWN_TIME, actor);
    }

    /**
     * Moves sword root to actor center
     */
    @Override
    public void act() {
        if (sword != null) {
            IntCoordinate delta = new IntCoordinate(
                    (int) actor.getShape().getBounds().getCenterX(),
                    (int) actor.getShape().getBounds().getCenterY()
            );
            sword.setPosition(actor.getPosition());
            sword.getPosition().add(delta);
        }
        super.act();
    }

    /**
     * [Re]creates sword
     *
     * @param field -- game field
     */
    @Override
    public void runAttack(Field field) {
        if (sword != null) {
            sword.die();
        }
        sword = new Sword(this.actor);
    }

    @Override
    public void renderInInventory(Graphics2D graphics, int x, int y, int width, int height) {
        Usable.renderImageInInventory(graphics, x, y, width, height, IMAGE);
    }

    private final static Sort SWORD_SORT = new Sort("SWD", (i, p) -> new SwordAttack(p));

    @Override
    public Sort getSign() {
        return SWORD_SORT;
    }
}
