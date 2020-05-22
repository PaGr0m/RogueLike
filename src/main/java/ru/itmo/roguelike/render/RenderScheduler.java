package ru.itmo.roguelike.render;


import ru.itmo.roguelike.manager.gamemanager.GameManager;

import java.util.TimerTask;

public class RenderScheduler extends TimerTask {
    private final GameManager gameManager;

    public RenderScheduler(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (gameManager.isGameRunning()) {
            gameManager.step();
        }
    }
}
