package ru.itmo.roguelike.manager.gamemanager;

import ru.itmo.roguelike.LaunchWindow;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.field.AutoGeneratedField;
import ru.itmo.roguelike.field.Field;
import ru.itmo.roguelike.field.FiniteField;
import ru.itmo.roguelike.input.Event;
import ru.itmo.roguelike.input.EventStatus;
import ru.itmo.roguelike.input.InputHandler;
import ru.itmo.roguelike.ioc.ManagersModule;
import ru.itmo.roguelike.ioc.RenderModule;
import ru.itmo.roguelike.manager.actormanager.ActorManager;
import ru.itmo.roguelike.manager.actormanager.ProjectileManager;
import ru.itmo.roguelike.manager.collidemanager.CollideManager;
import ru.itmo.roguelike.manager.eventmanager.EventManager;
import ru.itmo.roguelike.render.Camera;
import ru.itmo.roguelike.render.RenderEngine;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.render.particles.MovingUpText;
import ru.itmo.roguelike.render.particles.TextWithPoint;
import ru.itmo.roguelike.settings.GameSettings;
import ru.itmo.roguelike.utils.IntCoordinate;

import javax.inject.Inject;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

import static ru.itmo.roguelike.input.Event.*;
import static ru.itmo.roguelike.input.EventStatus.PRESSED_CTRL;

public class GameManager {
    public static long GLOBAL_TIME = 0;
    private final InputHandler inputHandler;
    private final RenderEngine renderEngine;
    private final ActorManager actorManager;
    private final Camera camera;
    private final ProjectileManager projectileManager;
    private final Player player;
    private final GameStateHandler state = new GameStateHandler();
    private final EventManager eventManager;
    private Field field;

    @Inject
    public GameManager(
            Player player,
            @ManagersModule.MobManager ActorManager mobManager,
            InputHandler inputHandler,
            @RenderModule.Jexer RenderEngine renderEngine,
            Camera camera,
            ProjectileManager projectileManager,
            EventManager eventManager
    ) {
        this.player = player;
        this.actorManager = mobManager;
        this.inputHandler = inputHandler;
        this.renderEngine = renderEngine;
        this.camera = camera;
        this.projectileManager = projectileManager;
        this.eventManager = eventManager;
    }

