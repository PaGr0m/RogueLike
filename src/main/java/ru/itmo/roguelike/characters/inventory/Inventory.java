package ru.itmo.roguelike.characters.inventory;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * <p>
 *     Class, representing actor's inventory. It holds constant amount of items at once and supports providing access to
 *     the stored items.
 * </p>
 *
 * <p>
 *     Each item can be accessed by the number of cell in inventory. <b>Items are numbered starting with 1.</b>
 * </p>
 *
 * <p>
 *     Inventory also supports keeping one item as currently selected and provides convenient methods for it's access
 *     and quick usage.
 * </p>
 */
public class Inventory {
    public Inventory(int size) {
        items = new Usable[size];
    }

    /**
     * @return Currently selected item if there is any, {@code Optional.empty()} otherwise.
     */
    public Optional<Usable> getSelectedItem() {
        return getItem(selectedItem);
    }

    /**
     * @return Item by specified index. If there is no item by this index, {@code Optional.empty()} returned.
     */
    public Optional<Usable> getItem(int i) {
        i--;
        if (isIndexOutOfBounds(i)) {
            return Optional.empty();
        }

        return Optional.ofNullable(items[i]);
    }

    public void setSelectedItem(Usable usable) {
        setItem(usable, selectedItem);
    }

    /**
     * Puts an item to any free cell in inventory.
     * @return Index of the cell in inventory, where the item was put. {@code Optional.empty()} if operation didn't
     * succeeded (for example, if inventory is full).
     */
    public OptionalInt setNextFreeItem(Usable usable) {
        OptionalInt maybeI = IntStream.range(0, items.length).filter(i -> items[i] == null).findAny();

        if (maybeI.isPresent()) {
            maybeI = OptionalInt.of(maybeI.getAsInt() + 1);
            setItem(usable, maybeI.getAsInt());
        }

        return maybeI;
    }

    /**
     * Puts an item to specified position in inventory.
     */
    public void setItem(Usable usable, int i) {
        i--;
        if (isIndexOutOfBounds(i)) {
            return;
        }

        if (items[i] == null) {
            size++;
        }

        items[i] = usable;
    }

    /**
     * Sets currently selected as item at specified position.
     */
    public void selectItem(int i) {
        i--;
        if (isIndexOutOfBounds(i)) {
            return;
        }

        selectedItem = i;
    }

    public boolean isFull() {
        return size == items.length;
    }

    private int selectedItem = 0;

    private int size = 0;

    private final Usable[] items;

    private boolean isIndexOutOfBounds(int i) {
        return i < 0 || i >= items.length;
    }
}
