package ru.itmo.roguelike.items;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.utils.FileUtils;

import java.awt.*;

public class VampiresCowl extends Armor {
    {
        armorResistance = 0.85f;
        image = FileUtils.loadImage("pic/vamp_cowl.png");
    }

    @Override
    public void use(Actor actor) {
        new MovingUpText(actor.getPosition(), "Put on Vampire's Cowl\n15% to resistance", Color.RED);
        super.use(actor);
    }
}
