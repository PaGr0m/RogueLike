package ru.itmo.roguelike.render;

import ru.itmo.roguelike.settings.GameSettings;
import ru.itmo.roguelike.manager.uimanager.UIManager;
import ru.itmo.roguelike.map.NoiseGenerator;
import ru.itmo.roguelike.render.drawable.Drawable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class JexerRenderEngine implements RenderEngine {
    private final int width;
    private final int height;

    private final Canvas canvas = new Canvas();
    private final KeyListener keyListener;

    public JexerRenderEngine(int width, int height, KeyListener keyListener) {
        this.width = width;
        this.height = height;
        this.keyListener = keyListener;

        prepare();
    }

    private void prepare() {
        JFrame frame = new JFrame(GameSettings.WINDOW_TITLE);

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addKeyListener(keyListener);

        canvas.setSize(width, height);
        canvas.setVisible(true);
        canvas.setFocusable(false);

        frame.add(canvas);

        canvas.createBufferStrategy(3);
    }

    final int w = 10;
    final int h = 10;
    final float[][] chunk = new float[w][h];
    final NoiseGenerator generator = new NoiseGenerator(w, h);

    @Override
    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.fillRect(0, 0, 800, 600); // FIXme: set real w/h

        Drawable.getRegistry()
                .stream()
                .peek(Drawable::draw)
                .map((Drawable::getDrawableDescriptor))
                .forEach(drawableDescriptor -> {
                    graphics.setColor(drawableDescriptor.getColor());
                    graphics.fillRect(drawableDescriptor.getX(), drawableDescriptor.getY(), 10, 10);
                });

        UIManager.addStatusBar(graphics);

        bufferStrategy.show();
        graphics.dispose();
    }
}
