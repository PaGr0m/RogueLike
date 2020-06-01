package ru.itmo.roguelike;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.exceptions.DieException;
import ru.itmo.roguelike.ioc.IOModule;
import ru.itmo.roguelike.ioc.ManagersModule;
import ru.itmo.roguelike.ioc.RenderModule;
import ru.itmo.roguelike.manager.actormanager.MobManager;
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
    }

    public void run() {
        Injector injector = Guice.createInjector(
                new IOModule(),
                new ManagersModule(),
                new RenderModule()
        );
        GameManager gameManager = injector.getInstance(GameManager.class);
        gameManager.start();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        while (rescheduleGameLoop(executorService, () -> {
            if (gameManager.isGameRunning()) {
                gameManager.step();
            }
        })) {
            gameManager.reset();
        }
    }

    private boolean rescheduleGameLoop(
            @NotNull ScheduledExecutorService executorService,
            @NotNull Runnable runnable
    ) {
        ScheduledFuture<?> handle = executorService.scheduleAtFixedRate(
                runnable,
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
