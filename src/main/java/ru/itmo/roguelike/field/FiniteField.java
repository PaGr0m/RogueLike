package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static ru.itmo.roguelike.field.FiniteField.TileSymbol.*;


/**
 * <p>
 * A finite field, loadable from text file.
 * </p>
 * File format:
 * <br/>
 * Single integer on the first line --- field WIDTH.
 * Then lines of length no more than WIDTH, consisting only of characters
 * {@link TileSymbol#GRASS}('-'), {@link TileSymbol#WATER}('~')
 * {@link TileSymbol#STONE}('#'), {@link TileSymbol#BEDROCK}(' ')
 * {@link TileSymbol#PLAYER}('p'), {@link TileSymbol#ZOMBIE}('z')
 * {@link TileSymbol#SLIME}('s').
 * <p>
 * Example:
 *        <table>
 *        <tr> <td>#</td><td>#</td><td>#</td><td>#</td><td>#</td><td> </td><td> </td><td> </td><td> </td>  </tr>
 *        <tr> <td>#</td><td>p</td><td>-</td><td>s</td><td>#</td><td> </td><td> </td><td> </td><td> </td> </tr>
 *        <tr> <td>#</td><td>-</td><td>-</td><td>z</td><td>#</td><td> </td><td> </td><td> </td><td> </td> </tr>
 *        <tr> <td>#</td><td>-</td><td>-</td><td>-</td><td>#</td><td> </td><td> </td><td> </td><td> </td> </tr>
 *        <tr> <td>#</td><td>-</td><td>-</td><td>-</td><td>#</td><td>#</td><td>#</td><td>#</td><td>#</td> </tr>
 *        <tr> <td>#</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>#</td> </tr>
 *        <tr> <td>#</td><td>z</td><td>~</td><td>~</td><td>-</td><td>-</td><td>s</td><td>-</td><td>#</td> </tr>
 *        <tr> <td>#</td><td>-</td><td>~</td><td>~</td><td>-</td><td>-</td><td>-</td><td>-</td><td>#</td> </tr>
 *        <tr> <td>#</td><td>#</td><td>#</td><td>#</td><td>#</td><td>#</td><td>#</td><td>#</td><td>#</td> </tr>
 *        </table>
 * </p>
 */
public class FiniteField implements Field {
    private final Player player;
    private final Map<Spawner.EntityClass, List<IntCoordinate>> defaultEntities = new HashMap<>();
    private final List<Tile> stones = new ArrayList<>();
    private Tile[][] field;
    private IntCoordinate defaultPlayerPos;

    /**
     * Read field from a file
     */
    public FiniteField(Path file, Player player) {
        this.player = player;

        try (
                BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))
        ) {
            final int width = Integer.parseInt(reader.readLine());

            final List<String> lines = reader.lines().collect(Collectors.toList());
            field = new Tile[lines.size()][width];

            for (int row = 0; row < lines.size(); row++) {
                Tile[] result = new Tile[width];

                String s = lines.get(row);
                for (int column = 0; column < s.length(); column++) {
                    TileSymbol ts = TileSymbol.fromChar(s.charAt(column));
                    result[column] = createTile(ts, row, column);
                    if (ts == STONE) {
                        stones.add(result[column]);
                    }
                    updateActors(result[column].getX(), result[column].getY(), ts);
                }

                for (int i = s.length(); i < width; i++) {
                    result[i] = createTile(BEDROCK, row, i);
                }

                field[row] = result;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Tile createTile(TileSymbol ts, int x, int y) {
        Tile result = new Tile(ts.value);
        result.setXY(x, y);
        return result;
    }

    private void updateActors(int x, int y, TileSymbol symbol) {
        IntCoordinate coordinate = new IntCoordinate(x, y);

        if (symbol == PLAYER) {
            defaultPlayerPos = coordinate;
        }

        symbol.getEntityClass().ifPresent(
                cls -> {
                    defaultEntities.putIfAbsent(cls, new ArrayList<>());
                    defaultEntities.get(cls).add(coordinate);
                }
        );
    }

    @Override
    public void reInit(IntCoordinate coordinate) {
    }

    @Override
    public void resetEntities() {
        defaultEntities.forEach((cls, poss) -> poss.forEach(pos -> Spawner.spawners.get(cls).accept(player, pos)));
        stones.forEach(b -> b.reInit(STONE.value));
    }

    @Override
    public Optional<Tile> getTile(IntCoordinate coordinate) {
        int xIdx = coordinate.getX() / Tile.WIDTH_IN_PIX;
        int yIdx = coordinate.getY() / Tile.HEIGHT_IN_PIX;

        if (xIdx < 0 || xIdx >= field.length || yIdx < 0 || yIdx >= field[0].length) {
            return Optional.empty();
        }
        return Optional.of(field[xIdx][yIdx]);
    }

    @Override
    public void process(IntCoordinate centerCoordinate) {
    }

    @Override
    public void setDefaultPosToPlayer(Player p) {
        p.setCoordinate(defaultPlayerPos);
    }

    public enum TileSymbol {
        GRASS('-', 0.5f),
        WATER('~', 0.2f),
        STONE('#', 0.9f),
        BEDROCK(' ', -1f),
        PLAYER('p', 0.5f),
        ZOMBIE('z', 0.5f),
        SLIME('s', 0.5f),
        MEDKIT_SMALL('n', 0.5f),
        MEDKIT_MEDIUM('m', 0.5f),
        MEDKIT_BIG('M', 0.5f),
        ARMOR_SMALL('4', 0.5f),
        ARMOR_MEDIUM('a', 0.5f),
        ARMOR_HEAVY('A', 0.5f),
        TELEPORT('*', 0.5f);

        private final char symbol;
        private final float value;

        TileSymbol(char symbol, float value) {
            this.symbol = symbol;
            this.value = value;
        }

        public static TileSymbol fromChar(char symbol) {
            for (TileSymbol ts : values()) {
                if (ts.symbol == symbol) {
                    return ts;
                }
            }
            return BEDROCK;
        }

        public Optional<Spawner.EntityClass> getEntityClass() {
            switch (this) {
                case ZOMBIE:
                    return Optional.of(Spawner.EntityClass.ZOMBIE);
                case SLIME:
                    return Optional.of(Spawner.EntityClass.SLIME);
                case MEDKIT_SMALL:
                    return Optional.of(Spawner.EntityClass.MED_KIT_S);
                case MEDKIT_MEDIUM:
                    return Optional.of(Spawner.EntityClass.MED_KIT_M);
                case MEDKIT_BIG:
                    return Optional.of(Spawner.EntityClass.MED_KIT_B);
                case ARMOR_SMALL:
                    return Optional.of(Spawner.EntityClass.JACKET);
                case ARMOR_MEDIUM:
                    return Optional.of(Spawner.EntityClass.COWL);
                case ARMOR_HEAVY:
                    return Optional.of(Spawner.EntityClass.TUNIC);
                case TELEPORT:
                    return Optional.of(Spawner.EntityClass.TELEPORT);
                default:
                    return Optional.empty();
            }
        }
    }
}
