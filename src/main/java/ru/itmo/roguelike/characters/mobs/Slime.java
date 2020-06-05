package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.movement.MoverEmbarrassment;
import ru.itmo.roguelike.manager.eventmanager.Event;
import ru.itmo.roguelike.render.particles.Splash;

import java.awt.*;

import static ru.itmo.roguelike.utils.MathUtils.getRandomDouble;

/**
 * Моб-пузырь
 */
public class Slime extends Enemy {
    private static final float MIN_BOUND_XP = 0;
    private static final float MAX_BOUND_XP = 1;

    {
        drawableDescriptor.setColor(new Color(0x5900FF));
    }

    public Slime() {
    }

    public Slime(Actor target) {
        super(target);
    }

    @Override
    public void collide(Collidable c) {
        if (c instanceof Player) {
            Event event = new Event(200, 0, drawableDescriptor.getColor(),
                    i -> {
                        if (i % 3 == 0) {
                            new Splash(c.getPosition(), 1,
                                    drawableDescriptor.getColor().brighter().brighter());
                        }
                    });
            ((Player) c).activateMoveEffect(MoverEmbarrassment::new, event);
        }
        super.collide(c);
    }

    @Override
    protected float getXPInBounds() {
        return (float) getRandomDouble(MIN_BOUND_XP, MAX_BOUND_XP);
    }

}
