package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.mobs.Slime;
import ru.itmo.roguelike.characters.mobs.Zombie;
import ru.itmo.roguelike.characters.mobs.strategy.AggressiveBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.CowardlyBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.itmo.roguelike.field.FiniteField.TileSymbol.BEDROCK;


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
    public enum TileSymbol {
        GRASS('-', 0.5f),
        WATER('~', 0.2f),
        STONE('#', 0.9f),
        BEDROCK(' ', -1f),
        PLAYER('p', 0.5f),
        ZOMBIE('z', 0.5f),
        SLIME('s', 0.5f);

        TileSymbol(char symbol, float value) {
            this.symbol = symbol;
            this.value = value;
        }

        private final char symbol;
        private final float value;

        public static TileSymbol fromChar(char symbol) {
            switch (symbol) {
                case '-':
                    return GRASS;
                case '~':
                    return WATER;
                case '#':
                    return STONE;
                case 'p':
                    return PLAYER;
                case 'z':
                    return ZOMBIE;
                case 's':
                    return SLIME;
                default:
                    return BEDROCK;
            }
        }
    }

    private final Player player;
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
                    char c = s.charAt(column);
                    result[column] = createTile(c, row, column);
                    spawnActors(result[column].getX(), result[column].getY(), c);
                }

                for (int i = s.length(); i < width; i++) {
                    result[i] = createTile(BEDROCK.symbol, row, i);
                }

                field[row] = result;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Tile createTile(char c, int x, int y) {
        Tile result = new Tile(TileSymbol.fromChar(c).value);
        result.setXY(x, y);
        return result;
    }

    private void spawnActors(int x, int y, char c) {
        IntCoordinate coordinate = new IntCoordinate(x, y);
        TileSymbol symbol = TileSymbol.fromChar(c);

        switch (symbol) {
            case PLAYER:
                defaultPlayerPos = coordinate;
                break;
            case ZOMBIE:
                Enemy.builder(Zombie::new).setPosition(coordinate)
                        .setRadius(1000).setTarget(player)
                        .setBehavior(MobWithTarget.builder(AggressiveBehavior::new)).build();
                break;
            case SLIME:
                Enemy.builder(Slime::new).setPosition(coordinate)
                        .setRadius(1000).setTarget(player)
                        .setBehavior(MobWithTarget.builder(CowardlyBehavior::new)).build();
                break;
            default:
        }
    }

    @Override
    public void reInit(IntCoordinate coordinate) {
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
}
