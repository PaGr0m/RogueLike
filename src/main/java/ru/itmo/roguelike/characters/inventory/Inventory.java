package ru.itmo.roguelike.characters.inventory;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Inventory {
    public Inventory(int size) {
        items = new Usable[size];
    }

    public Optional<Usable> getSelectedItem() {
        return getItem(selectedItem);
    }

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

    public OptionalInt setNextFreeItem(Usable usable) {
        OptionalInt maybeI = IntStream.range(0, items.length).filter(i -> items[i] == null).findAny();

        if (maybeI.isPresent()) {
            setItem(usable, maybeI.getAsInt() + 1);
        }

        return maybeI;
    }

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
