package ru.itmo.roguelike.manager.gamemanager;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.input.Event;
import ru.itmo.roguelike.input.InputHandler;
import ru.itmo.roguelike.manager.actormanager.ActorManager;
import ru.itmo.roguelike.map.Map;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.RenderEngine;

public class GameManager {
    private GameState gameState;
    private final InputHandler inputHandler;
    private final RenderEngine renderEngine;
    private final ActorManager actorManager;
    private final Camera camera;

    private Player player;
    private Map map;

    public GameManager(InputHandler inputHandler,
                       RenderEngine renderEngine,
                       ActorManager actorManager,
                       Camera camera)
    {
        this.inputHandler = inputHandler;
        this.renderEngine = renderEngine;
        this.actorManager = actorManager;
        this.camera = camera;
    }

    public void start() {
        gameState = GameState.RUNNING;
        map = new Map(800, 600, 2, 2); // FIXme: set real w/h
        player = new Player();

        player.setX(400);
        player.setY(300);

        // FIXME: MAGIC NUMBERS
        inputHandler.registerEventListener(Event.MOVE_UP, () -> {
            player.go(0, -5);
            camera.moveY(5);
        });
        inputHandler.registerEventListener(Event.MOVE_DOWN, () -> {
            player.go(0, 5);
            camera.moveY(-5);
        });
        inputHandler.registerEventListener(Event.MOVE_LEFT, () -> {
            player.go(-5, 0);
            camera.moveX(5);
        });
        inputHandler.registerEventListener(Event.MOVE_RIGHT, () -> {
            player.go(5, 0);
            camera.moveX(-5);
        });
    }

    public boolean isGameRunning() {
        return gameState == GameState.RUNNING;
    }

    public void step() {
        inputHandler.handleInputs();
        renderEngine.render();
        actorManager.actAll();
        renderEngine.render();
        map.process(player.getX(), player.getY()); // TODO: replace player with camera
    }

    public Player getPlayer() {
        return  player;
    }
}
