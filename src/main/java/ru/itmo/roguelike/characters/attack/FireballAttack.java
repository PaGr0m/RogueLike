package ru.itmo.roguelike.characters.attack;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.field.Field;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FireballAttack extends Attack {
    public static final int COOLDOWN_TIME = 10;
    private static Image image = null;

    static {
        try {
            File pathToFile = new File("src/pic/fire.png");
            image = ImageIO.read(pathToFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

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
        fireball.act(field);
    }

    @Override
    public void renderInInventory(Graphics2D graphics, int x, int y, int width, int height) {
        graphics.drawImage(image, x, y, width, height, null);
    }
}
