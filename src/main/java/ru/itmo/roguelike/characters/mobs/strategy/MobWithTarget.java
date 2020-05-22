package ru.itmo.roguelike.characters.mobs.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;

import java.util.function.Supplier;

public interface MobWithTarget extends MobBehavior, WithTarget {
    @NotNull
    static Builder builder(@NotNull Supplier<MobWithTarget> mobWithTargetSupplier) {
        return new Builder(mobWithTargetSupplier.get());
    }

    class Builder {
        private final MobWithTarget mobWithTarget;

        public Builder(MobWithTarget mobWithTarget) {
            this.mobWithTarget = mobWithTarget;
        }

        @NotNull
        public Builder setTarget(@NotNull Actor target) {
            mobWithTarget.setTarget(target);
            return this;
        }

        @NotNull
        public Builder setRadius(float radius) {
            mobWithTarget.setRadius(radius);
            return this;
        }

        @NotNull
        public Builder setSelf(@NotNull Actor self) {
            mobWithTarget.setSelf(self);
            return this;
        }

        @NotNull
        public MobWithTarget build() {
            return mobWithTarget;
        }
    }
}
