package ru.itmo.roguelike.characters.inventory;

import ru.itmo.roguelike.characters.Actor;

/**
 * All items that can be used by some actor
 */
public interface Usable {
    /**
     * Activates effect of usage when used by specified actor.
     */
    void use(Actor actor);

    /**
     * Some usable items may be used only once, another – limited amount of time, and the other – infinitely.
     * @return {@code true} if this item is still may be used.
     */
    boolean isUsed();
}
