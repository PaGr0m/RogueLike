package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.characters.Actor;

public interface WithTarget {
    void setSelf(Actor self);

    void setTarget(Actor target);

    void setRadius(float radius);
}
