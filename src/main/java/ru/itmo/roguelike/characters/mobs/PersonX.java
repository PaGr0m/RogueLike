package ru.itmo.roguelike.characters.mobs;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.render.Camera;

import java.awt.*;

/**
 * Like a boss on level
 */
public class PersonX extends Enemy {
    public PersonX() {
    }

    public PersonX(Actor target) {
        super(target);
    }

}
