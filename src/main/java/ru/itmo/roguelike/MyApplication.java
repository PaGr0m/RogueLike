package ru.itmo.roguelike;

import jexer.TApplication;

public class MyApplication extends TApplication {

    public MyApplication() throws Exception {
        super(BackendType.SWING);

        // Create standard menus for Tool, File, and Window.
        addToolMenu();
        addFileMenu();
        addWindowMenu();
    }

    public static void main(String [] args) throws Exception {
        MyApplication app = new MyApplication();
        app.run();
    }
}
