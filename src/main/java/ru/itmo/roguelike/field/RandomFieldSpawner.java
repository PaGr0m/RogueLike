package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.utils.IntCoordinate;
import ru.itmo.roguelike.utils.MathUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static ru.itmo.roguelike.field.Spawner.EntityClass.*;

/**
 * Mob and Collectible spawner
 */
public class RandomFieldSpawner {
    private static final int SAFE_RADIUS = 150;
    private static final Map<Spawner.EntityClass, Integer> probs = new HashMap<>();
    private static final int sumProbs;

    private final Random random;
    private Player player;

    public RandomFieldSpawner(Player player) {
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

        boolean farEnoughFromPlayer = delta.lenL2() > SAFE_RADIUS;
        boolean randomDecision = random.nextInt(100) > 98;
        boolean goodGridPosition = x % 100 < 50 && y % 100 < 50;

        if (goodGridPosition && randomDecision && farEnoughFromPlayer) {
            Spawner.spawners.get(getRandomClass()).accept(player, new IntCoordinate(tile.getX(), tile.getY()));
        }
    }

    static {
        probs.put(ZOMBIE, 100);
        probs.put(SLIME, 80);
        probs.put(MED_KIT_S, 20);
        probs.put(MED_KIT_M, 10);
        probs.put(MED_KIT_B, 5);
        probs.put(TELEPORT, 10);
        probs.put(TUNIC, 1);
        probs.put(JACKET, 5);
        probs.put(COWL, 2);

        sumProbs = probs.values().stream().reduce(Integer::sum).orElse(0);
    }

    private static Spawner.EntityClass getRandomClass() {
        int idx = MathUtils.getRandomInt(0, sumProbs);
        for (Map.Entry<Spawner.EntityClass, Integer> cls : probs.entrySet()) {
            idx -= cls.getValue();
            if (idx <= 0) {
                return cls.getKey();
            }
        }
        return null;
    }
}
