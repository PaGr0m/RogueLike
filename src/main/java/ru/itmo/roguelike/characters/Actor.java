package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.attack.Attack;
import ru.itmo.roguelike.characters.attack.FireballAttack;
import ru.itmo.roguelike.characters.movement.Mover;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.TileType;
import ru.itmo.roguelike.items.Armor;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.render.particles.Splash;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;

public abstract class Actor extends Drawable implements Collidable {
    protected Attack attackMethod = new FireballAttack(this);

    protected IntCoordinate position = IntCoordinate.getZeroPosition();
    protected IntCoordinate direction;
    protected int damage;

    protected int maxHp;
    protected int hp;

    protected float radius;
    protected Mover mover = new Mover();
    /**
     * Resistance to mob's damage in percent
     * Getting mob_damage*def points of damage
     * Getting higher with level
     */
    protected double def = 0.99;
    protected Armor armor;
    private Instant lastWarning = Instant.now();

    {
        CollideManager.register(this);
    }

    public Actor() {
    }

    public Actor(Drawer drawer) {
        super(drawer);
    }

    protected void init(int hp) {
        maxHp = hp;
        this.hp = maxHp;
    }

    public void setAttackMethod(Attack attackMethod) {
        this.attackMethod = attackMethod;
    }

    /**
     * Heals actor. Increases it's current HP by specified amount, but not more than it's maximum HP
     *
     * @param hp amount of HP to heal
     * @return amount of HP that actually was healed
     */
    public int heal(int hp) {
        assert hp >= 0;

        int newHp = Math.min(this.hp + hp, maxHp);
        int delta = newHp - this.hp;
        this.hp = newHp;

        if (delta == 0) {
            if (Duration.between(lastWarning, Instant.now()).getSeconds() > 1) {
                new MovingUpText(position, "Your HP is full", Color.RED);
                lastWarning = Instant.now();
            }
        }

        return delta;
    }

    public boolean hasFullHP() {
        return hp == maxHp;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public IntCoordinate getPosition() {
        return position;
    }

    public void setPosition(IntCoordinate position) {
        this.position = new IntCoordinate(position);
    }

    @Override
    public IntCoordinate getLastPosition() {
        return new IntCoordinate(mover.getLastMove());
    }

    public void act(Field field) {
        if (field.getTileType(position) == TileType.BEDROCK && !(this instanceof Player)) {
            this.die();
        }
    }

    public void go(IntCoordinate by, Field field) {
        IntCoordinate newCoord = mover.move(position, by);
        TileType nextTile = field.getTileType(newCoord);
        if (!nextTile.isSolid()) {
            position.set(newCoord);
        }
    }

    @Override
    public void draw(Graphics2D graphics, Camera camera) {
        drawableDescriptor.setPosition(position);
        super.draw(graphics, camera);
    }

    public void die() {
        Drawable.unregister(this);
        CollideManager.unregister(this);
    }

    /**
     * Damage the actor. Creates {@link Splash} visual effect.
     */
    public void strike(int damage) {
        if (damage > 0) {
            Splash.createSplashAndRegister(position, 3, drawableDescriptor.getColor());
        }
        if (armor != null) {
            this.hp -= this.def * damage * armor.getArmorResistance();
        } else {
            this.hp -= this.def * damage;
        }
        if (hp <= 0) die();
    }

    public int getHp() {
        return hp;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        assert armor.getArmorResistance() >= 0 && armor.getArmorResistance() <= 100;
        this.armor = armor;
    }
}
