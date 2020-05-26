package ru.itmo.roguelike.characters.mobs.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.util.Optional;
import java.util.function.Supplier;

public interface MobBehavior {
    @NotNull
    static Builder builder(@NotNull Supplier<MobBehavior> behaviorSupplier) {
        return new Builder(behaviorSupplier.get());
    }

    Optional<IntCoordinate> getPath();

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
