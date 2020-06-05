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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * <p>
 * A finite field, loadable from text file.
 * </p>
 * File format:
 * <br/>
 * Single integer on the first line --- field WIDTH.
 * Then lines of length no more than WIDTH, consisting only of characters
 * {@link FiniteField#GRASS_C}('-'), {@link FiniteField#WATER_C}('~')
 * {@link FiniteField#STONE_C}('#'), {@link FiniteField#BADROCK_C}(' ')
 * {@link FiniteField#PLAYER_C}('p'), {@link FiniteField#ZOMBIE_C}('z')
 * {@link FiniteField#SLIME_C}('s').
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
    private final static char GRASS_C = '-';
    private final static char WATER_C = '~';
    private final static char STONE_C = '#';
    private final static char BADROCK_C = ' ';
    private final static char PLAYER_C = 'p';
    private final static char ZOMBIE_C = 'z';
    private final static char SLIME_C = 's';
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
                    result[i] = createTile(BADROCK_C, row, i);
                }

                field[row] = result;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Tile createTile(char c, int x, int y) {
        Tile result;
        switch (c) {
            case BADROCK_C: {
                result = new Tile(-1f);
                break;
            }
            case STONE_C: {
                result = new Tile(0.9f);
                break;
            }
            case WATER_C: {
                result = new Tile(0.2f);
                break;
            }
            case GRASS_C:
            default: {
                result = new Tile(0.5f);
                break;
            }
        }
        result.setXY(x, y);
        return result;
    }

    private void spawnActors(int x, int y, char c) {
        IntCoordinate coordinate = new IntCoordinate(x, y);

        switch (c) {
            case PLAYER_C:
                defaultPlayerPos = coordinate;
                break;
            case ZOMBIE_C:
                Enemy.builder(Zombie::new).setPosition(coordinate)
                        .setRadius(1000).setTarget(player)
                        .setBehavior(MobWithTarget.builder(AggressiveBehavior::new)).build();
                break;
            case SLIME_C:
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
