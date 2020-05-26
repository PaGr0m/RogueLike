package ru.itmo.roguelike;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.exceptions.DieException;
import ru.itmo.roguelike.input.InputHandlerImpl;
import ru.itmo.roguelike.manager.actormanager.MobManager;
import ru.itmo.roguelike.manager.gamemanager.GameManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.JexerRenderEngine;
import ru.itmo.roguelike.render.RenderScheduler;
import ru.itmo.roguelike.settings.GameSettings;

import java.util.concurrent.*;

public class Application {
    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

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
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        while (rescheduleGameLoop(executorService, renderScheduler)) {
            gameManager.reset();
        }
    }

    private boolean rescheduleGameLoop(
            @NotNull ScheduledExecutorService executorService,
            @NotNull RenderScheduler renderScheduler
    ) {
        ScheduledFuture<?> handle = executorService.scheduleAtFixedRate(
                renderScheduler,
                0,
                1000 / GameSettings.FPS,
                TimeUnit.MILLISECONDS
        );

        try {
            handle.get();
        } catch (InterruptedException | ExecutionException e) {
            return e.getCause() instanceof DieException;
        }

        return false;
    }
}
