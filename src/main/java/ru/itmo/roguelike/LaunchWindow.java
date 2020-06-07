package ru.itmo.roguelike;

import com.google.common.util.concurrent.SettableFuture;
import ru.itmo.roguelike.utils.NullableRequest;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static javax.swing.JFileChooser.APPROVE_OPTION;

public class LaunchWindow extends JFrame {

    private final SettableFuture<NullableRequest<String>> promise = SettableFuture.create();

    public static LaunchWindow createAndShow(Configuration configuration) {
        return new LaunchWindow(configuration);
    }

    private LaunchWindow(Configuration configuration) {
        super("ROGUELIKE");
        selfBuild(configuration);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void selfBuild(Configuration configuration) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                promise.set(new NullableRequest<>());
                super.windowClosing(windowEvent);
            }
        });
        setResizable(false);

        setSize(200, 100);

        add(BorderLayout.PAGE_START, getOtherButton(configuration));
        add(BorderLayout.PAGE_END, getFileChooserButton(configuration));

        pack();
    }

    public static String showDialogAndGetFileName(Configuration configuration) {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));

        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(configuration.getFilter());
        fc.setDialogTitle(configuration.getFileChooserCaption());

        int returnCode = fc.showDialog(null, configuration.getFileChooserAcceptText());
        if (returnCode == APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    public JButton getOtherButton(Configuration configuration) {
        JButton startRandom = new JButton(configuration.getOtherBtnText());
        startRandom.setSize(200, 30);

        startRandom.addActionListener(actionEvent -> {
            promise.set(new NullableRequest<>(null));
            LaunchWindow.this.close();
        });

        return startRandom;
    }

    public JButton getFileChooserButton(Configuration configuration) {
        JButton startRandom = new JButton(configuration.getFileChooserBtnText());
        startRandom.setSize(200, 30);

        startRandom.addActionListener(actionEvent -> {
            promise.set(new NullableRequest<>(showDialogAndGetFileName(configuration)));
            LaunchWindow.this.close();
        });

        return startRandom;
    }

    private void close() {
        setVisible(false);
        dispose();
    }

    public Optional<Optional<String>> getPromiseAsOptional() {
        try {
            return promise.get().toOptional();
        } catch (InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
    }

    public static class Configuration {
        private final String otherBtnText;
        private final String fileChooserBtnText;
        private final String fileChooserCaption;
        private final String fileChooserAcceptText;
        private final FileNameExtensionFilter filter;

        public Configuration(
                String otherBtnText,
                String fileChooserBtnText,
                String fileChooserCaption,
                String fileChooserAcceptText,
                FileNameExtensionFilter filter
        ) {
            this.otherBtnText = otherBtnText;
            this.fileChooserBtnText = fileChooserBtnText;
            this.fileChooserCaption = fileChooserCaption;
            this.fileChooserAcceptText = fileChooserAcceptText;
            this.filter = filter;
        }

        public String getOtherBtnText() {
            return otherBtnText;
        }

        public String getFileChooserBtnText() {
            return fileChooserBtnText;
        }

        public String getFileChooserCaption() {
            return fileChooserCaption;
        }

        public String getFileChooserAcceptText() {
            return fileChooserAcceptText;
        }

        public FileNameExtensionFilter getFilter() {
            return filter;
        }
    }
}
