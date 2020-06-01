package ru.itmo.roguelike.utils;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.inventory.Usable;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.function.BiFunction;

public abstract class FuncUtils {

    public interface UsableCreator {
        Usable create(DataInputStream inputStream, Player p) throws IOException;
    }

    public interface BoolFunc {
        boolean get();
    }

}
