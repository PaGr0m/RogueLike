package ru.itmo.roguelike;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.ioc.IOModule;
import ru.itmo.roguelike.ioc.ManagersModule;
import ru.itmo.roguelike.ioc.RenderModule;
import ru.itmo.roguelike.manager.gamemanager.GameManager;
import ru.itmo.roguelike.settings.GameSettings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.*;

public class Application {
    private static boolean RUN_GAME = false;

    public static void main(String[] args) throws IOException {
        LaunchWindow.createAndShow(GameSettings.MAP_FILE_CHOOSE).getPromiseAsOptional().ifPresent(
                opt -> {
                    GameSettings.MAP_FILE_NAME = opt.orElse(null);
                    RUN_GAME = true;
                    LaunchWindow.createAndShow(GameSettings.SAVE_FILE_CHOOSE).getPromiseAsOptional().ifPresent(
                            saveFileName -> GameSettings.SAVE_FILE_NAME = saveFileName.orElse(null)
                    );
                }
        );

        if (GameSettings.SAVE_FILE_NAME == null) {
            Files.deleteIfExists(Paths.get(GameSettings.getSaveFileName()));
        }

        if (RUN_GAME) {
            new Application().run();
        }

        System.exit(0);
    }

    public void run() {
        Injector injector = Guice.createInjector(
                new IOModule(),
                new ManagersModule(),
                new RenderModule()
        );
        GameManager gameManager = injector.getInstance(GameManager.class);
        gameManager.start();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        try {
            rescheduleGameLoop(executorService, gameManager);
        } catch (Exception e) {
            if (!(e.getCause() instanceof RejectedExecutionException)) {
                e.printStackTrace();
            }
        }
    }

    private void rescheduleGameLoop(
            @NotNull ScheduledExecutorService executorService,
            @NotNull GameManager gameManager
    ) throws ExecutionException, InterruptedException {
        executorService.scheduleAtFixedRate(
                gameManager::step,
                0,
                1000 / GameSettings.FPS,
                TimeUnit.MILLISECONDS
        ).get();
    }
}
