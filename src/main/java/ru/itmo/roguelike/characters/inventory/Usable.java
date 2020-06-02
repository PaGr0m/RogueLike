package ru.itmo.roguelike.characters.inventory;

import org.jetbrains.annotations.NotNull;
import ru.itmo.roguelike.characters.Actor;
import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.attack.FireballAttack;
import ru.itmo.roguelike.characters.attack.SwordAttack;
import ru.itmo.roguelike.characters.projectiles.Fireball;
import ru.itmo.roguelike.items.MedKit;
import ru.itmo.roguelike.items.Teleport;
import ru.itmo.roguelike.utils.FuncUtils;
import ru.itmo.roguelike.utils.FuncUtils.UsableCreator;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

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

    String getSort();

    default void saveToFile(DataOutputStream output) throws IOException {
    }

    Map<String, UsableCreator> creators = collectCreators();
    String NULL_SORT = "NUL";

    static Map<String, UsableCreator> collectCreators() {
        Map<String, UsableCreator> res = new HashMap<>();

        res.put(FireballAttack.SORT, FireballAttack::fromFile);
        res.put(SwordAttack.SORT, SwordAttack::fromFile);
        res.put(MedKit.SORT, MedKit::fromFile);
        res.put(Teleport.SORT, Teleport::fromFile);
        res.put(NULL_SORT, (i, p) -> null);

        return res;
    }

    static void saveToFile(Usable usable, DataOutputStream output) throws IOException {
        String sort = usable == null ? NULL_SORT : usable.getSort();
        output.writeChar(sort.charAt(0));
        output.writeChar(sort.charAt(1));
        output.writeChar(sort.charAt(2));
        if (usable != null) {
            usable.saveToFile(output);
        }
    }

    static Usable readFromFile(DataInputStream input, Player player) throws IOException {
        String sort = String.valueOf(new char[]{input.readChar(), input.readChar(), input.readChar()});
        UsableCreator creator = creators.get(sort);
        return creator.create(input, player);
    }
}
