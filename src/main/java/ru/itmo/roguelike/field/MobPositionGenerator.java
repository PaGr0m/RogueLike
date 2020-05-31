package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.mobs.Slime;
import ru.itmo.roguelike.characters.mobs.Zombie;
import ru.itmo.roguelike.characters.mobs.strategy.CowardlyBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.characters.mobs.strategy.RandomWalkBehavior;
import ru.itmo.roguelike.items.MedKitBig;
import ru.itmo.roguelike.items.MedKitMedium;
import ru.itmo.roguelike.items.MedKitSmall;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * Mob and Collectible spawner
 */
public class MobPositionGenerator {
    private static final int SAFE_RADIUS = 150;
    private static final Map<SpawnClass, BiConsumer<Player, IntCoordinate>> spawners = new HashMap<>();

    static {
        spawners.put(SpawnClass.ZOMBIE,
                (player, coordinate) -> {
                    Enemy e = Enemy.builder(Zombie::new)
                            .setPosition(coordinate)
                            .setBehavior(MobWithTarget.builder(RandomWalkBehavior::new))
                            .setRadius(10000)
                            .setTarget(player)
                            .build();
                }
        );
        spawners.put(SpawnClass.SLIME,
                (player, coordinate) -> {
                    Enemy e = Enemy.builder(Slime::new)
                            .setPosition(coordinate)
                            .setBehavior(MobWithTarget.builder(CowardlyBehavior::new))
                            .setRadius(10000)
                            .setTarget(player)
                            .build();
                }
        );
        spawners.put(SpawnClass.MED_KIT_S, (p, coordinate) -> new MedKitSmall().setPosition(coordinate));
        spawners.put(SpawnClass.MED_KIT_B, (p, coordinate) -> new MedKitBig().setPosition(coordinate));
        spawners.put(SpawnClass.MED_KIT_M, (p, coordinate) -> new MedKitMedium().setPosition(coordinate));
    }

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
            spawners.get(SpawnClass.values()[random.nextInt(SpawnClass.values().length)])
                    .accept(player, new IntCoordinate(tile.getX(), tile.getY()));
        }
    }

    private enum SpawnClass {
        ZOMBIE,
        SLIME,
        MED_KIT_S,
        MED_KIT_M,
        MED_KIT_B
    }
}
