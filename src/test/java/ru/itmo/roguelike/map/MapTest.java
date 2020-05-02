package ru.itmo.roguelike.map;

import org.junit.Test;
import ru.itmo.roguelike.Tile;
import ru.itmo.roguelike.utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MapTest {
    private static final int FPS = 25;
    private static final int MAX_DURATION_MS = 5000;

    private final int width = 800;
    private final int height = 600;
    private final int w = 5, h = 5;

    private final float[][] chunk = new float[w][h];
    private final Canvas canvas = new Canvas();
    private final Map map = new Map(5, 5, 5, 5, 0, 0);
    private int xPos = 0;
    boolean finish = false;

    @Test
    public void interactiveTest() throws InterruptedException {
        prepare();

        long startTest = System.currentTimeMillis();
        while (!finish && (System.currentTimeMillis() - startTest < MAX_DURATION_MS)) {
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

        map.process(0, 0);
        for (java.util.Map.Entry<Pair<Integer, Integer>, Chunk> chunk : map.getChunks().entrySet()) {
            Tile[][] tiles = chunk.getValue().getTiles();
            int tWidth = tiles.length, tHeight = tiles[0].length;
            for (int i = 0; i < tWidth; i++) {
                for (int j = 0; j < tHeight; j++) {
                    int col = (int) (tiles[i][j].getValue() * 255.0f);
                    if (col > 127) {
                        col = (col - 128) * 2;
                        graphics.setColor(new Color(col, col / 2, 0));
                    } else {
                        col *= 2;
                        graphics.setColor(new Color(col / 2, col, 0));
                    }
                    Pair<Integer, Integer> pos = chunk.getKey();
                    int x = (pos.getFirst() - 5 / 2) * tWidth + i - tWidth + 50;
                    int y = (pos.getSecond() - 5 / 2) * tHeight + j - tHeight + 50;
                    graphics.fillRect(x * w, y * w, w, h);
                }
            }
ma
        }


        graphics.setColor(Color.RED);
        graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        graphics.drawString("PRESS ESC TO EXIT", 50, 50);

        bufferStrategy.show();
        graphics.dispose();
    }

}