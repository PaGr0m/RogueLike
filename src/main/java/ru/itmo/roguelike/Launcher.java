package ru.itmo.roguelike;

import com.google.common.util.concurrent.SettableFuture;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;

import static javax.swing.JFileChooser.APPROVE_OPTION;

public class Launcher extends JFrame {

    private final SettableFuture<Optional<Optional<String>>> promise = SettableFuture.create();

    public Launcher() {
        super("LAUNCHER");
        selfBuild();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void selfBuild() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                promise.set(Optional.empty());
                super.windowClosing(windowEvent);
            }
        });
        setResizable(false);

        setSize(200, 100);

        add(BorderLayout.PAGE_START, getSelectMapButton());
        add(BorderLayout.PAGE_END, getRandomMapButton());

        pack();
    }

    public static String showDialogAndGetMapFileName() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));

        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("MAP FILE", "mapfile");
        fc.addChoosableFileFilter(filter);
        fc.setDialogTitle("SELECT MAP FILE");

        int returnCode = fc.showDialog(null, "LOAD");
        if (returnCode == APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    public JButton getRandomMapButton() {
        JButton startRandom = new JButton("Autogenerate map");
        startRandom.setSize(200, 30);

        startRandom.addActionListener(actionEvent -> {
            promise.set(Optional.of(Optional.empty()));
            Launcher.this.close();
        });

        return startRandom;
    }

    public JButton getSelectMapButton() {
        JButton startRandom = new JButton("Select map file");
        startRandom.setSize(200, 30);

        startRandom.addActionListener(actionEvent -> {
            promise.set(Optional.ofNullable(showDialogAndGetMapFileName()).map(Optional::of));
            Launcher.this.close();
        });

        return startRandom;
    }

    private void close() {
        setVisible(false);
        dispose();
    }

    public SettableFuture<Optional<Optional<String>>> getPromise() {
        return promise;
    }
}
