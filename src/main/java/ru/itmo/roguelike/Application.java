package ru.itmo.roguelike;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.itmo.roguelike.ioc.IOModule;
import ru.itmo.roguelike.ioc.ManagersModule;
import ru.itmo.roguelike.ioc.RenderModule;
import ru.itmo.roguelike.manager.gamemanager.GameManager;
import ru.itmo.roguelike.settings.GameSettings;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.concurrent.*;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.FILES_ONLY;

public class Application {
    public static void main(String[] args) {
        GameSettings.FILENAME = chooseMapFileName();
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

    public static @Nullable String chooseMapFileName() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().endsWith(".mapfile");
            }

            @Override
            public String getDescription() {
                return "Map files";
            }
        });

        fc.setFileSelectionMode(FILES_ONLY);
        fc.setApproveButtonText("LOAD");

        if (APPROVE_OPTION == fc.showOpenDialog(null)) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        return null;
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
