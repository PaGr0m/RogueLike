package ru.itmo.roguelike.characters.inventory;

public class Inventory {
    public Inventory(int size) {
        items = new Usable[size];
    }

    public Usable getSelectedItem() {
        return getItem(selectedItem);
    }

    public Usable getItem(int i) {
        i--;
        check(i);

        return items[i];
    }

    public void setSelectedItem(Usable usable) {
        setItem(usable, selectedItem);
    }

    public void setItem(Usable usable, int i) {
        i--;
        check(i);

        items[i] = usable;
    }

    public void selectItem(int i) {
        i--;
        check(i);

        selectedItem = i;
    }

    private int selectedItem = 0;

    private final Usable[] items;

    private void check(int i) {
        assert i >= 0 && i < items.length;
    }
}