    private static void deleteSave() {
        try {
            Files.deleteIfExists(Paths.get(GameSettings.getSaveFileName()));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void reset() {
        player.reborn();
        field.setDefaultPosToPlayer(player);
        player.die();
        resetGameState();
    }

    private void resetGameState() {
        player.fixPosition(field);
        state.run();
        Drawable.unregisterAllForeground();
        CollideManager.unregisterAll();
        CollideManager.register(player);
        Drawable.register(player);
        camera.moveForce(player.getPosition());
        actorManager.killAll();
        projectileManager.killAll();
        eventManager.clear();
        player.registerDrawableEvents();
        field.resetEntities();
    }

    public void start() {
        state.run();

        field = GameSettings.MAP_FILE_NAME == null
                ? new AutoGeneratedField(1, player)
                : new FiniteField(Paths.get(GameSettings.MAP_FILE_NAME), player);

        CollideManager.register(player);

        setUpControls();
        reset();
        loadGame();
    }

    private void setUpControls() {
        inputHandler.registerEventListener(Event.MOVE_UP,
                state.runIfNotPaused(s -> player.move(new IntCoordinate(0, -GameSettings.STEP))));
        inputHandler.registerEventListener(Event.MOVE_DOWN,
                state.runIfNotPaused(s -> player.move(new IntCoordinate(0, GameSettings.STEP))));
        inputHandler.registerEventListener(Event.MOVE_LEFT,
                state.runIfNotPaused(s -> player.move(new IntCoordinate(-GameSettings.STEP, 0))));
        inputHandler.registerEventListener(Event.MOVE_RIGHT,
                state.runIfNotPaused(s -> player.move(new IntCoordinate(GameSettings.STEP, 0))));

        inputHandler.registerEventListener(Event.FIRE_UP,
                state.runIfNotPaused(s -> player.attack(new IntCoordinate(0, -1))));
        inputHandler.registerEventListener(Event.FIRE_LEFT,
                state.runIfNotPaused(s -> player.attack(new IntCoordinate(-1, 0))));
        inputHandler.registerEventListener(Event.FIRE_RIGHT,
                state.runIfNotPaused(s -> player.attack(new IntCoordinate(1, 0))));
        inputHandler.registerEventListener(Event.FIRE_DOWN,
                state.runIfNotPaused(s -> player.attack(new IntCoordinate(0, 1))));

        inputHandler.registerEventListener(Event.RESTART, state.runIfNotPaused(s -> state.restart()));

        inputHandler.registerEventListener(SAVE, status -> saveGame());

        inputHandler.registerEventListener(Event.PAUSE, s -> {
            synchronized (state) {
                if (state.isPaused()) {
                    state.resume();
                } else {
                    state.pause();
                }
            }
        });

        List<Event> events = Arrays.asList(USE_1, USE_2, USE_3, USE_4, USE_5, USE_6, USE_7, USE_8);
        for (int i = 0; i < events.size(); i++) {
            int index = i;
            inputHandler.registerEventListener(events.get(i), state.runIfNotPaused(status -> {
                if (status.flagIsSet(PRESSED_CTRL)) {
                    player.dropItem(index, field);
                } else {
                    player.useFromInventory(index);
                }
            }));
        }

        inputHandler.registerEventListener(Event.EXIT, s -> state.gameOver());
    }

    public void step() {
        inputHandler.handleInputs();

        if (state.isPaused()) {
            renderEngine.renderPause();
        } else {
            GLOBAL_TIME++;
            CollideManager.collideAll();
            renderEngine.render();
            projectileManager.actAll(field);
            actorManager.actAll(field);
            player.act(field);
            camera.update(player.getPosition());
            field.process(camera.getCenter());
            eventManager.actAll();
        }

        state.process();
    }

    public void saveGame() {
        String filename = GameSettings.getSaveFileName();
        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(filename))) {
            player.saveToFile(output);
            player.getInventory().saveToFile(output);

            new TextWithPoint(player.getPosition(), "SAVED HERE", Color.RED);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void loadGame() {
        String filename = GameSettings.getSaveFileName();
        try (DataInputStream input = new DataInputStream(new FileInputStream(filename))) {

            player.loadFromFile(input);
            player.getInventory().reLoadFromFile(input, player);
            resetGameState();

            new MovingUpText(player.getPosition(),
                    "LOADED SAVE\n     (" + GameSettings.simplify(filename) + ")", Color.RED);
        } catch (IOException e) {
            new MovingUpText(player.getPosition(), "NEW GAME", Color.RED);
        }
    }

    public Player getPlayer() {
        return player;
    }

    private class GameStateHandler {
        private GameState state = GameState.RUNNING;

        public synchronized void run() {
            state = GameState.RUNNING;
        }

        public synchronized void gameOver() {
            state = GameState.GAME_OVER;
        }

        public synchronized void restart() {
            state = GameState.RESTARTING;
        }

        public synchronized void pause() {
            state = GameState.PAUSED;
        }

        public synchronized void resume() {
            state = GameState.RUNNING;
        }

        public boolean isPaused() {
            return state == GameState.PAUSED;
        }

        public synchronized Consumer<EventStatus> runIfNotPaused(Consumer<EventStatus> runnable) {
            return status -> {
                if (!isPaused()) {
                    runnable.accept(status);
                }
            };
        }

        public synchronized void process() {
            switch (state) {
                case RUNNING:
                    if (player.isDead()) {
                        reset();
                        deleteSave();
                    }
                    break;
                case RESTARTING:
                    reset();
                    state = GameState.RUNNING;
                    break;
                case GAME_OVER:
                    state = GameState.EXITING;
                    LaunchWindow.createAndShow(GameSettings.ON_EXIT_SAVE_FILE_CHOOSE)
                            .getPromiseAsOptional().flatMap(Function.identity())
                            .ifPresent(str -> {
                                GameSettings.SAVE_FILE_NAME = str;
                                saveGame();
                            });
                    throw new RejectedExecutionException();
                default:
            }
        }
    }

}
