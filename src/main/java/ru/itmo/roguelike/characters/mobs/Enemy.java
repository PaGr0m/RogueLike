package ru.itmo.roguelike.characters.mobs;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.mobs.strategy.MobBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.characters.mobs.strategy.PassiveBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.WithTarget;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.manager.actormanager.MobManager;
import ru.itmo.roguelike.utils.Coordinate;
import ru.itmo.roguelike.utils.Pair;

import java.util.function.Supplier;

public abstract class Enemy extends Actor implements Collidable {
    private Actor target = null;
    private MobBehavior strategy = new PassiveBehavior();

    {
        MobManager.addToRegister(this);
        damage = 2;
    }

    public Enemy() {
        super();
    }

    public Enemy(Actor target) {
        this.target = target;
    }

    public Enemy(Actor target, MobBehavior strategy) {
        super();
        this.target = target;
        this.strategy = strategy;
    }

    @NotNull
    public static Enemy.Builder builder(@NotNull Supplier<Enemy> enemySupplier) {
        return new Builder(enemySupplier.get());
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
            target.strike(this.damage);
        }

        positionX = mover.getLastX();
        positionY = mover.getLastY();
    }

    @Override
    public void die() {
        super.die();
        MobManager.deleteFromRegister(this);
    }

    @Override
    public void act(Field field) {
        Pair<Integer, Integer> path = strategy.getPath();

        go(new Coordinate(path.getFirst() * 3, path.getSecond() * 3), field);
        super.act(field);
    }

    public float getRadius() {
        return radius;
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

        public Builder setBehavior(@NotNull MobBehavior.Builder mobBuilder) {
            enemy.setBehaviour(mobBuilder.build());
            return this;
        }

        public Builder setBehavior(@NotNull MobWithTarget.Builder mobBuilder) {
            enemy.setBehaviour(mobBuilder.setSelf(enemy).build());
            return this;
        }

        public Builder setTarget(Actor target) {
            enemy.setTarget(target);
            return this;
        }

        public Builder setRadius(float radius) {
            enemy.radius = radius;
            return this;
        }

        @NotNull
        public Enemy build() {
            return enemy;
        }
    }
}
