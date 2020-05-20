package ru.itmo.roguelike.manager.gamemanager;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.input.Event;
import ru.itmo.roguelike.input.InputHandler;
import ru.itmo.roguelike.manager.actormanager.ActorManager;
import ru.itmo.roguelike.render.RenderEngine;

public class GameManager {
    private GameState gameState;
    private final InputHandler inputHandler;
    private final RenderEngine renderEngine;
    private final ActorManager actorManager;

    private Player player;

    public GameManager(InputHandler inputHandler, RenderEngine renderEngine, ActorManager actorManager) {
        this.inputHandler = inputHandler;
        this.renderEngine = renderEngine;
        this.actorManager = actorManager;
    }

    public void start() {
        gameState = GameState.RUNNING;
        player = new Player();

        player.setPositionX(30);
        player.setPositionY(100);

        inputHandler.registerEventListener(Event.MOVE_UP, () -> player.go(0, -1));
        inputHandler.registerEventListener(Event.MOVE_DOWN, () -> player.go(0, 1));
        inputHandler.registerEventListener(Event.MOVE_LEFT, () -> player.go(-1, 0));
        inputHandler.registerEventListener(Event.MOVE_RIGHT, () -> player.go(1, 0));
    }

    public boolean isGameRunning() {
        return gameState == GameState.RUNNING;
    }

    public void step() {
        inputHandler.handleInputs();
        renderEngine.render();
        actorManager.actAll();
        renderEngine.render();
    }

    public Player getPlayer() {
        return  player;
    }
}
