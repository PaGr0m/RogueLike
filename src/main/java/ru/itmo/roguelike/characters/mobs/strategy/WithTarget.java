package ru.itmo.roguelike.characters.mobs.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;

public interface WithTarget {
    void setSelf(@NotNull Actor self);

    void setTarget(@NotNull Actor target);

    @NotNull
    Actor getTarget();
}
