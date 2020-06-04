package ru.itmo.roguelike.items;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.inventory.Usable;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.utils.FileUtils;

import java.awt.*;

public class TunicOfTheCyclopsKing extends Armor {
    private static Image image = FileUtils.loadImage("pic/tunic_cyclop.png");
    {
        bonusSize = 55;
    }

    @Override
    public void use(Actor actor) {
        new MovingUpText(actor.getPosition(), "Tunic Of The Cyclops King\n    55% to  resistance", Color.CYAN);
        super.use(actor);
    }

    @Override
    public void renderInInventory(Graphics2D graphics, int x, int y, int width, int height) {
        Usable.renderImageInInventory(graphics, x, y, width, height, image);
    }
}
