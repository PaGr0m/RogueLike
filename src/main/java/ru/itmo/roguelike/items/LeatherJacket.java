package ru.itmo.roguelike.items;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.utils.FileUtils;

import java.awt.*;

public class LeatherJacket extends Armor {
    {
        bonusSize = 5;
        image = FileUtils.loadImage("pic/leather_jacket.png");
    }

    @Override
    public void use(Actor actor) {
        new MovingUpText(actor.getPosition(), "Put on Leather Jacket\n5% to resistance", Color.RED);
        super.use(actor);
    }
}
