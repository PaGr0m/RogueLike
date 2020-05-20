package ru.itmo.roguelike.characters.mobs.strategy;

import ru.itmo.roguelike.utils.Pair;

public class PassiveBehavior implements MobBehavior {
    @Override
    public Pair<Integer, Integer> getPath() {
        return new Pair<>(0, 0);
    }
}
