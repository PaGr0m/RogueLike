package ru.itmo.roguelike.characters.inventory;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.utils.IntCoordinate;

public interface Droppable {
    /**
     * Drop the item from inventory to specified position if {@code isDroppable()} call returns true. Does nothing
     * otherwise
     */
    void drop(@NotNull IntCoordinate position);
}
