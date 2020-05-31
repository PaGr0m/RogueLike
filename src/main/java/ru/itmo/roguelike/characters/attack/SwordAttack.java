package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.projectiles.Sword;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.utils.IntCoordinate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SwordAttack extends Attack {
    public static final int COOLDOWN_TIME = 20;
    private Sword sword;
    private static Image image = null;

    static {
        try {
            File pathToFile = new File("resources/pic/sword.png");
            image = ImageIO.read(pathToFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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
        graphics.drawImage(image, x, y, width, height, null);
    }
}
