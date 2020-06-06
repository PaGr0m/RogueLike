package ru.itmo.roguelike.characters.mobs.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;

import java.util.function.Supplier;

public abstract class MobWithTarget implements MobBehavior, WithTarget {
    protected Actor self;
    protected Actor target;

    @NotNull
    public static MobWithTarget.Builder builder(@NotNull Supplier<MobWithTarget> mobWithTargetSupplier) {
        return new Builder(mobWithTargetSupplier.get());
    }

    public Actor getSelf() {
        return self;
    }

    @Override
    public void setSelf(Actor self) {
        this.self = self;
    }

    public Actor getTarget() {
        return target;
    }

    public void setTarget(Actor target) {
        this.target = target;
    }

    public static class Builder extends MobBehavior.Builder {
        private final MobWithTarget mobWithTarget;

        public Builder(MobWithTarget mobWithTarget) {
            super(mobWithTarget);

            this.mobWithTarget = mobWithTarget;
        }

        @NotNull
        public Builder setTarget(@NotNull Actor target) {
            mobWithTarget.setTarget(target);
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
