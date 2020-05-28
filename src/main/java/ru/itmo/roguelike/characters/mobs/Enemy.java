package ru.itmo.roguelike.characters.mobs;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.strategy.MobBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.characters.mobs.strategy.PassiveBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.WithTarget;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.manager.actormanager.MobManager;
import ru.itmo.roguelike.utils.IntCoordinate;

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

        position.set(mover.getLastMove());
    }

    @Override
    public void die() {
        super.die();
        MobManager.deleteFromRegister(this);
    }

    @Override
    public void act(Field field) {
        IntCoordinate path = strategy.getPath();
        go(new IntCoordinate(path.getX() * 3, path.getY() * 3), field);
        super.act(field);
    }

    public float getRadius() {
        return radius;
    }

    public void strike(int damage, Player player) {
        this.hp -= damage;
        if (hp < 0) {
            player.addExp(getExp());
            die();
        }
    }

    protected abstract float getExp();

    public final static class Builder {
        private final Enemy enemy;

        private Builder(Enemy enemy) {
            this.enemy = enemy;
        }

        public Builder setPosition(IntCoordinate pos) {
            enemy.position.set(pos);

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
