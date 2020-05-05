package ru.itmo.roguelike.manager.gamemanager;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.input.Event;
import ru.itmo.roguelike.input.InputHandler;
import ru.itmo.roguelike.manager.actormanager.ActorManager;
import ru.itmo.roguelike.map.Map;
import ru.itmo.roguelike.render.RenderEngine;

public class GameManager {
    private GameState gameState;
    private final InputHandler inputHandler;
    private final RenderEngine renderEngine;
    private final ActorManager actorManager;

    private Player player;
    private Map map;

    public GameManager(InputHandler inputHandler, RenderEngine renderEngine, ActorManager actorManager) {
        this.inputHandler = inputHandler;
        this.renderEngine = renderEngine;
        this.actorManager = actorManager;
    }

    public void start() {
        gameState = GameState.RUNNING;
        map = new Map(600, 600, 1, 1); // FIXme: set real w/h
        player = new Player();

        player.setPositionX(30);
        player.setPositionY(100);

        inputHandler.registerEventListener(Event.MOVE_UP, () -> player.go(0, -50));
        inputHandler.registerEventListener(Event.MOVE_DOWN, () -> player.go(0, 50));
        inputHandler.registerEventListener(Event.MOVE_LEFT, () -> player.go(-50, 0));
        inputHandler.registerEventListener(Event.MOVE_RIGHT, () -> player.go(50, 0));
    }

    public boolean isGameRunning() {
        return gameState == GameState.RUNNING;
    }

    public void step() {
        inputHandler.handleInputs();
        renderEngine.render();
        actorManager.actAll();
        renderEngine.render();
        map.process(player.getPositionX(), player.getPositionY()); // TODO: replace player with camera
    }
}
