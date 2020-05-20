package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.utils.Pair;

public interface MobBehavior {
    Pair<Integer, Integer> getPath();
}
