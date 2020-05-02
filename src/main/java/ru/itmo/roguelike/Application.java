package ru.itmo.roguelike;

import ru.itmo.roguelike.input.InputHandlerImpl;
import ru.itmo.roguelike.manager.actormanager.MobManager;
import ru.itmo.roguelike.manager.gamemanager.GameManager;
import ru.itmo.roguelike.render.JexerRenderEngine;

public class Application {
    public void run() {
        InputHandlerImpl inputHandler = new InputHandlerImpl();
        GameManager gameManager = new GameManager(
                inputHandler,
                new JexerRenderEngine(800, 600, inputHandler),
                new MobManager()
        );
        gameManager.start();

        while (gameManager.isGameRunning()) {
            gameManager.step();
        }
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }
}
