package ru.itmo.roguelike.characters.inventory;

import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.attack.Attack;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * All items that can be used by some actor
 */
public interface Usable {
    static void renderImageInInventory(Graphics2D graphics, int x, int y, int width, int height, Image image) {
        float prop = (float) image.getWidth(null) / image.getHeight(null);
        int expectedWidth = (int) (prop * height);
        int expectedHeight = (int) (width / prop);

        if (expectedHeight > height) {
            x += (width - expectedWidth) / 2;
            expectedHeight = height;
        } else {
            y += (height - expectedHeight) / 2;
            expectedWidth = width;
        }
        graphics.drawImage(image, x, y, expectedWidth, expectedHeight, null);
    }

    /**
     * Activates effect of usage when used by specified actor.
     */
    void use(Actor actor);

    /**
     * Some usable items may be used only once, another – limited amount of time, and the other – infinitely.
     *
     * @return {@code true} if this item is still may be used.
     */
    boolean isUsed();

    /**
     * Render object picture in inventory
     *
     * @param graphics graphics2D object from UIManager
     * @param x        x coordinate of left upper corner of inventory cell
     * @param y        y coordinate of left upper corner of inventory cell
     * @param width    width of inventory cell
     * @param height   height of inventory cell
     */
    void renderInInventory(Graphics2D graphics, int x, int y, int width, int height);

    Sign getSign();

    default void saveToFile(DataOutputStream output) throws IOException {
        getSign().saveToFile(output);
    }

    static Usable readFromFile(DataInputStream input, Player player) throws IOException {
        Sign sign = Sign.readFromFile(input);
        System.out.println(sign);
        return sign.getSupplier().apply(input, player);
    }

    class Sign {
        private final static Map<Sign, BiFunction<DataInputStream, Player, Usable>> creators = new HashMap<>();
        private final String word;

        public Sign(String word) {
            this.word = word;
        }

        public Sign(String word, BiFunction<DataInputStream, Player, Usable> creator) {
            this(word);
            creators.put(this, creator);
        }

        public void saveToFile(DataOutputStream output) throws IOException {
            output.writeChar(word.charAt(0));
            output.writeChar(word.charAt(1));
            output.writeChar(word.charAt(2));
        }

        public static Sign readFromFile(DataInputStream input) throws IOException {
            return new Sign(String.valueOf(new char[]{
                    input.readChar(), input.readChar(), input.readChar()
            }));
        }

        public BiFunction<DataInputStream, Player, Usable> getSupplier() {
            return creators.get(this);
        }

        @Override
        public String toString() {
            return "Sign{" +
                    "word='" + word + '\'' +
                    '}';
        }

        @Override
        public int hashCode() {
            return word.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Sign)) return false;
            return word.equals(((Sign) o).word);
        }
    }
}
