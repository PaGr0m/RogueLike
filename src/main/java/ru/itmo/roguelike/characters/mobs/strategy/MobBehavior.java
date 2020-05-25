package ru.itmo.roguelike.characters.mobs.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.utils.Coordinate;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Class specifying mobs behaviour (esp., their reaction on player
 */
public interface MobBehavior {
    /**
     * Specifying mobs step according to behaviour
     *
     * @return next mob step, according to behaviour
     */
    Optional<Coordinate> getPath();

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
