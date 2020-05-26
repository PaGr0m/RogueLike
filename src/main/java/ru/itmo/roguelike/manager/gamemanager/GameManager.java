package ru.itmo.roguelike.manager.gamemanager;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.mobs.Slime;
import ru.itmo.roguelike.characters.mobs.Zombie;
import ru.itmo.roguelike.characters.mobs.strategy.AggressiveBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.CowardlyBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.MobPositionGenerator;
import ru.itmo.roguelike.input.Event;
import ru.itmo.roguelike.input.InputHandler;
import ru.itmo.roguelike.manager.actormanager.ActorManager;
import ru.itmo.roguelike.manager.actormanager.ProjectileManager;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.RenderEngine;
import ru.itmo.roguelike.settings.GameSettings;
import ru.itmo.roguelike.utils.Pair;

public class GameManager {
    private final InputHandler inputHandler;
    private final RenderEngine renderEngine;
    private final ActorManager actorManager;
    private final Camera camera;
    private final ProjectileManager projectileManager;

    private GameState gameState;
    private Player player;
    private Field field;

    public GameManager(InputHandler inputHandler,
                       RenderEngine renderEngine,
                       ActorManager actorManager,
                       Camera camera) {
        this.inputHandler = inputHandler;
        this.renderEngine = renderEngine;
        this.actorManager = actorManager;
        this.camera = camera;
        this.projectileManager = new ProjectileManager();
    }

    public void start() {
        gameState = GameState.RUNNING;

        MobPositionGenerator mobGenerator = new MobPositionGenerator(player);
        field = new Field(800, 600, 1, 1, mobGenerator); // FIXme: set real w/h
        player = new Player();

        mobGenerator.setPlayer(player);

        player.setX(400);
        player.setY(400);

        CollideManager.register(player);

        // Effects
//        player.activateMoveEffect(MoverEmbarrassment::new);
//        player.deactivateMoveEffect(MoverEmbarrassment.class);

//        Enemy[] zombies = new Enemy[]{
//                Enemy.builder(Zombie::new)
//                        .setPosition(130, 200)
//                        .setBehavior(MobWithTarget.builder(AggressiveBehavior::new))
//                        .setRadius(10000)
//                        .setTarget(player)
//                        .build(),
//
//                Enemy.builder(Zombie::new)
//                        .setPosition(150, 200)
//                        .setBehavior(MobWithTarget.builder(AggressiveBehavior::new))
//                        .setRadius(10000)
//                        .setTarget(player)
//                        .build(),
//
//                Enemy.builder(Slime::new)
//                        .setPosition(170, 250)
//                        .setBehavior(MobWithTarget.builder(CowardlyBehavior::new))
//                        .setRadius(10000)
//                        .setTarget(player)
//                        .build(),
//
//                Enemy.builder(Zombie::new)
//                        .setPosition(400, 500)
//                        .setBehavior(MobWithTarget.builder(AggressiveBehavior::new))
//                        .setRadius(10000)
//                        .setTarget(player)
//                        .build(),
//        };

        inputHandler.registerEventListener(Event.MOVE_UP, () -> player.go(0, -GameSettings.STEP, field));
        inputHandler.registerEventListener(Event.MOVE_DOWN, () -> player.go(0, GameSettings.STEP, field));
        inputHandler.registerEventListener(Event.MOVE_LEFT, () -> player.go(-GameSettings.STEP, 0, field));
        inputHandler.registerEventListener(Event.MOVE_RIGHT, () -> player.go(GameSettings.STEP, 0, field));

        inputHandler.registerEventListener(Event.FIRE_UP, () -> {
            Fireball fireball = new Fireball(new Pair<>(0, -1));
            fireball.setX(player.getX());
            fireball.setY(player.getY());
            fireball.go(field);
        });
        inputHandler.registerEventListener(Event.FIRE_LEFT, () -> {
            Fireball fireball = new Fireball(new Pair<>(-1, 0));
            fireball.setX(player.getX());
            fireball.setY(player.getY());
            fireball.go(field);
        });
        inputHandler.registerEventListener(Event.FIRE_RIGHT, () -> {
            Fireball fireball = new Fireball(new Pair<>(1, 0));
            fireball.setX(player.getX());
            fireball.setY(player.getY());
            fireball.go(field);
        });
        inputHandler.registerEventListener(Event.FIRE_DOWN, () -> {
            Fireball fireball = new Fireball(new Pair<>(0, 1));
            fireball.setX(player.getX());
            fireball.setY(player.getY());
            fireball.go(field);
        });
    }

    public boolean isGameRunning() {
        return gameState == GameState.RUNNING;
    }

    public void step() {
        inputHandler.handleInputs();
        CollideManager.collideAll();
        renderEngine.render();
        projectileManager.actAll(field);
        actorManager.actAll(field);
        renderEngine.render();
        camera.update(player.getX() - 400, player.getY() - 300);
        field.process(camera.getPosX() + 400, camera.getPosY() + 300);
    }

    public Player getPlayer() {
        return player;
    }
}
