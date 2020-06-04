package ru.itmo.roguelike.render;

import ru.itmo.roguelike.ioc.IOModule;
import ru.itmo.roguelike.manager.uimanager.UIManager;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.render.particles.Particle;
import ru.itmo.roguelike.settings.GameSettings;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

@Singleton
public class JexerRenderEngine implements RenderEngine {
    private final int width;
    private final int height;
    private final Camera camera;
    private final KeyListener keyListener;
    private final BufferStrategy bufferStrategy;
    private final UIManager uiManager;

    @Inject
    public JexerRenderEngine(@IOModule.DefaultInputHandler KeyListener keyListener,
                             Camera camera,
                             UIManager uiManager) {
        this(GameSettings.WINDOW_WIDTH, GameSettings.WINDOW_HEIGHT, keyListener, camera, uiManager);
    }

    public JexerRenderEngine(int width, int height, KeyListener keyListener, Camera camera, UIManager uiManager) {
        this.width = width;
        this.height = height;
        this.keyListener = keyListener;
        this.camera = camera;
        this.uiManager = uiManager;

        bufferStrategy = prepareCanvasAndGetBufferStrategy();
    }

    /**
     * Set up for render
     */
    private BufferStrategy prepareCanvasAndGetBufferStrategy() {
        JFrame frame = new JFrame(GameSettings.WINDOW_TITLE);

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addKeyListener(keyListener);

        Canvas canvas = new Canvas();
        canvas.setSize(width, height);
        canvas.setVisible(true);
        canvas.setFocusable(false);

        frame.add(canvas);

        canvas.createBufferStrategy(3);
        return canvas.getBufferStrategy();
    }

    private void renderFrame(Graphics2D graphics) {
        graphics.fillRect(0, 0, width, height);

        for (Drawable drawable : Drawable.getBackgroundRegistry()) {
            drawable.draw(graphics, camera);
        }

        for (Drawable drawable : Drawable.getRegistry()) {
            drawable.draw(graphics, camera);
        }

        Particle.deleteOld();
        uiManager.renderStatusBar(graphics);
    }

    @Override
    public void render() {
        Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        renderFrame(graphics);
        graphics.dispose();
        bufferStrategy.show();
    }

    @Override
    public void renderPause() {
        Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        renderFrame(graphics);
        uiManager.drawPauseText(graphics);
        graphics.dispose();
        bufferStrategy.show();
    }
}
