package ru.itmo.roguelike.characters.mobs.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.utils.Pair;

import java.util.function.Supplier;

public interface MobBehavior {
    Pair<Integer, Integer> getPath();

    @NotNull
    static Builder builder(@NotNull Supplier<MobBehavior> behaviorSupplier) {
        return new Builder(behaviorSupplier.get());
    }

    class Builder {
        private final MobBehavior mobBehavior;

        public Builder(MobBehavior mobBehavior) {
            this.mobBehavior = mobBehavior;
        }

        @NotNull
        public MobBehavior build() {
            return mobBehavior;
        }
    }
}
