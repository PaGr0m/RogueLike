package ru.itmo.roguelike.utils;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.inventory.Usable;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class FuncUtils {

    public interface UsableCreator {
        Usable create(DataInputStream inputStream, Player p) throws IOException;
    }

}
