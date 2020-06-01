package ru.itmo.roguelike.characters.inventory;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * <p>
 * Class, representing actor's inventory. It holds constant amount of items at once and supports providing access to
 * the stored items.
 * </p>
 *
 * <p>
 * Each item can be accessed by the number of cell in inventory.
 * </p>
 *
 * <p>
 * Inventory also supports keeping one item as currently selected and provides convenient methods for it's access
 * and quick usage.
 * </p>
 */
public class Inventory {
    private final Usable[] items;
    private int selectedItem = 0;
    private int size = 0;

    public Inventory(int size) {
        items = new Usable[size];
    }

    /**
     * Clears contents of inventory
     */
    public void clear() {
        size = 0;
        Arrays.fill(items, null);
    }

    /**
     * @return Currently selected item if there is any, {@code Optional.empty()} otherwise.
     */
    public Optional<Usable> getSelectedItem() {
        return getItem(selectedItem);
    }

    public void setSelectedItem(Usable usable) {
        setItem(usable, selectedItem);
    }

    /**
     * @return Item by specified index. If there is no item by this index, {@code Optional.empty()} returned.
     */
    public Optional<Usable> getItem(int i) {
        if (isIndexOutOfBounds(i)) {
            return Optional.empty();
        }

        return Optional.ofNullable(items[i]);
    }

    /**
     * Puts an item to any free cell in inventory.
     *
     * @return Index of the cell in inventory, where the item was put. {@code Optional.empty()} if operation didn't
     * succeeded (for example, if inventory is full).
     */
    public OptionalInt setNextFreeItem(Usable usable) {
        OptionalInt maybeI = IntStream.range(0, items.length)
                .filter(i -> items[i] == null)
                .limit(1)
                .findAny();

        maybeI.ifPresent(i -> setItem(usable, i));

        return maybeI;
    }

    /**
     * Puts an item to specified position in inventory.
     */
    public void setItem(@NotNull Usable usable, int i) {
        if (isIndexOutOfBounds(i)) {
            return;
        }

        if (items[i] == null) {
            size++;
        }

        items[i] = usable;
    }

    /**
     * Removes item at specified position from inventory
     */
    public void removeItem(int i) {
        if (isIndexOutOfBounds(i)) {
            return;
        }

        if (items[i] != null) {
            size--;
        }
        items[i] = null;
    }

    /**
     * Sets currently selected as item at specified position.
     */
    public void selectItem(int i) {
        if (isIndexOutOfBounds(i)) {
            return;
        }

        selectedItem = i;
    }

    public boolean isFull() {
        return size == items.length;
    }

    /**
     * Return maximum size of inventory
     */
    public int getInventoryLength() {
        return items.length;
    }

    private boolean isIndexOutOfBounds(int i) {
        return i < 0 || i >= items.length;
    }

    public void saveToFile(DataOutputStream outputStream) throws IOException {
        for (Usable usable : items) {
            Usable.saveToFile(usable, outputStream);
        }
    }

    public void reLoadFromFile(DataInputStream input, Player player) throws IOException {
        size = 0;
        selectedItem = 0;

        for (int i = 0; i < items.length; i++) {
            items[i] = Usable.readFromFile(input, player);
            if (items[i] != null) {
                size++;
            }
        }
    }

}
