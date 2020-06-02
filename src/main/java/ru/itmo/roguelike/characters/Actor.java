package ru.itmo.roguelike.characters;

import ru.itmo.roguelike.Collidable;
import ru.itmo.roguelike.characters.attack.Attack;
import ru.itmo.roguelike.characters.attack.FireballAttack;
import ru.itmo.roguelike.characters.movement.Mover;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.TileType;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.render.particles.Splash;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;

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
     * Resistance of additional armor in percent
     * Getting mob_damage*def*armorResistance points of damage
     */
    protected double armorResistance = 1;
    /**
     * Resistance to mob's damage in percent
     * Getting mob_damage*def points of damage
     * Getting higher with level
     */
    protected double def = 0.99;

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
     */
    public void heal(int hp) {
        assert hp >= 0;

        this.hp = Math.min(this.hp + hp, maxHp);
    }

    public void protect(int armorResistance) {
        assert armorResistance >= 0 && armorResistance <= 100;

        this.armorResistance = (100 - (double) armorResistance) / 100;
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
        if (field.getTileType(position) == TileType.BADROCK) {
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
            new Splash(position, 3, drawableDescriptor.getColor());
        }
        this.hp -= this.def * this.armorResistance * damage;
        if (hp <= 0) die();
    }

    public int getHp() {
        return hp;
    }

    public boolean isDead() {
        return hp <= 0;
    }
}
