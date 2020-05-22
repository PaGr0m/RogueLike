package ru.itmo.roguelike.characters.mobs;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.mobs.strategy.*;
import ru.itmo.roguelike.manager.actormanager.MobManager;
import ru.itmo.roguelike.utils.Pair;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Enemy extends Actor implements Collidable {
    private Actor target;
    private MobBehavior strategy;

    public Enemy() {
        this(null);
    }

    public Enemy(Actor target) {
        this(target, new PassiveBehavior());
    }

    public Enemy(Actor target, MobBehavior strategy) {
        this.target = target;
        this.strategy = strategy;
    }

    {
        MobManager.addToRegister(this);
    }

    public void setBehaviour(MobBehavior strategy) {
        this.strategy = strategy;
    }

    public void setTarget(Actor target) {
        this.target = target;

        if (this.strategy instanceof WithTarget) {
            ((WithTarget) this.strategy).setTarget(target);
        }
    }

    @Override
    public void collide(Collidable c) {
        // если настигли цель
        if (c.equals(target)) {
            target.getDamage(this.damage);
        }
    }

    @Override
    public void die() {
        MobManager.deleteFromRegister(this);
    }

    @Override
    public void go() {
        Pair<Integer, Integer> path = strategy.getPath();

        positionX += path.getFirst() * 20;
        positionY += path.getSecond() * 20;
    }

    @NotNull
    public static Enemy.Builder builder(@NotNull Supplier<Enemy> enemySupplier) {
        return new Builder(enemySupplier.get());
    }

    public final static class Builder {
        private final Enemy enemy;

        private Builder(Enemy enemy) {
            this.enemy = enemy;
        }

        public Builder setPosition(int x, int y) {
            enemy.setX(x);
            enemy.setY(y);

            return this;
        }

        public Builder setBehavior(
                @NotNull Class<? extends MobWithTarget> behaviorClass
        ) {
            try {
                enemy.setBehaviour(
                        behaviorClass
                                .getDeclaredConstructor(Actor.class, float.class)
                                .newInstance(enemy, 10000)
                );
            } catch (
                    InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored
            ) {
            }

            return this;
        }

        public Builder setTarget(Actor target) {
            enemy.setTarget(target);
            return this;
        }

        @NotNull
        public Enemy build() {
            return enemy;
        }
    }
}
