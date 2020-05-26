package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.characters.Actor;

/**
 * Interface specifying actors's interaction with targer
 */
public interface WithTarget {
    void setSelf(Actor self);

    void setTarget(Actor target);
}
