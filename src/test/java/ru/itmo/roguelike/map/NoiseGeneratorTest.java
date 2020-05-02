package ru.itmo.roguelike.map;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.concurrent.TimeUnit;

public class NoiseGeneratorTest {
    private static final int FPS = 25;
    private final int width = 800;
    private final int height = 600;
    private final int w = 10, h = 10;

    private final float[][] chunk = new float[w][h];
    private final Canvas canvas = new Canvas();
    private final NoiseGenerator generator = new NoiseGenerator(w, h);
    private int xPos = 0;
    boolean finish = false;

    @Test
    public void test() throws InterruptedException {
        prepare();

        while (!finish) {
            long startRendering = System.nanoTime();
            render();
            long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startRendering);
            if (durationMs < 1000 / FPS) {
                Thread.sleep(1000 / FPS - durationMs, 0);
            }
        }
    }

    public void prepare() {
        JFrame frame = new JFrame("Interactive Test");

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {  }
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    finish = true;
                }
            }
            @Override
            public void keyReleased(KeyEvent keyEvent) {  }
        });

        canvas.setSize(width, height);
        canvas.setVisible(true);
        canvas.setFocusable(false);

        frame.add(canvas);

        canvas.createBufferStrategy(3);
    }

    private void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        ++xPos;
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

        graphics.setColor(Color.RED);
        graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        graphics.drawString("PRESS ESC TO EXIT", 50, 50);

        bufferStrategy.show();
        graphics.dispose();
    }

}