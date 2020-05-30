package ru.itmo.roguelike.render;

public interface RenderEngine {
    /**
     * The main function for all render engines. Subsequent calls of this function should render one frame each,
     * respectively. This function should be optimized as much as possible to be capable of being called
     * {@link ru.itmo.roguelike.settings.GameSettings#FPS} times per second.
     */
    void render();
}
