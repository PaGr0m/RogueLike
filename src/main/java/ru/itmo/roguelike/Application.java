package ru.itmo.roguelike;

import ru.itmo.roguelike.settings.GameSettings;
import ru.itmo.roguelike.input.InputHandlerImpl;
import ru.itmo.roguelike.manager.actormanager.MobManager;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.manager.gamemanager.GameManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.JexerRenderEngine;
import ru.itmo.roguelike.render.RenderScheduler;

import java.util.Timer;

public class Application {
    public void run() {
        InputHandlerImpl inputHandler = new InputHandlerImpl();
        Camera camera = new Camera();

        GameManager gameManager = new GameManager(
                inputHandler,
                new JexerRenderEngine(800, 600, inputHandler, camera),
                new MobManager(),
                camera
        );
        gameManager.start();
        

        RenderScheduler renderScheduler = new RenderScheduler(gameManager);
        Timer timer = new Timer();
        timer.schedule(renderScheduler, 0, 1000 / GameSettings.FPS);
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }
}
