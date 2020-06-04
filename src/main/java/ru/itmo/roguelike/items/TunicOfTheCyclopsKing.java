package ru.itmo.roguelike.items;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.utils.FileUtils;

import java.awt.*;

public class TunicOfTheCyclopsKing extends Armor {
    {
        bonusSize = 55;
        image = FileUtils.loadImage("pic/tunic_cyclop.png");
    }

    @Override
    public void use(Actor actor) {
        new MovingUpText(actor.getPosition(), "Tunic Of The Cyclops King\n55% to  resistance", Color.RED);
        super.use(actor);
    }
}
