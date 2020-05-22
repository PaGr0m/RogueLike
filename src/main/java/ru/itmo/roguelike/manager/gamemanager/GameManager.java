package ru.itmo.roguelike.manager.gamemanager;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.mobs.Slime;
import ru.itmo.roguelike.characters.mobs.Zombie;
import ru.itmo.roguelike.characters.mobs.strategy.AggressiveBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.CowardlyBehavior;
import ru.itmo.roguelike.input.Event;
import ru.itmo.roguelike.input.InputHandler;
import ru.itmo.roguelike.manager.actormanager.ActorManager;
import ru.itmo.roguelike.map.Map;
import ru.itmo.roguelike.render.RenderEngine;

import java.lang.reflect.InvocationTargetException;

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

        Enemy[] zombies = new Enemy[]{
                Enemy.build(Zombie.class, player)
                        .setPosition(130, 200)
                        .setBehavior(AggressiveBehavior.class)
                        .setTarget(player)
                        .build(),

                Enemy.build(Zombie.class, player)
                        .setPosition(150, 200)
                        .setBehavior(AggressiveBehavior.class)
                        .setTarget(player)
                        .build(),

                Enemy.build(Slime.class, player)
                        .setPosition(170, 250)
                        .setBehavior(CowardlyBehavior.class)
                        .setTarget(player)
                        .build(),

                Enemy.build(Zombie.class, player)
                        .setPosition(400, 500)
                        .setBehavior(AggressiveBehavior.class)
                        .setTarget(player)
                        .build(),
        };

        inputHandler.registerEventListener(Event.MOVE_UP, () -> player.go(0, -20));
        inputHandler.registerEventListener(Event.MOVE_DOWN, () -> player.go(0, 20));
        inputHandler.registerEventListener(Event.MOVE_LEFT, () -> player.go(-20, 0));
        inputHandler.registerEventListener(Event.MOVE_RIGHT, () -> player.go(20, 0));
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

    public Player getPlayer() {
        return player;
    }
}
