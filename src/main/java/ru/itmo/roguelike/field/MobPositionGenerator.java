package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.mobs.Zombie;
import ru.itmo.roguelike.characters.mobs.strategy.AggressiveBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.util.Random;

/**
 * Mob spawner
 * */
public class MobPositionGenerator {
    private static final int SAFE_RADIUS = 150;

    private final Random random;
    private Player player;

    public MobPositionGenerator(Player player) {
        this.player = player;
        this.random = new Random();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Randomly spawns mob on given tile.
     *
     * @param tile newly generated tile
     */
    public void addNewPosition(Tile tile) {
        if (tile.getType() != TileType.GRASS || player == null) {
            return;
        }

        int x = tile.getX();
        int y = tile.getY();
        IntCoordinate delta = new IntCoordinate(x, y);
        delta.substract(player.getPosition());
        if (x % 100 < 50 && y % 100 < 50 && random.nextInt(100) > 98 && delta.lenL2() > SAFE_RADIUS) {
            @SuppressWarnings("notUsedLocalVariable") Enemy enemy =
                    Enemy.builder(Zombie::new)
                            .setPosition(tile.getPos())
                            .setBehavior(MobWithTarget.builder(AggressiveBehavior::new))
                            .setRadius(10000)
                            .setTarget(player)
                            .build();
        }
    }
}
