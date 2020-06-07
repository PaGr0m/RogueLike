package ru.itmo.roguelike.manager.actormanager;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.mobs.PersonX;
import ru.itmo.roguelike.characters.mobs.strategy.BossBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.manager.gamemanager.GameManager;
import ru.itmo.roguelike.utils.IntCoordinate;

public class BossManager {
    private final GameManager gameManager;

    public BossManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void createBoss() {
        Field field = gameManager.getField();
        Player player = gameManager.getPlayer();

        IntCoordinate bossPosition = getFreeBossCoordinate(
                field,
                player.getPosition().getX(),
                player.getPosition().getY()
        );

        Enemy.builder(PersonX::new)
                .setPosition(bossPosition)
                .setBehavior(MobWithTarget.builder(() -> new BossBehavior(field)))
                .setRadius(10000000)
                .setTarget(player)
                .createAndRegister();
    }

    @NotNull
    private IntCoordinate getFreeBossCoordinate(@NotNull Field field, int x, int y) {
        final int cellSize = 100;

        for (int k = 2; ; k++) {
            for (int j = -k; j < k; j++) {
                for (IntCoordinate coordinate : new IntCoordinate[]{
                        new IntCoordinate(x + k * cellSize, y + j * cellSize),
                        new IntCoordinate(x - k * cellSize, y + j * cellSize),
                        new IntCoordinate(x + j * cellSize, y + k * cellSize),
                        new IntCoordinate(x + j * cellSize, y - k * cellSize),
                }) {
                    if (!field.getTileType(coordinate).isSolid()) {
                        return new IntCoordinate(coordinate);
                    }
                }
            }
        }
    }
}
