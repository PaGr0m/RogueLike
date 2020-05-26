package ru.itmo.roguelike.characters.mobs;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.mobs.strategy.MobBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.characters.mobs.strategy.PassiveBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.WithTarget;
import ru.itmo.roguelike.manager.actormanager.MobManager;
import ru.itmo.roguelike.utils.Coordinate;

import java.util.function.Supplier;

/**
 *
 */
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

    /**
     * If mob collides with target, mob fight
     * Else do nothing
     *
     * @param c --- object with which this collided
     */
    @Override
    public void collide(Collidable c) {
        // If we catch player
        if (c.equals(target)) {
            target.strike(this.damage);
        }
    }

    /**
     * Die and no longer available
     */
    @Override
    public void die() {
        MobManager.deleteFromRegister(this);
    }

    /**
     * Make step according to strategy
     */
    @Override
    public void go() {
        Coordinate path = strategy.getPath().orElse(new Coordinate(0, 0));

        positionX += path.getX() * 20;
        positionY += path.getY() * 20;
    }

    @NotNull
    public static Enemy.Builder builder(@NotNull Supplier<Enemy> enemySupplier) {
        return new Builder(enemySupplier.get());
    }

    /**
     * Can see target if it in radius
     *
     * @return radius of action
     */
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
