package ru.itmo.roguelike.manager.gamemanager;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.mobs.Slime;
import ru.itmo.roguelike.characters.mobs.Zombie;
import ru.itmo.roguelike.characters.mobs.strategy.AggressiveBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.CowardlyBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.input.Event;
import ru.itmo.roguelike.input.InputHandler;
import ru.itmo.roguelike.manager.actormanager.ActorManager;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.map.Map;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.RenderEngine;
import ru.itmo.roguelike.settings.GameSettings;

public class GameManager {
    private final InputHandler inputHandler;
    private final RenderEngine renderEngine;
    private final ActorManager actorManager;
    private final Camera camera;
    private final CollideManager collideManager;

    private GameState gameState;
    private Player player;
    private Map map;

    public GameManager(InputHandler inputHandler,
                       RenderEngine renderEngine,
                       ActorManager actorManager,
                       CollideManager collideManager,
                       Camera camera) {
        this.inputHandler = inputHandler;
        this.renderEngine = renderEngine;
        this.actorManager = actorManager;
        this.collideManager = collideManager;
        this.camera = camera;
    }

    public void start() {
        gameState = GameState.RUNNING;
        map = new Map(800, 600, 2, 2, collideManager); // FIXme: set real w/h
        player = new Player();

        player.setX(400);
        player.setY(400);

        collideManager.register(player);

        // Effects
//        player.activateMoveEffect(MoverEmbarrassment::new);
//        player.deactivateMoveEffect(MoverEmbarrassment.class);

        Enemy[] zombies = new Enemy[]{
                Enemy.builder(Zombie::new)
                        .setPosition(130, 200)
                        .setBehavior(MobWithTarget.builder(AggressiveBehavior::new))
                        .setRadius(10000)
                        .setTarget(player)
                        .build(),

                Enemy.builder(Zombie::new)
                        .setPosition(150, 200)
                        .setBehavior(MobWithTarget.builder(AggressiveBehavior::new))
                        .setRadius(10000)
                        .setTarget(player)
                        .build(),

                Enemy.builder(Slime::new)
                        .setPosition(170, 250)
                        .setBehavior(MobWithTarget.builder(CowardlyBehavior::new))
                        .setRadius(10000)
                        .setTarget(player)
                        .build(),

                Enemy.builder(Zombie::new)
                        .setPosition(400, 500)
                        .setBehavior(MobWithTarget.builder(AggressiveBehavior::new))
                        .setRadius(10000)
                        .setTarget(player)
                        .build(),
        };

        inputHandler.registerEventListener(Event.MOVE_UP, () -> {
            player.go(0, -GameSettings.STEP);
            camera.setPosY(player.getY() - 300);
        });
        inputHandler.registerEventListener(Event.MOVE_DOWN, () -> {
            player.go(0, GameSettings.STEP);
            camera.setPosY(player.getY() - 300);
        });
        inputHandler.registerEventListener(Event.MOVE_LEFT, () -> {
            player.go(-GameSettings.STEP, 0);
            camera.setPosX(player.getX() - 400);
        });
        inputHandler.registerEventListener(Event.MOVE_RIGHT, () -> {
            player.go(GameSettings.STEP, 0);
            camera.setPosX(player.getX() - 400);
        });
    }

    public boolean isGameRunning() {
        return gameState == GameState.RUNNING;
    }

    public void step() {
        inputHandler.handleInputs();
        collideManager.collideAll();
        renderEngine.render();
        actorManager.actAll();
        renderEngine.render();
        camera.update();
        map.process(camera.getPosX(), camera.getPosY());
    }

    public Player getPlayer() {
        return player;
    }
}
