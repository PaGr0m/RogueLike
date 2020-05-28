package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.mobs.Slime;
import ru.itmo.roguelike.characters.mobs.Zombie;
import ru.itmo.roguelike.characters.mobs.strategy.AggressiveBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.CowardlyBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.io.*;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

public class FiniteField implements Field {
    private Tile[][] field;
    private IntCoordinate defaultPlayerPos;

    public FiniteField(Path file) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))
        ) {

            field = reader.lines().map(new Function<String, Tile[]>() {
                int row = 0;

                @Override
                public Tile[] apply(String s) {
                    Tile[] result = new Tile[s.length()];

                    for (int column = 0; column < s.length(); column++) {
                        char c =  s.charAt(column);

                        if (!Character.isSpaceChar(c)) {
                            result[column] = getTile(c);
                            result[column].setXY(row, column);

                            spawnActors(result[column].getX(), result[column].getY(), c);
                        }
                    }
                    row++;

                    return result;
                }
            }).toArray(Tile[][]::new);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Tile getTile(char c) {
        return new Tile(c == '#' ? 0.9f : c == '~' ? 0.2f : 0.5f);
    }

    private void spawnActors(int x, int y, char c) {
        IntCoordinate coordinate = new IntCoordinate(x, y);

        switch (c) {
            case 'p':
                defaultPlayerPos = coordinate;
                break;
            case 'z':
                Enemy.builder(Zombie::new).setPosition(coordinate)
                        .setRadius(1000) // .setTarget(Player.getPlayer())
                        .setBehavior(MobWithTarget.builder(AggressiveBehavior::new)).build();
                break;
            case 's':
                Enemy.builder(Slime::new).setPosition(coordinate)
                        .setRadius(1000) // .setTarget(Player.getPlayer())
                        .setBehavior(MobWithTarget.builder(CowardlyBehavior::new)).build();
                break;
            default:
        }
    }

    @Override
    public void reInit(int posX, int posY) {
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
