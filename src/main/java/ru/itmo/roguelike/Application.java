package ru.itmo.roguelike;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.ioc.IOModule;
import ru.itmo.roguelike.ioc.ManagersModule;
import ru.itmo.roguelike.ioc.RenderModule;
import ru.itmo.roguelike.manager.gamemanager.GameManager;
import ru.itmo.roguelike.settings.GameSettings;

import java.util.concurrent.*;

public class Application {
    public static void main(String[] args) {
        if (args.length > 0) {
            GameSettings.FILENAME = args[0];
        }

        Application application = new Application();
        application.run();
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
