package ru.itmo.roguelike.manager.gamemanager;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.input.Event;
import ru.itmo.roguelike.input.InputHandler;
import ru.itmo.roguelike.manager.actormanager.ActorManager;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.map.Map;
import ru.itmo.roguelike.render.RenderEngine;

public class GameManager {
    private GameState gameState;
    private final InputHandler inputHandler;
    private final RenderEngine renderEngine;
    private final ActorManager actorManager;
    private final CollideManager collideManager;

    private Player player;
    private Map map;

    public GameManager(InputHandler inputHandler, RenderEngine renderEngine, ActorManager actorManager, CollideManager collideManager) {
        this.inputHandler = inputHandler;
        this.renderEngine = renderEngine;
        this.actorManager = actorManager;
        this.collideManager = collideManager;
    }

    public void start() {
        gameState = GameState.RUNNING;
        map = new Map(800, 600, 1, 1, collideManager); // FIXme: set real w/h
        player = new Player();

        player.setX(300);
        player.setY(400);

        collideManager.register(player);

        inputHandler.registerEventListener(Event.MOVE_UP, () -> player.go(0, -5));
        inputHandler.registerEventListener(Event.MOVE_DOWN, () -> player.go(0, 5));
        inputHandler.registerEventListener(Event.MOVE_LEFT, () -> player.go(-5, 0));
        inputHandler.registerEventListener(Event.MOVE_RIGHT, () -> player.go(5, 0));
    }

    public boolean isGameRunning() {
        return gameState == GameState.RUNNING;
    }

    public void step() {
        collideManager.collideAll();
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
