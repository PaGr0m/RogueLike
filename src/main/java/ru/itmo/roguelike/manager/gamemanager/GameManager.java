package ru.itmo.roguelike.manager.gamemanager;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.field.AutoGeneratedField;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.FiniteField;
import ru.itmo.roguelike.field.MobPositionGenerator;
import ru.itmo.roguelike.input.Event;
import ru.itmo.roguelike.input.InputHandler;
import ru.itmo.roguelike.manager.actormanager.ActorManager;
import ru.itmo.roguelike.manager.actormanager.ProjectileManager;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.RenderEngine;
import ru.itmo.roguelike.settings.GameSettings;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.nio.file.Paths;
import java.util.Random;

public class GameManager {
    private static final Random random = new Random();
    public static long GLOBAL_TIME = 0;
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

    public void reset() {
        field.reInit(player.getPosition().getX(), player.getPosition().getY());
        while (field.getTileType(player.getPosition()).isSolid()) {
            player.getPosition().setX(random.nextInt(1_000_000) - 500_000);
            player.getPosition().setY(random.nextInt(1_000_000) - 500_000);
            field.reInit(player.getPosition().getX(), player.getPosition().getY());
        }

        camera.moveForce(player.getPosition().getX(), player.getPosition().getY());
    }

    public void start() {
        gameState = GameState.RUNNING;

        MobPositionGenerator mobGenerator = new MobPositionGenerator(player);
        field = new AutoGeneratedField(800, 600, 1, 1, mobGenerator); // FIXme: set real w/h
//        field = new FiniteField(Paths.get("test.mapfile"));
        player = new Player();

        field.setDefaultPosToPlayer(player);

        mobGenerator.setPlayer(player);

        player.getPosition().setX(400);
        player.getPosition().setY(400);

        CollideManager.register(player);

        inputHandler.registerEventListener(Event.MOVE_UP, () -> player.move(new IntCoordinate(0, -GameSettings.STEP)));
        inputHandler.registerEventListener(Event.MOVE_DOWN, () -> player.move(new IntCoordinate(0, GameSettings.STEP)));
        inputHandler.registerEventListener(Event.MOVE_LEFT, () -> player.move(new IntCoordinate(-GameSettings.STEP, 0)));
        inputHandler.registerEventListener(Event.MOVE_RIGHT, () -> player.move(new IntCoordinate(GameSettings.STEP, 0)));

        inputHandler.registerEventListener(Event.FIRE_UP, () -> player.attack(new IntCoordinate(0, -1)));
        inputHandler.registerEventListener(Event.FIRE_LEFT, () -> player.attack(new IntCoordinate(-1, 0)));
        inputHandler.registerEventListener(Event.FIRE_RIGHT, () -> player.attack(new IntCoordinate(1, 0)));
        inputHandler.registerEventListener(Event.FIRE_DOWN, () -> player.attack(new IntCoordinate(0, 1)));

        inputHandler.registerEventListener(Event.RESTART, player::die);
    }

    public boolean isGameRunning() {
        return gameState == GameState.RUNNING;
    }

    public void step() {
        GLOBAL_TIME++;
        inputHandler.handleInputs();
        CollideManager.collideAll();
        renderEngine.render();
        projectileManager.actAll(field);
        actorManager.actAll(field);
        player.act(field);
        renderEngine.render();
        IntCoordinate tmp = new IntCoordinate(-400, -300);
        tmp.add(player.getPosition());
        camera.update(tmp);
        field.process(camera.getCenter());
    }

    public Player getPlayer() {
        return player;
    }
}
