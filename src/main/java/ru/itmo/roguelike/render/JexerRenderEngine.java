package ru.itmo.roguelike.render;

import ru.itmo.roguelike.constants.GameConstants;
import ru.itmo.roguelike.map.NoiseGenerator;
import ru.itmo.roguelike.render.drawable.Drawable;
import ru.itmo.roguelike.render.drawable.DrawableDescriptor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.stream.Collectors;

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
        JFrame frame = new JFrame(GameConstants.WINDOW_TITLE);

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

        int xPos = 0;
        for (int x = (xPos / w / 10); x < width / w / 10 + 1 + (xPos / w / 10); x++) {
            for (int y = 0; y < height / h / 10; y++) {
                generator.generate(x, y, chunk);
                for (int i = 0; i < chunk.length; i++) {
                    for (int j = 0; j < chunk[i].length; j++) {
                        int col = (int) (chunk[i][j] * 255.0f);
                        if (col > 127) {
                            col = (col - 128) * 2;
                            graphics.setColor(new Color(col, col / 2, 0));
                        } else {
                            col *= 2;
                            graphics.setColor(new Color(col / 2, col, 0));
                        }
                        graphics.fillRect(-xPos + 10 * i + w * 10 * x, 10 * j + h * 10 * y, 10, 10);
                    }
                }
            }
        }

        for (DrawableDescriptor drawableDescriptor : Drawable.getRegistry()
                .stream()
                .map((Drawable::draw))
                .collect(Collectors.toList())) {
            graphics.setColor(drawableDescriptor.getColor());
            graphics.fillRect(drawableDescriptor.getX(), drawableDescriptor.getY(), 10, 10);
        }

        bufferStrategy.show();
        graphics.dispose();
    }
}
