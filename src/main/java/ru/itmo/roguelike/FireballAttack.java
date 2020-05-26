package ru.itmo.roguelike;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.utils.Pair;

public class FireballAttack extends Attack {
    public static final int CD_TYPE = 100;
    public final Player player;

    public FireballAttack(Player player) {
        super(CD_TYPE);
        this.player = player;
    }

    @Override
    public void attack() {
        Fireball fireball = new Fireball(new Pair<>(player.getX(), player.getY()));
        fireball.setRadius(10000);
    }
}
