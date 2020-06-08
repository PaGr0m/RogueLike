package ru.itmo.roguelike.field;

import ru.itmo.roguelike.characters.Player;
import ru.itmo.roguelike.characters.mobs.Enemy;
import ru.itmo.roguelike.characters.mobs.Slime;
import ru.itmo.roguelike.characters.mobs.Zombie;
import ru.itmo.roguelike.characters.mobs.strategy.CowardlyBehavior;
import ru.itmo.roguelike.characters.mobs.strategy.MobWithTarget;
import ru.itmo.roguelike.characters.mobs.strategy.RandomWalkBehavior;
import ru.itmo.roguelike.items.*;
import ru.itmo.roguelike.utils.IntCoordinate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static ru.itmo.roguelike.field.Spawner.EntityClass.*;

public final class Spawner {
    public static final Map<EntityClass, BiConsumer<Player, IntCoordinate>> spawners = new HashMap<>();

    static {
        spawners.put(ZOMBIE,
                (player, coordinate) -> {
                    Enemy.builder(Zombie::new)
                            .setPosition(coordinate)
                            .setBehavior(MobWithTarget.builder(RandomWalkBehavior::new))
                            .setRadius(10000)
                            .setTarget(player)
                            .createAndRegister();
                }
        );
        spawners.put(SLIME,
                (player, coordinate) -> {
                    Enemy.builder(Slime::new)
                            .setPosition(coordinate)
                            .setBehavior(MobWithTarget.builder(CowardlyBehavior::new))
                            .setRadius(10000)
                            .setTarget(player)
                            .createAndRegister();
                }
        );
        spawners.put(MED_KIT_S, (p, coordinate) -> new MedKitSmall().setPosition(coordinate));
        spawners.put(MED_KIT_B, (p, coordinate) -> new MedKitBig().setPosition(coordinate));
        spawners.put(MED_KIT_M, (p, coordinate) -> new MedKitMedium().setPosition(coordinate));
        spawners.put(TELEPORT, (p, coordinate) -> new Teleport().setPosition(coordinate));
        spawners.put(TUNIC, (p, coordinate) -> new HeavyArmor().setPosition(coordinate));
        spawners.put(JACKET, (p, coordinate) -> new LightArmor().setPosition(coordinate));
        spawners.put(COWL, (p, coordinate) -> new MediumArmor().setPosition(coordinate));
    }

    public enum EntityClass {
        ZOMBIE, SLIME, MED_KIT_S, MED_KIT_M, MED_KIT_B, TELEPORT, TUNIC, JACKET, COWL;
    }
}
